<#if list?size==0>
暂无信息

<#else>

<#assign item = list[0]>
<#list columns as c>
<#if c.show>
<div><span>${c.title}</span>：${item(c.rowName)}  <#if c.type=Constant.COLUMN_TYPE_YUNDAN><span class="copy" data-info="${item(c.rowName)}">复制</span></#if></div>
</#if>
</#list>

<#assign info = Tool.getRowInfo(columns,item)>
<#if info(Constant.COLUMN_TYPE_YUNDAN)??>
<script type="text/javascript">
$.ajax({
	url:"http://q.kdpt.net/api?id=XDB2g2sjbns211ow972aNo0I_377099997&com=${(Constant.coms(info(Constant.COLUMN_TYPE_YUNDAN_COM)))!'auto'}&nu=${info(Constant.COLUMN_TYPE_YUNDAN)}&show=json&order=desc",
	dataType:"jsonp",
	cache:false,
	success:function(data){
		v.infos=data.data;
	}
});

$(".copy").mousedown(function(){
	var inff = $(this).attr('data-info');
	var input = document.createElement('input');
	input.setAttribute('readonly', 'readonly');
	input.setAttribute('value', inff);
    document.body.appendChild(input);
    input.setSelectionRange(0, 9999);
    if (document.execCommand) {
        document.execCommand('copy');
        alert('复制成功');
    }
    document.body.removeChild(input);
});
</script>
</#if>

</#if>