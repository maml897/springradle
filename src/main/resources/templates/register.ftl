<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>啦啦队</title>
<link style="text/css" rel="stylesheet" href="${base}/css/login.css" />
<script type="text/javascript" src="${base}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#id_reg").click(function(){
		$("#id_form").submit();
	});
	
	var tip=$("#id_tip");
	function valid(message){
		tip.html(message).show().delay(2000).fadeOut();
	}
	
	$("#id_form").submit(function(){
		var realName = $("#id_realName");
		if(realName.val()==""){
			valid("姓名不能为空");
			realName.focus();
			return false;
		}
		if(realName.val().length>7){
			valid("姓名不能操过6个字");
			realName.focus();
			return false;
		}
		
		var phone = $("#id_phone");
		if(phone.val()==""){
			valid("手机号不能为空");
			phone.focus();
			return false;
		}
		var phoneReg=/^1[0-9]{10}$/;
		if(!phoneReg.test(phone.val())){
			valid("手机号格式不正确");
			phone.focus();
			return false;
		}
		
		var mail = $("#id_mail");
		/*
		if(mail.val()==""){
			valid("邮箱不允许为空");
			phone.focus();
			return false;
		}
		*/
		if(mail.val()!=""){
			var mailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			if(!mailReg.test(mail.val())){
				valid("邮箱格式不正确");
				mail.focus();
				return false;
			}
		}
		
		var password = $("#id_password");
		if(password.val()==""){
			valid("请输入密码");
			password.focus();
			return false;
		}
		if(password.val().length<6){
			valid("密码长度必须大于6位");
			password.focus();
			return false;
		}
		
		var password1 = $("#id_password1");
		if(password1.val()==""){
			valid("请输入确认密码");
			password.focus();
			return false;
		}
		if(password1.val()!=password.val()){
			valid("两次输入密码不一致");
			password.focus();
			return false;
		}
		
		return true;
	});
	
	<#if flag??>
	<#if flag="phone">valid("手机号已经存在，请更换。")</#if>
	<#if flag="mail">valid("邮箱已经存在，请更换。")</#if>
	</#if>
	
});
</script>
</head>
<body>
	<div class="tip" id="id_tip"></div>
	<div class="content">
		<#include "inc/banner.ftl">
		<div class="form-container">
			<form id="id_form" method="post" action="${base}/register/register-process">
				<div class="form-ctrl" target="phone">
					<input type="text" placeholder="姓名" name="realName" id="id_realName" value="${realName}" autocomplete="off">
				</div>
				<div class="form-ctrl" target="phone">
					<input type="text" placeholder="手机号，必填，用于登录" name="phone" id="id_phone" value="${phone}" autocomplete="off">
				</div>
				<div class="form-ctrl" target="phone">
					<input type="text" placeholder="邮箱，建议输入，用于找回密码" name="mail" id="id_mail" value="${mail}" autocomplete="off">
				</div>
				<div class="form-ctrl" id="pass">
					<input type="password" placeholder="请输入不少于6位密码" name="password" id="id_password" value="${password!}">
				</div>
				<div class="form-ctrl" id="pass">
					<input type="password" placeholder="再次输入确认密码" style="border-bottom: none;" id="id_password1" value="${password!}">
				</div>
			</form>
		</div>
		<div class="reg" id="id_reg">注册</div>
		<div class="type" id="type" style="text-align: right;margin-top: 6px;">
				<a href="${base}/login">已有账号，去登录</a>
		</div>
	</div>
	<#include "inc/foot.ftl">
</body>
</html>