<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/functions.tld" %>
<%@ taglib uri="/WEB-INF/tld/spring.tld" prefix="spring" %>

<c:set var="ctxPath" scope="request">${pageContext.request.contextPath}</c:set>

<c:set var="imgPath" scope="request"><c:out value="${ctxPath}"/>/img</c:set>
<c:set var="cssPath" scope="request"><c:out value="${ctxPath}"/>/css</c:set>
<c:set var="jsPath" scope="request"><c:out value="${ctxPath}"/>/js</c:set>