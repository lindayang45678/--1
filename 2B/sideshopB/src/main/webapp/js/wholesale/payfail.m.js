var paychanel = "";

$(function(){
	//删除二次支付标识
	storage.removeItem("ispay");
});

$("#to-pay").on("tap",function(){
	var url = window.location.href;
	paychanel = getParamValueByName(url, "paychanel");
	if(paychanel != ""){
		paychanel = paychanel;
	}else{
		paychanel = storage.getItem("paychanel") || "";
	}
	var payurl = storage.getItem("payurl") || "";
	window.location.href = payurl;
})