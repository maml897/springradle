var tool=!function($,window){
	$.fn.formTarget = function(fun){
		var $this =$(this);
		var t= new Date().getTime();
		var iframe = $('<iframe name="formTarget_'+t+'" style="display:none;"></iframe>').appendTo($this);
		$this.attr("target",iframe.attr("name"));
		iframe.unbind().bind("load",function(){
			var body= $(this).contents().find("body");
		 	fun.call($this,body.text(),body);
		 	iframe.remove();
		});
		return $this;
	};	
	
	return {
		
	}
}($,window);