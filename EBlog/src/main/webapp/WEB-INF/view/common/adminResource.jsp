<!-- 后台页面公共资源 -->
<%@ page pageEncoding="UTF-8"%>

<link href="${cssPath}/admin.css" rel="stylesheet" type="text/css" />
<link href="${jsPath}/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script src="${jsPath}/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${jsPath}/jquery-easyui/local/easyui-lang-${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']}.js" type="text/javascript"></script>
<link href="${jsPath}/jquery-easyui/themes/${cookie.easyuiTheme.value==null?'metro-blue':cookie.easyuiTheme.value}/easyui.css" id="swicth-style" rel="stylesheet" type="text/css" />