/**
 * 注册页使用的脚本
 */

$(document).ready(function() {
	// 禁用提交按钮
	$("#btnsubmit").attr({
		"disabled" : "disabled"
	});

	//用户名输入框获得焦点
	$("#userCode").blur(function() {
		if ($("#userCode").val() == "") {
			$("#exMsg").html("用户名不能为空，请重新输入用户名！");
			
			// 禁用提交按钮
			$("#btnsubmit").attr({
				"disabled" : "disabled"
			});
		} else {
			$("#exMsg").html("");
			$("#btnsubmit").removeAttr("disabled");// 将按钮可用

			// 参数，这里是一个json语句
			var param = {
				"userCode" : $("#userCode").val()
			};
			$.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : getContextPath() + '/Login/isExist.do',
				dataType : 'json',
				data : param,
				success : function(data) {
					if (data && data.success == "true") {
						$("#exMsg").html("用户名为：" + $("#userCode").val()+ "的用户已经存在，请重新输入用户名！");
						
						// 禁用提交按钮
						$("#btnsubmit").attr({
							"disabled" : "disabled"
						});
					} else {
						$("#exMsg").html("");
						$("#btnsubmit").removeAttr("disabled");// 将按钮可用
					}
				},
				error : function() {
					alert("注册用户出现错误！");
				}
			});
		}
	});

	// 验证两次输入的密码是否一致
	$("#userPasswordAgain").blur(function() {
		var userPsword = $("#userPassword").val();
		var userPswordAgain = $("#userPasswordAgain").val();

		if (userPsword != userPswordAgain) {
			$("#exMsg").html("两次输入的密码不一致，请确认！");
			
			// 禁用提交按钮
			$("#btnsubmit").attr({
				"disabled" : "disabled"
			});
		} else {
			$("#exMsg").html("");
			$("#btnsubmit").removeAttr("disabled");// 将按钮可用
		}
	});

	// 验证两次输入的密码是否一致
	$("#userPassword").blur(function() {
		var userPsword = $("#userPassword").val();
		var userPswordAgain = $("#userPasswordAgain").val();

		if (userPswordAgain != "" && userPsword != userPswordAgain) {
			$("#exMsg").html("两次输入的密码不一致，请确认！");
			
			// 禁用提交按钮
			$("#btnsubmit").attr({
				"disabled" : "disabled"
			});
		} else {
			$("#exMsg").html("");
			$("#btnsubmit").removeAttr("disabled");// 将按钮可用
		}
	});
	
	// 点击注册
	$("#btnsubmit").click(function() {
		var params=new FormData($("#registerform")[0]);//使用FormData接收数据
		$.ajax({
			type : 'POST',
			url : getContextPath()+'/admin/user/registerUser.do',
			data : params,
			dataType : 'json',
			processData : false,//使用FormData这个必须加上
			contentType : false,//使用FormData这个必须加上
			success : function(data) {
				if (data && data.success == "true") {
					$("#exMsg").html($("#userCode").val() + "注册成功！");
				} else {
					$("#exMsg").html(data.content);
				}
			},
			error : function() {
				$("#exMsg").html("注册出现网络错误！");
			}
		});
	});
});

//显示用户头像
function imagesSelected(myFiles) {
	  var f = myFiles[0];//
	    var imageReader = new FileReader();
	    imageReader.onload = (function (aFile) {
	        return function (e) {
	            $("#userPhoto").attr("src",e.target.result);
	        };
	    })(f);
	    
	    imageReader.readAsDataURL(f);
}


