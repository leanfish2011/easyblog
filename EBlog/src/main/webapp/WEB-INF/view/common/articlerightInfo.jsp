<!-- 文章浏览页，右侧公告等信息 -->
<%@ page pageEncoding="UTF-8"%>
<link href="${cssPath}/articleRightInfo.css" rel="stylesheet" type="text/css" />

<div id="rightInfo">
	<div id="authorInfo">
		<p id="authorInfoTitle">公告</p>
		<div>
			<p>昵称：${selectedUser.userName}</p>
			<p>创建时间：${selectedUser.createTime}</p>
		</div>
	</div>
	<div id="rightCategory">
		<p>
			<spring:message code="blogtype" />
		</p>
		<div id="categoryLinks"></div>
	</div>
</div>

<script type="text/javascript">
	//点击右侧分类，则增加对应分类菜单
	function addTypeMenu(typeName, typeid) {
		// 该元素不存在，则创建
		if ($("#" + typeid + "").length <= 0) {
			$("#divMenu ul").append("<li><a id=\""
									+ typeid
									+ "\" href='javascript:void(0);' onclick=\"getArticleType(\'"
									+ typeid + "\')\" >" + typeName + "</a></li>");
		}
	
		// 触发click事件
		$("#" + typeid + "").click();
	}

	// 点击分类
	function getArticleType(typeid) {
		getArticle(getContextPath() + '/main/getArticleByType.do?typeid=' + typeid + '&page=1');
	}
	
	// 根据传递的url，取不同的文章分类结果，显示在页面
	function getArticle(url) {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : url,
			dataType : 'json',
			success : function(data) {
				if (data && data.success == "true") {
					var strAllArticle=appendArticle(data.data);
					$('#articleInfo').html(strAllArticle);
				}
			},
			error : function() {
				alert(localResource["netErrorReadBlog"]);
			}
		});
	}
	
	//根据数据拼接文章内容
	function appendArticle(dataSource) {
		var strAllArticle = "";
		$.each(dataSource, function(i, item) {
			strAllArticle += "<div>";
			strAllArticle += "<a href='" + getContextPath() + "/main/getDetailByIdView.do?id=";
			strAllArticle += item.id;
			strAllArticle += "' target='_blank'>";
			strAllArticle += item.title;
			strAllArticle += "</a>";
			strAllArticle += "<p>";
			strAllArticle += item.content;
			strAllArticle += "</p>";
			strAllArticle += "<p id='postInfo'>";
			strAllArticle += "<a style='text-decoration:none' href='" + getContextPath() + "/main/getArticleByCreateBy.do?userId=";
			strAllArticle += item.creator;
			strAllArticle += "' target='_blank'>";
			strAllArticle += item.creatorName;
			strAllArticle += "</a>";
			strAllArticle += "&nbsp;";
			strAllArticle += localResource["postat"];
			strAllArticle += "&nbsp;";
			strAllArticle += item.createTime;
			strAllArticle += "&nbsp;";
			strAllArticle += localResource["read"];
			strAllArticle += "("+item.readCount + ")";
			strAllArticle += "&nbsp;";
			strAllArticle += localResource["suggest"];
			strAllArticle += "("+ item.suggestCount + ")";
			strAllArticle += "&nbsp;";
			strAllArticle += localResource["commit"];
			strAllArticle += "("+ item.comCount + ")";
			strAllArticle += "</p>";
			strAllArticle += "</div>";
			strAllArticle += "<hr id='articleSplit'/>";
		});
		
		return strAllArticle;
	}
	
	// 读取文章分类
	function getCategory() {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : getContextPath() + '/main/getCategoryByUser.do?userId=${selectedUser.id}',
			dataType : 'json',
			success : function(data) {
				if (data && data.success == "true") {
					$('#categoryLinks').html(data.data);
				} else {
					alert(localResource["errorReadBlogType"]);
				}
			},
			error : function() {
				alert(localResource["netErrorReadBlogType"]);
			}
		});
	}
	
	// 页面首先加载全部
	$(document).ready(function() {
		getCategory();
	});
</script>