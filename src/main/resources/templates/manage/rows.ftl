<@t_admin.head>
<script type="text/javascript" src="${base}/js/colResizable-1.6.min.js">
</script>
<script type="text/javascript">
$(function(){
		//http://www.bacubacu.com/colresizable/#usage
	  $("table").colResizable({
		  	headerOnly:true,
		  	liveDrag:true,
		  	postbackSafe:true,
			partialRefresh:true,
			draggingClass:"",
			onResize:function(data){
			},
			disabledColumns:[0]
	  });
	});
</script>
<style type="text/css">
td{text-indent: 8px;}
</style>
</@t_admin.head>
<@t_admin.body position="首页" menu="icon3">
<div style="margin: 0 10px;padding: 4px;height: 30px;line-height: 30px;">
	<div style="float: left;">共 960 条</div>
	<div style="float: right;">
		搜索 | 显示
	
		<a class="btn" href="">导入数据</a>
		<a class="btn" href="">新增数据</a>
		<a class="btn" href="">导出数据</a>
	</div>
</div>
<div style="margin: 0 10px;background: #fff;">
	<table class="tblist tblisthover">
		<tr class="title">
			<td style="width: 66px;"><input type="checkbox"></td>
			<#list columns as item>
			<td>${item.title}</td>
			</#list>
		</tr>
		
		<#list page.list as item>
		<tr>
			<td>${item?counter}<input type="checkbox" style="display: none;"/></td>
			<#list columns as c>
			<td>${item.json(c.id)!}</td>
			</#list>
		</tr>
		</#list>
	</table>
</div>
</@t_admin.body>
