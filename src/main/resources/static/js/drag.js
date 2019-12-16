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
	        
	        $document.bind("mousemove",{relaX:relaX,relaY:relaY,position:position},documentmousemove);
	        $document.bind("mouseup",{relaX:relaX,relaY:relaY,position:position},documentmouseup);
	        
		});
		
		function documentmousemove(ev){
			ev = ev || window.event;
			var data =ev.data;
			var left = ev.clientX - data.relaX
			var top = ev.clientY - data.relaY
			target.offset({
				left:left,
				top:top
			});
			
			var flag=false;
			$this.each(function(){
				if($(this).offset().left<left && $(this).offset().top<top)
				{
					flag=true;
					target.parent().insertAfter($(this).parent());
				}
			})
			if(!flag){
				target.parent().insertBefore($this.eq(0).parent());
			}
			
			return false;
		}
		function documentmouseup(){
			$document.unbind("mousemove",documentmousemove);
			$document.unbind("mouseup",documentmouseup);
			target.css({
				left:"",
				top:""
			});
			
			var el = $this.get(0);
			if(el.releaseCapture)
			{
				el.releaseCapture()
			}
		}
		
		function change(){
		}
		
		function gettarget(target){
			return $(target).closest(".tablecontent");
		}
	}
})(jQuery,window);