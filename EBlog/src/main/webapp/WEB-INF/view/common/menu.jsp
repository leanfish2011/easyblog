<%@ page pageEncoding="UTF-8"%>
<link href="${cssPath}/gotoTop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${jsPath}/tools/gotoTop.js"></script>
<script type="text/javascript">
	// 定义菜单栏离页面顶部的距离，默认为100    
	var divOffsetTop = 100;

	$(document).ready(function() {
		//点击菜单，加载背景	
		var subNav_change = function(target) {
			var subNav_active = $(".adv_active");
			subNav_active.removeClass("adv_active");
			target.parent().addClass("adv_active");
			subNav_active = target.parent();
		};

		//动态绑定现在和将来新增的元素事件
		$("#divMenu a").live("click", "a", function() {
			subNav_change($(this));
		});

		//页面加载完之后，计算菜单栏到页面顶部的实际距离
		var divMenu = document.getElementById("topMenu");
		divOffsetTop = divMenu.offsetTop - 5;//因为设置了margin-top=5
		gotoTop();//加载“返回顶部按钮”
		$(window).scroll(function() {
			//滚动固定菜单栏
			// 计算页面滚动了多少（需要区分不同浏览器）    
			var topVal = 0;
			if (window.pageYOffset) {//这一条滤去了大部分， 只留了IE678    
				topVal = window.pageYOffset;
			} else if (document.documentElement.scrollTop) {//IE678 的非quirk模式    
				topVal = document.documentElement.scrollTop;
			} else if (document.body.scrolltop) {//IE678 的quirk模式    
				topVal = document.body.scrolltop;
			}
			if (topVal <= divOffsetTop) {
				divMenu.style.position = "";
			} else {
				divMenu.style.position = "fixed";
			}
		});
	});
</script>
<div id="topMenu">
	<div id="top">
		<table width="100%">
			<tr>
				<td>
					<img src="${imgPath}/log.jpg">
				</td>
				<td>
					<div id="logTitle"><spring:message code="blogworld"/></div>
				</td>
				<td>
					<div id="midSearch">
						<input type="text" id="txtKeyword" /> <span><a id="btnSearch" href="#" onclick="searchKeyword()"><spring:message code="search"/></a></span>
					</div>
				</td>
				<td style="text-align: right">
					<a href="${ctxPath}/Global/changeLang.do?from=index&langType=zh" >简体中文</a> 
                    <a href="${ctxPath}/Global/changeLang.do?from=index&langType=en" >English</a>
                 </td>
				<td style="text-align: right">
					<c:choose>
						<c:when test="${empty Current_User}">
							<a href='${ctxPath}/Login/loginpage.do'><spring:message code='login'/></a>
						</c:when>
						<c:otherwise>
							<a href='${ctxPath}/admin/index.do'>${Current_User.userName}</a> 
							<a href='${ctxPath}/Login/logout.do'><spring:message code='logout'/></a>
						</c:otherwise>
					</c:choose>
					&nbsp; <a href="${ctxPath}/main/register.do"><spring:message code="register"/></a>
				</td>
			</tr>
		</table>
	</div>

	<div id="divMenu">
		<ul>
			<li class="adv_active"><a id="allArticle" href="javascript:void(0);" onclick="getArticleByMenu('getallArticle')"><spring:message code="index"/></a></li>
			<li><a id="articleRead" href="javascript:void(0);" onclick="getArticleByMenu('getArticleRead')"><spring:message code="hotread"/></a></li>
			<li><a id="articleSuggest" href="javascript:void(0);" onclick="getArticleByMenu('getArticleSuggest')"><spring:message code="suggest"/></a></li>
			<li><a id="articleCommit" href="javascript:void(0);" onclick="getArticleByMenu('getArticleCommit')"><spring:message code="hotcommit"/></a></li>
		</ul>
	</div>

</div>
