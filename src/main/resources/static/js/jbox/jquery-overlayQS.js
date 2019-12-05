$.overlayQs=function(css){
	var defaultCss={
		background: "#000",
		width: "100%",
		top: "0px",
		left: "0px",
		overflow: "hidden",
		opacity:0.5,
		"z-index": "99999",
		"outline-style": "none",
		"height": $(document).height()
	}
	css=$.extend({}, defaultCss,css);
	var overlay =$("<div style=\"position: fixed;_position:absolute;\"></div>").css(css).appendTo("body");
	//overlay.css({opacity:css.opacity});
	return overlay;
}