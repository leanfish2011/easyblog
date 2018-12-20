/**
 * 登录页使用的脚本
 */

$(document).ready(function() {
	$("#txtUserCode").blur(function() {
		if ($("#txtUserCode").val() == "") {
			$("#exMsg").html(localResource["errorUserCodeMust"]);
		} else {
			$("#exMsg").html("");
			// 参数，这里是一个json语句
			var param = {
				"userCode" : $("#txtUserCode").val()
			};
			$.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : getContextPath()+'/Login/isExist.do',
				dataType : 'json',
				data : param,
				success : function(data) {
					if (data && data.success == "false") {
						$("#exMsg").html($("#txtUserCode").val() +'&nbsp;'+ localResource["errorUserNotExist"]);
					} else {
						$("#exMsg").html("");
					}
				},
				error : function() {
					$("#exMsg").html(localResource["errorVerfiUserExist"]);
				}
			});
		}
	});

	$("#txtUserCode").focus(function() {
		$(this).css('border', '2px solid #69C3C1');
	}).blur(function() {
		$(this).css('border', '');
	});

	$("#txtPass").focus(function() {
		$(this).css('border', '2px solid #69C3C1');
	}).blur(function() {
		$(this).css('border', '');
	});

	// 点击登录
	$("#btnLogin").click(function() {
		if (checkForm()) {
			var params = $("#loginForm").serialize();
			$.ajax({
				type : 'POST',
				url : getContextPath()+'/Login/login.do',
				data : params,
				dataType : 'json',
				success : function(data) {
					if (data && data.success == "true") {
						$("#exMsg").html($("#txtUserCode").val() +'&nbsp;'+ localResource["loginSuccess"]);
						
						window.location.href = getContextPath()+"/admin/index.do";// 跳转到管理页
					} else {
						$("#exMsg").html(localResource["loginFail"]);
					}
				},
				error : function() {
					$("#exMsg").html(localResource["errorLogin"]);
				}
			});
		}
	});
});

// 登录前参数检查
function checkForm() {
	var username = $("#txtUserCode").val();
	var password = $("#txtPass").val();
	if (username == null || username == "") {
		$("#exMsg").html(localResource["errorUserCodeMust"]);
		return false;
	}

	if (password == null || password == "") {
		$("#exMsg").html(localResource["errorPasswordMust"]);
		return false;
	}

	return true;
}

// 按下回车键
document.onkeypress = function(e) {
	e = e || event;
	if (e.keyCode == 13) {
		$("#btnLogin").click();
		event.returnValue = false;
	}
}

// 取消
function cancel() {
	$("#txtUserCode").val("");
	$("#txtPass").val("");
}