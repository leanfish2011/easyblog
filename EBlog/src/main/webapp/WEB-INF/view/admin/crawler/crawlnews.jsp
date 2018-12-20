<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>我的抓取</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body>
	<div style="height: 100%; width: 100%;">
		<div id="toolbar" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeNews()">刪除</a>
		</div>
		<table id="newdataGrid"></table>
	</div>
</body>
<script type="text/javascript">
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
		$('#newdataGrid').datagrid({
			url : '${ctxPath}/admin/crawlerNews/getListCrawlerNewsByUser.do',
			toolbar : '#toolbar',
			rownumbers : true,
			pagination : true,
			fitColumns : true,
			fit : true,
			pageSize : 10,
			loadFilter : pagerFilter,
			columns : [[
					{
						checkbox : true,
					},{
						field : 'title',
						title : '标题',
						width : 150,
						formatter : function(value, row,index) {
							return "<a href='"+ row.url + "' target='_blank'>"
									+ value
									+ "</a>";
						}
					}, {
						field : 'content',
						title : '内容',
						width : 200
					}, {
						field : 'postTime',
						title : '发表时间',
						width : 150
					},{
						field : 'author',
						title : '作者',
						width : 100
					},{
						field : 'authorPage',
						title : '作者主页',
						width : 100,
						formatter : function(value, row,index) {
							return "<a href='"+ value + "' target='_blank'>"
									+ value
									+ "</a>";
						}
					},{
						field : 'createTime',
						title : '抓取时间',
						width : 150
					}]]
		});
	});
</script>
</html>