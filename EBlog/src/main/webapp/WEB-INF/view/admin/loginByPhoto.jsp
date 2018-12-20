<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>使用头像登录</title>
	<script type="text/javascript" src="${jsPath}/tools/jquery-1.7.2.min.js"></script>
</head>
<body>
	<video id="userVideo" width="700" height="500" style="border: 1px solid #95B8E7;" autoplay></video>
	<canvas id="userCanvas" width="700" height="500" style="border: 1px solid #95B8E7; display: none;"></canvas>
	<div>
		<span id="message"></span> <input type="button" id="btnCancel" value="取消" /> <input type="button" id="btnGetImg" value="确定" />
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {

		// 获取元素，设置属性
		var canvas = document.getElementById("userCanvas");
		var context = canvas.getContext("2d");

		var video = document.getElementById("userVideo");
		var videoObj = {
			"video" : true
		};
		var errBack = function(error) {
			$("#message").html("错误: ", error.code);
		};

		//获取视频，并自动播放。（判断各种浏览器）
		if (navigator.getUserMedia) { // Standard
			navigator.getUserMedia(videoObj, function(stream) {
				video.src = stream;
				video.play();
			}, errBack);
		} else if (navigator.webkitGetUserMedia) { // WebKit-prefixed
			navigator.webkitGetUserMedia(videoObj, function(stream) {
				video.src = window.webkitURL.createObjectURL(stream);
				video.play();
			}, errBack);
		} else if (navigator.mozGetUserMedia) { // WebKit-prefixed
			navigator.mozGetUserMedia(videoObj, function(stream) {
				video.src = window.URL.createObjectURL(stream);
				video.play();
			}, errBack);
		}

		//点击确定，进行拍照
		$("#btnGetImg").click(function() {
			$("#userVideo").hide();//隐藏视频界面
			$("#userCanvas").show();//显示图片

			//将当前视频绘制到画布上
			context.drawImage(video, 0, 0, 700, 500);
			loginPhoto();
		});

		//点击“取消”按钮
		$("#btnCancel").click(function() {
			$("#userVideo").show();//显示视频界面
			$("#userCanvas").hide();//隐藏图片
		});

	});

	//得到图片，将图片上传到服务器端进行登录验证
	function loginPhoto() {
		var imgcanvas = document.getElementById("userCanvas");

		//以下开始编数据
		var imgData = imgcanvas.toDataURL();

		//将图像转换为base64数据
		//在前端截取22位之后的字符串作为图像数据
		var base64Data = imgData.substr(22);

		var param = {
			"userPhoto" : base64Data
		};
		$.ajax({
			type : 'POST',
			url : '${ctxPath}/LoginByPhoto/photoLogin.do',
			data : param,
			dataType : 'json',
			success : function(data) {
				if (data && data.success == "true") {
					$("#message").html("登录成功！");
					window.location.href = "../admin/admin.jsp";//跳转到管理页
				} else {
					$("#message").html("登录失败，用户头像不匹配！");
				}
			},
			error : function() {
				$("#message").html("登录出现网络错误！");
			}
		});
	}
</script>
</html>