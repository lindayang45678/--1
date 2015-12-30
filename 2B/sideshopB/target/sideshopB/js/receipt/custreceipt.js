$(function() {
	$('#btnCustreceipt').on("tap", function() {		
		var verificationCode = $('.receipt input').val();//顾客取货验证码
		//var verificationCode = 74061976;//测试用
		var data = {"netno":netNo,"mobile":mobile,"sincecode":verificationCode};
		if(verificationCode == ''){
			showAlertMsg(msgVerificationEmpty);
			return;
		}
		var resultJson = ajaxCommon(urlCustreceipt, data);
		//var resultJson = {"_ReturnCode":"000000","_ReturnData":{"state":102,"actualamount":22.0000,"custelno":"15210786169","cusname":"王小胜","paychanel":240,"returnstate":112,"orderproviderid":"15021016112095940983454","qoclist":[{"goodsname":"测试生鲜商品运费","goodsnum":"1","norms":""}]},"_ReturnMsg":null};
		if(resultJson._ReturnCode === returnCodeSuccess) {
			if(resultJson._ReturnData.state == 103) {
				showAlertMsg("已取货，不能重复取货！");
				return;
			}
			if(resultJson._ReturnData.state == 104) {
				showAlertMsg("已取货，不能重复取货！");
				return;
			}
			if(resultJson._ReturnData.returnstate == 114) {
				showAlertMsg("已拒收，不能取货！");
				return;
			}
			resultJson = convertNullToEmpty(resultJson);
			resultJson = JSON.stringify(resultJson);
			storage.setItem("custReceiptQuery", resultJson);
			window.location.href = "custreceiptinfo.html?sincecode=" +verificationCode+ "&t=" + t;	
		}
		else {
			window.location.href = "custreceiptfail.html?t=" + t;
		}	
	});	
});