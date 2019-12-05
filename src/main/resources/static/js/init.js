$(function(){
	var $win=$(window),main=$("#id_main"),flag=false;
	function set(){
		main.css("min-height",$win.height());
		flag=true;
	}
	flag || set();
	$win.resize(set);
});