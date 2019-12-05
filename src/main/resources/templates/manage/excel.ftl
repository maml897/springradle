<div class="result">
	<div class="tip" style="border: 1px solid #d5d5d5;background: #fff;height: 40px;margin-bottom: 20px;line-height: 40px;text-indent: 20px;color: #4b4b4b;">
		确认导入的数据，以及始据类型
	</div>
	<div class="scroll">
		<table style="width: 100%;" class="tblist">
		<tr>
			<td style="min-width: 100px;"></td>
			<#list titles as title>
			<td>
				${Constant.rowNames.get(title?index)}
			</td>
			</#list>
		</tr>
 
		<tr style="background: #eff">
			<td style="min-width: 100px;">表头属性</td>
			<#list titles as title>
			<td class="types">
			<select>
				<#list Constant.columnType as k,v>
				<option value="${k}">${v}</option>
				</#list>
				<option value="-1">不导入</option>
			</select>
			</td>
			</#list>
		</tr>
		
		<tr style="background: #f5f5f5;">
			<td>标题行</td>
			<#list titles as title>
			<td>${title}</td>
			</#list>
		</tr>
		
		<#list rows as row>
		<tr>
			<td>${row?counter}</td>
			<#list row as item>
			<td>${item}</td>
			</#list>
		</tr>
		</#list>
		</table>
	</div>
	<div>
		<div class="button" id="id_excel_button1">确定导入</div>
	</div>

<script type="text/javascript">
try{
	$(function(){
		$("#id_excel_button1").click(function(){
			var types =$(".types").find("select");
			var typestr="";
			types.each(function(){
				typestr=typestr+"types="+$(this).val()+"&"
			});
			
			$.ajax({
				url:"${base}/manage/add-table",
				type:"post",
				data:"path=${path?url}&"+typestr,
				success:function(data){
					if(data=="ok"){
						alert("导入成功");
						window.location.href="${base}/manage/rows"
					}
				}
			});
		});
	});
}
catch{
	
}
</script>
</div>
