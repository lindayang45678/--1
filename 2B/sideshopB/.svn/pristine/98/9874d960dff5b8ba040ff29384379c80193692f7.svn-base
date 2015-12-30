$(function(){
	var url = window.location.href;
	var orderProviderId = getParamValueByName(url, "orderProviderId");
	var returnFlg = getParamValueByName(url, "returnFlg");
	var earningsTime = getParamValueByName(url, "earningsTime");
	var earningsType = getParamValueByName(url, "earningsType");
	var data = {
		"orderProviderId":orderProviderId,
		"returnFlg":returnFlg,
		"earningsTime":earningsTime
	};
	var resultJson = ajaxCommon(urlGetOrderDetail, data);
	resultJson = convertNullToEmpty(resultJson);
	//测试数据
	//resultJson = {"_ReturnCode":"000000","_ReturnData":{"token":"4tYRPwmovTOMHzFN3YTBtA==","orderDate":"2015-03-06 15:05:39","cusTelNo":"18810054187","oditmList":[{"goodSalePrice":5,"invaliState":"正常订单","receiveDate":"2015-10-10 12:12:12","goodCount":4,"returnDate":"2015-11-22 12:12","orderPrice":20,"orderState":"已发货","goodName":"测试普通商品1111 胡九杰","earningsAmount":4.8},{"goodSalePrice":5,"invaliState":"正常订单","receiveDate":"2015-10-10 12:12:12","goodCount":4,"returnDate":"2015-11-22 12:12","orderPrice":20,"orderState":"已发货","goodName":"测试普通商品1111 胡九杰","earningsAmount":4.8}],"terraceGoodsProfit":10.2,"ownGoodsAccounts":5,"returnFlg":"正常订单","platorSelf":"自营","addr":"丹棱街6号中关村金融大厦","cusName":"胡九杰","prvoidOrderId":"1503493045445343","isDeliverTohome":"送货上门","source":"开店宝"},"_ReturnMsg":null};
	var payContainer = $(".detail-top");
	var payContent = "";
	var goodsContainer = $(".detail-middle");
	var goodsContent = "";
	var profitContainer = $(".detail-bottom");
	var profitContent = "";
	var returnGoodsContent = "";
	if(resultJson._ReturnCode === returnCodeSuccess){
		var _returnData = resultJson._ReturnData;
		//订单信息
		payContent += "<li><i>订单号：</i><em>" +_returnData.prvoidOrderId+ "</em></li>";
		payContent += "<li><i>下订时间：</i><em>" +_returnData.orderDate+ "</em></li>";
		payContent += "<li><i>收货人姓名：</i><em>" +_returnData.cusName+ "</em></li>";
		payContent += "<li><i>收货人手机号：</i><em>" +_returnData.cusTelNo+ "</em></li>";
		payContent += "<li><i>配送方式：</i><em>" +_returnData.isDeliverTohome+ "</em></li>";
		payContent += "<li><i>配送地址：</i><em>" +_returnData.addr+ "</em></li>";
		payContent += "<li><i>订单来源：</i><em>" +_returnData.source+ "</em></li>";
		payContainer.html("").append(payContent);
		//购买清单
		var oditmList = _returnData.oditmList;
		if(oditmList.length > 0){
			for(var i = 0;i < oditmList.length; i ++){
				goodsContent += "<li>";
				goodsContent += "<div><em>" +oditmList[i].goodName+ "</em></div>";
				goodsContent += "<div><i>单价：</i><em>" +oditmList[i].goodSalePrice+ "</em><i>数量：</i><em>" +oditmList[i].goodCount+ "</em><i>金额：</i><em>" +oditmList[i].orderPrice+ "</em></div>";
				goodsContent += "<div><i>订单状态：</i><em>" +oditmList[i].orderState+ "</em></div>";
				goodsContent += "<div><i>签收时间：</i><em>" +oditmList[i].receiveDate+ "</em></div>";
				goodsContent += "<div><i>售后状态：</i><em>" +oditmList[i].invaliState+ "</em></div>";
				goodsContent += "</li>";
				goodsContainer.html("").append(goodsContent);
				//退货详情
				returnGoodsContent += "<p><em>" +oditmList[i].goodName+ "</em><i>于</i><em>" +oditmList[i].returnDate+ "</em><i>发起退货，需扣除已给的分润收益；</i></p>";
			}
		}else{
			//购买清单为空
		}
		//收益情况
		//自营商品
		if(earningsType === selfGoods){
			profitContent += "<li><i>本单收益：</i><em>" +_returnData.ownGoodsAccounts+ "</em></li>";
		}else{
			profitContent += "<li><i>本单收益：</i><em>" +_returnData.terraceGoodsProfit+ "</em></li>";
		}
		//退货清单
		if(returnFlg === "2"){
			profitContent += "<li>";
			profitContent += "<i>说明：</i>";
			profitContent += returnGoodsContent;
			profitContent += "</li>";
		}
		profitContainer.html("").append(profitContent);
	}else{
		//接口返回错误
	}
})