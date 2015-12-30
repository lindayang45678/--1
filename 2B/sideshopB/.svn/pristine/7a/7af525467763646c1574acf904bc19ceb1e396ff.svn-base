$(function() {
	$("#shopreceipt").on("tap", function() {		
		var logno = $(".receipt input").val();  //店主快递单号
		if($.trim(logno) == "") {
			showAlertMsg("请输入物流单号");
			return;
		}
		var data = {"netno":netNo, "mobile":mobile, "logno":logno, "psam":psam};
		var resultJson = ajaxCommon(urlreceipt, data);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			resultJson = convertNullToEmpty(resultJson);
			resultJson = JSON.stringify(resultJson);
			storage.setItem("shopReceiptQuery",resultJson);
			window.location.href = "shopreceiptinfo.html?logno="+ logno +"&t=" + t;	
		} else {
			if(resultJson._ReturnMsg == "b") {
				showAlertMsg("已收货，不能重复收货");
				return;
			}
			window.location.href = encodeURI(encodeURI("shopreceiptfail.html?logno="+ logno +"&t=" + t));
		}
	});
});