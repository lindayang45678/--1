var earningsType = "";
var earningsDate = "";
var startTime = "";
var endtTime = "";

/* 初始化iScroll*/
var incomesScroll;
function scrollIncomes(){
	incomesScroll = new iScroll("incomes-wrap",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			getIncomesList();
		},
	});
}

/* 根据浏览器的高度计算iScroll的高度*/
$(function(){
	$("#incomes-wrap").css("height",windowHeight-$(".order-incomes ul").height()-$("header").height());
});

$(function(){
	scrollIncomes();
	getIncomesList();
})

/* 单个订单周期收益*/
$(function() {
	var url = window.location.href;
	var settlementId = getParamValueByName(url, "settlementId");
	earningsType = getParamValueByName(url, "type");
	earningsDate= getParamValueByName(url, "earningsDate");
	var data = {
		"mobile":mobile,
		"settlementId":settlementId,
		"ecNetNo":netNo,
		"earningsType":earningsType,
		"earningsDate":earningsDate
	};
	var container = $(".order-incomes ul");
	var content = "";
	var resultJson = ajaxCommon(urlGetEarningsOrderData, data);
	//测试数据
	//resultJson = {"_ReturnCode":"000000","_ReturnData":{"token":"4tYRPwmovTOMHzFN3YTBtA==","orderStartTime":"2015-01-20","orderEndtTime":"2014-12-21","earningsTime":"2014-12-21","terraceGoodsProfit":1130.98,"ownGoodsAccounts":200.1,"settlementId":"2014122120150120","terraceEarningsOrderNum":110,"ownEarningsOrderNum":2000,"authFlag":"未结算","totalEarningsOrderNum":2110,"totalEarningsAmount":1331.08},"_ReturnMsg":null};
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		content += "<li>";
		content += "<div class='order-incomes-title'>";
		if(earningsType === selfGoods){
			content += "<span>" +formatData(_returnData.earningsTime)+ "</span>";
			content += "<span><img src='../../images/question-mark.png' class='hidden'/></span>";
			content += "</div>";
			content += "<div class='order-incomes-item'>";
			content += "<div><span>￥" +_returnData.ownGoodsAccounts+ "</span><span>" +_returnData.ownEarningsOrderNum+ "单</span></div>";
		}else{
			content += "<span>" +formatData(_returnData.orderStartTime)+ "-" +formatData(_returnData.orderEndtTime)+ "</span>";
			content += "</div>";
			content += "<div class='order-incomes-item'>";
			content += "<div><span>￥" +_returnData.terraceGoodsProfit+ "</span><span>" +_returnData.terraceEarningsOrderNum+ "单</span></div>";
		}
		if(_returnData.authFlag === "未结算"){
			content += "<div><span class='color'>未结算</span></div>";
		}else{
			content += "<div><span>已结算</span></div>";
		}
		content += "</div>";
		content += "<p id='formula' class='hidden'>结算金额=（商品金额-优惠+运费）-（商品金额-优惠+运费）*支付手费率</p>";
		content += "</li>";
		container.html("").append(content);
		
		startTime = _returnData.orderStartTime;
		endtTime = _returnData.orderEndtTime;
	}else{
		//接口返回错误
	}
})

var page = 0;
var pageSize = 10;
var hasNextPage = true;//是否有下一页

/*平台/自营订单收益 */
function getIncomesList(){
	var container = $(".detail-plat-self ul");
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			page ++;
			console.log("page: "+page);
			var data = {
					"mobile":mobile,
					"startTime":startTime,
					"endtTime":endtTime,
					"earningsType":earningsType,
					"ecNetNo":netNo,
					"page":page,
					"pageSize":pageSize
			};
			var resultJson = ajaxCommon(urlGetEarningsData, data);
			console.log(resultJson);
			//测试数据
			//resultJson = {"_ReturnCode":"000000","_ReturnData":[{"token":"Kj4Elvxq713P1AqiIFSBCg==","prioderOrderId":"150306150056797000011439","earningsAmount":10.2,"settlementAmount":105.5,"settlementFormula":"(105.5000-95.5000)","payRateAmount":0,"payRateFormula":"(105.5000*0%)","orderId":"15030615005679700001","payMode":"拉卡拉","payRate":0,"returnFlg":0,"earningsDate":"2015-03-10"},{"token":"Kj4Elvxq713P1AqiIFSBCg==","prioderOrderId":"150306150056797000011439","earningsAmount":10.2,"settlementAmount":105.5,"settlementFormula":"(105.5000-95.5000)","payRateAmount":0,"payRateFormula":"(105.5000*0%)","orderId":"15030615005679700001","payMode":"拉卡拉","payRate":0,"returnFlg":0,"earningsDate":"2015-03-10"}],"_ReturnMsg":null};
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _returnData = resultJson._ReturnData;
				if(_returnData.length > 0){
					for(var i = 0; i< _returnData.length; i ++){
						content += "<li>";
						content += "<a href='details.html?orderProviderId=" +_returnData[i].prioderOrderId+ "&earningsTime=" +_returnData[i].earningsDate+ "&returnFlg=" +_returnData[i].returnFlg+ "&earningsType=" +earningsType+ "'>";
						content += "<div>";
						//自营商品
						if(earningsType === selfGoods){
							content += "<div><i>订单号：</i><em>" +_returnData[i].prioderOrderId+ "</em></div>";
							content += "<div><i>商品金额：</i><em>￥" +_returnData[i].orderAmount+ "</em></div>";
							content += "<div><i>运费：</i><em>￥" +_returnData[i].freight+ "</em></div>";
							content += "<div><i>优惠金额：</i><em>￥" +_returnData[i].couponValue+ "</em></div>";
							content += "<div><i>付款方式：</i><em>" +_returnData[i].payMode+ "</em></div>";
							content += "<div><i>支付手续费率：</i><em>" +_returnData[i].payRate+ "%</em></div>";
							content += "<div><i>支付手续费：</i><em>￥" +_returnData[i].payRateAmount+ "</em>&nbsp;&nbsp;<em>" +_returnData[i].payRateFormula+ "</em></div>";
							content += "<div><i>结算金额：</i><em>￥" +_returnData[i].settlementAmount+ "</em>&nbsp;&nbsp;<em>" +_returnData[i].settlementFormula+ "</em></div>";
						}else{
							content += "<div class='color'>" +formatData(_returnData[i].earningsDate)+ "</div>";
							content += "<div><i>订单号：</i><em>" +_returnData[i].prioderOrderId+ "</em></div>";
							content += "<div><i>付款方式：</i><em>" +_returnData[i].payMode+ "</em></div>";
							content += "<div><i>店主收益</i><em>-55.00</em></div>";
						}
						content += "</div>";
						content += "</a>";
						content += "</li>";
						
					}
					container.append(content);
					incomesScroll.refresh();
					//显示/隐藏自营金额结算公式
					selfFormula();
				}else{
					hasNextPage = false;
				}
			}else{
				//接口返回错误
			}
		}
	}, 50)
}

//页面标题和一级标题显示，自营商品结算说明
$(function() {
	if (earningsType === selfGoods) {
		$("title").html("自营商品结算");
		$("h1").html("自营商品结算");
	} else {
		$("title").html("平台商品结算");
		$("h1").html("平台商品结算");
	}
})

//显示/隐藏自营金额结算公式
function selfFormula(){
	if (earningsType === selfGoods) {
		$(".order-incomes-title img").removeClass("hidden");
	}
	$(".order-incomes-title img").on('tap', function() {
		$("#formula").toggleClass("hidden");
	});

	$("#formula").on('tap', function() {
		$(this).toggleClass("hidden");
	});
}
