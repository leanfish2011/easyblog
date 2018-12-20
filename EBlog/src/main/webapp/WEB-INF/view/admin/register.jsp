<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>博客世界-注册</title>
	<link href="${cssPath}/login.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="${jsPath}/tools/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${jsPath}/tools/common.js"></script>
	<script type="text/javascript" src="${jsPath}/view/register.js"></script>
</head>
<body>
	<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td width="245" height="40" align="center" valign="top">
				<p style="height: 10px; width: 245px; font-size: 25px;">博客世界管理-注册</p>
			</td>
			<td align="right" valign="top">
				<a href="${ctxPath}/Login/loginpage.do">立即登录</a> 
				<a href="${ctxPath}/main/index.do">回到主页</a>
			</td>						
		</tr>
		<tr>
			<td align="center">
				<p style="height: 5px;" id="exMsg"></p>
				<form id="registerform" enctype="multipart/form-data">
					<table  width="400">
						<tr>
							<td>用户名：</td>
							<td><input id="userCode" type="text" name="userCode" /></td>
						</tr>
						<tr>
							<td>密码：</td>
							<td><input id="userPassword" type="password" name="userPassword" /></td>
						</tr>
						<tr>
							<td>确认密码：</td>
							<td><input id="userPasswordAgain" type="password" name="userPasswordAgain" /></td>
						</tr>
						<tr>
							<td>姓名：</td>
							<td><input type="text" name="userName" /></td>
						</tr>
						<tr>
							<td>邮箱：</td>
							<td><input type="text" name="email" /></td>
						</tr>
						<tr>
							<td>头像：</td>
							<td><input type="file" name="uploadFile" onchange="imagesSelected(this.files)" /></td>
						</tr>
						<tr>
							<td><input id="btnreset" type="reset" value="重填" /></td>
							<td><input id="btnsubmit" type="button" value="提交" /></td>
						</tr>	
					</table>
				</form>
			</td>
			<td><img id="userPhoto" alt="用户头像" src="" style="width:200px;height:200px;" /></td>
		</tr>
		<tr>
			<td height="70" align="center">版权所有：余飞©2017</td>
			<td></td>				
		</tr>
	</table>
</body>
</html>