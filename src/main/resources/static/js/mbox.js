(function($,window){
	$.fn.mbox = function(options){
		var innerDefault={};
		var options = $.extend( {}, $.fn.mbox.defaults,options,innerDefault);
		var $document=$(document);
		
		var dest;//最终要返回query对象
		var flag=false;
		if(options.data instanceof jQuery){ //如果是jquery对象，就是页面已经存在的隐藏的元素
			dest = options.data;
		}
		else if (typeof options.data =="function"){//函数,返回jquery对象
			flag=true;
		}
		else if (typeof options.data =="string"){//html
			
		}
		else if (typeof options.data =="json"){//json数据
			
		}
		
		$(this).click(function(){
			var $this=$(this);
			if($this.data("opend")){
				return false;
			}
			if(flag){
				dest =options.data($this);
			}
			
			var position = $this.offset();
			var top= options.top($this,position);
			var left= options.left($this,position);
			
			dest.css({top:top,left:left}).show();
			$this.data("opend",true);
			
			dest.source = $this;//当前
			if(options.source){
				dest.source =options.source($this);//可以自定义source
			}
			dest.target=$this;
			$document.bind("mousedown",{source:$this},bindmousedown);
		});
		
		function bindmousedown(event){
			var target = $(event.target);
			var source = event.data.source;
			if (!target.closest(dest).length && !target.closest(source).length) {
				close();
			}
		}
		
		function close(){
			dest.target.removeData("opend");
			dest.hide();
			options.onClose();
			$document.unbind("mousedown",bindmousedown);
		}
		
		dest.close=close;
		return dest;
	}
	$.fn.mbox.defaults = {
		data:null, //相关ID
		left:function(obj,objposition){return objposition.left;},
		top:function(obj,objposition){return objposition.top+obj.outerHeight()},
		onOpen:function(){
		},
		onClose:function(){
		},
		source:null
	};
})(jQuery,window);