var browserQs=(function(){
	function isIE(ver){
		var b = document.createElement('b')
		b.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->'
		return b.getElementsByTagName('i').length === 1;
	}
	
	return {
		isIE:isIE,
		isIE10:function(){return false;}
	}
})();