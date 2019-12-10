<@t_admin.head>
<script type="text/javascript">
$(function(){
	$(".items").click(function(){
		window.location.href="${base}/manage/import-table";
	});
});
</script>
<style type="text/css">
.info{text-align: center;}
.info .items{display: inline-block;cursor: pointer;border-right: 1px solid #ddd;padding: 0 40px;}
.info .items div{font-size: 14px;margin-top: 8px;}
.info .items i{font-size: 28px;}
</style>
</@t_admin.head>
<@t_admin.body position="首页" menu="icon4">
<div id="cf" style="padding: 16px;">
	<div class="info" style="margin: 0 auto;">
		<div class="items">
			<i class="fa fa-cloud-upload" aria-hidden="true" style="color: #66bb6a;"></i>
			<div>导入本地Excel</div>
		</div>
		<div class="items">
			<i class="fa fa-life-ring" aria-hidden="true" style="color:#ffa726;"></i>
			<div>选择应用市场模板</div>
		</div>
		<div class="items" style="border-right:none;">
			<i class="fa fa-hand-pointer-o" aria-hidden="true" style="color:#5c6bc0;"></i>
			<div>手动创建表格</div>
		</div>
	</div>
</div>
<div style="margin: 0 10px;background: #fff;">
	<#--
	<#list page.list as item>
	<div style="width:80px;float: left;margin-right: 16px;text-align: center;" class="table">
		<div style="width: 60px;height: 60px;margin-bottom: 4px;margin-left: 10px;" class="xls"></div>
		<div>${item.title}</div>
	</div>
	</#list>
	-->
	<table class="tblist">
		<tr class="title">
			<td>表名称</td>
			<td>创建时间</td>
			<td>设置</td>
		</tr>
		<#list page.list as item>
		<tr>
			<td style="text-align: left;"><a href="${base}/manage/rows/${item.id}">${item.title}</a></td>
			<td>${item.createDate}</td>
			<td>
				<a class="btn" href="${base}/manage/table-set?tableID=${item.id}">表格设置</a>
				<a class="btn" href="${base}/manage/q-set?tableID=${item.id}">查询设置</a>
			</td>
		</tr>
		</#list>
	</table>
	
</div>
</@t_admin.body>
