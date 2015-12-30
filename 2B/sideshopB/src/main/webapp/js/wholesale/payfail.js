var paychanel = "";

$(function(){
	loadJs();
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
	if(parseInt(paychanel) == paychannelLkl || parseInt(paychanel) == paychannelLklWallet){
		window.location.href = payurl;
	}else{
		cordova._native.pay.pay(
				paychanel,
				payurl,
				function(){
					window.location.href = "paysuccess.html";
				},
				function(){
					window.location.href = "payfail.html?t=" +t+ "&paychanel=" +paychanel;
				}
		)
	}
})