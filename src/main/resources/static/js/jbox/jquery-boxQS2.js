/**
 * 1.多次调用的方法，返回值缓存，减少调用
 * 2.窗口定位尽量百分比，可以减少后期的窗口缩放需要重新定位带来的问题。
 * @param $
 * @param window
 */
(function($,window) {
	var $win = $(window),$win_={},boxs=[],supportCss3={},$document = $(document);
	var closeEvent="qsbox_close";
	function getWinProp(prop,focus){
		var value = $win_[prop];
		if(!value || focus){
			value= $win[prop]();
			$win_[prop]=value;
		}
		return value;
	}
	
	var css3Support=(function whichTransitionEvent(){  
	    var t;  
	    var el = document.createElement('b');  
	    var transitions = {  
	      'transition':'transitionend',  
	      'OTransition':'oTransitionEnd',  
	      'MozTransition':'transitionend',  
	      'WebkitTransition':'webkitTransitionEnd',  
	      'MsTransition':'msTransitionEnd'  
	    }  
	    for(t in transitions){if( el.style[t] !== undefined ){return transitions[t];} }  
	})();

	function supportCss3Prop(prop){
		var support=supportCss3[prop];
		if(support!==undefined){
			return support;
		}
		var el = document.createElement('b'),t;  
		var props = [prop,'-ms-'+prop, '-moz-'+prop,'-webkit-'+prop, '-o-'+prop];
		for(t in props){
			var propone = props[t]
			if( el.style[propone] !== undefined ){
				supportCss3[prop]=propone;
				return propone;
			} 
		} 
		supportCss3[prop]=false;
		return false;
	}
	
	var cssBefore={
		position: browserQs.isIE(6)?"absolute":"fixed",
		left:"50%",
		top:"50%",
		transform: "translate(-50%,-50%) scale(0.7)",
		transition:"transform 0.3s",
		overflow:"hidden",
		visibility: "hidden",//主要是给ie6789动画用，目前只有ie9用了动画
		"z-index":"99999"
	}
			
	var cssAfter={
		transform: "translate(-50%,-50%) scale(1)",
		visibility:"visible"//主要是给ie6789动画用，目前只有ie9用了动画
	}
	
	function jAlert(message,okFunction,className,title,okButton){
		title = title||"温馨提示";
		okButton=okButton||"确定";
		var $template =$("<div class=\"alerts\"><h1>"+title+"</h1><div id=\"popup_content\"><div id=\"popup_message\"><i></i>"+message+"</div><div id=\"popup_panel\"><span id=\"popup_ok\" tabindex=\"1\" hidefocus=\"true\">"+okButton+"</span></div></div></div>");
		className&& $template.addClass(className);
		var box =createBox({container:$template});
		
		$template.find("#popup_ok").click(function(){
			box.close(true);
			okFunction && okFunction();
		});
		return false;
	}
	
	function jConfirm(message){
		var strArr=[],funArr=[],title,className,okFunction,cancelFunction,okButton,cancelButton;
		for(var i=0;i<arguments.length;i++){
			var argumentsi= arguments[i];
			if(typeof(argumentsi)=="function"){
				funArr.push(argumentsi);
			}
			else{
				strArr.push(argumentsi);
			}
		}
		title=strArr[1]||"温馨提示";
		className=strArr[2];
		okButton= strArr[3]|| "确定";
		cancelButton= strArr[4]|| "取消";
		okFunction= funArr[0];
		cancelFunction= funArr[1];
		
		var $template=$("<div class=\"alerts _confirms\"><h1 id=\"popup_title\">"+title+"</h1><div id=\"popup_content\" class=\"confirm\"><div id=\"popup_message\"><i></i>"+message+"</div><div id=\"popup_panel\"><span id=\"popup_ok\" tabindex=\"1\">"+okButton+"</span><span id=\"popup_cancel\" tabindex=\"1\">"+cancelButton+"</span></div></div></div>");
		className&& $template.addClass(className);
		var box =createBox({container:$template});
		
		$template.find("#popup_ok").click(function(){
			okFunction && okFunction();
			box.close(true);
		});
		$template.find("#popup_cancel").bind("click",function(){
			if(cancelFunction){cancelFunction();}
			box.close(true);
		});
		return false;
	}
	
	function setFocus(obj){
		obj.focus().bind("keydown",function(e){
			var code = e.which;
	        if (code === 13 || code === 32) {
	            $(this).trigger("click");
	        }
		});
	}
	
	function processIframe(obj,iframeName){
		var iframe = window.frames["_box__"];
		iframe.document.open();
        iframe.document.write(obj.data);
        iframe.document.close();
	}
	
	function getWrapData(obj){
		if(obj.iframe){
			return "<div class=\""+obj.className+"\"><iframe name=\"_box__\" style=\"width:100%;height:100%;padding:0;margin:0;\" frameborder=\"0\"></iframe></div>";
		}
		if(!obj.useCustom){
			return "<div class=\""+obj.className+"\">"+obj.data+"</div>";
		}
		return obj.data;
	}
	
	function getStyle(obj){
		var style="";
		if(obj.innerWidth){
			style=style+"width:"+obj.innerWidth+"px;";
		}
		if(obj.innerHeight){
			style=style+"height:"+obj.innerHeight+"px;";
		}
		return style;
	}
	
	function _jbox(obj,overlay,load){
		if(obj.wrapData){
			obj.data=obj.wrapData(obj.data);
		}
		var box =createBox({
			container:$(getWrapData(obj)),
			overlay:overlay,
			loadbox:load,
			hook:true
		});
		if(obj.iframe){processIframe(obj);}
		obj.closeClassName=obj.closeClassName|| "closeBox";
		var colse= box.container.find("."+obj.closeClassName);
		if(colse.length){
			colse.bind("click",box.close);
		}
		return box;
	}
	
	//公开,特殊案例，其实主要使用layer，考虑此种情况用的多，所以单独做一个方法
	function colorbox(param){
		var defaultParam={
			wrapData:function(data){
				return "<h1>"+(param.title || "欢迎访问")+"<span class=\"closeBox\"></span></h1><div id=\"qs_jbox_content\" style=\""+getStyle(param)+"overflow:auto;\">"+data+"</div>";
			},
			className:"qs_jbox"
		}
		param = $.extend({},param,defaultParam);
		layer(param)
	}
	
	//外部调用
	function layer(obj){
		//url|data,wrapData,closeClassName,iframe,useCustom
		
		var box;
		if(obj.url){
			var load=pop("<div class=\"load\"></div>");
			$.ajax({url:obj.url,cache:false})
			.done(function(data){
				obj.data=data;
				box=_jbox(obj,load.overlay,load);
			});
		}
		else if(obj.data){
			box=_jbox(obj);
		}
		return box;
	}
	
	//公开，简单的弹出,不支持hook
	function pop(html,overlayCss,opendfun,closedfun){
		var reg=/^<.*>$/;
		var obj;
		if(reg.test(html)){//html
			obj=$(html);
		}
		else{
			obj = $("<div class=\"pop\">"+html+"</div>");
		}
		var box =createBox({
			container:obj,
			overlayCss:overlayCss,
			opendfun:opendfun,
			closefun:closedfun
		});
		return box;
	}
	
	var defaultParam={
		container:null,//必须,包装好的html jquery对象
		overlay:null,//选填
		overlayCss:null,//选填
		overlayClose:false,//选填
		opendfun:null,//选填
		closefun:null,//选填
		loadbox:null,//选填
		hook:false//如果document绑定了hook方法，是否需要执行hook
	};
	function createBox(param){
		param=$.extend({}, defaultParam,param);
		var overlay =param.overlay ||$.overlayQs(param.overlayCss);
		var container= param.container.css(cssBefore).appendTo("body");
		var opendfun=param.opendfun;
		var closefun=param.closefun;
		var loadbox=param.loadbox;
		var hook=param.hook;
		
		var box={
			container:container,
			overlay:overlay,
			close:function(closeOverlay){//参数，否否关闭背景层，false不关闭。其余都关闭.是否执行hook,不是都是false都执行。
				var index=jQuery.inArray(this,boxs);
				boxs.splice(index,1); 
				container.remove();
				if(closeOverlay!==false){overlay.remove();}
				closefun&&closefun();
				//调用Event Hooks
				if(hook){
					var documentevents=$document.data('events') || $._data($document.get(0),'events');//低版本和高版本jquery获取事件的不同方法
					if(documentevents){
						//closehook
						var qsbox_closeevent = documentevents["qsbox_close"];
						qsbox_closeevent&&$document.triggerHandler("qsbox_close");
					}
				}
			}
		}
		boxs[boxs.length]=box;
		
		var setBoxPosition=function(){
			if(css3Support)//使用css3动画
			{
				loadbox&&loadbox.close(false);
				if(opendfun){container.bind(css3Support,function(){opendfun.call(box);});}
				setTimeout(function(){container.css(cssAfter);}, 20);
			}
			else
			{
				//设置位置居中，高版本浏览器设置了translateX(-50%)，低版本浏览器设置margin-left，但是ie9支持translateX(-50%)，但是被判定不支持css3Support；
				var transform=supportCss3Prop("transform");//支持该属性的，但不一定支持transition，例如ie9.能支持该属性，说明性能还行。可以使用js模拟动画
				if(!transform){
					var h = container.height();
					var w = container.width();
					var marginTop = parseInt(container.css("margin-top"));
					marginTop = marginTop-h/2;//元素设置的margin-top表示偏移量
					container.css({"margin-left": -(w/2)+"px", "margin-top":marginTop}); 
				}
				if(!loadbox || !transform){//只有支持transform才使用js动画，不支持的使用了js动画也不会流畅
					container.css(cssAfter);
					opendfun && opendfun.call(box);
				}
				else{
					var cssObj={left:container.css("left"),top:container.css("top"),width:container.width(),height:container.height(),"margin-left":container.css("margin-left"),"margin-top":container.css("margin-top")};
					loadbox.container.animate(cssObj, 300,function(){
						container.css(cssAfter);
						loadbox.close(false);
						opendfun && opendfun.call(box);
					}); 
				}
			}
		}
		setBoxPosition();
		return box;
	}
	
	window.qs={
		alert:jAlert,//提示
		confirm:jConfirm,//确认
		layer:layer,//小提示
		boxs:function(i){
			if(!i){i=boxs.length-1;}
			return boxs[i];
		},
		boxArr:boxs,
		pop:pop,
		colorbox:colorbox
	}
})(jQuery,window);