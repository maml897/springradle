<@t_admin.head>
<script type="text/javascript">
$(function(){
	$("#id_excel_button").click(function(){
		$("#id_file").trigger("click");
	});
	$("#id_file").change(function(){
		var value=$(this).val();
		var reg=/^.*?\.(xls|xlsx)$/;
		if(!reg.test(value)){
			alert("请选择excel");
			return false;
		}
		else{
			$("#id_frm").formTarget(function(data,body){
				var result = body.find("div.result").html();
				if(data=="1"){
					alert("上传文件为空或者失败");
					$(".import-content").find("div").show();
					$(".import-content").find("p").hide();
					$("#id_file").val("");
					return false;
				}
				$("#id_include").html(body.find("div.result").html());
			}).submit();
			$(".import-content").find("div").hide();
			$(".import-content").find("p").show();
		}
	});
});
</script>
</@t_admin.head>
<@t_admin.body position="首页">
<div class="import-excel">
	<div class="step">
		<div class="step-line"></div>
		<div class="item" style="left:60px;">
			<span>1</span>
			<div class="info">选择Excel</div>
		</div>
		
		<div class="item" style="left:300px;">
			<span>2</span>
			<div class="info">设置字段</div>
		</div>
		
		<div class="item" style="left:540px;">
			<span>3</span>
			<div class="info">导入数据</div>
		</div>
		
		<div class="item" style="left:780px;">
			<span>4</span>
			<div class="info">查询设置</div>
		</div>
	</div>
	
	<div id="id_include">
		<form id="id_frm" method="post" enctype="multipart/form-data" action="${base}/manage/upload-excel">
		<div class="import-content">
			<div>
				<input type="file" name="file" style="display: none;" id="id_file" accept=".csv, text/csv, .xls, application/vnd.ms-excel, .xlsx, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
				<div id="id_excel_button" class="button">选择Excel</div>
			</div>
			<p style="display: none;height: 100%;text-align: center;line-height: 120px;">正在上传，请稍后...</p>
		</div>
		</form>
		
		<div class="demo">
			<div style="height: 30px;line-height: 30px;color: #aaa;">表格示例：有标准行列的一维数据表格 <a href="${base}/excel/excel.xlsx" style="text-decoration: underline;color: red;">示例表格下载</a></div>
			<div class="demo-img"></div>
		</div>
	</div>
</div>
</@t_admin.body>
