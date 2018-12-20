<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${selectedUser.userName}-<spring:message code="blogworld"/></title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<link href="${cssPath}/articleViewlistuser.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@include file="/WEB-INF/view/common/articleMenu.jsp"%>
	<div id="content">
		<div id="leftArticle">
			<div id="articleInfo">
				<c:forEach items="${dto}" var="item">
					<a href="${ctxPath}/main/getDetailByIdView.do?id=${item.ID}" target="_blank">${item.title}</a>
					<p>${item.content}</p>
					<p id="postInfo">${item.creatorName}&nbsp;<spring:message code="postat"/>&nbsp;${item.createTime}&nbsp;<spring:message code="read"/>(${item.readCount})&nbsp;<spring:message code="suggest"/>(${item.suggestCount})&nbsp;<spring:message code="commit"/>(${item.comCount})</p>
					<hr>
				</c:forEach>
			</div>
		</div>
		<%@include file="/WEB-INF/view/common/articlerightInfo.jsp"%>
	</div>
	<%@include file="/WEB-INF/view/common/footer.jsp"%>
	<script type="text/javascript">
		//外部引入的js不能直接使用标签，页面先读取所有的资源，外部js中再使用
		var localResource={
				'postat':'<spring:message code="postat"/>',
				'read':'<spring:message code="read"/>',
				'suggest':'<spring:message code="suggest"/>',
				'commit':'<spring:message code="commit"/>',
				'errorReadBlogType':'<spring:message code="errorReadBlogType"/>',
				'netErrorReadBlogType':'<spring:message code="netErrorReadBlogType"/>',
				'errorReadPage':'<spring:message code="errorReadPage"/>',
				'netErrorReadPage':'<spring:message code="netErrorReadPage"/>',
				'netErrorReadBlog':'<spring:message code="netErrorReadBlog"/>',
				'search':'<spring:message code="search"/>',
				'inputKeyWord':'<spring:message code="inputKeyWord"/>',
				'netErrorSearch':'<spring:message code="netErrorSearch"/>'
		};
	</script>
</body>
</html>