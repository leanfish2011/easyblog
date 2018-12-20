<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色管理</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body>
	<div style="height: 100%; width: 100%;">
		<div id="tb" style="height: auto">
			<a href="${ctxPath}/admin/role/add.do" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"><spring:message code="add"/></a> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRole()">刪除</a> <a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search',plain:true" onclick="showSearchWin()">查找</a>
		</div>
		<table id="rolesDataGrid"></table>
		<input type="hidden" id="currentUserId" />
		<div id="searchRoleWin" class="easyui-window" title="查找角色" data-options="modal:true,closed:true,iconCls:'icon-search',minimizable:false,maximizable:false,collapsible:false" style="width: 450px; height: 300px; padding: 10px 50px 20px 50px">
			<table cellpadding="5">
				<tr>
					<td>角色名称:</td>
					<td><input id="roleName" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" type="text" name="roleName"></input></td>
				</tr>
			</table>
			<div style="text-align: center; padding: 5px">
				<a id="btnSearch" href="javascript:void(0)" class="easyui-linkbutton">查找</a> <a id="btnReset"  href="javascript:void(0)" class="easyui-linkbutton">重置</a>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//弹出搜索框
	function showSearchWin() {
		$('#searchRoleWin').window('open');
	}

	//删除选择的角色
	function deleteRole() {
		var checkedRows = $('#rolesDataGrid').datagrid('getChecked');//获取选择的角色
		if (checkedRows.length > 0) {
			$.messager.confirm('确认', '确定删除？', function(r) {
				if (r) {
					var deleteIds = [];//获取待删除的角色id
					for (var i = 0; i < checkedRows.length; i++) {
						//id为0、1的账号为系统预置角色，不能删除
						if (checkedRows[i].id == "0"|| checkedRows[i].id == "1") {
							$.messager.alert('提醒', checkedRows[i].roleName + '为系统预置角色，不能删除！');
							
							return;
						} else {
							deleteIds.push(checkedRows[i].id);//获取角色id
						}
					}

					//调用删除。数组转字符串，以“,”分割
					var param = {
						"roleId" : deleteIds.join(",")
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
								$.messager.alert("失败", data.content);
							}
							
							$('#rolesDataGrid').datagrid('reload');//重新刷新
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

	//分页
	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') {
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $(this);
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage : function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
				dg.datagrid('loadData', data);
			}
		});
		if (!data.originalRows) {
			data.originalRows = (data.rows);
		}
		
		var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = (data.originalRows.slice(start, end));
		
		return data;
	}
	
	//搜索条件重置
	function searchReset(){
		$("#roleName").val("");
	}

	$(document).ready(
		function() {
			$('#rolesDataGrid').datagrid({
				url : '${ctxPath}/admin/role/searchRole.do',
				toolbar : '#tb',
				rownumbers : true,
				pagination : true,
				fitColumns : true,
				fit : true,
				pageSize : 10,
				loadFilter : pagerFilter,
				columns : [[
						{
							checkbox : true,
						},
						{
							field : 'roleName',
							title : '角色名称',
							width : 100,
							align : 'center',
							formatter : function(value, row,index) {
								return "<a href='${ctxPath}/admin/role/editRole.do?roleId="
										+ row.id
										+ "'>"
										+ row.roleName
										+ "</a>";
							}
						},
						{
							field : 'createTime',
							title : '创建时间',
							width : 150,
							align : 'center'
						},
						{
							field : 'creatorName',
							title : '创建人',
							width : 150,
							align : 'center'
						},
						{
							field : 'modifyTime',
							title : '修改时间',
							width : 150,
							align : 'center'
						}, {
							field : 'modifierName',
							title : '修改人',
							width : 150,
							align : 'center'
						},{
							field : 'remark',
							title : '备注',
							width : 150,
							align : 'center'
						}]]
			});
			
			//搜索
			$('#btnSearch').click(function() {
				$('#rolesDataGrid').datagrid('load', {
					vroleName : $("#roleName").val()
				});
				
				$('#searchRoleWin').window('close');
			});
			
			//重置
			$('#btnReset').click(function() {
				searchReset();
			});
	});
</script>
</html>