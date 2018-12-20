/**
 * 首页js 2017-07-08
 */

// 读取文章分类
function getCategory() {
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : getContextPath() + '/main/getCategory.do',
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

// 生成分页控件
function getPageFilter(action) {
	$('#pageFilter').empty();// 先清空分页信息，避免aPageChange方法中先获取到了上一次的元素
	$('#pageFilter').html();
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : getContextPath() + '/main/getArticlePage.do?action=' + action,
		dataType : 'json',
		success : function(data) {
			if (data && data.success == "true") {
				$('#pageFilter').html(data.data);
			} else {
				alert(localResource["errorReadPage"]);
			}
		},
		error : function() {
			alert(localResource["netErrorReadPage"]);
		}
	});
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

//页面中点击菜单，调用对应控制器方法，生成分页即内容
//调用的控制器方法 
//首页：getallArticle； 热读：getArticleRead； 热评：getArticleCommit；推荐：getArticleSuggest；
function getArticleByMenu(method) {
	getPageFilter("/main/" + method + ".do");// 生成底部的分页
	aPageChange();// 分页切换
	getArticle(getContextPath() + "/main/" + method + ".do?page=1");
}

// 分页切换
function aPageChange() {
	var aDisablePage = $(".aPageDisable");
	var diable_change = function(target) {
		// 解决第一次加载获取不到元素的情况
		if (aDisablePage.html() == null) {
			aDisablePage = $(".aPageDisable");
		}
		aDisablePage.removeClass("aPageDisable");
		target.addClass("aPageDisable");
		aDisablePage = target;
	};
	// 后来新增的元素，使用live方法
	$("#pageFilter a").live("click", function() {
		diable_change($(this));
	});
}

// 点击分类
function getArticleType(typeid) {
	// 生成分页
	getPageFilter("/main/getArticleByType.do?typeid=" + typeid);// 生成底部的分页
	aPageChange();
	getArticle(getContextPath() + '/main/getArticleByType.do?typeid=' + typeid + '&page=1');
}

// 点击右侧分类，则增加对应分类菜单
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

// 搜索方法
function searchKeyword() {
	var keyword = $("#txtKeyword").val();
	if (keyword != "") {
		getSearchBlog(keyword);
	} else {
		alert(localResource["inputKeyWord"]);
	}
}

// 调用搜索
function getSearchBlog(keyword) {
	var param = {
		keyword : keyword
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		url : getContextPath() + "/BlogSearch/searchBlog.do",
		data : $.param(param),
		success : function(data) {
			if(data.enableES == false) {
				alert("未开启搜索功能！");
				return;
			}
			
			if (data && data.total != 0) {
				// 显示查询菜单
				if ($("#searchMenu").length <= 0) {
					$("#divMenu ul").append("<li><a id='searchMenu' href='javascript:void(0);'>"+localResource["search"]+"</a></li>");
				}

				$("#searchMenu").click();// 只是为了加上选中样式而进行一次点击
				
				var strSearchArticle = appendArticle(data.rows);
				$('#articleInfo').html(strSearchArticle);
				
				// TODO实现查询分页
				$('#pageFilter').html("");
			}
		},
		error : function() {
			alert(localResource["netErrorSearch"]);
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

// 页面首先加载全部
$(document).ready(function() {
	getArticleByMenu('getallArticle');// 页面加载，先默认点击了首页菜单
	getCategory();
});

