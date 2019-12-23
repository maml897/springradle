(function($,window){
	$.fn.mdrag = function(options){
		var $this=$(this);
		var $document=$(document);
		var target;
		
		$this.mousedown(function(ev){
			var ev = ev || window.event; 
			target = gettarget(ev.target);
			var position = target.offset();
			var relaX = ev.clientX - position.left;
	        var relaY = ev.clientY - position.top;
	        
	        var el = $this.get(0);
	        if(el.setCapture){
	        	el.setCapture();
	        }
	        
	        var parent=target.parent();
	        target =parent.clone().css("border","none").css("opacity","0.7").appendTo("body");
	        $document.bind("mousemove",{relaX:relaX,relaY:relaY,position:position},documentmousemove);
	        $document.bind("mouseup",{relaX:relaX,relaY:relaY,position:position},documentmouseup);
		});
		
		function documentmousemove(ev){
			ev = ev || window.event;
			var data =ev.data;
			var left = ev.clientX - data.relaX
			var top = ev.clientY - data.relaY
			
			//compare(left,top,$this);
			target.offset({
				left:left,
				top:top
			});
			
			//target.find(".log").html(left+","+top);
			return false;
		}
		
		function compare(left,top,$this){
			var flag=false;
			$(".tablecontent").each(function(){
				
				if($(this).attr("data")==target.attr("data")){
					//console.log(left+","+top+"--"+$(this).find(".t").html(),$(this).offset().top,$(this).offset().left);
					return true;
				}
				
				var tt = $(this);
				if(tt.offset().left<left && tt.offset().top<top)
				{
					//console.log("满足啊",tt.attr("data")+"之后插入",tt.offset().left,tt.offset().top,left,top);
					flag=true;
					target.parent().insertAfter(tt.parent());
				}
			});
			
			
			if(!flag){
				//target.parent().insertBefore($this.eq(0).parent());
			}
		}
		
		
		function documentmouseup(){
			$document.unbind("mousemove",documentmousemove);
			$document.unbind("mouseup",documentmouseup);
			
			//删除target的样式，回到最初的状态
			target.css({
				left:"",
				top:""
			});
			
			var el = $this.get(0);
			if(el.releaseCapture)
			{
				el.releaseCapture()
			}
			
			$this.each(function(){
				var t =$(this);
				$(this).find(".log").html(t.offset().top+","+t.offset().left);
			});
		}
		
		function change(){
		}
		
		function gettarget(target){
			return $(target).closest(".tablecontent");
		}
	}
})(jQuery,window);