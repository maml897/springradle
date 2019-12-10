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
<div id="cf" style="height: 48px;line-height: 48px;margin-bottom: 0px;">
	<div style="float: right;margin-right: 10px;">
		<a href="" style="color: #9da1c4"><i class="fa fa-search" aria-hidden="true"></i></a>&nbsp;&nbsp;
		<a href="" style="color: #9da1c4"><i class="fa fa-list-ul" aria-hidden="true"></i></a>&nbsp;&nbsp;
		<span style="color: #ddd">|</span>&nbsp;&nbsp;
		<a href="" style="color: #9da1c4"><i class="fa fa-cloud-upload" aria-hidden="true"></i> 导入</a>&nbsp;&nbsp;
		<a href="" style="color: #9da1c4"><i class="fa fa-cloud-download" aria-hidden="true"></i> 导出</a>&nbsp;&nbsp;
		<a href="" style="color: #9da1c4"><i class="fa fa-plus-square" aria-hidden="true"></i> 增加记录</a>&nbsp;&nbsp;
		<span style="color: #ddd">|</span>&nbsp;&nbsp;
		<a href="${base}/manage/tables" style="color: #9da1c4"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> 返回</a>&nbsp;&nbsp;
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
