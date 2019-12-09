function edititle(td){
	td.find(".modify_title").show().find("input").select().focus();;
}

function addColumn($trs,index,data,name){
	console.log(data.id);
	
	$trs.each(function (i) {
		var $tr=$(this);
		if(i==0){
			var first = $tr.find("td:eq(0)");
	   		var clone=first.clone(true).attr("data-column",data.id).attr("data-order",data.order);
	   		
	   		clone.find(".content").html(name);
	   		clone.find("input").val(name).attr("alt",data.id);
	   		var last_td=clone.insertBefore($tr.find("td:eq(" + (index) + ")"));
	   		edititle(last_td);
		}
		else{
			var first = $tr.find("td:eq(0)");
			first.clone().insertBefore($tr.find("td:eq(" + (index) + ")"));
		}
   		
   		
    });
}