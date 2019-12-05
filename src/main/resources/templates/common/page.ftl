<#macro paging urlLink="" urlParameter="" number=10 ajax="" page=page prefix="" prev="&laquo;&nbsp;上一页" next="下一页&nbsp;&raquo;">
<#if page.totalPage==0>
${page.paging()}
</#if>
<#if (page.totalPage <= 0) || (page.pageNumber > page.totalPage)><#return></#if>

<#if urlParameter!="" && !urlParameter?starts_with("&")>
<#local urlParameter="&"+urlParameter>
</#if>
<#if urlParameter=="&">
<#local urlParameter="">
</#if>

<div class="${(ajax!='')?string('s_page_ajax','s_page')}">
<#if (page.pageNumber>1)>
<a href="${response.encodeURL('${urlLink}?${prefix}totalNumber=${page.totalNumber}&${prefix}pageNumber=${page.preNumber}${urlParameter}')}">&laquo;&nbsp;上一页</a>
<#else>
<span class="disabled">${prev}</span>
</#if>

<#if (page.totalPage<=number)>
	<#list 1..page.totalPage as n>
		<#if page.pageNumber !=n>
		<a href="${response.encodeURL('${urlLink}?${prefix}totalNumber=${page.totalNumber}&${prefix}pageNumber=${n}${urlParameter}')}">${n}</a>
		<#else>
		<span class="this_page">${n}</span>
		</#if>
	</#list>
<#else>
	<#local pre = avg(number)>
	<#local next = number -5- pre>

	<#local startPage = page.pageNumber - pre>
	<#local endPage = page.pageNumber + next>
	<#if (startPage-1 <= 2)><#local startPage = 1><#local endPage = (number-2)></#if>
	<#if (page.totalPage-endPage <= 2)><#local endPage = page.totalPage><#local startPage = page.totalPage-(number-3)></#if>

	<#if (startPage !=1)>
	<a href="${response.encodeURL('${urlLink}?${prefix}totalNumber=${page.totalNumber}&${prefix}pageNumber=1${urlParameter}')}">1</a>
	<span class="text">...</span>
	</#if>
	
	<#list startPage..endPage as n>
		<#if page.pageNumber!=n>
		<a href="${response.encodeURL('${urlLink}?${prefix}totalNumber=${page.totalNumber}&${prefix}pageNumber=${n}${urlParameter}')}">${n}</a>
		<#else>
		<span class="this_page">${n}</span>
		</#if>
	</#list>
	
	<#if (endPage !=page.totalPage)>
	<span class="text">...</span>
	<a href="${response.encodeURL('${urlLink}?${prefix}totalNumber=${page.totalNumber}&${prefix}pageNumber=${page.totalPage}${urlParameter}')}">${page.totalPage}</a>
	</#if>
</#if>

<#if (page.pageNumber < page.totalPage)>
<a href="${response.encodeURL('${urlLink}?${prefix}totalNumber=${page.totalNumber}&${prefix}pageNumber=${page.nextNumber}${urlParameter}')}" class="a_page">下一页&nbsp;&raquo;</a>
<#else>
<span class="disabled">${next}</span>
</#if>
</div>

<#if ajax!="">
<script type="text/javascript">
$(".s_page_ajax").find("a:not(.this_page,.disabled)").click(function(event){
	event.preventDefault();
	event.stopPropagation();
	event.stopImmediatePropagation();
	var $this =$(this); 
	var url=$(this).attr("href"),callBack=${ajax};
	$.ajaxQs({
		url:url,
		success:function(data){if(callBack){callBack(data,$this);}}
	});
	return false;
});
</script>
</#if>
</#macro>
<#function avg number><#return ((number-5)/2)?floor></#function>

