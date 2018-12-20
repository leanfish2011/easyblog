<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>博客类别管理</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
	<title>博客类别编辑</title>
</head>
<body>
	<div id="tb" style="height: auto">
		<a href="${ctxPath}/admin/blogType/index.do" class="easyui-linkbutton" data-options="iconCls:'icon-blur',plain:true">列表</a> 
		<a <c:choose><c:when test="${empty blogTypeDTO}">style="display: none;"</c:when> </c:choose> href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteBlogType()">刪除</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveBlogType()">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="clearForm()">重置</a>
	</div>
	<form id="blogTypeForm">
		<input id="blogTypeId" type="hidden" name="id" value="${blogTypeDTO.id}" />
		<table cellpadding="5">
			<tr>
				<td>类别名称:</td>
				<td><input id="typeName" <c:choose><c:when test="${not empty blogTypeDTO}">disabled="disabled" </c:when></c:choose> class="easyui-validatebox tb" type="text" name="typeName" data-options="required:true,validateOnCreate:false,validateOnBlur:true" value="${blogTypeDTO.typeName}"></input></td>
			</tr>
			<tr>
				<td>描述:</td>
				<td><input id="description" type="text" name="description" value="${blogTypeDTO.description}"></input></td>
			</tr>
		</table>
	</form>
</body>
	<script type="text/javascript">
		//保存前，各个内容是否为空校验
		function validInput() {
			var vtypeName = $("#typeName").val();
	
			if (vtypeName == "") {
				$.messager.alert('提醒', '请输入类别名称！');
				
				return false;
			}
	
			return true;
		}
		
		//重置
		function clearForm() {
			$('#blogTypeForm').form('clear');
		}
		
		//保存
		function saveBlogType() {
			var passInput = validInput();
			if (passInput) {
				var params = new FormData($("#blogTypeForm")[0]);//使用FormData接收数据
				//根据是否有id，判断是新增还是修改
				var strUrl = "";
				if ($("#blogTypeId").val() != null && $("#blogTypeId").val() != "") {
					strUrl = '${ctxPath}/admin/blogType/updateBlogType.do';
				} else {
					strUrl = '${ctxPath}/admin/blogType/addBlogType.do';
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
							
							location.href = "${ctxPath}/admin/blogType/index.do";
						} else {
							$.messager.alert("失败", data.content, "info");
						}
					},
					error : function() {
						$.messager.alert("错误", "管理用户发生网络异常！", "error");
					}
				});
			}
		}
		
		//删除选择的类别
		function deleteBlogType() {
			var blogTypeId = $("#blogTypeId").val();//获取待删除的博客类别id
			if (blogTypeId != null && blogTypeId != "") {
				$.messager.confirm('确认', '确定删除？', function(r) {
					if (r) {
						//调用删除
						var param = {
							"blogTypeIds" : blogTypeId
						};
						$.ajax({
							type : 'GET',
							contentType : 'application/json',
							url : '${ctxPath}/admin/blogType/deleteBlogType.do',
							dataType : 'json',
							data : param,
							success : function(data) {
								if (data && data.success == "true") {
									$.messager.alert("成功", data.content, "info");
								} else {
									$.messager.alert("失败", data.content);
								}
								
								location.href = "${ctxPath}/admin/blogType/index.do";
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
	</script>
</html>