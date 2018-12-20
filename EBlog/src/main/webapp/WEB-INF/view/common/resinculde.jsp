<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>

<title><c:if test="${!(empty current_menu)}">${current_menu.appName} &lt; </c:if>${i18n.system.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<link rel="stylesheet" type="text/css" href="${cssPath}/common.css" />
<script type="text/javascript" src="${jsPath}/tools/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${jsPath}/tools/common.js"></script>
<script type="text/javascript" src="${jsPath}/tools/jquery.cookie.js"></script>