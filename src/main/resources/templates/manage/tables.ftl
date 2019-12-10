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
.info .items span{font-size: 14px;}
.appcolor{
	display: block;
    text-align: center;
    font-weight: 400!important;
    text-decoration: none!important;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    -webkit-text-stroke-width: .22px;
    position: relative;
    font-family: hbicon;
    padding-left: 1px;
    margin: 0 auto 5px;
    width: 28px;
    height: 28px;
    line-height: 28px;
    font-size: 28px;
    color: #66bb6a;
    border: 1px solid #66bb6a;
    background-color: #66bb6a;
    border: none!important;
    background-color: transparent!important;
    font-style: normal;
}
</style>
</@t_admin.head>
<@t_admin.body position="首页" menu="icon4">
<div id="cf" style="padding: 16px;">
	<div class="info" style="margin: 0 auto;">
		<div class="items">
			<i class="appcolor color_b"></i>
			<span>导入本地Excel</span>
		</div>
		<div class="items">
			<i class="appcolor color_b"></i>
			<span>选择应用市场模板</span>
		</div>
		<div class="items" style="border-right:none;">
			<i class="appcolor color_b"></i>
			<span>手动创建表格</span>
		</div>
	</div>
</div>
<div style="margin: 0 10px;background: #fff;">
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
