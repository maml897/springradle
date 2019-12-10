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
<div id="cf">
	<div style="float: right;margin-right: 10px;">
		<a href="${base}/manage/import-table" class="btn" >+导入Excel表格</a>
		<a href="${base}/manage/import-table" class="btn" >+从模板选择表格</a>
		<a href="${base}/manage/import-table" class="btn" >+手动创建表格</a>
	</div>
</div>

<div style="margin: 0 10px;background: #fff;">
	<table class="tblist tblisthover">
		<tr class="title">
			<td style="width: 56px;text-align: center;"><input type="checkbox"></td>
			<#list columns as item>
			<td>${item.title}</td>
			</#list>
		</tr>
		
		<#list page.list as item>
		<tr>
			<td style="text-align: center;">${item?counter}<input type="checkbox" style="display: none;"/></td>
			<#list columns as c>
			<td>${item.json(c.id)!}</td>
			</#list>
		</tr>
		</#list>
	</table>
	
	<@t_page.paging/>
	
	<br/>
</div>
</@t_admin.body>
