$(function(){
	var url = window.location.href;
	var orderProviderId = getParamValueByName(url, "orderProviderId");
	var returnFlag = getParamValueByName(url, "returnFlag");
	var earningsTime = getParamValueByName(url, "earningsTime");
	var earningsType = getParamValueByName(url, "earningsType");
	var payId = getParamValueByName(url, "payId");
	var data = {
		"earningsDate":earningsTime,
		"orderProviderId":orderProviderId,
		"returnFlg":returnFlag,
		"earningsType":earningsType,
		"payId":payId
		
	};
	var resultJson = ajaxCommon(urlGetOrderDetail, data);
	var payContainer_1 = $(".detail-top:eq(0)");
	var payContainer_2 = $(".detail-top:eq(1)");
	var payContainer_3 = $(".detail-top:eq(2)");
	var payContent_1 = "";
	var payContent_2 = "";
	var payContent_3 = "";
	var goodsContainer = $(".detail-middle");
	var goodsContent = "";
	var returnGoodsContent = "";
	if(resultJson._ReturnCode === returnCodeSuccess){
		var _returnData = resultJson._ReturnData;
		//订单信息
		payContent_1 += "<li><i>订单号：</i><em>" +_returnData.providerOrderId+ "</em></li>";
		payContent_1 += "<li><i>下单时间：</i><em>" +_returnData.orderDate+ "</em></li>";
		payContent_1 += "<li><i>配送方式：</i><em>" +_returnData.isDeliverTohome+ "</em></li>";
		payContent_1 += "<li><i>付款方式：</i><em>" +_returnData.payMode+ "</em></li>";
		payContent_1 += "<li><i>订单来源：</i><em>" +_returnData.source+ "</em></li>";
		
		payContent_2 += "<li><i>订单金额：</i><em>￥" +_returnData.orderAmount+ "</em></li>";
		payContent_2 += "<li><i>商品金额：</i><em>￥" +_returnData.orderProviderAmount+ "</em></li>";
		if(earningsType == selfGoods){
			payContent_2 += "<li><i>运费：</i><em>￥" +_returnData.freight+ "</em></li>";
			payContent_2 += "<li><i>拉卡拉优惠金额：</i><em>￥" +_returnData.couponValue+ "</em></li>";
			payContent_2 += "<li><i>结算金额：</i><em>￥"+_returnData.settleAmount2+"</em></li>";
		}
		if(_returnData.settleAmount != ""){
			var payStatus = _returnData.payStatus;
			var statusDesc = "已到账";
			if(payStatus != "4") {
				statusDesc = "未到账";
			}
			payContent_3  += "<li><i>到账金额：</i><em>￥" +_returnData.settleAmount2+ "</em></li>";
			payContent_3  += "<li><i>支付说明：</i><em>" +_returnData.payRank+ "</em></li>";
			if(payStatus == "4") {
				payContent_3  += "<li><i>支付流水号：</i><em>" +_returnData.payId+ "</em></li>";
			}
			payContent_3  += "<li><i>到账状态：</i><em>" +statusDesc+ "</em></li>";
			if(payStatus == "4") {
				payContent_3  += "<li><i>到账时间：</i><em>" +_returnData.payTime+ "</em></li>";
			}
			if(payStatus == "5") {
				if(_returnData.erroLog != msgSuccessEarnings){
					payContent_3 += "<li><i>支付失败，原因：</i><em>" +_returnData.erroLog+ "</em></li>";
				}
			}
		}
		payContainer_1.html("").append(payContent_1);
		payContainer_2.html("").append(payContent_2);
		payContainer_3.html("").append(payContent_3);
		//购买清单
		var oditmList = _returnData.oditmList;
		if(oditmList.length > 0){
			for(var i = 0;i < oditmList.length; i ++){
				goodsContent += "<li>";
				goodsContent += "<div class='clearfix'>";
				var goodsid = oditmList[i].goodId;
				if(typeof(oditmList[i].tgoodinfopoolid)!="undefined" && oditmList[i].tgoodinfopoolid!="") {
					goodsid = oditmList[i].tgoodinfopoolid;
				}
				goodsContent += "<span><img src='" +urlImage+ "/" +goodsid+ "/" +imgw300+ "/" +oditmList[i].goodPicture.split(";")[0]+ "' onerror='productErrImg(this);'/></span>";
				goodsContent += "<span>";
				if(oditmList[i].directflag != "" && oditmList[i].directflag == "1") {
					goodsContent += "<i class='color drop'>直降</i>";
				}
				goodsContent += "<em>" +oditmList[i].goodName+ "</em>";
				if(oditmList[i].goodSpecification != "") {
					goodsContent += "<i></i><em>"+oditmList[i].goodSpecification+"</em><br/>";
				}
				if(earningsType == platGoods) {
					goodsContent += "<i>分润：</i><em>￥" +parseFloat(oditmList[i].earningsAmount).toFixed(2)+ "</em><br/>";
				}else {
					//goodsContent += "<i>商品流水：</i><em>￥" +parseFloat(oditmList[i].earningsAmount).toFixed(2)+ "</em>";
				}
				goodsContent += "</span>";
				goodsContent += "<span>";
				goodsContent += "<i>￥</i><em>" +parseFloat(oditmList[i].goodSalePrice).toFixed(2)+ "</em><br/>";
				goodsContent += "<i>x</i><em>" +oditmList[i].goodCount+ "</em>";
				goodsContent += "</span>";
				goodsContent += "</div>";
				goodsContent += "<ul>";
				goodsContent += "<li><i>订单状态：</i><em>" +oditmList[i].orderState+ "</em></li>";
				goodsContent += "<li><i>签收时间：</i><em>" +oditmList[i].receiveDate+ "</em></li>";
				if(_returnData.returnFlg == "1") {
					goodsContent += "<li><i>售后状态：</i><em>" +oditmList[i].invaliState+ "</em></li>";
					goodsContent += "<li><i>发起退货时间：</i><em>" +oditmList[i].returnDate+ "</em></li>";
					goodsContent += "<li><i>退货完成时间：</i><em>" +oditmList[i].refundTime+ "</em></li>";
				}else {
					goodsContent += "<li><i>售后状态：</i><em>无售后</em></li>";
				}
				goodsContent += "</ul>";
			}
			goodsContainer.html("").append(goodsContent);
		}else{
			//购买清单为空
		}
	}else{
		//接口返回错误
	}
})