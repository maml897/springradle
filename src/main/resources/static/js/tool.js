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


//http://sortablejs.github.io/Sortable/Sortable.js
function getParentOrHost(el){
    return el.host && el !== document && el.host.nodeType ? el.host : el.parentNode;
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
							&& matches(el, selector) : matches(el,
							selector)) || includeCTX && el === ctx) {
				return el;
			}

			if (el === ctx)
				break;
		} while (el = getParentOrHost(el));
	}
	return null;
}