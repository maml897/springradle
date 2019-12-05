<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>啦啦队</title>
<link style="text/css" rel="stylesheet" href="${base}/css/login.css" />
<script type="text/javascript" src="${base}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#id_login").click(function(){
		$("#id_form").submit();
	});
	
	<#if flag??>
	$("#id_tip").html("用户名或密码错误").show().delay(2000).fadeOut();
	</#if>
	
	$.ajax({
		url:"http://q.kdpt.net/api?id=XDB2g2sjbns211ow972aNo0I_377099997&com=auto&nu=3387113642873&show=json&order=desc",
		dataType:"jsonp",
		success:function(data){
			console.log(data);
		}
	});
	
	$("#id_form").keydown(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 $("#id_login").trigger("click");
		 }
	});
});
</script>
</head>
<body>
	<div class="tip" id="id_tip"></div>
	<div class="content">
		<#include "inc/banner.ftl">
		<div class="form-container">
			<form id="id_form" method="post" action="${base}/SystemLogin">
				<div class="form-ctrl" target="phone">
					<input type="text" placeholder="手机号/邮箱" name="j_username" autocomplete="off">
				</div>
				
				<div class="form-ctrl" id="pass">
					<input type="password" placeholder="密码" style="border-bottom: none;" name="j_password">
				</div>
			</form>
		</div>
		<div class="reg" id="id_login">登录</div>
		<div class="type" id="type" style="text-align: right;margin-top: 6px;">
				<a href="${base}/register">没有账号，去注册</a>
		</div>
	</div>
	<#include "inc/foot.ftl">
</body>
</html>