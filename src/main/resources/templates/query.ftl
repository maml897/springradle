<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>欢迎访问</title>
<link type="text/css" href="${base}/css/query.css"  rel="stylesheet"/>
<script type="text/javascript" src="${base}/js/jquery-1.8.3.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script type="text/javascript">
$(function(){
	function set(value){
		if(!window.localStorage){
	        return false;
	    }
		var storage=window.localStorage;
		
		for(var i=0;i<window.localStorage.length;i++){
	        var key=window.localStorage.key(i);
	        if(value==window.localStorage[key]){
	        	window.localStorage.removeItem(key);
	        }
	    }
		
	    storage[new Date().getTime()]=value;
	    while(window.localStorage.length>5){
	    	window.localStorage.removeItem(window.localStorage.key(0));
	    }
	    return get();
	}
	
	function get(){
		var history=[];
		for(var i=0;i<window.localStorage.length;i++){
	        var key=window.localStorage.key(i);
	        history.push(window.localStorage[key]);
	    }
		history.reverse();
		return history;
	}
	
	window.v=new Vue({
		el: '#id_vue',
		data: {
			history:get(),
			infos:[]
		},
		methods: {
			clear:function(){
				if(!window.localStorage){
		            return false;
		        }
				window.localStorage.clear();
				v.history=[];
			},
			search:function(event){
				var el = event.currentTarget;
		        $("#id_key").val(el.innerHTML);
		        $("#id_query").trigger("click");
			}
		}
	});
	
	$("#id_query").click(function(){
		var value = $("#id_key").val();
		if(value==""){
			return false;
		}
		v.history=set(value);
		$.ajax({
			url:"${base}/query/q?tableID=${tableID}&key="+value,
			success:function(data){
				$(".userinfo").html(data);
			}
		});
		return false;
	});
	
	$("#id_key").focus(function(){
		$(".about").hide();
	}).blur(function(){
		$(".about").show();
	});
});
</script>
</head>
<body>
<div class="content">
	<#include "inc/banner.ftl">
	<div class="form-container">
		<input type="text" placeholder="输入<#list columns as item>${item.title}<#sep>/</#list>查询" id="id_key"><a href="javascript:void(0);" id="id_query">查询</a>
	</div>
	
	<div id="id_vue">
		<div v-if="history.length >0" class="historys">
			<span v-for="(item,index) in history" v-on:click="search">{{item}}</span><span v-on:click="clear">清空历史</span>
		</div>
	
		<div class="userinfo">
		</div>
		
		<div class="infos" id="id_infos">
			<div v-for="(item,index) in infos" class="info" :class="index === 0?'first':''">
				<div>{{ item.context }}</div>
				<div style="font-size: 12px;">{{ item.time }}</div>  
				<em></em>
			</div>
		</div>
	</div>
	
</div>

<div class="about">
		<div style="font-size: .2px;">拉拉队技术团队  <a href="http://www.lala.team">WWW.LALA.TEAM</a> © 2018-2020</div>
	</div>
</body>
</html>
