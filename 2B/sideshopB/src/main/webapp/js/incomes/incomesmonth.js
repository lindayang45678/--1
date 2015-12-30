var earningsType = "";
var ownSettleDate = "";
var terraceSettleDateS = "";
var terraceSettleDateE = "";

/* 流水iScroll*/
var settleNoScroll;
function scrollSettleNo(){
	settleNoScroll = new iScroll("settel-no",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			incomesSearch(settleNo);
		},
	});
}

/* 到账iScroll*/
var settleYesScroll;
function scrollSettleYes(){
	settleYesScroll = new iScroll("settel-yes",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			incomesSearch(settleYes);
		}
	});
}

/* 根据浏览器计算iSroll高度*/
$(function(){
	$("#settel-no").height(windowHeight - $("header").height() - $(".month").height());
	scrollSettleNo();
})

function initScrollSettelYes() {
	$("#settel-yes").height(windowHeight - $("header").height() - $(".month").height() - $(".detail-settelyes-detail").height() - 10);
	scrollSettleYes();
}

/* 分润/到账切换*/
$(".month a").on("tap", function(){
	$(".month em").removeClass("active");
	$(this).find("em").addClass("active");
	$(".detail-settelyes").addClass("hidden");
	$(".detail-settelno").addClass("hidden");
	if($(this).attr("id") == "detail-settelyes"){
		$(".detail-settelyes").removeClass("hidden");
		page = 0;
		hasNextPage = true;
		$("#settel-yes ul").html("");
		incomesSettelYes();
		initScrollSettelYes();
		incomesSearch(settleYes);
		session.setItem("settle","yes");
	}else{
		$(".detail-settelno").removeClass("hidden");
		page = 0;
		hasNextPage = true;
		$("#settel-no ul").html("");
		incomesSearch(settleNo);
		session.setItem("settle","no");
	}
})

/* 单个订单周期收益*/
$(function() {
	var url = window.location.href;
	earningsType = getParamValueByName(url, "type");//收益类型
	var ownTodayEarnings = getParamValueByName(url, "ownTodayEarnings");//自营今日流水
	var ownTodayOrderNum = getParamValueByName(url, "ownTodayOrderNum");//自营今日订单
	var ownSettleAmont = getParamValueByName(url, "ownSettleAmont");//自营今日到账
	var ownSettleNum = getParamValueByName(url, "ownSettleNum");//自营结算单数
	ownSettleDate = getParamValueByName(url, "ownSettleDate");//自营结算今日日期
	
	var terraceEarnings = getParamValueByName(url, "terraceEarnings");//平台本月分润
	var terraceOrderNum = getParamValueByName(url, "terraceOrderNum");//平台本月订单
	var terraceSettleAmont = getParamValueByName(url, "terraceSettleAmont");//平台本月到账
	var terraceSettleNum = getParamValueByName(url, "terraceSettleNum");//平台结算单数
	terraceSettleDateS = getParamValueByName(url, "terraceSettleDateS");//平台结算周期开始
	terraceSettleDateE = getParamValueByName(url, "terraceSettleDateE");//平台结算周期结束
	
	var from = getParamValueByName(url, "from");
	if(from == ""){
		$(".back").attr("href","index.html");
	}else{
		$(".back").attr("href","javascript:window.history.go(-1)");
	}
	
	if(earningsType == "self"){
		earningsType = selfGoods;
		$("h1").html(formatData(ownSettleDate,"2") + "流水");
		$("title").html(formatData(ownSettleDate,"2")  + "流水");
		$("#detail-settelno em").html("流水￥" +ownTodayEarnings+ "（" +ownTodayOrderNum+ "单）");
		$("#detail-settelyes em").html("到账￥" +ownSettleAmont+ "（" +ownSettleNum+ "单）");
	}else{
		earningsType = platGoods;
		$("h1").html(formatData(terraceSettleDateS,"2") + "-" + formatData(terraceSettleDateE,"2"));
		$("title").html(formatData(terraceSettleDateS,"2") + "-" + formatData(terraceSettleDateE,"2"));
		$("#detail-settelno em").html("分润￥" +terraceEarnings+ "（" +terraceOrderNum+ "单）");
		$("#detail-settelyes em").html("到账￥" +terraceSettleAmont+ "（" +terraceSettleNum+ "单）");
	}
	
	var settle = session.getItem("settle");
	if(settle != null){
		$(".month em").removeClass("active");
		$(".detail-settelyes").addClass("hidden");
		$(".detail-settelno").addClass("hidden");
		if(settle == "yes"){
			$("#detail-settelyes em").addClass("active");
			$(".detail-settelyes").removeClass("hidden");
			incomesSettelYes();
			initScrollSettelYes();
			incomesSearch(settleYes);
		}else{
			$("#detail-settelno em").addClass("active");
			$(".detail-settelno").removeClass("hidden");
			incomesSearch(settleNo);
		}
	}else{
		settle = getParamValueByName(url, "settel");
		$(".month em").removeClass("active");
		$(".detail-settelyes").addClass("hidden");
		$(".detail-settelno").addClass("hidden");
		if(settle == "yes"){
			session.setItem("settle","yes");
			$("#detail-settelyes em").addClass("active");
			$(".detail-settelyes").removeClass("hidden");
			incomesSettelYes();
			initScrollSettelYes();
			incomesSearch(settleYes);
		}else{
			session.setItem("settle","no");
			$("#detail-settelno em").addClass("active");
			$(".detail-settelno").removeClass("hidden");
			incomesSearch(settleNo);
		}
	} 
})

var page = 0;
var hasNextPage = true;//是否有下一页
var pageSize = 10;
/* 收益流水查询*/
function incomesSearch(settelFlag){
	$(".none-data").hide();
	var startTime = terraceSettleDateS;
	var endTime = terraceSettleDateE;
	if(earningsType == selfGoods){
		startTime = ownSettleDate;
		endTime = ownSettleDate;
	}
	var isSettle = settelFlag;
	if(settelFlag == settleNo){
		isSettle = "7879";
	}
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			page ++;
			var data = {
					"mobile":mobile,
					"isSettle":isSettle,
					"startTime":startTime,
					"endTime":endTime,
					"earningsType":earningsType,
					"ecNetNo":netNo,
					"page":page,
					"pageSize":pageSize
			}
			var resultJson = ajaxCommon(urlQueryEarningsOrder, data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _returnData = resultJson._ReturnData;
				if(_returnData.length > 0){
					for(var i = 0; i< _returnData.length; i ++){
						content += "<li>";
						var payId = "";
						if(settelFlag == settleYes){
							payId = _returnData[i].payId;
						}
						var url = "details.html?earningsTime=" +_returnData[i].earningsDate +"&orderProviderId=" +_returnData[i].providerOrderId+ "&returnFlag=" +_returnData[i].returnFlg+ "&earningsType=" +earningsType +"&payId=" + payId;
						content += "<a href='" +url+ "'>";
						content += "<div class='settel'>";
						if(earningsType == platGoods){
							content += "<p class='settel-calendar'><span>" +formatData(_returnData[i].earningsDate,"4")+ "</span></p>";
						}
						content += "<div class='settel-orderid'><i>订单号：</i><em class='incomes-order'>" +_returnData[i].providerOrderId+ "</em><em class='incomes-status fr c-black'>" +_returnData[i].afterState+ "</em></div>";
						if(earningsType == selfGoods){
							content += "<div><i>付款方式：</i><em class='fr c-black'>" +_returnData[i].payMode+ "</em></div>";
							content += "<div><i>结算金额：</i><em class='fr c-black'>￥" +_returnData[i].settlementAmount+ "</em></div>";
						}else{
							content += "<div><i>商品金额小计：</i><em class='fr c-black'>￥" +_returnData[i].orderAmount+ "</em></div>";
							content += "<div><i>商品分润：</i><em class='fr c-black'>￥" +_returnData[i].earningsAmount+ "</em></div>";
						}
						content += "</div>";
						content += "</a>";
						content += "</li>";
					}
					if(settelFlag == settleNo){
						$("#settel-no ul").append(content);
						settleNoScroll.refresh();
					}else{
						$("#settel-yes ul").append(content);
						settleYesScroll.refresh();
					}
					if(earningsType == selfGoods){
						$(".settel-orderid").css("border-top-width","0");
					}
				}else{
					if(page == 1){
						var settle = session.getItem("settle");
						//暂无收益
						if(earningsType == selfGoods){
							if(settle == "yes"){
								showNoDataMsg(msgNoSelfAmont);
							}else{
								showNoDataMsg(msgNoSelfEarnings);
							}
						}else{
							if(settle == "yes"){
								showNoDataMsg(msgNoPlatAmont);
							}else{
								showNoDataMsg(msgNoPlatEarnings);
							}
						}
					}else{
						hasNextPage = false;
					}
				}
			}else{
				//接口返回错误
			}
		}
	},400);
}

/* 到账收益*/
function incomesSettelYes(){
	var startTime = terraceSettleDateS;
	var endTime = terraceSettleDateE;
	if(earningsType == selfGoods){
		startTime = ownSettleDate;
		endTime = ownSettleDate;
	}
	var container = $(".detail-settelyes-detail");
	var content = "";
	var data = {
		    "mobile":mobile,
		    "startTime":startTime,
		    "endTime":endTime,
		    "earningsType":earningsType,
		    "ecNetNo":netNo
	};
	var resultJson = ajaxCommon(urlGetOwnSettleInfo, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess){
		if(resultJson._ReturnData != ""){
			var _returnData = resultJson._ReturnData[0];
			content += "<p>到账金额：<em>￥" +_returnData.settleAmount+ "</em></p>";
			var payStatus = _returnData.payStatus;
			var statusDesc = "未到账";
			if(payStatus == "4") {
				statusDesc = "已到账";
			}
			content += "<p>到账状态：<em>" +statusDesc+ "</em></p>";
			content += "<p>支付说明：<em>" +_returnData.payRank+ "</em></p>";
			if(payStatus == "4") {
				content += "<p>支付流水号：<em>" +_returnData.payId+ "</em></p>";
				content += "<p>到账时间：<em>" +_returnData.payTime+ "</em></p>";
			}
			if(payStatus == "5"){
				content += "<p>支付失败原因：<em>" +_returnData.erroLog+ "</em></p>";
			}
			container.html("").append(content);
		}
	}else{
		//接口返回错误
	}
}