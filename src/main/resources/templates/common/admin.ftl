<#macro head title="拉拉队-助你成功，为你加油">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title}</title>
<link style="text/css" rel="stylesheet" href="${base}/${css_admin}" />
<link rel="stylesheet" href="${base}/font-awesome-4.7.0/css/font-awesome.min.css">
<script language="javascript" type="text/javascript" src="${base}/js/jquery-1.8.3.min.js" ></script>
<script language="javascript" type="text/javascript" src="${base}/js/init.js" ></script>
<script language="javascript" type="text/javascript" src="${base}/js/tool.js" ></script>
<script type="text/javascript" src="${base}/js/mbox.js"></script>
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
				<a href="${base}/manage/tables" class="curr"><i class="fa fa-database" aria-hidden="true"></i> 我的表格</a>
			</div>
			<div class="menu">
				<a href="${base}/manage/rows"><i class="fa fa-bars" aria-hidden="true"></i> 数据信息</a>
			</div>
			<div class="menu">
				<a href=""><i class="fa fa-paper-plane" aria-hidden="true"></i> 报名信息 </a>
			</div>
			<div class="menu">
				<a href=""><i class="fa fa-hourglass-end" aria-hidden="true"></i> 预约信息</a>
			</div>
			<div class="menu">
				<a href=""><i class="fa fa-cog" aria-hidden="true"></i> 系统设置</a>
			</div>
		</div>
	</div>
	
	<div id="id_main">
		<div id="id_main_top">
			<div id="id_main_top_left"><i class="fa fa-outdent" aria-hidden="true"></i> &nbsp;&nbsp;首页  /  我的表格</div>
			<div id="id_main_top_right">
				<div style="height: 40px;width: 40px;background: #8BE2E0;color: #fff;border-radius: 100px;line-height: 40px;text-align: center;font-size: 16px;font-weight: bold;margin-top: 4px;">${userDetails.realName[0]}</div>
				<i class="fa fa-sort-desc" aria-hidden="true"></i>
				<div class="useropt popover" style="width: 80px;">
					<div class="popover-content">
						<div class="pop_menu">
							<ul>
								<li class=""><a href="javascript:void(0);"><span>修改密码</span></a></li>
								<li class=""><a href="${base}/SystemLogout"><span>退出登录</span></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div>
			<#nested>
		</div>
	</div>
</div>

<script type="text/javascript">
<#if menu!="">$(".${menu}").addClass("curr");</#if>
$(".fa-sort-desc").mbox({
	data:$(".useropt"),
	left:function(a,b){
		return -30
	},
	top:function(a,b){
		return 56
	}
});
</script>
</body>
</html>
</#macro>