//获取运费信息
$(function(){
	var minAmounts ,transportExpenses ,singleFreeExpenses;
	var regs = '^[0-9]+(\.{0,1}[0-9]{0,})?$';
	if($.trim(minAmount).match(regs)){//判断localstorage起送价空或格式错误，给默认值
		if(minAmount.indexOf(".")!=-1){
			if(minAmount.split(".")[1].length>2)
				minAmount=minAmount.split(".")[0]+"."+minAmount.split(".")[1].slice(0,2);
			if(minAmount.split(".")[1].length==1)
				minAmounts = minAmount.toString()+"0";
			if(minAmount.split(".")[1].length==2)
				minAmounts = minAmount;
		}else{
			minAmounts = minAmount.toString()+".00";
		}
	}else{
		minAmounts = "29.00";
	}
	if($.trim(singleFreeExpense).match(regs)){//判断localstorage单笔满免空或格式错误，给默认值
		if(singleFreeExpense.indexOf(".") != -1){
			if(singleFreeExpense.split(".")[1].length > 2)
				singleFreeExpense=singleFreeExpense.split(".")[0]+"."+singleFreeExpense.split(".")[1].slice(0,2);
			if(singleFreeExpense.split(".")[1].length == 1)
				singleFreeExpenses = singleFreeExpense.toString()+"0";
			if(singleFreeExpense.split(".")[1].length==2)
				singleFreeExpenses = singleFreeExpense;
		}else{
			singleFreeExpenses = singleFreeExpense.toString()+".00";
		}
	}else{
		singleFreeExpenses = "79.00";
	}
	if($.trim(transportExpense).match(regs)){//判断localstorage运费空或格式错误，给默认值
		if(transportExpense.indexOf(".")!=-1){
			if(transportExpense.split(".")[1].length>2)
				transportExpense=transportExpense.split(".")[0]+"."+transportExpense.split(".")[1].slice(0,2);
			if(transportExpense.split(".")[1].length==1)
				transportExpenses = transportExpense.toString()+"0";
			if(transportExpense.split(".")[1].length==2)
				transportExpenses = transportExpense;
		}else{
			transportExpenses = transportExpense.toString()+".00";
		}
	}else{
		transportExpenses = "5.00";
	}
	
	$("#minAmount").val(minAmounts);
	$("#singleFreeExpense").val(singleFreeExpenses);
	$("#transportExpense").val(transportExpenses);
	
	document.getElementById("minAmount").onchange = function(){//纠正起送金额错误的输入
		var values = $(this).val();
		if(!values.match(regs)){
			$(this).val(minAmounts);
		}
		if(values.indexOf(".")!=-1){
			if(values.split(".")[1].length==0)
				$(this).val(values.toString()+"00");
			if(values.split(".")[1].length>2)
				$(this).val(values.split(".")[0]+"."+values.split(".")[1].slice(0,2));
		}
	}
	document.getElementById("singleFreeExpense").onchange = function(){//纠正单笔满多少钱免运费错误的输入
		var values = $(this).val();
		if(!values.match(regs)){
			$(this).val(singleFreeExpenses);
		}
		if(values.indexOf(".")!=-1){
			if(values.split(".")[1].length==0)
				$(this).val(values.toString()+"00");
			if(values.split(".")[1].length>2)
				$(this).val(values.split(".")[0]+"."+values.split(".")[1].slice(0,2));
		}
	}
	document.getElementById("transportExpense").onchange = function(){//纠正运费错误的输入
		var values = $(this).val();
		if(!values.match(regs)){
			$(this).val(transportExpenses);
		}
		if(values.indexOf(".")!=-1){
			if(values.split(".")[1].length==0)
				$(this).val(values.toString()+"00");
			if(values.split(".")[1].length>2)
				$(this).val(values.split(".")[0]+"."+values.split(".")[1].slice(0,2));
		}
	}
	
	//保存运费到localstorage
	$(".btn-red").on("touchend",function(e){
		var minAmounts=$.trim($("#minAmount").val());
		var singleFreeExpenses=$.trim($("#singleFreeExpense").val());
		var transportExpenses=$.trim($("#transportExpense").val());
		var aMs=parseFloat(minAmounts);
		var tEs=parseFloat(singleFreeExpenses);
		$(this).removeClass("focus");
		if(aMs>tEs){//单笔满免金额不能小于起送金额 
			showAlertMsg(msgFullFreeError);
			return false;
		}
		if(!minAmounts.match(regs)){
			showAlertMsg(msgFromAmountNotEptFmtError);
			return false;
		}
		if(!singleFreeExpenses.match(regs)){
			showAlertMsg(msgFullFreeNotEptFmtError);
			return false;
		}
		if(!transportExpenses.match(regs)){
			showAlertMsg(msgFreightNotEptFmtError);
			return false;
		}
		
		if(minAmounts.indexOf(".")!=-1){//起送金额截取两位小数
			if(minAmounts.split(".")[1].length>2)
				minAmounts=minAmounts.split(".")[0]+"."+minAmounts.split(".")[1].slice(0,2);
		}
		if(singleFreeExpenses.indexOf(".") != -1){//运费金额截取两位小数
			if(singleFreeExpenses.split(".")[1].length > 2)
				singleFreeExpenses = singleFreeExpenses.split(".")[0]+"."+singleFreeExpenses.split(".")[1].slice(0,2);
		}
		if(transportExpenses.indexOf(".") != -1){//单笔满免金额截取两位小数
			if(transportExpenses.split(".")[1].length > 2)
				transportExpenses=transportExpenses.split(".")[0]+"."+transportExpenses.split(".")[1].slice(0,2);
		}
		e.preventDefault();
		storage.setItem("minAmount",parseFloat(minAmounts));
		storage.setItem("singleFreeExpense",parseFloat(singleFreeExpenses));
		storage.setItem("transportExpense",parseFloat(transportExpenses));
		window.location.href = "editshop1.html?preSaveData=1&t="+t;
	});
})