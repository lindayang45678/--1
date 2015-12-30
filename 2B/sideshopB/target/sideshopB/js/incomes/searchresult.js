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

/*根据浏览器的高度计算iScroll的高度*/
$(function(){
	$("#incomes-wrap").css("height",windowHeight-$("header").height());
});

$(function(){
	scrollIncomes();
	getIncomesList();
})

var page = 0;
var hasNextPage = true;//是否有下一页
var data;

$(function(){
	var url = window.location.href;
	data = getParamValueByName(url, "data");
	console.log(data);
	data = $.parseJSON(data);
})

function getIncomesList(){
	var container = $(".incomes ul");
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			page ++;
			data.page = page;
			console.log("page: "+page);
			var resultJson = ajaxCommon(urlGetEarningsData, data);
			//测试数据
			//resultJson = {"_ReturnCode":"000000","_ReturnData":[{"token":"Kj4Elvxq713P1AqiIFSBCg==","prioderOrderId":"150306150056797000011439","earningsAmount":10.2,"settlementAmount":105.5,"settlementFormula":"(105.5000-95.5000)","payRateAmount":0,"payRateFormula":"(105.5000*0%)","orderId":"15030615005679700001","payMode":"拉卡拉","payRate":0,"returnFlg":0,"earningsDate":"2015-03-10"},{"token":"Kj4Elvxq713P1AqiIFSBCg==","prioderOrderId":"150306150056797000011439","earningsAmount":10.2,"settlementAmount":105.5,"settlementFormula":"(105.5000-95.5000)","payRateAmount":0,"payRateFormula":"(105.5000*0%)","orderId":"15030615005679700001","payMode":"拉卡拉","payRate":0,"returnFlg":0,"earningsDate":"2015-03-10"}],"_ReturnMsg":null};
			if (resultJson._ReturnCode === returnCodeSuccess) {
				var _ReturnData = resultJson._ReturnData;
				if(_ReturnData.length > 0){
					for (var i = 0; i < _ReturnData.length; i++) {
						content += "<li class='incomes-result'>";
						content += "<h2 class='color'>" +formatData(_ReturnData[i].earningsDate)+ "</h2>";
						content += "<div><i>订单号：</i><em>" +_ReturnData[i].prioderOrderId+ "</em></div>";
						content += "<div><i>付款方式：</i><em>" +_ReturnData[i].payMode+ "</em></div>";
						if(data.earningsType === selfGoods){
							content += "<div><i>支付手续费率：</i><em>" +_ReturnData[i].payRate+ "%</em></div>";
							content += "<div><i>支付手续费：</i><em>￥" +_ReturnData[i].earningsAmount+ "</em>&nbsp;&nbsp;<em>" +_ReturnData[i].payRateFormula+ "</em></div>";
							content += "<div><i>结算金额：</i><em>￥" +_ReturnData[i].earningsAmount+ "</em>&nbsp;&nbsp;<em>" +_ReturnData[i].settlementFormula+ "</em></div>";
						}else{
							content += "<div><i>店主收益：</i><em>￥" +_ReturnData[i].earningsAmount+ "</em></div>";
						}
						content += "</li>";
					}
					container.append(content);
					incomesScroll.refresh();
				}else{
					if(page == 1) {
						window.location.href = "searchfail.html";
					} else {
						hasNextPage = false;
					}
				}
			} else {
				//接口返回错误
				//window.location.href = "searchfail.html";
			}
		}
	}, 50)
}