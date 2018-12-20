<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
	<head>
		<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>博客类别管理</title>
		<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	    <%@include file="/WEB-INF/view/common/adminResource.jsp"%>
	</head>
	<body>
		<div style="height: 100%; width: 100%;">
			<div id="tb" style="height: auto">
				<a href="${ctxPath}/admin/blogType/add.do" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteBlogType()">刪除</a> 
			</div>
			<table id="typeDataGrid"></table>
		</div>
	</body>
	<script type="text/javascript">
		//删除选择的类别
		function deleteBlogType() {
			var checkedRows = $('#typeDataGrid').datagrid('getChecked');//获取选择的类别
			if (checkedRows.length > 0) {
				$.messager.confirm('确认', '确定删除？', function(r) {
					if (r) {
						var deleteIds = [];//获取待删除的类别id
						for (var i = 0; i < checkedRows.length; i++) {
							deleteIds.push(checkedRows[i].id);//获取类别id
						}
		
						//调用删除。数组转字符串，以“,”分割
						var param = {
							"blogTypeIds" : deleteIds.join(",")
						};
						$.ajax({
							type : 'GET',
							contentType : 'application/json',
							url : '${ctxPath}/admin/blogType/deleteBlogType.do',
							dataType : 'json',
							data : param,
							success : function(data) {
								if (data && data.success == "true") {
									$.messager.alert("成功", "博客类别删除成功！", "info");
								} else {
									$.messager.alert("失败", data.content);
								}
								$('#typeDataGrid').datagrid('reload');//重新刷新
							},
							error : function() {
								$.messager.alert("错误", "删除博客类别发生网络异常！", "error");
							}
						})
					}
				});
		
			} else {
				$.messager.alert('提醒', '请选择需要删除的博客类别！', 'warning');
			}
		}
		
		//分页
		function pagerFilter(data) {
			if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
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
		
		$(document).ready(function() {
			$('#typeDataGrid').datagrid({
				url : '${ctxPath}/admin/blogType/getBlogTypeListByUser.do',
				toolbar : '#tb',
				rownumbers : true,
				pagination : true,
				fitColumns : true,
				fit : true,
				pageSize : 10,
				loadFilter : pagerFilter,
				columns : [ [ 
					{
						checkbox : true,
					},
					{
						field : 'typeName',
						title : '类别名称',
						width : 300,
						align : 'center',
						editor : 'text',
						formatter : function(value, row,index) {
							return "<a href='${ctxPath}/admin/blogType/editBlogType.do?blogTypeId="
								+ row.id
								+ "'>"
								+ row.typeName
								+ "</a>";
					}
					}, 
					{
						field : 'description',
						title : '描述',
						width : 300,
						align : 'center'
					} ] ]
			});
		});
	</script>
</html>