<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>抓取任务</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body>
	<div style="height: 100%; width: 100%;">
		<div id="toolbar" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCrawTask()">新增</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeCrawTask()">刪除</a>
		</div>
		<table id="crawTaskdataGrid"></table>
	</div>
	<div id="addCrawlTask" class="easyui-window" title="新增抓取任务" data-options="modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true,iconCls:'icon-add',onClose:afterCloseTaskWindow" style="width: 400px; height: 300px; padding: 10px 50px 20px 50px">
		<form id="crawTaskForm">
			<input id="crawTaskId" type="hidden" name="crawTaskId" value="" />
			<table cellpadding="5">
				<tr>
					<td>抓取网页:</td>
					<td><input class="easyui-validatebox" id="crawlUrl" name="crawlUrl" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
				</tr>
				<tr>
					<td>关键词:</td>
					<td><input class="easyui-validatebox" type="text" id="keyWords" name="keyWords" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitcrawTaskForm()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearcrawTaskForm()">重置</a>
		</div>
	</div>
</body>
<script type="text/javascript">
	//删除
	function removeCrawTask() {
		var checkedRows = $('#crawTaskdataGrid').datagrid('getChecked');//获取选择的行
		if (checkedRows.length > 0) {
			$.messager.confirm('确认', '确定删除？', function(r) {
				if (r) {
					var deleteIds = [];//获取待删除的类别id
					for (var i = 0; i < checkedRows.length; i++) {
						deleteIds.push(checkedRows[i].id);//获取类别id
					}

					//调用删除。数组转字符串，以“,”分割
					var param = {
						"deleteIds" : deleteIds.join(",")
					};
					$.ajax({
						type : 'GET',
						contentType : 'application/json',
						url : '${ctxPath}/admin/crawlerTask/removeCrawTask.do',
						dataType : 'json',
						data : param,
						success : function(data) {
							if (data && data.success == "true") {
								$.messager.alert("成功", "删除成功！", "info");
							} else {
								$.messager.alert("失败", data.content);
							}
							
							$('#crawTaskdataGrid').datagrid('reload');//重新刷新
						},
						error : function() {
							$.messager.alert("错误", "删除发生网络异常！", "error");
						}
					})
				}
			});

		} else {
			$.messager.alert('提醒', '请选择需要删除的项！', 'warning');
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

	function addCrawTask() {
		clearcrawTaskForm();
		$('#addCrawlTask').window('open');
	}
	
	function clearcrawTaskForm() {
		$('#crawTaskForm').form('clear');
	}
	
	function afterCloseTaskWindow(){
	    $('#crawTaskdataGrid').datagrid('reload');
	}
	
	function validInput() {
		var vcrawlUrl=$("#crawlUrl").val();
		var vkeyWords=$("#keyWords").val();

		if (vcrawlUrl == "") {
			$.messager.alert('提醒', '请输入待抓取的网址！');
			
			return false;
		}
		
		if (vkeyWords == "") {
			$.messager.alert('提醒', '请输入关键词！');
			
			return false;
		}

		return true;
	}
	
	function submitcrawTaskForm() {
		var passInput = validInput();
		if (passInput) {
			var params = new FormData($("#crawTaskForm")[0]);//使用FormData接收数据
			//根据是否有id，判断是新增还是修改
			var strUrl = "";
			if ($("#crawTaskId").val() != null && $("#crawTaskId").val() != "") {
				strUrl = '${ctxPath}/admin/crawlerTask/updateCrawlerTask.do';
			} else {
				strUrl = '${ctxPath}/admin/crawlerTask/addCrawlerTask.do';
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
					} else {
						$.messager.alert("失败", data.content, "info");
					}
					
					$('#crawTaskdataGrid').datagrid('reload');//重新刷新
				},
				error : function() {
					$.messager.alert("错误", "管理抓取任务发生网络异常！", "error");
				}
			});
		}
	}
	
	$(document).ready(function() {
		$('#crawTaskdataGrid').datagrid({
			url : '${ctxPath}/admin/crawlerTask/getListCrawlerTaskByUser.do',
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
						field : 'crawlUrl',
						title : '抓取网页',
						width : 300,
						formatter : function(value, row,index) {
							return "<a href='"+ row.crawlUrl + "' target='_blank'>"
									+ value
									+ "</a>";
						}
					}, {
						field : 'keyWords',
						title : '关键词',
						width : 300
					}, {
						field : 'createTime',
						title : '创建时间',
						width : 100
					}, {
						field : 'state',
						title : '状态',
						width : 50,
						formatter : function(value, row,index) {
							var strState="创建";
							switch (value) {
								case 0 :
									strState="创建";
									break;
								case 1 :
									strState="执行中";
									break;
								case 2 :
									strState="执行成功";
									break;
								case 3 :
									strState="执行失败";
									break;
								default :
									strState="创建";
									break;
							}
							
							return strState;
						}
					}, {
						field : 'finishTime',
						title : '完成时间',
						width : 100
					}]]
		});
	});
</script>
</html>