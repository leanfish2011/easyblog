<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
	<head>
		<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的关注</title>
		<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
		<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
	</head>
	<body>
		<div class="easyui-tabs" style="height: auto; width: auto;">
			<div title="关注的博客">
				<div style="height: 94%; width: 100%;">
					<div id="tb" style="height: auto">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendBlog()">新增</a> 
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeBlog()">刪除</a> 
					</div>
					<table id="favUserDataGrid"></table>
				</div>
			</div>
			<div title="关注的文章">
				<div style="height: 94%; width: 100%;">
					<div id="tbArticle" style="height: auto">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendArticle()">新增</a> 
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeArticle()">刪除</a>
					</div>
					<table id="favArticleDataGrid"></table>
				</div>
			</div>
		</div>
		<div id="addFavBlog" class="easyui-window" title="新增关注的博客" data-options="modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true,iconCls:'icon-add',onClose:afterCloseBlogWindow" style="width: 400px; height: 300px; padding: 10px 50px 20px 50px">
			<form id="favBlogForm">
				<input id="favuserId" type="hidden" name="favuserId" value="" />
				<table cellpadding="5">
					<tr>
						<td>博主:</td>
						<td><input class="easyui-combobox" id="favUser" name="favUser" data-options="valueField:'text',textField:'text',url:'${ctxPath}/admin/user/getUserNotCurrent.do'"></input></td>
					</tr>
					<tr>
						<td>描述:</td>
						<td><input class="easyui-validatebox" type="text" id="favUserDescrible" name="favUserDescrible"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitBlogForm()">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearBlogForm()">重置</a>
		    </div>
		 </div>
		<div id="addFavArticle" class="easyui-window" title="新增关注的文章" data-options="modal:true,collapsible:false,maximizable:false,minimizable:false,closed:true,iconCls:'icon-add',onClose:afterCloseArticleWindow" style="width: 400px; height: 300px; padding: 10px 50px 20px 50px">
			<form id="favArticleForm">
				<input id="articleId" type="hidden" name="id" value="" />
				<table cellpadding="5">
					<tr>
						<td>标题:</td>
						<td><input class="easyui-validatebox tb" type="text" id="articleTitle" name="articleTitle" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
					</tr>
					<tr>
						<td>网址:</td>
						<td><input class="easyui-validatebox tb" type="text" id="articleUrl" name="articleUrl" data-options="required:true,validateOnCreate:false,validateOnBlur:true"></input></td>
					</tr>
					<tr>
						<td>描述:</td>
						<td><input class="easyui-validatebox" type="text" id="describle" name="describle"></input></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitArticleForm()">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearArticleForm()">重置</a>
		    </div>
		 </div>
	</body>
	<script type="text/javascript">
		//保存前，各个内容是否为空校验
		function validArticleInput() {
			var varticleTitle=$("#articleTitle").val();
			var varticleUrl=$("#articleUrl").val();
	
			if (varticleTitle == "") {
				$.messager.alert('提醒', '请输入文章标题！');
				
				return false;
			}
			
			if (varticleUrl == "") {
				$.messager.alert('提醒', '请输入文章网址！');
				
				return false;
			}
	
			return true;
		}
		
		function validBlogInput() {
			var vfavUser=$("#favUser").combobox('getValue');
			if (vfavUser == "") {
				$.messager.alert('提醒', '请选择博主！');
				
				return false;
			}
	
			return true;
		}
	
		//删除选择的文章
		function removeArticle() {
			var checkedRows = $('#favArticleDataGrid').datagrid('getChecked');//获取选择的行
			if (checkedRows.length > 0) {
				$.messager.confirm('确认', '确定删除？', function(r) {
					if (r) {
						var deleteIds = [];//获取待删除的类别id
						for (var i = 0; i < checkedRows.length; i++) {
							deleteIds.push(checkedRows[i].id);//获取类别id
						}
		
						//调用删除。数组转字符串，以“,”分割
						var param = {
							"favArticleIdIds" : deleteIds.join(",")
						};
						$.ajax({
							type : 'GET',
							contentType : 'application/json',
							url : '${ctxPath}/admin/favoriteArticle/deleteMyFavoriteArticle.do',
							dataType : 'json',
							data : param,
							success : function(data) {
								if (data && data.success == "true") {
									$.messager.alert("成功", "删除成功！", "info");
								} else {
									$.messager.alert("失败", "删除失败！", "info");
								}
								$('#favArticleDataGrid').datagrid('reload');//重新刷新
							},
							error : function() {
								$.messager.alert("错误", "删除关注的文章发生网络异常！", "error");
							}
						})
					}
				});
		
			} else {
				$.messager.alert('提醒', '请选择需要删除的关注文章！', 'warning');
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
		
		//-----------------------关注的博客------------------------
		
		function appendBlog() {
			clearArticleForm();
			$('#addFavBlog').window('open');
		}
		
		function clearBlogForm() {
			$('#favBlogForm').form('clear');
		}
		
		function afterCloseBlogWindow(){
		    $('#favUserDataGrid').datagrid('reload');
		}
		
		function submitBlogForm() {
			var passInput = validBlogInput();
			if (passInput) {
				var params = new FormData($("#favBlogForm")[0]);//使用FormData接收数据
				//根据是否有id，判断是新增还是修改
				var strUrl = "";
				if ($("#favuserId").val() != null && $("#favuserId").val() != "") {
					strUrl = '${ctxPath}/admin/favoriteUser/updateMyFavoriteUser.do';
				} else {
					strUrl = '${ctxPath}/admin/favoriteUser/addMyFavoriteUser.do';
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
						
						$('#favUserDataGrid').datagrid('reload');//重新刷新
					},
					error : function() {
						$.messager.alert("错误", "管理关注的博客发生网络异常！", "error");
					}
				});
			}
		}
		
		function removeBlog() {
			var checkedRows = $('#favUserDataGrid').datagrid('getChecked');//获取选择的行
			if (checkedRows.length > 0) {
				$.messager.confirm('确认', '确定删除？', function(r) {
					if (r) {
						var deleteIds = [];//获取待删除的类别id
						for (var i = 0; i < checkedRows.length; i++) {
							deleteIds.push(checkedRows[i].id);//获取类别id
						}
		
						//调用删除。数组转字符串，以“,”分割
						var param = {
							"favUserIds" : deleteIds.join(",")
						};
						$.ajax({
							type : 'GET',
							contentType : 'application/json',
							url : '${ctxPath}/admin/favoriteUser/deleteMyFavoriteUser.do',
							dataType : 'json',
							data : param,
							success : function(data) {
								if (data && data.success == "true") {
									$.messager.alert("成功", "删除成功！", "info");
								} else {
									$.messager.alert("失败", "删除失败！", "info");
								}
								$('#favUserDataGrid').datagrid('reload');//重新刷新
							},
							error : function() {
								$.messager.alert("错误", "删除关注的博客发生网络异常！", "error");
							}
						})
					}
				});
		
			} else {
				$.messager.alert('提醒', '请选择需要删除的关注博客！', 'warning');
			}
		}
		
		//---------------------------关注的文章-----------------------
		
		//弹出新增文章窗口
		function appendArticle() {
			clearArticleForm();
			$('#addFavArticle').window('open');
		}
		
		//新增文章或者修改文章
		function submitArticleForm() {
			var passInput = validArticleInput();
			if (passInput) {
				var params = new FormData($("#favArticleForm")[0]);//使用FormData接收数据
				//根据是否有id，判断是新增还是修改
				var strUrl = "";
				if ($("#articleId").val() != null && $("#articleId").val() != "") {
					strUrl = '${ctxPath}/admin/favoriteArticle/updateMyFavoriteArticle.do';
				} else {
					strUrl = '${ctxPath}/admin/favoriteArticle/addMyFavoriteArticle.do';
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
						
						$('#favArticleDataGrid').datagrid('reload');//重新刷新
					},
					error : function() {
						$.messager.alert("错误", "管理关注的文章发生网络异常！", "error");
					}
				});
			}
		}
		
		//清除新增文章内容
		function clearArticleForm() {
			$('#favArticleForm').form('clear');
		}
		
		//关闭新增文章窗口后，刷新文章列表页面
		function afterCloseArticleWindow(){
		    $('#favArticleDataGrid').datagrid('reload');
		}
		
		//编辑文章弹窗
		function editArticle(id,articleTitle,articleUrl,describle) {
			//赋值
			$("#articleId").val(id);
			$("#articleTitle").val(articleTitle);
			$("#articleUrl").val(articleUrl);
			$("#describle").val(describle);
			
			//弹窗
			$('#addFavArticle').window('open');
		}
		
		$(document).ready(function() {
			$('#favUserDataGrid').datagrid({
				url : '${ctxPath}/admin/favoriteUser/getMyFavoriteUser.do',
				toolbar : '#tb',
				fitColumns : true,
				fit : true,
				rownumbers : true,
				pagination : true,
				pageSize : 10,
				loadFilter : pagerFilter,
				columns : [ [ 
					{
						checkbox : true,
					},
					{
					field : 'favUser',
					title : '博主',
					width : 300,
					align : 'center',
					formatter:
					function(value,row){
						return "<a href='${ctxPath}/main/getArticleByCreateBy.do?userId="
						+ value + "' target='_blank'>"+value+"</a>" 
					},
					editor:{
						type:'combobox',
						options:{
							valueField:'id',
							textField:'text',
							method:'get',
							url:'${ctxPath}/admin/user/getUserNotCurrent.do',
							required:true
						}
				}
				}, {
					field : 'describle',
					title : '描述',
					width : 300,
					align : 'center',
					editor : 'text'
				} ] ]
			});
			
			$('#favArticleDataGrid').datagrid({
				url : '${ctxPath}/admin/favoriteArticle/getMyFavoriteArticle.do',
				toolbar : '#tbArticle',
				fitColumns : true,
				fit : true,
				rownumbers : true,
				pagination : true,
				pageSize : 10,
				loadFilter : pagerFilter,
				columns : [ [ 					
					{
						checkbox : true,
					},
					{
						field : 'articleTitle',
						title : '标题',
						width : 300,
						align : 'center',
						formatter:
							function(value,row){
								return "<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-add\',plain:true\" onclick=\"editArticle(\'" + row.id + "\',\'" + row.articleTitle +"\',\'" + row.articleUrl + "\',\'" + row.describle + "\')\" >"+value+"</a>";
							}
					}, 
					{
						field : 'describle',
						title : '描述',
						width : 300,
						align : 'center'
					},
					{
						field : 'articleUrl',
						title : '网址',
						width : 100,
						align : 'center',
						formatter:
							function(value,row){
								return "<a href='"+ row.articleUrl + "' target='_blank'>"+value+"</a>" ;
							}
					}] ]
			});
		});
	</script>
</html>