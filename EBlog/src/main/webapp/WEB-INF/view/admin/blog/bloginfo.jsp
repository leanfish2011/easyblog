<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文章新建/编辑</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
	
	<link href="${jsPath}/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${jsPath}/kindeditor/plugins/code/prettify.css" />
	<script src="${jsPath}/kindeditor/kindeditor-min.js" type="text/javascript"></script>
	<script src="${jsPath}/kindeditor/lang/zh_CN.js" type="text/javascript"></script>
	<script src="${jsPath}/kindeditor/plugins/code/prettify.js"></script>
</head>
<body>
	<div id="tb" style="height: auto">
		<a href="${ctxPath}/admin/blogInfo/index.do" class="easyui-linkbutton" data-options="iconCls:'icon-blur',plain:true">列表</a> 
		<a <c:choose><c:when test="${empty articleDTO}">style="display: none;"</c:when> </c:choose> href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteArticle()">刪除</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveArticle()">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="clearForm()">重置</a>
	</div>
	<form id="articleForm">
		<input id="vblogid" type="hidden" name="id" value="${articleDTO.id}" />
		<div style="width: 100%;">
			<div id="palBaseInfo" class="easyui-panel" title="基本信息" style="width: 100%; height: 50px; padding: 10px;" data-options="fit:true,collapsible:true">
				<div align="center">
					<table cellspacing="10">
						<tr>
							<td>文章标题：</td>
							<td><input name="title" value="${articleDTO.title}" id="txtTitle" class="easyui-validatebox tb" data-options="required:true,validateOnCreate:false,validateOnBlur:true" style="width: 300; height: 22px; border: 1px solid #95B8E7;" /></td>
							<td>文章类型：</td>
							<td><input name="typeId" class="easyui-combobox" id="comblogType" 
								<c:choose>
									<c:when test="${empty articleDTO}"></c:when>
									<c:otherwise>value=${articleDTO.typeId}</c:otherwise>
								</c:choose>"></td>
							<c:choose>
								<c:when test="${empty articleDTO}"></c:when>
								<c:otherwise>
									<td>发布时间：</td>
									<td><p id="postDate">${articleDTO.createTime}</p></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div style="width: 100%;">
			<div class="easyui-panel" title="文章内容" style="height: 320px; width: 100%;" data-options="fit:true">
				<textarea name="content" style="width: 99%; height: 320px;">${articleDTO.content}</textarea>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">	
	//初始化文本编辑器
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			cssPath : '../../js/kindeditor/plugins/code/prettify.css',
			resizeType : 1,
			allowImageUpload : false
		});
		prettyPrint();
	});
	
	//重置
	function clearForm() {
		$('#articleForm').form('clear');
		editor.html('');//清空内容
	}
	
	//保存前，各个内容是否为空校验
	function validInput() {
		var vTitle = $("#txtTitle").val();
		var vBlogType = $("#comblogType").combobox('getValue');
		var vContent = editor.html();//取得HTML内容
		
		if (vTitle == "") {
			$.messager.alert('提醒', '请输入标题！');
			return false;
		}
		if (vBlogType == "") {
			$.messager.alert('提醒', '请选择文章类型！');
			return false;
		}
		if (vContent == "") {
			$.messager.alert('提醒', '请输入文章内容！');
			return false;
		}
		
		return true;
	}
	
	//保存
	function saveArticle() {
		var passInput = validInput();
		if (passInput) {
			var vTitle = $("#txtTitle").val();
			var vBlogTypeId = $("#comblogType").combobox('getValue');
			var vBlogTypeName = $("#comblogType").combobox('getText');
			var vContent = editor.html();//取得HTML内容
			
			//新建
			if ($("#vblogid").val() == null || $("#vblogid").val() == "") {
				var param = {
					title : vTitle,
					blogTypeId : vBlogTypeId,
					blogTypeName : vBlogTypeName,
					content : vContent
				};
				$.ajax({
					type : 'POST',
					dataType : 'json',
					url : '${ctxPath}/admin/blogInfo/saveBlog.do', //新增信息
					data : $.param(param),
					success : function(data) {
						if (data && data.success == "true") {
							$.messager.alert('成功', '新增成功！');
							
							location.href = "${ctxPath}/admin/blogInfo/index.do";
						} else {
							$.messager.alert('失败', '新增失败！');
						}
					},
					error : function() {
						$.messager.alert('失败', '新增失败！');
					}
				});
			} else {
				var param = {
					blogid : $("#vblogid").val(),
					title : vTitle,
					blogTypeId : vBlogTypeId,
					blogTypeName : vBlogTypeName,
					content : vContent
				};
				$.ajax({
					type : 'POST',
					dataType : 'json',
					url : '${ctxPath}/admin/blogInfo/updateBlog.do', //更新信息
					data : $.param(param),
					success : function(data) {
						if (data && data.success == "true") {
							$.messager.alert('成功', '更新成功！');
							
							location.href = "${ctxPath}/admin/blogInfo/index.do";
						} else {
							$.messager.alert('失败', '更新失败！');
						}
					},
					error : function() {
						$.messager.alert('失败', '更新失败！');
					}
				});
			}
		}
	}
	
	//删除
	function deleteArticle() {
		$.messager.confirm(
			'确认','确定删除该文章？',
			function(r) {
				if (r) {
					//文章id
					var param = {
						blogid : $("#vblogid").val()
					};
					
					$.ajax({
						type : 'POST',
						dataType : "json",
						url : "${ctxPath}/admin/blogInfo/deleteBlog.do",
						data : $.param(param),
						success : function(data) {
							if (data&& data.success == "true") {
								$.messager.alert('成功','删除成功！');
								
								location.href = "${ctxPath}/admin/blogInfo/index.do";
							} else {
								$.messager.alert('失败','删除失败！');
							}
						},
						error : function() {
							$.messager.alert('失败', '删除失败！');
						}
					});
				}
			});
	}
	
	$(document).ready(function() {
	    $('#comblogType').combobox({
	        url:'${ctxPath}/admin/blogType/getBlogTypeByUser.do',
	        valueField:'id',
	        textField:'text'
	    });
	});
</script>
</html>