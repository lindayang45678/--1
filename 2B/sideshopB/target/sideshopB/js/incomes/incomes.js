var earningsType = "";
/* 初始化iScroll*/
var incomesScroll;
function scrollIncomes(){
	incomesScroll = new iScroll("incomes-wrap",{ 
		hScrollbar: false, 
		vScrollbar: false,
		checkDOMChanges: true,
		onScrollEnd: function(){
			getIncomesList();
		}
	});
}

/* 根据浏览器的高度计算iScroll的高度*/
$(function(){
	$("#incomes-wrap").css("height",windowHeight-$(".order-incomes-remark").height()-$("header").height());
});


$(function(){
	scrollIncomes();
	getIncomesList();
})

$(function(){
	var url = window.location.href;
	earningsType= getParamValueByName(url, "type");
	if(earningsType === "self"){
		$("title").html("自营商品结算");
		$("h1").html("自营商品结算");
		earningsType = selfGoods;
	}else{
		$("title").html("平台商品结算");
		$("h1").html("平台商品结算");
		earningsType = platGoods;
	}
})

var page = 0;
var pageSize = 10;
var hasNextPage = true;//是否有下一页

function getIncomesList(){
	var container = $(".order-incomes ul");
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			if(earningsType === selfGoods){
				page ++;
				pageSize = 10;
			}else{
				page = 1;
				pageSize = 3;
			}
			console.log("page: "+page);
			var data = {
					"mobile":mobile,
					"ecNetNo":netNo,
					"earningsType":earningsType,
					"page":page,
					"pageSize":pageSize
			};
			console.log(data);
			var resultJson = ajaxCommon(urlGetEarningsOrderList, data);
			resultJson = convertNullToEmpty(resultJson);
			console.log(resultJson);
			//测试数据
			//resultJson = {"_ReturnCode":"000000","_ReturnData":[{"token":"ibhC0Yu31mcRC7Y+QpI6dg==","orderStartTime":"2015-01-20","orderEndtTime":"2014-12-21","earningsTime":"2014-12-21","terraceGoodsProfit":1130.98,"ownGoodsAccounts":200.1,"settlementId":"2014122120150120","terraceEarningsOrderNum":110,"ownEarningsOrderNum":2000,"authFlag":"未结算","totalEarningsOrderNum":2110,"totalEarningsAmount":1331.08},{"token":"ibhC0Yu31mcRC7Y+QpI6dg==","orderStartTime":"2015-02-20","orderEndtTime":"2015-01-21","earningsTime":"2014-12-21","terraceGoodsProfit":1130.98,"ownGoodsAccounts":200.1,"settlementId":"2015012120150220","terraceEarningsOrderNum":110,"ownEarningsOrderNum":2000,"authFlag":"未结算","totalEarningsOrderNum":2110,"totalEarningsAmount":1331.08},{"token":"ibhC0Yu31mcRC7Y+QpI6dg==","orderStartTime":"2015-03-20","orderEndtTime":"2015-02-21","earningsTime":"2014-12-21","terraceGoodsProfit":1130.98,"ownGoodsAccounts":200.1,"settlementId":"2015022120150320","terraceEarningsOrderNum":110,"ownEarningsOrderNum":2000,"authFlag":"未结算","totalEarningsOrderNum":2110,"totalEarningsAmount":1331.08}],"_ReturnMsg":null};
			var container = $(".order-incomes ul");
			var content = "";
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _returnData = resultJson._ReturnData;
				if(_returnData.length > 0){
					for(var i = 0; i< _returnData.length; i ++){
						content += "<li>";
						content += "<a href='incomesmonth.html?type=" +earningsType+ "&settlementId=" +_returnData[i].settlementId+ "&earningsDate=" +_returnData[i].earningsTime+ "'>";
						content += "<div class='order-incomes-title'>";
						//自营商品结算
						if(earningsType === selfGoods){
							content += "<span>" +formatData(_returnData[i].earningsTime)+ "</span>";
							content += "</div>";
							content += "<div class='order-incomes-item'>";
							content += "<div><span>￥" +_returnData[i].ownGoodsAccounts+ "</span><span>" +_returnData[i].ownEarningsOrderNum+ "单</span></div>";
						}else{
							content += "<span>" +formatData(_returnData[i].orderStartTime)+ "-" +formatData(_returnData[i].orderEndtTime)+ "</span>";
							content += "</div>";
							content += "<div class='order-incomes-item'>";
							content += "<div><span>￥" +_returnData[i].terraceGoodsProfit+ "</span><span>" +_returnData[i].terraceEarningsOrderNum+ "单</span></div>";
						}
						if(_returnData[i].authFlag === "未结算"){
							content += "<div><span class='color'>未结算</span><span class='index-link'></span></div>";
						}else{
							content += "<div><span>已结算</span><span class='index-link'></span></div>";
						}
						content += "</div>";
						content += "</a>";
						content += "</li>";
					}
					container.append(content);
					incomesScroll.refresh();
					if(earningsType === platGoods){
						hasNextPage = false;
					}
				}else{
					//无商品结算
					hasNextPage = false;
				}
			}else{
				//接口返回错误
			}
		}
	}, 50)
}