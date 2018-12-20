<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><spring:message code="blogworld"/></title>
		<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
		<link href="${cssPath}/index.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${jsPath}/view/index.js"></script>
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
	</head>
	<body>
		<%@include file="/WEB-INF/view/common/menu.jsp"%>
		<div id="container">
			<div id="leftArticle">
				<div id="articleInfo"></div>
				<div id="pageFilter"></div>
			</div>
			<div id="rightCategory">
				<p><spring:message code="blogtype"/></p>
				<hr style='border: 1px dotted #E4DDDD' />
				<div id="categoryLinks"></div>
			</div>
		</div>
		<%@include file="/WEB-INF/view/common/footer.jsp"%>
	</body>
</html>