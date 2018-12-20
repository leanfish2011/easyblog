<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户角色管理</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body class="easyui-layout">
	<input type="hidden" id="currentUserId" />
	<div data-options="region:'north',border:false" style="height:25px;">
		<div id="tb" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRoleUser()">增加</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeRoleUser()">移除</a> 
		</div>
	</div>
	<div data-options="region:'west',split:true" title="角色列表" style="width:200px;">
		<table id="rolesDataGrid"></table>
	</div>
	<div data-options="region:'center',iconCls:'icon-man'" title="角色用户">
		<table id="roleUsersDataGrid"></table>	
	</div>
	<div data-options="region:'east',iconCls:'icon-man',split:true" title="用户列表" style="width:300px;">
		<table id="usersDataGrid"></table>
	</div>
</body>
<script type="text/javascript">
	
	//增加用户到角色中
	function addRoleUser(){
		var checkedRole = $('#rolesDataGrid').datagrid('getChecked');//获取选择的角色
		if (checkedRole.length == 0) {
			$.messager.alert('提醒', '请选择角色！');
			
			return;
		}
		
		var checkedUsers = $('#usersDataGrid').datagrid('getChecked');//获取选择的用户
		if (checkedUsers.length==0) {
			$.messager.alert('提醒', '请选择用户！');
			
			return;
		}
		
		var selectedRoleId = checkedRole[0].id;//角色只能选择一个
		var selectedUserIds = [];//获取选择的用户id
		for (var i = 0; i < checkedUsers.length; i++) {
			selectedUserIds.push(checkedUsers[i].id);//获取用户id
		}
		
		//将选择的多个用户加入到一个角色中
		var param = {
				"roleId" : selectedRoleId,
				"userIds" : selectedUserIds.join(",")
			};
		
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : '${ctxPath}/admin/roleUser/addRoleUser.do',
			dataType : 'json',
			data : param,
			success : function(data) {
				if (data && data.success == "true") {
					$.messager.alert("成功", "新增用户成功！", "info");
				} else {
					$.messager.alert("失败",data.content);
				}
				
				$('#roleUsersDataGrid').datagrid('reload');//重新刷新
			},
			error : function() {
				$.messager.alert("错误", "增加用户发生网络异常！", "error");
			}
		})
	}
	
	//从角色中移除用户
	function removeRoleUser() {
		var checkedRole = $('#rolesDataGrid').datagrid('getChecked');//获取选择的角色
		if (checkedRole.length == 0) {
			$.messager.alert('提醒', '请选择角色！');
			
			return;
		}
		
		var checkedRoleUsers = $('#roleUsersDataGrid').datagrid('getChecked');//获取选择的用户
		if (checkedRoleUsers.length==0) {
			$.messager.alert('提醒', '请选择角色用户！');
			
			return;
		}
		$.messager.confirm('确认', '确定移除？', function(r) {
			if (r) {
				var selectedRoleId = checkedRole[0].id;//角色只能选择一个
				var selectedRoleUserIds = [];//获取选择的用户编码
				for (var i = 0; i < checkedRoleUsers.length; i++) {
					if ((selectedRoleId=="0" && checkedRoleUsers[i].id=="0")||(selectedRoleId=="1" && checkedRoleUsers[i].id=="1")) {
						$.messager.alert('提醒', '不能移除系统预置账号！');
						
						return;
					}else{
						selectedRoleUserIds.push(checkedRoleUsers[i].id);//获取用户编码
					}
				}
				
				//从选择的一个角色中，移除多个用户
				var param = {
						"roleId" : selectedRoleId,
						"userIds" : selectedRoleUserIds.join(",")
					};
				
				$.ajax({
					type : 'GET',
					contentType : 'application/json',
					url : '${ctxPath}/admin/roleUser/removeRoleUser.do',
					dataType : 'json',
					data : param,
					success : function(data) {
						if (data && data.success == "true") {
							$.messager.alert("成功", "移除用户成功！", "info");
						} else {
							$.messager.alert("失败", "移除用户失败！");
						}
						
						$('#roleUsersDataGrid').datagrid('reload');//重新刷新
					},
					error : function() {
						$.messager.alert("错误", "移除用户发生网络异常！", "error");
					}
				});
			}});
	}
	
	//加载选中角色下用户列表
	function getRoleUsers(roleId) {
		$('#roleUsersDataGrid').datagrid({
			url : '${ctxPath}/admin/roleUser/getRoleUser.do?roleId=' + roleId,
			rownumbers : true,
			fitColumns : true,
			fit : true,
			columns : [[
					{
						checkbox : true,
					},
					{
						field : 'userCode',
						title : '用户名',
						width : 100,
						align : 'center'
					},
					{
						field : 'userName',
						title : '姓名',
						width : 100,
						align : 'center'
					}]]
		});
	}
	
	$(document).ready(
		function() {
			//加载角色列表
			$('#rolesDataGrid').datagrid({
				url : '${ctxPath}/admin/role/searchRole.do',
				rownumbers : true,
				fitColumns : true,
				fit : true,
				singleSelect : true,
				columns : [[
					    {
							field : 'roleName',
							title : '角色名称',
							width : 100,
							align : 'center',
							formatter : function(value, row,index) {
								return "<a href='#'>" + row.roleName + "</a>";
							}
						}]],
				onClickRow: function(rowIndex, rowData){
					getRoleUsers(rowData.id);
				}
			});
			
			//加载用户列表
			$('#usersDataGrid').datagrid({
				url : '${ctxPath}/admin/user/searchUser.do',
				rownumbers : true,
				fitColumns : true,
				fit : true,
				columns : [[
						{
							checkbox : true,
						},
						{
							field : 'userCode',
							title : '用户名',
							width : 100,
							align : 'center'
						},
						{
							field : 'userName',
							title : '姓名',
							width : 100,
							align : 'center'
						}]]
			});
	});
</script>
</html>