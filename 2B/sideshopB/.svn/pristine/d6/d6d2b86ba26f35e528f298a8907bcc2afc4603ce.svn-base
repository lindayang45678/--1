//全局变量
var page = 0;
var hasNextPage = true;//是否有下一页
var pageSize = 10;
var earningsType = "";
var ownSettleDate = "";

$(function() {
	var url = window.location.href;
	earningsType = getParamValueByName(url, "type");//收益类型
	var ownTodayEarnings = getParamValueByName(url, "ownTodayEarnings");//自营今日流水
	var ownTodayOrderNum = getParamValueByName(url, "ownTodayOrderNum");//自营今日订单
	var ownSettleTodayAmont = getParamValueByName(url, "ownSettleTodayAmont");//自营今日结算
	var ownSettleTodayNum = getParamValueByName(url, "ownSettleTodayNum");//自营今日结算单数
	var ownSettleAmont = getParamValueByName(url, "ownSettleAmont");//自营今日到账
	var ownSettleNum = getParamValueByName(url, "ownSettleNum");//自营结算单数
	ownSettleDate = getParamValueByName(url, "ownSettleDate");//自营结算今日日期
	var from = getParamValueByName(url, "from");
	
	if(earningsType == "self"){
		earningsType = selfGoods;
	}
	
	if(from == ""){
		$(".back").attr("href","index.html");
	}else{
		$(".back").attr("href","javascript:window.history.go(-1)");
	}
	
	$("title").html(formatData(ownSettleDate,"2") + "自营商品收益");//title
	$("h1").html(formatData(ownSettleDate,"2") + "自营商品收益");//h1
	
	$("#self-no em").html("流水￥<i>" +ownTodayEarnings + "</i></br><i>" +ownTodayOrderNum+ "</i>单");//流水
	$("#self-settle em").html("结算￥<i>" +ownSettleTodayAmont+ "</i></br><i>" +ownSettleTodayNum+ "</i>单");//结算
	$("#self-yes em").html("到账￥<i>" +ownSettleAmont+ "</i></br><i>" +ownSettleNum+ "</i>单");//到账
	
	var settle = session.getItem("settle");
	if(settle != null) {
		$(".month em").removeClass("active");
		$(".detail-settelno").addClass("hidden");
		$(".detail-settelsettel").addClass("hidden");
		$(".detail-settelyes").addClass("hidden");
		
		if(settle == "no") {
			$("#self-no em").addClass("active");
			$(".detail-settelno").removeClass("hidden");
			initScrollSettleNo();
			getSelfList(settleNo);
		}
		
		if(settle == "settle") {
			$("#self-settle em").addClass("active");
			$(".detail-settelsettel").removeClass("hidden");
			initScrollSettleSettle();
			getSelfList(settleSettle);
		}
		
		if(settle == "yes") {
			$("#self-yes em").addClass("active");
			$(".detail-settelyes").removeClass("hidden");
			incomesSettleYes();
			initScrollSettleYes();
			getSelfList(settleYes);
		}
	}else {
		settle = getParamValueByName(url, "settel");
		$(".month em").removeClass("active");
		$(".detail-settelno").addClass("hidden");
		$(".detail-settelsettel").addClass("hidden");
		$(".detail-settelyes").addClass("hidden");
		
		if(settle == "no") {
			session.setItem("settle","no");
			$("#self-no em").addClass("active");
			$(".detail-settelno").removeClass("hidden");
			initScrollSettleNo();
			getSelfList(settleNo);
		}
		
		if(settle == "settle") {
			session.setItem("settle","settle");
			$("#self-settle em").addClass("active");
			$(".detail-settelsettel").removeClass("hidden");
			initScrollSettleSettle();
			getSelfList(settleSettle);
		}
		
		if(settle == "yes") {
			session.setItem("settle","yes");
			$("#self-yes em").addClass("active");
			$(".detail-settelyes").removeClass("hidden");
			incomesSettleYes();
			initScrollSettleYes();
			getSelfList(settleYes);
		}
	}
});

/* 流水iScroll*/
var settleNoScroll;
function scrollSettleNo(){
	settleNoScroll = new iScroll("settel-no",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			getSelfList(settleNo);
		},
	});
}

function initScrollSettleNo() {
//	$("#settel-no").height(windowHeight - $("header").height() - $(".month").height());
	scrollSettleNo();
}

/* 结算iScroll*/
var settleSettleScroll;
function scrollSettleSettle(){
	settleSettleScroll = new iScroll("settel-settel",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			getSelfList(settleSettle);
		},
	});
}

function initScrollSettleSettle() {
	//$("#settel-settel").height(windowHeight - $("header").height() - $(".month").height());
	scrollSettleSettle();
}

/* 到账iScroll*/
var settleYesScroll;
function scrollSettleYes(){
	settleYesScroll = new iScroll("settel-yes",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			getSelfList(settleYes);
		}
	});
}

function initScrollSettleYes() {
	$("#settel-yes").height(windowHeight - $("header").height() - $(".month").height() - $(".detail-settelyes-detail").height() - 10);
	scrollSettleYes();
}

/* 自营流水、结算、到账切换*/
$(".month a").on("tap", function(){
	$(".month em").removeClass("active");
	$(this).find("em").addClass("active");
	$(".detail-settelyes").addClass("hidden");
	$(".detail-settelsettel").addClass("hidden");
	$(".detail-settelno").addClass("hidden");
	page = 0;
	hasNextPage = true;
	if($(this).attr("id") == "self-no"){
		$(".detail-settelno").removeClass("hidden");
		$("#settel-no ul").html("");
		session.setItem("settle","no");
		initScrollSettleNo();
		getSelfList(settleNo);
	}
	if($(this).attr("id") == "self-settle"){
		$(".detail-settelsettel").removeClass("hidden");
		$("#settel-settel ul").html("");
		session.setItem("settle","settle");
		initScrollSettleSettle();
		getSelfList(settleSettle);
	}
	if($(this).attr("id") == "self-yes"){
		$(".detail-settelyes").removeClass("hidden");
		$("#settel-yes ul").html("");
		session.setItem("settle","yes");
		incomesSettleYes();
		initScrollSettleYes();
		getSelfList(settleYes);
	}
})

/* 自营流水、结算、到账列表*/
function getSelfList(settleFlag) {
	$(".none-data").hide();
	var startTime = ownSettleDate;
	var endTime = ownSettleDate;
	var isSettle = settleFlag;
	if(settleFlag == settleNo || settleFlag == settleSettle){
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
						if(settleFlag == settleYes){
							payId = _returnData[i].payId;
						}
						var url = "details.html?earningsTime=" +_returnData[i].earningsDate +"&orderProviderId=" +_returnData[i].providerOrderId+ "&returnFlag=" +_returnData[i].returnFlg+ "&earningsType=" +earningsType +"&payId=" + payId;
						content += "<a href='" +url+ "'>";
						content += "<div class='settel'>";
						
						content += "<div class='settel-orderid'>";
						content += "<i>订单号：</i>";
						content += "<em class='incomes-order'>" +_returnData[i].providerOrderId+ "</em>";
						content += "<em class='incomes-status fr c-black'>" +_returnData[i].afterState+ "</em>"
						content += "</div>";
						
						
						content += "<div>";
						content += "<i>下单时间：</i>";
						content += "<em class='fr c-black'>" +_returnData[i].orderTime+ "</em>";
						content += "</div>";
						
						
						content += "<div>";
						content += "<i>付款方式：</i>";
						content += "<em class='fr c-black'>" +_returnData[i].payMode+ "</em>";
						content += "</div>";
						
						content += "<div>";
						content += "<i>订单金额：</i>";
						content += "<em class='fr c-black'>￥" +_returnData[i].orderAmount+ "</em>";
						content += "</div>";
						
						if(settleFlag == settleSettle) {
							content += "<div>";
							content += "<i>拉卡拉优惠金额：</i>";
							content += "<em class='fr c-black'>￥" +_returnData[i].couponValue+ "</em>";
							content += "</div>";
							
							content += "<div>";
							content += "<i>结算金额：</i>";
							content += "<em class='fr c-black'>￥" +_returnData[i].settlementAmount2+ "</em>";
							content += "</div>";
						}
						
						if(settleFlag == settleYes) {
							content += "<div>";
							content += "<i>拉卡拉优惠金额：</i>";
							content += "<em class='fr c-black'>￥" +_returnData[i].couponValue+ "</em>";
							content += "</div>";
							
							content += "<div>";
							content += "<i>结算金额：</i>";
							content += "<em class='fr c-black'>￥" +_returnData[i].settlementAmount2+ "</em>";
							content += "</div>";
							
							content += "<div>";
							content += "<i>到账金额：</i>";
							content += "<em class='fr c-black'>￥" +_returnData[i].settlementAmount+ "</em>";
							content += "</div>";
						}
						
						
						content += "</div>";
						content += "</a>";
						content += "</li>";
					}
					if(settleFlag == settleNo) {
						$("#settel-no ul").append(content);
						settleNoScroll.refresh();
					}
					if(settleFlag == settleSettle) {
						$("#settel-settel ul").append(content);
						settleSettleScroll.refresh();
					}
					if(settleFlag == settleYes) {
						$("#settel-yes ul").append(content);
						settleYesScroll.refresh();
					}
					$(".settel-orderid").css("border-top-width","0");
				}else{
					if(page == 1){
						var settle = session.getItem("settle");
						if(settle == "no") {
							showNoDataMsg(msgNoSelfEarnings);
						}
						if(settle == "settle") {
							showNoDataMsg(msgNoSelfSettle);
						}
						if(settle == "yes") {
							showNoDataMsg(msgNoSelfAmont);
							if($(".detail-settelyes-detail").hasClass("data")) {
								$(".none-data div").css("top","79%");
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

/* 到账*/
function incomesSettleYes(){
	var startTime = ownSettleDate;
	var endTime = ownSettleDate;
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
			$(".detail-settelyes-detail").addClass("data");
		}
	}else{
		//接口返回错误
	}
}