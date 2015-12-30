var paydata = $.parseJSON(storage.getItem("paydata"));
$(function() {
	var ispay = storage.getItem("ispay");
	if(ispay == "ispay") {
		window.location.href = "../wholesale/payfail.html";
	}
	loadJs();
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
		"source":sourceSbApp2BShenbian,
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
        		if(paychanel == paychanelWx) {
        			payurl = {"payurl":$.parseJSON(payurl)};
        		}
        		if(paychanel == paychanelZfb) {
        			payurl = {"payurl":$.parseJSON(JSON.stringify(payurl))};
        		}
        		if(paychanel == paychannelLklCard) {
        			payurl = $.parseJSON(payurl);
        			payurl.mobile = paydata.mobile;
        			payurl = {"payurl":payurl};
        		}
        		storage.setItem("payurl",JSON.stringify(payurl));
        		storage.setItem("paychanel",paychanel);
        		storage.setItem("ispay","ispay");
        		if(parseInt(paychanel) == paychannelLkl) {
        			storage.setItem("payurl",payurl);
        			window.location.href = payurl;
        		} else {
        			if(typeof(cordova)!=undefined && typeof(cordova)!="undefined") {
        				cordova._native.pay.pay(
        					paychanel,
        					JSON.stringify(payurl),
        					function(){
        						window.location.href = "../wholesale/paysuccess.html";
        					},
        					function(){
        						window.location.href = "../wholesale/payfail.html?t=" +t+ "&paychanel=" +paychanel;
        					}	
        				)
        			}
        		}
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