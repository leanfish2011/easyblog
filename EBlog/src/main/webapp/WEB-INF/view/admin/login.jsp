<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="blogworld"/>-<spring:message code="login"/></title>
	<link href="${cssPath}/login.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${jsPath}/tools/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${jsPath}/tools/common.js"></script>
	<script type="text/javascript" src="${jsPath}/view/login.js"></script>
	<script type="text/javascript">
		//外部引入的js不能直接使用标签，页面先读取所有的资源，外部js中再使用
		var localResource={
				'errorUserCodeMust':'<spring:message code="errorUserCodeMust"/>',
				'errorUserNotExist':'<spring:message code="errorUserNotExist"/>',
				'errorVerfiUserExist':'<spring:message code="errorVerfiUserExist"/>',
				'loginSuccess':'<spring:message code="loginSuccess"/>',
				'loginFail':'<spring:message code="loginFail"/>',
				'errorLogin':'<spring:message code="errorLogin"/>',
				'errorPasswordMust':'<spring:message code="errorPasswordMust"/>'
		};
	</script>
</head>
<body style="background-color: #EEF2F5;">
    <a style="float: right;" href="../admin/loginByPhoto.jsp"><spring:message code="loginByPhoto"/></a>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0" style="border: 5px solid #5590E8;background-color: #FEFEFE;border-color: ">
		<tbody>
			<tr>
				<td align="center"><br>
					<table width="570" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" width="570" border="0">
									<tbody>
										<tr>
										    <td><img style="float: right;" src="${imgPath}/log.jpg"></td>
											<td width="230" height="40" align="center" valign="top">
												<p id="logTitle" style="height: 10px; width: 230px; font-size: 25px;color:#E33E06;"><spring:message code="blogworld"/>-<spring:message code="login"/></p>
											</td>
											<td align="right" valign="top">
												<a href="${ctxPath}/main/register.do"><spring:message code="registNow"/></a>
												&nbsp;&nbsp;
												<a href="${ctxPath}/main/index.do"><spring:message code="goToIndex"/></a>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<p style="height: 5px;" id="exMsg"></p>
								<form id="loginForm">
									<table width="520" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center">
												<table cellspacing="0" cellpadding="5" border="0">
													<tr>
														<td height="25" valign="top"><spring:message code="userCode"/>： <input tabindex="1" maxlength="22" size="25" name="userCode" id="txtUserCode" />
														</td>
													</tr>
													<tr>
														<td valign="bottom" height="12"><spring:message code="password"/>： <input name="userPassWord" type="password" tabindex="1" size="25" maxlength="22" id="txtPass" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
														<input type="button" id="btnCancel" onClick="cancel();" value="<spring:message code="cancel"/>" /> 
														<input type="button" id="btnLogin" value="<spring:message code="login"/>" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="70" align="center"><spring:message code="rightAll"/>：Tim©2017</td>
									</tr>
								</table>
							</td>
						</tr>
					</table></td>
			</tr>
		</tbody>
	</table>
</body>
</html>