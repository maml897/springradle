<@t_admin.head>
<script type="text/javascript" src="${base}/js/tabledrag.js"></script>
<script type="text/javascript" src="${base}/js/mbox.js"></script>
<script type="text/javascript" src="${base}/js/jquery.qrcode.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".layui-form-checkbox").click(function(){
		var $this=$(this);
		var id=$this.attr("data-id");
		var show=!$this.hasClass("selected");
		$this.addClass("sending");
		$.ajax({
			url:"${base}/manage/set-column-searchshow?columnID="+id+"&show="+show,
			success:function(){
				$this.toggleClass("selected");
				$this.removeClass("sending");
			}
		});
	});
	
	var url="http://"+document.domain+""+window.location.pathname+"/q/${tableID}";
	$("#id_url").val(url);
	$(".qr").qrcode({width: 180,height: 180,text:url });
	
	var info=$(".columndata").mbox({
		data:$(".popover")
	});
	
	$(".sel").click(function(){
		var $this=$(this);
		$.ajax({
			url:"${base}/manage/set-column-type?columnID="+info.source.attr("rel")+"&type="+$this.attr("alt"),
			success:function(data){
				info.source.html($this.text());
				info.close();
			}
		});
	});
});
</script>
<style type="text/css">
.block{
	padding: 10px;
	min-height: 200px;
	margin-bottom: 10px;
	border-color: #e6e6e6;
    border-style: solid;
    border-width: 1px 1px 1px 5px;
    background: 0 0;
}

.block p{
	font-size: 14px;
	font-weight: bold;
	margin-bottom: 10px;
}

.layui-form-checkbox {
 	margin: 4px 0;
    position: relative;
    height: 30px;
    line-height: 30px;
    padding-right: 30px;
    cursor: pointer;
    font-size: 0;
    /*
    -webkit-transition: .1s linear;
    transition: .1s linear;
    */
    box-sizing: border-box;
    border-radius: 2px;
    border: 1px solid #d2d2d2;
}
.layui-form-checkbox, .layui-form-checkbox * {
    display: inline-block;
    vertical-align: middle;
}
.layui-form-checkbox span {
    padding: 0 10px;
    height: 100%;
    font-size: 14px;
    background-color: #d2d2d2;
    color: #fff;
    overflow: hidden;
}

.layui-form-checkbox i {
    position: absolute;
    right: 0;
    top: 0;
    width: 30px;
    height: 28px;
    color: #fff;
    font-size: 20px;
    text-align: center;
}

.layui-form-checkbox:hover i{
    color: #d2d2d2;
}
.layui-form-checkbox:hover i:before,.layui-form-checkbox.selected i:before{
    content: "√";
}

/*选中*/
.layui-form-checkbox.selected{
    border: 1px solid #5FB878;
}
.layui-form-checkbox.selected span{
    background-color: #5FB878;
}
.layui-form-checkbox.selected i{
	color: #5FB878;
	border-color: #5FB878;
}

/*发送中*/
.sending{
	background: url("${base}/img/load.gif") no-repeat 56px center;
}
.sending i{
	display: none;
}

/*二维码*/
.qr{margin-top: 20px;}
.columndata{
	height: 24px;
	width: 100px;
	background: #f2f6ff;
	line-height: 24px;
	cursor: pointer;
	color: #666;
	position: relative;
	width: 100px;
	display: block;
	margin:0 auto;
}
/*mbox*/
.quan{
	display: block;
	position: absolute;
	border-radius:100px;
	width: 14px;height: 14px;
	text-align: center;
	line-height: 14px;
	background: #5FB878;
	color: #fff;
}
.search_column{padding: 10px;}
.search_column:hover .delete{
	display: block;
}

.search_column .number{display: none;}
.search_column .delete{
	right:8px;bottom:8px;
	cursor: pointer;
	background: red;
	display: none;
}

.search_column.on{
	margin-bottom: 18px;
	border:1px dotted #aaa;
	position: relative;
}
.search_column.on .number{
	top:-8px;left:-8px;
	display: block;
}
</style>
</@t_admin.head>
<@t_admin.body position="首页" menu="icon3">
<div style="margin: 20px;background: #fff;padding: 20px;">
	<div style="float: left;width: 46%;">
		<#---
		<div class="block" style="min-height:100px;">
			<p>1.查询条件设置</p>
			<div>+增加一组查询条件</div>
			
			<div class="search_column on">
				<div class="number quan">1</div>
				<div class="delete quan">X</div>
				<#list columns as item> 
				<div class="layui-form-checkbox" data-id="${item.id}"><span>${item.title}</span><i></i></div>
				</#list>
			</div>
			
			<div class="search_column on">
				<div class="number quan">2</div>
				<div class="delete quan">X</div>
				<#list columns as item> 
				<div class="layui-form-checkbox" data-id="${item.id}"><span>${item.title}</span><i></i></div>
				</#list>
			</div>
			<div style="text-decoration: underline;"><a href="" style="color: #333;">+增加一组查询条件</a></div>
		</div>
		-->
		<div class="block">
			<p>1.结果显示设置</p>
			<table class="tblist">
				<tr>
					<td style="text-align: left;padding-left: 12px;width: 100px;">名称</td>
					<td style="width: 80px;">属性</td>
					<td style="width: 120px;">查询条件设置</td>
					<td style="width: 120px;">查询结果是否显示</td>
				</tr>
				<#list columns as item>
				<tr>
					<td style="text-align: left;padding-left: 12px;width: 100px;"><b>${item.title}</b></td>
					<td style="width: 80px;"><a class="columndata" rel="${item.id}">${Constant.columnType(item.type)}</a></td>
					<td style="width: 120px;"></td>
					<td style="width: 120px;"><div class="layui-form-checkbox<#if item.searchShow> selected</#if>" data-id="${item.id}"><span>显示</span><i></i></div></td>
				</tr>
				</#list>
			</table>
		</div>
		<div class="block">
			<p>2.开启查询</p>
			<div style="text-align: center;">扫描下方二维码或者复制链接分享到朋友圈</div>
			<div style="text-align: center;">
				<div class="qr"> </div>
				<input value="http://www.lala.team/query/${tableID}" style="width: 200px;margin-top: 20px;" id="id_url"/>
			</div>
		</div>
	</div>
	
	<div style="float: right;width: 50%;background: url('${base}/img/bgiphone.png') no-repeat 0px top;height: 800px;">
		<iframe src="${base}/query/${tableID}" style="border: none;height: 590px;margin-top: 100px;margin-left: 20px;width: 340px;"></iframe>
	</div>
	<div style="clear: both;"></div>
</div>
<div class="popover bottom-left in" style="display: block; height: auto; width: 148px; max-width: 148px; left: 715px; top: 168px;display: none;">
	<div class="popover-content ">
		<div class="pop_menu cl">
			<ul class="cl">
				<#list Constant.columnType as k,v>
				<li class="sel" alt="${k}"><a href="###"><span>${v}</span></a></li>
				</#list>
			</ul>
		</div>
	</div>
</div>
</@t_admin.body>
