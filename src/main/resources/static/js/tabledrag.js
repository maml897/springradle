(function($,window){
	$.fn.tabledrag = function(options){
		var innerDefault={};
		var options = $.extend( {}, $.fn.tabledrag.defaults,options,innerDefault);
		
		var table=$(this);
		var tableposition=table.position();
		var trs =table.find("tr");
		var flag=false;
		
		var d = trs.eq(0).find(options.dragIdentifier).not("."+options.nodrag);
		d.unbind("mousedown").mousedown(function(e){
				if($(e.target).hasClass(options.notClass)){
					return;
				}
			
				var $this =$(this),
				$document=$(document),
				$body=$("body");
				if(!$this.is("td"))
				{
					$this=$this.parent("td");
				}
				
				var position=$this.position();
				var tableheight=table.height();
				
				var $column=$('<div class="column"></div>').appendTo($body);//竖线或者标志
				var $sign=$('<div class="sign"></div>').appendTo($body).css({
					'top': tableposition.top,
					'height':tableheight
				});//竖线或者标志
				
				var startIndex = $this.index(),endIndex
				var flag = true;
			    
			    $column.html(options.content($this)).css({
			    	top:position.top,
			    	left:position.left,
			    	width:$this.outerWidth(),
			    	height:tableheight
			    });
			    
			    /**
			     * 控制哪列不参与,此处可以在初始化的时候做
			     */
			    var positions=[];
			    var tds =$('.title td');
			    tds.each(function(i,n){
			    	var $n=$(n);
			    	if(!$n.hasClass(options.nodragto)){
			    		positions.push($n.position().left);
			    	}
			    });
			    
			    var offsetLeft =  e.clientX - position.left;//起始偏移量
			    var x=0;
			    $body.addClass('no-select-text');
			    
			    var havaMoved=false;
			    $document.mousemove(function (e) {
			        if (!flag) {return false;}
			        
			        havaMoved=true;
			        //设置移动
			        $column.show();
			        x = (e.clientX-offsetLeft);
			        $column.css({left: x});
			        e.preventDefault();
			        
			        //设置标线
			        var tdLeft;
			        var end;
			        $(positions).each(function(i,n){
			        	if(x<n){
			        		tdLeft=n;
			        		end=i;
			        		return false;
			        	}
			        });
			        
			        if(tdLeft){
			        	setLine(tdLeft,end);
			        }
			        else{
			        	if(options.canLast){
			        		setLine(table.position().left+table.width());
			        	}
			        }
			        return false;
			    });
			    
			    $document.mouseup(function () {
			        flag = false;
			        $sign.remove();
			        $column.remove();
			        
			        $document.unbind("mousemove");
			        $document.unbind("mouseup");
			        $body.removeClass('no-select-text');
			        
			        if(!havaMoved){
			        	return ;
			        }
			        
			        //操作
			        var flag=insertTds(startIndex,endIndex);
			        
			        //此处应该是只有变化了才能
			        if(flag){
			        	 options.draged(table);
			        }
			    });
			    
			    /**
			     * 设置标识点
			     */
			    function setLine(tdLeft,end){
			    	endIndex=end;
			    	$sign.css({
			            'display':'block',
			            'left': tdLeft
			        });
			    }
			    
			    /**
			     * 插入单元格,insertColumn：要插入的位置，j:要移动column
			     */
			    function insertTds(startColumnIndex,endIndex){
			    	//前两种情况都不需要真正的移动
			    	 if(startColumnIndex==endIndex){//往前挪动
			    		 return false;
			    	 }
			    	 if(startColumnIndex==endIndex-1){//往后挪动
			    		 return false;
			    	 }
			    	 
			    	//移动到最后
			    	if(endIndex==undefined && options.canLast){
			    		trs.each(function (i) {
				    		 var $tr=$(this);
				    		 var starttd = $tr.find("td:eq(" + startColumnIndex + ")");
				    		 $tr.append(starttd);
				         });
			    		return false;
			    	}
			    	
			    	//可以考虑统一更新一次
			    	trs.each(function (i) {
			    		 var $tr=$(this);
			    		 var starttd = $tr.find("td:eq(" + startColumnIndex + ")");
			    		 var endtd = $tr.find("td:eq(" + endIndex + ")");
			    		 endtd.before(starttd);
			         });
			    	
			    	return true;
			    }
			    
		});
		
	}
	$.fn.tabledrag.defaults = {
		dragIdentifier:"td",// default td
		canLast:true,//是否可以移动到最后
		notClass:"",
		nodragto:"nodragto",
		nodrag:"nodrag",
		draged:function(){
			
		},
		content:function($this){
			return $this.text();
		}
	};
})(jQuery,window);
