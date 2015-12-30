var paydata = $.parseJSON(storage.getItem("paydata"));
$(function() {
	var ispay = storage.getItem("ispay");
	if(ispay == "ispay") {
		window.location.href = "../wholesale/payfail.html";
	}
	var goodsname = paydata.goodsname;
	var actualamount = paydata.actualamount;
	var goodsnameDesc = "";
	if(goodsname.split(",").length > 1) {
		goodsnameDesc = goodsname.split(",")[0] + ";...";
	}else{
		goodsnameDesc = goodsname.split(",")[0];
	}
	$("#goodsname").html(goodsnameDesc);
	$("#actualamount").html(parseFloat(actualamount).toFixed(2));
	
	//遮罩层高度
	$(".masker").css("top",$("header").height()).height(windowHeight - $("header").height());
});

$(".pay-items div").on("tap", function() {
	$(this).addClass("paytype-selected").siblings("div").removeClass("paytype-selected");
});

$("#topay").on("click", function() {
	$(".masker").removeClass("hidden");
	var paychanel = $(".pay-items div[class=paytype-selected]").find("button").attr("value");
	var data = {
		"tallorderid":paydata.tallorderid,
		"source":sourceSbApp2BWebchat,
		"paychanel":paychanel,
		"mobile":paydata.mobile
	};
	
	//请求接口
	$.ajax({
        type: "POST",
        url: urlOrdertopay,
        data: data,
        dataType: "json",
        async: true,
        cache: false,
        timeout:15000,
        beforeSend: function() {},
        success: function(result) {
        	var resultJson = result;
        	if (resultJson._ReturnCode === returnCodeSuccess) {
        		var payurl = resultJson._ReturnData.payurl;
        		storage.setItem("payurl",payurl);
        		storage.setItem("ispay","ispay");
        		window.location.href = payurl;
        	} else {
        		$(".masker").addClass("hidden");
        		showAlertMsg(resultJson._ReturnMsg);
        	}
        },
        error: function(xhr, errorType, error) {
        	$(".masker").addClass("hidden");
        	showAlertMsg(msgToPayTimeOut);
        },
        complete: function() {}
	});
});