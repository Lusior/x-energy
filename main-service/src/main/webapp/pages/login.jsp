<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>轩云科技能耗分析系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico">
<style>
	.error {
		color: red;
		font-size:12px;
	}
	 .lag a:hover{
    color :  #FF0000;
	text-decoration: none;
}
</style>
</head>
<body>
	<div id="main-content">
	<div class="title">能耗分析系统</div>
        <div class="center-content">
            <div class="center-img">
                <img src="img/shanghu_center.png" alt="能耗分析系统">
            </div>
		<div class="login-wrap">
			<h3>登录</h3>
			<form id="loginform" action="" method="post">
				<ul>
					<li class="username"><i></i><input name="loginId"   placeholder="用户名" value="${loginId}" autofocus="autofocus"></li>
					<li class="pwd"><i></i><input name="loginPwd"  type="password" placeholder="密码" value="${loginPwd}"></li>
					<!--li> class="code"><i></i><input type="text" name="jcaptchaCode" autocomplete="off" placeholder="验证码">
						<div class="codeImg">
							<img id="captcha" src="${pageContext.request.contextPath}/jcaptcha.jpg" >
						</div>
					</li-->
					<div class="error">${error}</div>
				</ul>
				<button type="submit" id="btButton" class="submit">登录</button>
			</form>
		</div>
	</div>
	</div>
	<script>
		$(document).ready(
			function() {
				window.onload = function() {
					$('body').height($('#main-content').height())
				};
				$("#captcha").click(
					function() {
						$("#captcha").attr("src",
							'${pageContext.request.contextPath}/jcaptcha.jpg?'
								+ new Date().getTime());
					});
				// 表单提交
				$('#btButton').click(function() {
					var jigou = $('.jigou input').val();
					var username = $('.username input').val();
					var password = $('.pwd input').val();
					var code = $('.code input').val();
					if (username.length == '') {
		                $('.error').html('用户名不能空');
		                return false;
		            } else if (password.length == '') {
		            	 $('.error').html('密码不能为空');
		                return false;
		            }// else if(code.length == ''){
		            // 	 $('.error').html('验证码不能为空');
			        //      return false;
		            // }
					$("#loginform").submit();
				});
			});
	</script>
</body>
</html>