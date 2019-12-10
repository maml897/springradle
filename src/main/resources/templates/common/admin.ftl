<#macro head title="拉拉队-助你成功，为你加油">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title}</title>
<link style="text/css" rel="stylesheet" href="${base}/${css_admin}" />
<script language="javascript" type="text/javascript" src="${base}/js/jquery-1.8.3.min.js" ></script>
<script language="javascript" type="text/javascript" src="${base}/js/init.js" ></script>
<script language="javascript" type="text/javascript" src="${base}/js/tool.js" ></script>
<#nested>
</head>
</#macro>
<#macro body position="" menu="">
<body>
<div id="id_container">
	<div id="id_left">
		<div id="id_left_top">
			<span style="font-size: 18px;position: absolute;top:10px;left: 24px;background: url('${base}/svg/logo.svg') no-repeat 0px 2px;padding-left: 26px;color: rgb(191, 203, 217);">拉拉队</span>
			<span style="position: absolute;bottom: 0px;left:26px;font-size: 12px;color: #4caf50;">www.lala.team</span>
		</div>
		<div class="menus">
			<div class="menu">
				<a class="icon3" href="${base}/manage/rows">数据信息</a>
			</div>
			<div class="menu">
				<a class="icon4" href="${base}/manage/tables">我的表格</a>
			</div>
			<div class="menu">
				<a class="icon2" href="">报名信息 </a>
			</div>
			<div class="menu">
				<a class="icon1" href="">预约信息</a>
			</div>
			<div class="menu">
				<a class="icon5" href="">系统设置</a>
			</div>
		</div>
	</div>
	
	<div id="id_main">
		<div id="id_main_top">
			<div id="id_main_top_left">首页 / 我的表格</div>
			<div id="id_main_top_right">${userDetails.realName}，欢迎您登录！ <a href="${base}/SystemLogout"><b>退出</b></a></div>
		</div>
		<div id="id_main_"><#nested></div>
	</div>
</div>
<#if menu!="">
<script type="text/javascript">
$(".${menu}").addClass("curr");
</script>
</#if>
</body>
</html>
</#macro>