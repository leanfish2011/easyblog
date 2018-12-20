<%@ page pageEncoding="UTF-8"%>
<link href="${cssPath}/articleMenu.css" rel="stylesheet" type="text/css" />

<div id="top">
	<table width="100%">
		<tr>
			<td><img src="${imgPath}/log.jpg"></td>
			<td><div id="logTitle">
					${selectedUser.userName}-
					<spring:message code="blogworld" />
				</div></td>
			<td style="text-align: right"><a href="${ctxPath}/Global/changeLang.do?from=index&langType=zh">简体中文</a> <a href="${ctxPath}/Global/changeLang.do?from=index&langType=en">English</a></td>
			<td style="text-align: right"><span id="loginInfo"> <c:choose>
						<c:when test="${empty Current_User}">
							<a href='${ctxPath}/Login/loginpage.do'><spring:message code='login' /></a>
						</c:when>
						<c:otherwise>
							<a href='${ctxPath}/admin/index.do'>${Current_User.userName}</a>
							<a href='${ctxPath}/Login/logout.do'><spring:message code='logout' /></a>
						</c:otherwise>
					</c:choose>
			</span></td>
		</tr>
	</table>
</div>

<div id="divMenu">
	<ul>
		<li><a href="${ctxPath}/main/index.do"><spring:message code="blogworld" /></a></li>
		<li><a href="${ctxPath}/main/getArticleByCreateBy.do?userId=${selectedUser.id}"><spring:message code="index" /></a></li>
		<li><a href="${ctxPath}/admin/index.do"><spring:message code="manage" /></a></li>
		<li><a href="${ctxPath}/admin/blogInfo/add.do"><spring:message code="addBlog" /></a></li>
	</ul>
</div>