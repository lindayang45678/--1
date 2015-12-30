$(function() {
	var sUrl = window.location.href;
	var logno = getParamValueByName(sUrl, "logno");
	var state = getParamValueByName(sUrl, "state");
	var orderitemsid = getParamValueByName(sUrl,"orderitemsid");
	var data = {"logno":logno,"orderitemsid":orderitemsid};
	console.log(data);
	var resultJson = ajaxCommon(urlOrderExpressSearch, data);
	resultJson = convertNullToEmpty(resultJson);
	console.log(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		if(state == stateYFH) {
			$("#signaturetime").hide();
			$("#signaturetime").prev().hide();
		}
		//发货人
		var sendName = resultJson._ReturnData.sendName;
		$("#sendName").text(sendName);
		//发货时间
		var startTime = resultJson._ReturnData.startTime.substring(0 , 19);
		$("#startTime").text(startTime);
		//快递公司
		var com = resultJson._ReturnData.com;
		$("#com").text(com);
		$("#logno").text(logno);
		//收件人
		var cusName = resultJson._ReturnData.cusName;
		$("#cusName").text(cusName);
		//收件时间
		var receivertime = resultJson._ReturnData.receivertime.substring(0 , 19);
		$("#signaturetime").text(receivertime);
		//配送状态--status=2已签收、否则未签收
		var status = resultJson._ReturnData.state;
		if(status == "104") {
			$("#status").text("已签收");
		} else {
			$("#status").text("已发货");
		}
		var deliveryinfos = resultJson._ReturnData.deliveryinfo.items;
		var ret = '';
		for(var i=0; i<deliveryinfos.length; i++) {
			ret += '<li class="clearfix"><div>';
			ret += deliveryinfos[i].time;
			ret += '</div><div class="dealing"><span>';
			ret += deliveryinfos[i].addr;
			ret += '&nbsp;&nbsp;';
			ret += deliveryinfos[i].remark;
			ret += '</span></div></li>';
		}
		$("#deliveryinfos").html(ret);
	}
});