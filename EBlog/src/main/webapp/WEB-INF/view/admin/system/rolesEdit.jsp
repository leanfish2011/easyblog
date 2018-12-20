<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色管理</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body>
	<div id="tb" style="height: auto">
		<a href="${ctxPath}/admin/role/index.do" class="easyui-linkbutton" data-options="iconCls:'icon-blur',plain:true">列表</a> 
		<a <c:choose><c:when test="${empty roleDTO}">style="display: none;"</c:when></c:choose> href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRole()">刪除</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="save()">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="clearForm()">重置</a>
	</div>
	<form id="roleForm" enctype="multipart/form-data">
	    <input id="roleId" type="hidden" name="id" value="${roleDTO.id}" />
		<table cellpadding="5">
			<tr>
				<td>角色名称:</td>
				<td><input id="roleName" class="easyui-validatebox tb" type="text" name="roleName" data-options="required:true,validateOnCreate:false,validateOnBlur:true" value="${roleDTO.roleName}"></input></td>
			</tr>
			<tr>
				<td>备注:</td>
				<td><input id="remark" class="easyui-validatebox" type="text" name="remark" value="${roleDTO.remark}"></input></td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">

	//保存前，各个内容是否为空校验
	function validInput() {
		var vroleName = $("#roleName").val();

		if (vroleName == "") {
			$.messager.alert('提醒', '请输入角色名称！');
			
			return false;
		}

		return true;
	}

	//重置
	function clearForm() {
		$('#roleForm').form('clear');
	}

	//保存
	function save() {
		//id为0、1的账号为系统预置角色，不能修改
		var roleId = $("#roleId").val();
		if (roleId == "0" || roleId == "1") {
			$.messager.alert('提醒', '不能修改系统预置角色！');
			
			return;
		}
		
		var passInput = validInput();
		if (passInput) {
			var params = new FormData($("#roleForm")[0]);//使用FormData接收数据
			var strUrl = "";
			if ($("#roleId").val() != null && $("#roleId").val() != "") {
				strUrl = getContextPath() + '/admin/role/updateRole.do';
			} else {
				strUrl = getContextPath() + '/admin/role/addRole.do';
			}

			$.ajax({
				type : 'POST',
				url : strUrl,
				data : params,
				dataType : 'json',
				processData : false,//使用FormData这个必须加上
				contentType : false,//使用FormData这个必须加上
				success : function(data) {
					if (data && data.success == "true") {
						$.messager.alert("成功", data.content, "info");
						
						location.href = "${ctxPath}/admin/role/index.do";
					} else {
						$.messager.alert("失败", data.content, "info");
					}
				},
				error : function() {
					$.messager.alert("错误", "管理角色发生网络异常！", "error");
				}
			});
		}
	}

	//删除选择的角色
	function deleteRole() {
		var roleId = $("#roleId").val();//获取待删除的角色id
		if (roleId != null && roleId != "") {
			$.messager.confirm('确认', '确定删除？', function(r) {
				if (r) {
					//id为0、1的账号为系统预置角色，不能删除
					if (roleId == "0" || roleId == "1") {
						$.messager.alert('提醒', '不能删除系统预置角色！');
						return;
					} 

					//调用删除
					var param = {
						"roleId" : roleId
					};
					$.ajax({
						type : 'GET',
						contentType : 'application/json',
						url : '${ctxPath}/admin/role/deleteRole.do',
						dataType : 'json',
						data : param,
						success : function(data) {
							if (data && data.success == "true") {
								$.messager.alert("成功", "角色删除成功！", "info");
							} else {
								$.messager.alert("失败", "角色删除失败！");
							}
							
							location.href = "${ctxPath}/admin/role/index.do";//返回到列表页
						},
						error : function() {
							$.messager.alert("错误", "删除角色发生网络异常！", "error");
						}
					})
				}
			});
		} else {
			$.messager.alert('提醒', '请选择需要删除的角色！', 'warning');
		}
	}
</script>
</html>