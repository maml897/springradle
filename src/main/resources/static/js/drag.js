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
			
			
			$this.each(function(){
				if($(this).offset().left<left && $(this).offset().top<top)
				{
					target.parent().insertAfter($(this).parent());
				}
			})
			
			return false;
		}
		function documentmouseup(){
			$document.unbind("mousemove",documentmousemove);
			$document.unbind("mouseup",documentmouseup);
			target.css({
				left:"",
				top:""
			});
		}
		
		function change(){
		}
		
		function gettarget(target){
			return $(target).closest(".tablecontent");
		}
	}
})(jQuery,window);