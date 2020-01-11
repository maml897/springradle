var tool = !function($, window) {
	$.fn.formTarget = function(fun) {
		var $this = $(this);
		var t = new Date().getTime();
		var iframe = $(
				'<iframe name="formTarget_' + t
						+ '" style="display:none;"></iframe>').appendTo($this);
		$this.attr("target", iframe.attr("name"));
		iframe.unbind().bind("load", function() {
			var body = $(this).contents().find("body");
			fun.call($this, body.text(), body);
			iframe.remove();
		});
		return $this;
	};

	return {

	}
}($, window);

var flytree = function() {
	// http://sortablejs.github.io/Sortable/Sortable.js
	function getParentOrHost(el) {
		return el.host && el !== document && el.host.nodeType ? el.host
				: el.parentNode;
	}
	function is(el, selector) {
		return !!closest(el, selector, el, false);
	}
	function matches(el, selector) {
		if (!selector)
			return;
		selector[0] === '>' && (selector = selector.substring(1));

		if (el) {
			try {
				if (el.matches) {
					return el.matches(selector);
				} else if (el.msMatchesSelector) {
					return el.msMatchesSelector(selector);
				} else if (el.webkitMatchesSelector) {
					return el.webkitMatchesSelector(selector);
				}
			} catch (_) {
				return false;
			}
		}

		return false;
	}

	function closest(el, selector, ctx, includeCTX) {
		if (el) {
			ctx = ctx || document;
			do {
				if (selector != null
						&& (selector[0] === '>' ? el.parentNode === ctx
								&& matches(el, selector)
								: matches(el, selector)) || includeCTX
						&& el === ctx) {
					return el;
				}

				if (el === ctx)
					break;
			} while (el = getParentOrHost(el));
		}
		return null;
	}

	function hasClass(obj, cls) {
		return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
	}

	function addClass(obj, cls) {
		if (!hasClass(obj, cls))
			obj.className += " " + cls;
	}

	function removeClass(obj, cls) {
		if (hasClass(obj, cls)) {
			var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
			obj.className = obj.className.replace(reg, ' ');
		}
	}
	
	function userAgent(pattern) {
	    return !!
	    /*@__PURE__*/
	    navigator.userAgent.match(pattern);
	  }
	
	 var IE11OrLess = userAgent(/(?:Trident.*rv[ :]?11\.|msie|iemobile|Windows Phone)/i);
	 var Edge = userAgent(/Edge/i);
	 var FireFox = userAgent(/firefox/i);
	 var Safari = userAgent(/safari/i) && !userAgent(/chrome/i) && !userAgent(/android/i);
	 var IOS = userAgent(/iP(ad|od|hone)/i);
	 var ChromeForAndroid = userAgent(/chrome/i) && userAgent(/android/i);

	  var captureMode = {
			  capture: false,
			  passive: false
	  };

	  function on(el, event, fn) {
		  el.addEventListener(event, fn, !IE11OrLess && captureMode);
	  }

	  function off(el, event, fn) {
		  el.removeEventListener(event, fn, !IE11OrLess && captureMode);
	  }

	return {
		is : is,
		closest : closest,
		hasClass:hasClass,
		addClass:addClass,
		removeClass:removeClass,
		on:on,
		off:off,
		insertAfter:function(newElement, targetElement){
			var parent = targetElement.parentNode;
			if (parent.lastChild == targetElement) 
			{
				parent.appendChild(newElement);
			}
			else 
			{
				parent.insertBefore(newElement, targetElement.nextSibling);
			}
		}
	}
}();
