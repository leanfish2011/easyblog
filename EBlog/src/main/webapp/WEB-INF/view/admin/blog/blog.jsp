<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文章内容管理</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body>
	<p>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showSearchWin()">查找</a>
	</p>
	<div id="blogSearchWin" class="easyui-window" title="查找博客" data-options="modal:true,closed:true,iconCls:'icon-search'" style="width: 500px; height: 300px; padding: 10px 50px 20px 50px">
		<table cellspacing="10">
			<tr>
				<td>文章类型：</td>
				<td><input class="easyui-combobox" id="comblogType" data-options="
								url:'${ctxPath}/admin/blogType/getBlogTypeByUser.do',
								width:100,
								method:'get',
								valueField:'id',
								textField:'text',
								panelHeight:'auto'
						"></td>
			</tr>
			<tr>
				<td>文章标题：</td>
				<td><input id="txtTitle" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" /></td>
			</tr>
			<tr>
				<td>发布时间：</td>
				<td><input id="startDate" style="width: 100px;" class="easyui-datebox"> — </input> <input id="endDate" style="width: 100px;" class="easyui-datebox"></input></td>
			</tr>
			<tr>
				<td>文章内容：</td>
				<td><input id="txtContent" class="easyui-textbox" style="width: 230; height: 22px; border: 1px solid #95B8E7;" /></td>
			</tr>
		</table>
		<div align="center">
			<a id="btnSearch" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a id="btnReset" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'">重置</a>
		</div>
	</div>
	<table id="blogListDataGrid"></table>
	<script type="text/javascript">
		//弹出搜索框
		function showSearchWin() {
			$('#blogSearchWin').window('open');
		}
		
		//点击查询
		function search() {
			$('#blogListDataGrid').datagrid('load', {
				blogType : $("#comblogType").combobox('getValue'),
				title : encodeURI($("#txtTitle").val()),
				startDate : $('#startDate').datebox('getValue'),
				endDate : $('#endDate').datebox('getValue'),
				content : encodeURI($("#txtContent").val())
			});
		}
		
		//重置
		function reset() {
			$("#comblogType").combobox('setValue', '');
			$("#txtTitle").val("");
			$('#startDate').datebox('setValue', "");
			$('#endDate').datebox('setValue', "");
			$("#txtContent").val("");
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
		
		
		$('#btnSearch').click(function() {
			search();
			$('#blogSearchWin').window('close');
		});
		
		$('#btnReset').click(function() {
			reset();
		});
		
		$(document).ready(function() {
			$('#blogListDataGrid').datagrid({
				url : '${ctxPath}/admin/blogInfo/searchBlog.do',
				width : 700,
				height : 400,
				singleSelect : true,
				fitColumns : true,
				fit : true,
				method : 'get',
				rownumbers : true,
				pagination : true,
				pageSize : 10,
				loadFilter : pagerFilter,
				columns : [ [ 
					{
					field : 'title',
					title : '标题',
					width : 500,
					formatter : function(value,row,index) {
							return  "<a href=\"${ctxPath}/admin/blogInfo/getDetailById.do?blogid=" + row.id
							+ "\">" + value + "</a>";
						}
					}, 
					{
						field : 'createTime',
						title : '发布时间',
						width : 100
					}, 
					{
						field : 'typeName',
						title : '类别',
						width : 100
					} ] ]
			});
		});
	</script>
</html>