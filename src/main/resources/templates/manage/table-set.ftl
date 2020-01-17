<@t_admin.head>
<link style="text/css" rel="stylesheet" href="${base}/js/jbox/css/box.css" />
<script type="text/javascript" src="${base}/js/tabledrag.js"></script>
<script type="text/javascript" src="${base}/js/jbox/browserQs.js"></script>
<script type="text/javascript" src="${base}/js/jbox/jquery-overlayQS.js"></script>
<script type="text/javascript" src="${base}/js/jbox/jquery-boxQS.js"></script>
<script type="text/javascript" src="${base}/js/tableset.js"></script>
<script type="text/javascript" src="https://unpkg.com/art-template@4.13.2/lib/template-web.js"></script>
<script id="conlumnType1" type="text/html">
<div>
	<div>平铺出所有备选项，从中只可选择一项</div>
	<div>字段类型</div>
	<input type="radio">单选项&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio">多选项
	<textarea style="width: 100%;height: 50px;"></textarea>
</div>
</script>

<script id="conlumnType6" type="text/html">
<div>
	<div style="color:#9f9f9f;line-height:22px;">手机号码可以用来发送验证码，运单号可以用来查询快递信息</div>

	<div style="font-weight:bold;">字段标识</div>
	<div><input type="radio">&nbsp;手机号&nbsp;&nbsp;&nbsp; <input type="radio">&nbsp;运单号&nbsp;&nbsp;&nbsp; <input type="radio">&nbsp;身份证号</div>

	<div style="font-weight:bold;">填写标识</div>
	<div><input type="checkbox">&nbsp;必填&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox">&nbsp;唯一</div>

	<div>填写说明</div>
	<textarea style="width: 100%;height: 50px;"></textarea>
</div>
</script>
    
<script type="text/javascript">
$(function(){
	//调整顺序
	$(".tblist").tabledrag({
		canLast:false,
		notClass:"nodragg",
		draged:function(table){
			var tds = table.find("tr").eq(0).find("td.columndata");
			var param = "columnID=";
			tds.each(function(i,n){
				var columnID=$(n).attr("data-column");
				if(columnID){
					param=param+$(n).attr("data-column");
					if(i!=tds.length-1){
						param=param+"&columnID=";
					}
				}
			});
			
			$.ajax({
				url:"${base}/manage/ser-column-order",
				data:param,
				type:"post",
				success:function(data){
				}
			});
		}
	});
	
	var dest=$(".opt_menu").mbox({
		data:$(".opt-column"),
		left:function(a,b){
			return b.left-10
		},
		onClose:function(){
			$(".opt-column").find(".popover-content-menu").show();
			$(".opt-column").find(".popover-content-changetype").hide();
		},
		source:function($this){
			return $this.parent("td");
		},
		value:function($this){
			return $this.parent("td").attr("data-column");
		},
		beforeOpen:function(){
			$(".popover-content").hide();
			$(".popover-content-menu").show();
		}
	});
	$(".columndata").on("mouseover",td_mouseover);
	
	function td_mouseover(){
		$(this).find(".opt_menu").show();
	}
	function td_mouseout(){
		if($(".popover").is(":hidden")){
			$(".opt_menu").hide();
		}
	}
	
	$(".columndata").bind("mouseout",td_mouseout);
	
	//删除
	$(".menu_remove").click(function(){
		var source = dest.source;
		var index = source.index();
		$.ajax({
			url:"remove-column?columnID="+source.attr("data-column"),
			success:function(data){
				dest.close();
				$("tr").each(function (i) {
			   		var $tr=$(this);
			   		$tr.find("td").eq(index).remove();
		        });
				
			}
		});
	});
	
	$(".menu_changetype").click(function(){
		//console.log(dest.value);
		//dest.close();
		$(".opt-column").find(".popover-content-menu").hide();
		$(".opt-column").find(".popover-content-changetype").show();
	});
	
	//修改
	$(".menu_modify").click(function(){
		var td = dest.source;
		dest && dest.close();
		edititle(td);
	});
	
	$(".menu_insertbefore").click(function(){
		var td = dest.source;
		var index = td.index();
		var trs = $("tr");
		var name = "第"+(index+1)+"列";
		var order = td.attr("data-order")-1;
		return false;
		$.ajax({
			url:"add-column",
			type:"post",
			dataType:"json",
			data:"tableID=${tableID}&title="+name+"&order="+order,
			success:function(data){
				addColumn($("tr"),index,data,name);
				dest && dest.close();
			}
		});
		
	});
	
	$(".menu_insertafter").click(function(){
		var td = dest.source;
		var index = td.index();
		var trs = $("tr");
		var name = "第"+(index+2)+"列";
		
		var order=td.attr("data-order")+1;
		var next_td=td.next("td.columndata");
		if(next_td.length){
			order = next_td.attr("data-order")-1;
		}
		return false;
		$.ajax({
			url:"add-column",
			type:"post",
			dataType:"json",
			data:"tableID=${tableID}&title="+name+"&order="+order,
			success:function(data){
				addColumn($("tr"),index+1,data,name);
				dest && dest.close();
			}
		});
	});
	
	$(".menu_copy").click(function(){
		alert("复制");
	});
	
	//保存
	$(".input_title").blur(function(){
		var $this=$(this);
		var name=$this.val();
		var id=$this.attr("alt");
		$.ajax({
			url:"modify-column",
			type:"post",
			data:"columnID="+id+"&name="+name,
			success:function(data){
				$this.parent().hide().prev().html(name).show();
			}
		});
	}).keydown(function(e) {  
        if (e.keyCode == 13) {  
           $(this).trigger("blur");
     	}  
	});  
	
	//添加列
	$(".add_column").click(function(){
		var index = $(this).index();
		var trs = $("tr");
		var name = "第"+(index+1)+"列";
		$.ajax({
			url:"add-column",
			type:"post",
			data:"tableID=${tableID}&title="+encodeURIComponent(name),
			dataType:"json",
			success:function(data){
				addColumn(trs,index,data,name);
			}
		});
	});
	
	$(".menu_changetypen").click(function(){
		$(".popover-content").hide();
		
		var type=dest.source.attr("data-column-type");
		var selecttype=$(this).attr("rel");
		if(selecttype!="6" && selecttype!="1"){
			return false;
		}
		
		var d1 = {
			    title: '基本例子',
			    isAdmin: true,
			    list: ['文艺', '博客', '摄影', '电影', '民谣', '旅行', '吉他']
		};

		var o=template('conlumnType'+selecttype, d1);
		$(".popover-content-changetype-content").html(o).show().parent().css({
			left:dest.source.offset().left+1,
			top:dest.source.offset().top+dest.source.outerHeight(),
		});
	});
});
</script>

<style>
.modify_title{
    background-color: #fff;
    box-shadow: 0 1px 4px rgba(0,0,0,.4);
    display: none;
    width: 239px;
    height: 43px;
    position: absolute;
    z-index: 999;
    top:0px;
    left:0px;
    line-height: 43px;
}
.modify_title input{
   outline: none;
   text-indent: 10px;
}

.opt_menu{
	display: none;
	position: absolute;top: 12px;right: 10px;
	border: 1px solid #ececec;
	width: 12px;height: 12px;
	border-radius:2px;
	text-align: center;
	cursor: pointer;
}
.opt_menu i{
    width:0;
    height:0;
    border-width:6px 4px 0;
    border-style:solid;
    border-color:#9da1c4 transparent transparent;/*灰 透明 透明 */
    position: absolute;
    top:3px;
    left:2px;
}
</style>
</@t_admin.head>
<@t_admin.body position="首页" menu="icon3">
<div id="cf" style="height: 48px;line-height:48px;margin-bottom: 0px;background: #fff;">
	<div style="float: right;margin-right: 20px;">
		<a href="${base}/manage/tables" style="color: red"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> 返回</a>&nbsp;&nbsp;
	</div>
</div>


<div style="margin: 0 20px 20px 20px;background: #fff;">
	<table class="tblist">
		<tr class="title">
			<#list columns as item>
			<td style="min-width: 60px;position: relative;overflow: visible;" data-column="${item.id}" data-order="${item.order}" data-column-type="${item.type}" class="columndata">
				<div class="content"><i class="fa fa-file-text" aria-hidden="true"></i>&nbsp;&nbsp;${item.title}</div> 
				<div class="modify_title nodragg"><input value="${item.title}" alt="${item.id}" style="height: 43px;border:none;width: 92%;" autocomplete="off" class="input_title nodragg"/></div><#--修改-->
				<span class="opt_menu nodragg"><i class="nodragg"></i></span>
			</td>
			</#list>
			<td style="min-width: 40px;width: 100px;cursor: pointer;" class="nodrag add_column">+添加列</td>
			<td style="min-width: 40px;" class="nodragto nodrag"></td>
		</tr>
		<tr>
			<#list columns as c>
			<td style="height: 400px;"></td>
			</#list>
			<td></td>
			<td></td>
		</tr>
	</table>
</div>
<div class="opt-column popover bottom-left in" style="display: block; height: auto;display: none;">
	<div class="popover-content popover-content-menu">
		<div class="pop_menu cl">
			<ul class="cl">
				<li class="menu_modify"><a href="javascript:void(0);"><span>改列名</span></a></li>
				<li class="menu_changetype"><a href="javascript:void(0);"><span>更改属性</span></a></li>
				<li class="menu_insertbefore"><a href="javascript:void(0);"><span>在前一列插入</span></a></li>
				<li class="menu_insertafter"><a href="###"><span>在后一列插入</span></a></li>
				<li class="menu_copy"><a href="###"><span>复制列</span></a></li>
				<li class="menu_remove"><a href="javascirpt:void(0);"><span>删除列</span></a></li>
			</ul>
		</div>
	</div>
	<div class="popover-content popover-content-changetype" style="display: none;min-width:100px;">
		<div class="pop_menu cl">
			<ul class="cl">
				<#list Constant.columnType as k,v>
				<li class="menu_changetypen menu_changetype${k}" rel="${k}"><i class="fa ${Constant.columnIcon(k)}" aria-hidden="true"></i> &nbsp;&nbsp;<a href="javascript:void(0);"><span>${v}</span></a></li>
				</#list>
			</ul>
		</div>
	</div>
	<div class="popover-content popover-content-changetype-content" data-changetype="" style="display: none;padding: 10px;min-height: 380px;font-size: 12px;line-height: 30px;width: 200px;">
	</div>
</div>



</@t_admin.body>
