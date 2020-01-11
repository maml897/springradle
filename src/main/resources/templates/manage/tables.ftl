<@t_admin.head>
<script src="${base}/js/drag.js"></script> 
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script type="text/javascript">
$(function(){
	//$(".tablecontent").mdrag();
	
	$(".items").click(function(){
		window.location.href="${base}/manage/import-table";
	});
	
	$(".table").click(function(event){
		var target=$(event.target);
		if(target.hasClass("optable")){
			return false;
		}
		window.location.href="${base}/manage/rows/"+$(this).attr("rel");
	});
	
	var dest=$(".optable").mbox({
		data:$(".tableopt"),
		left:function(a,b){
			return b.left-50
		},
		top:function(a,b){
			return b.top+20
		},
		onClose:function(){
			//$(".opt_menu").hide();
		},
		source:function($this){
			return $this.parent("div").parent("div");
		}
	});
	
	$(".to-edit").click(function(){
		window.location.href="${base}/manage/table-set?tableID="+dest.source.attr("rel");
	});
	$(".to-queryset").click(function(){
		window.location.href="${base}/manage/q-set?tableID="+dest.source.attr("rel");
	});
});
</script>
<style type="text/css">
.info{text-align: center;}
.info .items{display: inline-block;cursor: pointer;border-right: 1px solid #ddd;padding: 0 40px;}
.info .items div{font-size: 14px;margin-top: 8px;}
.info .items i{font-size: 28px;}

.table{width:90px;padding: 8px;height:100px;text-align: center;cursor: pointer;float: left;border: solid #eeecec;border-width: 1px 1px 1px 0;position: relative;}
.table .icon{width:40px;height: 40px;border: 1px solid #eee;border-radius: 100px;line-height: 40px;margin-left: 22px;margin-bottom: 7px;}
.table i{font-size: 24px;margin-top: 8px;}
.fa-angle-down{
	position: absolute;
	right:12px;
	top:20px;
	height:12px;
	width:12px;
	border-radius: 20px;
	background: transparent;
	border: 1px solid #ccc;
	color: #ccc;
	display: none;
}
.table:hover .fa-angle-down{display: block;}

.tablecontent{height: 100%;}

#ondragoverfun .icon{
	transition:0.2s ease all;
	transform:scale(1.2);
}
</style>
</@t_admin.head>
<@t_admin.body position="首页" menu="icon4">
<#include "inc/menu.ftl">
<div id="cf" style="padding: 16px;">
	<div class="info" style="margin: 0 auto;">
		<div class="items">
			<i class="fa fa-cloud-upload" aria-hidden="true" style="color: #66bb6a;"></i>
			<div>导入本地Excel</div>
		</div>
		<div class="items">
			<i class="fa fa-gavel" aria-hidden="true" style="color:#5c6bc0;"></i>
			<div>手动创建表格</div>
		</div>
		<div class="items" style="border-right:none;">
			<i class="fa fa-life-ring" aria-hidden="true" style="color:#ffa726;"></i>
			<div>选择应用市场模板</div>
		</div>
	</div>
</div>
<div style="margin: 0 10px;background: #fff;" ondragover="ondragoverfun();" ondragenter="ondragoverfun();" ondrop="drop()">
	
	
	<div style="border-left: 1px solid #eeecec;float: left;" class="table_container">
		
		<#list page.list as item>
		<div class="table" rel="${item.id}">
			<div class="tablecontent" style="position: relative;" data="${item?counter}" draggable="true" ondragstart="dragstartfun(this)">
				<div class="icon" style="border-color: ${item.color!'#42a5f5'};"><i class="fa ${item.icon!'fa-file-excel-o'}" aria-hidden="true" style="color: ${item.color!'#42a5f5'};"></i></div>
				<i class="fa fa-angle-down optable" aria-hidden="true" style="font-size: 12px;" rel="${item.id}"></i>
				<div class="t">${item.title}</div>
			</div>
		</div>
		</#list>
		
	</div>
	
	
	
	<div style="clear: both;"></div>
	<div style="background: #aaa;width: 2px;border-top:2px solid #aaa;border-bottom:2px solid #aaa;border-right:2px solid #fff;border-left:2px solid #fff;height: 114px;position: absolute;display: none;" id="ban"></div>
</div>


<script type="text/javascript">
var d = document.querySelector("#ban");
var tables = document.querySelectorAll(".table");

var dss = document.querySelectorAll(".tablecontent");
[].forEach.call(tables, function(div) {
	div.left=div.getBoundingClientRect().left;
	div.top=div.getBoundingClientRect().top;
});

var source=null;
var dragtarget=null;
function dragstartfun($this){
	source=$this.parentNode;
}

var haveOver=false;
function ondragoverfun(ev){
	ev=ev||window.event;
	ev.preventDefault();
	var target = ev.target;
	var tablecontent= flytree.closest(target,".tablecontent");
	
	if(haveOver){
		var cur=document.querySelector("#ondragoverfun");
		cur && cur.removeAttribute("id");
		haveOver=false;
	}
	
	if(tablecontent){
		console.log("合并");
		tablecontent.setAttribute("id","ondragoverfun");
		haveOver=true;
		d.style.display="none";
	}
	else
	{
		console.log("排序");
		[].forEach.call(tables, function(div) {
			if((ev.pageX)>div.left+div.offsetWidth/2 && ev.pageY>div.top){
				d.style.display="block";
				d.style.left=(div.left+div.offsetWidth-4)+"px";
				d.style.top=(div.top)+"px";
				dragtarget=div;
			}
		});
	}
	return false;
}


function drop(ev){
	ev=ev||window.event;
	ev.preventDefault();
	ev.stopPropagation();
	var target = ev.target;
	
	var cur=document.querySelector("#ondragoverfun");
	cur && cur.removeAttribute("id");
	d.style.display="none";
	
	var fileList = ev.dataTransfer.files; //获取文件对象
	console.log(fileList);
	if(fileList.length){//上传
		const param = new FormData();
		  param.append("file", fileList[0]);
		  const config = {
		    headers: { "Content-Type": "multipart/form-data" }
		  };
		  axios.post("/postest", param, config).then(res => {
		    console.log(res);
		  });
	}
	
	var tablecontent= flytree.closest(target,".tablecontent");
	if(tablecontent){
		console.log("合并。。");
	}
	else{
		console.log("排序。。");
		if(source && dragtarget){
			flytree.insertAfter(source,dragtarget);
			tables = document.querySelectorAll(".table");
			[].forEach.call(tables, function(div) {
				div.left=div.getBoundingClientRect().left;
				div.top=div.getBoundingClientRect().top;
			});
		}
	}
}

function setTableOrder(ids){
	$.ajax({
		
	});
}

</script>
</@t_admin.body>
