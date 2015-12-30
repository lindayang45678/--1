var earningsType = "";

/* 初始化iScroll*/
var incomesScroll;
function scrollIncomes(){
	incomesScroll = new iScroll("order-incomes",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			incomesSearch();
		},
	});
}

/*根据浏览器的高度计算iScroll的高度*/
$(function(){
	$("#order-incomes").height(windowHeight - $("header").height());
	$("#order-incomes").css("margin-top","0");
})

$(function(){
	var url = window.location.href;
	earningsType= getParamValueByName(url, "type");
	if(earningsType == "self"){
		$("title").html(msgSelfEarnings);
		$("h1").html(msgSelfEarnings);
		earningsType = selfGoods;
	}else{
		$("title").html(msgPlatEarnings);
		$("h1").html(msgPlatEarnings);
		earningsType = platGoods;
	}
})

$(function(){
	scrollIncomes();
	incomesSearch();
})

var page = 0;
var pageSize = 12;
var hasNextPage = true;//是否有下一页

function incomesSearch(){
	var container = $(".order-incomes ul");
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			page ++;
			console.log("page: "+page);
			if(earningsType == platGoods){
				pageSize = 3;
				hasNextPage = false;
			}else {
				hasNextPage = false;
			}
			var data = {
					"mobile":mobile,
					"ecNetNo":netNo,
					"earningsType":earningsType,
					"page":page,
					"pageSize":pageSize
			};
			var resultJson = ajaxCommon(urlGetCycleEarningsList, data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _returnData = resultJson._ReturnData;
				if(_returnData.length > 0){
					for(var i = 0; i < _returnData.length; i ++){
						content += "<li>";
						var url= "javascript:;";
						if(earningsType === selfGoods){
							/*url = "incomesmonth.html?";
							url += "type=self";
							url += "&ownTodayEarnings=" +_returnData[i].ownSelfAmount;
							url += "&ownTodayOrderNum=" +_returnData[i].ownSelf0rderNum;
							url += "&ownSettleAmont=" +_returnData[i].ownSettleAmount;
							url += "&ownSettleNum=" +_returnData[i].ownSettle0rderNum;
							url += "&ownSettleDate=" +_returnData[i].earningsTime;
							url += "&from=incomes";*/
							
							url = "selfincomes.html?";
							url += "settlementId=" +_returnData[i].settlementId;
							url += "&orderStartTime="+_returnData[i].orderStartTime;
							url += "&orderEndTime="+_returnData[i].orderEndTime;
							
						}else{
							url = "incomesmonth.html?";
							url += "type=plat";
							url += "&terraceEarnings=" +_returnData[i].terraceSelfAmount;
							url += "&terraceOrderNum=" +_returnData[i].terraceSelf0rderNum;
							url += "&terraceSettleAmont=" +_returnData[i].terraceSettleAmount;
							url += "&terraceSettleNum=" +_returnData[i].terraceSettle0rderNum;
							url += "&terraceSettleDateS=" +_returnData[i].orderStartTime;
							url += "&terraceSettleDateE=" +_returnData[i].orderEndTime;
							url += "&from=incomes";
						}
						content += "<a href='" +url+"'>";
						content += "<div class='order-incomes-title'>";
						content += "<span>" +formatData(_returnData[i].orderStartTime,"4")+ "-" +formatData(_returnData[i].orderEndTime,"4")+ "</span>";
						/*if(earningsType === selfGoods){
							content += "<span>" +formatData(_returnData[i].earningsTime,"4")+ "</span>";
						}else{
							content += "<span>" +formatData(_returnData[i].orderStartTime,"4")+ "-" +formatData(_returnData[i].orderEndTime,"4")+ "</span>";
						}*/
						content += "</div>";
						content += "<div class='order-incomes-item'>";
						content += "<div>";
						if(earningsType === selfGoods){
							$(".container").addClass("proprietary");
							content += "<span>流水：<i class='c-color'>￥</i><em class='c-color'>" +_returnData[i].ownSelfAmount+ "</em></span><span>" +_returnData[i].ownSelf0rderNum+ "单</span><br/>";
							content += "<span>结算：<i class='c-orange'>￥</i><em class='c-orange'>" +_returnData[i].ownSettleAmount2+ "</em></span><span>" +_returnData[i].ownSettle0rderNum2+ "单</span><br/>";
							content += "<span>到账：<i class='c-blue'>￥</i><em class='c-blue'>" +_returnData[i].ownSettleAmount+ "</em></span><span>" +_returnData[i].ownSettle0rderNum+ "单</span><br/>";
						}else{
							content += "<span>分润：￥<em>" +_returnData[i].terraceSelfAmount+ "</em></span><span>" +_returnData[i].terraceSelf0rderNum+ "单</span><br/>";
							content += "<span>到账：￥<em>" +_returnData[i].terraceSettleAmount+ "</em></span><span>" +_returnData[i].terraceSettle0rderNum+ "单</span>";
						}
						content += "</div>";
						content += "<div><span class='index-link'></span></div>";
						content += "</div>";
						content += "</a>";
						content += "</li>";	
					}
					container.append(content);
					incomesScroll.refresh();
				}else{
					if(page == 1){
						//无商品结算
						if(earningsType == selfGoods){
							showNoDataMsg(msgNoSelfAmont);
						}else{
							showNoDataMsg(msgNoPlatAmont);
						}
					}else{
						hasNextPage = false;
					}
				}
			}
		}
	},400)
}