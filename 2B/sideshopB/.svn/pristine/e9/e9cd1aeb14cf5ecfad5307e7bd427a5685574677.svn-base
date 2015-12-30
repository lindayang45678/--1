$(function() {

	var sUrl = window.location.href;
	var from = getParamValueByName(sUrl, "from");
	if(from == "order") {
		//从订单管理进来时，返回到订单管理
		$("#back").attr("href", "javascript:window.history.go(-1);");
	}

	$("#btnCustreceipt").on("tap", function() {		
		var sincecode = $(".receipt input").val(); //顾客取货验证码
		var data = {"netno":netNo, "mobile":mobile, "sincecode":sincecode};
		if($.trim(sincecode) == "") {
			showAlertMsg("请输入取货码");
			return;
		}
		var resultJson = ajaxCommon(urlCustreceipt, data);
		resultJson = convertNullToEmpty(resultJson);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			if(resultJson._ReturnData.state == stateWFH) {
				showAlertMsg("未发货，不能取货");
				return;
			}
			if(resultJson._ReturnData.state == stateBFQS) {
				showAlertMsg("已取货，不能重复取货");
				return;
			}
			if(resultJson._ReturnData.state == stateYQS) {
				showAlertMsg("已取货，不能重复取货");
				return;
			}
			if(resultJson._ReturnData.returnstate == 114) {
				showAlertMsg("已拒收，不能取货");
				return;
			}
			resultJson = JSON.stringify(resultJson);
			storage.setItem("custReceiptQuery", resultJson);
			window.location.href = "custreceiptinfo.html?sincecode=" + sincecode + "&t=" + t;
		} else {
			window.location.href = "custreceiptfail.html?sincecode=" + sincecode + "&t=" + t;
		}
	});

});