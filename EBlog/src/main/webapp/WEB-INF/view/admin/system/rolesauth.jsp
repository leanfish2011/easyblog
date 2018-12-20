<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色权限管理</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body class="easyui-layout">
	<input type="hidden" id="currentUserId" />
	<div data-options="region:'north',border:false" style="height:25px;">
		<div id="tb" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="saveRoleAuth()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="cancel()">还原</a> 
		</div>
	</div>
	<div data-options="region:'west',split:true" title="角色列表" style="width:300px;">
		<table id="rolesDataGrid"></table>
	</div>
	<div data-options="region:'center',iconCls:'icon-man'" title="角色权限">
		 <ul id="roleAuthDataTree"></ul>
	</div>
</body>
<script type="text/javascript">
	
	//还原到提交前
	function cancel(){
		var checkedRole = $('#rolesDataGrid').datagrid('getChecked');//获取选择的角色
		if (checkedRole.length == 0) {
			$.messager.alert('提醒', '请选择角色！');
			
			return;
		}
		
		getRoleAuth(checkedRole[0].id);//重新刷新
	}
	
	//增加权限到角色中
	function saveRoleAuth(){
		var checkedRole = $('#rolesDataGrid').datagrid('getChecked');//获取选择的角色
		if (checkedRole.length == 0) {
			$.messager.alert('提醒', '请选择角色！');
			
			return;
		}

		if (checkedRole[0].id == "0"|| checkedRole[0].id == "1") {
			$.messager.alert('提醒', '不能修改系统预置角色权限！');
			
			return;
		}
		
		var selectedAuths=[];
		var selectednodes = $('#roleAuthDataTree').tree('getChecked', ['checked','indeterminate']);//获取选中和有子节点选中的节点
		if (selectednodes.length > 0) {
			for(var i=0; i<selectednodes.length; i++){
				selectedAuths.push(selectednodes[i].id);
			}

			var param = {
					"roleId" : checkedRole[0].id,
					"authIds" : selectedAuths.join(",")
				};
			
			$.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : '${ctxPath}/admin/roleAuth/addRoleAuths.do',
				dataType : 'json',
				data : param,
				success : function(data) {
					if (data && data.success == "true") {
						$.messager.alert("成功", "增加角色权限成功！", "info");
					} else {
						$.messager.alert("失败", "增加角色权限失败！");
					}
					
					getRoleAuth(checkedRole[0].id);//重新刷新
				},
				error : function() {
					$.messager.alert("错误", "增加角色权限发生网络异常！", "error");
				}
			});
		}

	}
	
	//加载选中角色下权限
	function getRoleAuth(roleId) {
		//先清除所有选中
		var node = $('#roleAuthDataTree').tree('find', 0);
		$('#roleAuthDataTree').tree('uncheck', node.target);
		
		var param = {
				"roleId" : roleId
			};
		
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : '${ctxPath}/admin/roleAuth/getMenuByRoleId.do',
			dataType : 'json',
			data : param,
			success : function(data) {
				if (data && data.success == "true") {
					$.each(data.data, function(i, item) {
						//选中当前用户下的权限
						var node = $('#roleAuthDataTree').tree('find', item.id);
						if ($('#roleAuthDataTree').tree('isLeaf', node.target)) {
							//只有叶子节点才选中，避免选中了父级节点，导致子节点都选中
							$('#roleAuthDataTree').tree('check', node.target);
						}
						
					})
				}
			},
			error : function() {
				$.messager.alert("错误", "获取角色权限发生网络异常！", "error");
			}
		})
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
					getRoleAuth(rowData.id);
				}
			});
			
			//加载权限
			$('#roleAuthDataTree').tree({
				url:'${ctxPath}/admin/roleAuth/loadAllMenus.do',
				checkbox : true,
				animate : true,
				lines : true
			});
			
	});
</script>
</html>