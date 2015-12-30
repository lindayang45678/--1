$(function(){
	var data = {"mobile":mobile,"ecNetNo":netNo};
	var resultJson = ajaxCommon(urlToProfitHome, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess){
		//自营商品
		// 流水
		var ownTodayEarnings = resultJson._ReturnData.ownTodayEarnings;//自营今日流水
		var ownTodayOrderNum = resultJson._ReturnData.ownTodayOrderNum;//自营今日流水订单
		var ownAddEarnings = resultJson._ReturnData.ownAddEarnings;//自营累计流水
		var ownAddOrderNum = resultJson._ReturnData.ownAddOrderNum//自营累计流水订单
		//结算
		var ownSettleTodayAmont = resultJson._ReturnData.ownSettleTodayAmont;//自营今日结算
		var ownSettleTodayNum = resultJson._ReturnData.ownSettleTodayNum;//自营今日结算订单
		var ownSettleAddUpAmont = resultJson._ReturnData.ownSettleAddUpAmont;//自营累计结算
		var ownSettleAddUpNum = resultJson._ReturnData.ownSettleAddUpNum;//自营累计结算订单
		//到账
		var ownSettleAmont = resultJson._ReturnData.ownSettleAmont;//自营今日到账
		var ownSettleNum = resultJson._ReturnData.ownSettleNum;//自营今日到账订单
		var ownTotalSettleAmont = resultJson._ReturnData.ownTotalSettleAmont;//自营累计到账
		
		//平台商品
		var terraceEarnings = resultJson._ReturnData.terraceEarnings;//平台本月分润
		var terraceSettleAmont = resultJson._ReturnData.terraceSettleAmont;//平台本月到账
		var terraceAddEarnings = resultJson._ReturnData.terraceAddEarnings;//平台累计分润
		var terraceTotalSettleAmont = resultJson._ReturnData.terraceTotalSettleAmont;;//平台累计到账
		var terraceOrderNum = resultJson._ReturnData.terraceOrderNum;//平台本月订单
		var terraceSettleNum = resultJson._ReturnData.terraceSettleNum;//平台结算单数
		var terraceSettleDateS = resultJson._ReturnData.terraceSettleDateS;//平台结算周期开始
		var terraceSettleDateE = resultJson._ReturnData.terraceSettleDateE;//平台结算周期结束
				
		$("#self-no").find("em").html(ownTodayEarnings);//自营今日流水
		$("#self-settle").find("em").html(ownSettleTodayAmont);//自营今日结算
		$("#self-yes").find("em").html(ownSettleAmont);//自营今日到账
		$("#self-total-no").find("em").html(ownAddEarnings);//自营累计流水
		$("#self-total-settle").find("em").html(ownSettleAddUpAmont);//自营累计结算
		$("#self-total-yes").find("em").html(ownTotalSettleAmont);//自营累计到账
		
		$(".incomes-l:eq(1)").find("em").html(terraceEarnings);//平台本月分润
		$(".incomes-r:eq(1)").find("em").html(terraceSettleAmont);//平台本月到账
		$(".incomes-bottom:eq(1)").find("em:eq(0)").html(terraceAddEarnings);//平台累计分润
		$(".incomes-bottom:eq(1)").find("em:eq(1)").html(terraceTotalSettleAmont);//平台累计到账
		
		var urlSelf = "incomesdaydetail.html?";
		urlSelf += "type=self";
		urlSelf += "&ownTodayEarnings=" +ownTodayEarnings;
		urlSelf += "&ownTodayOrderNum=" +ownTodayOrderNum;
		urlSelf += "&ownSettleTodayAmont=" +ownSettleTodayAmont;
		urlSelf += "&ownSettleTodayNum=" +ownSettleTodayNum;
		urlSelf += "&ownSettleAmont=" +ownSettleAmont;
		urlSelf += "&ownSettleNum=" +ownSettleNum;
		urlSelf += "&ownSettleDate=" + getLocalTime(new Date()).split(" ")[0];
		urlSelf += "&t=" + t;
		$("#self-yes").attr("href",urlSelf + "&settel=yes");
		$("#self-settle").attr("href",urlSelf + "&settel=settle");
		$("#self-no").attr("href",urlSelf + "&settel=no");
		
		var urlPlat = "incomesmonth.html?";
		urlPlat += "type=plat";
		urlPlat += "&terraceEarnings=" +terraceEarnings;
		urlPlat += "&terraceOrderNum=" +terraceOrderNum;
		urlPlat += "&terraceSettleAmont=" +terraceSettleAmont;
		urlPlat += "&terraceSettleNum=" +terraceSettleNum;
		urlPlat += "&terraceSettleDateS=" +terraceSettleDateS;
		urlPlat += "&terraceSettleDateE=" +terraceSettleDateE;
		urlPlat += "&t=" + t;
		$("#plat-yes").attr("href",urlPlat + "&settel=yes");
		$("#plat-no").attr("href",urlPlat + "&settel=no");
		
		session.removeItem("settle");
	}else{
		//接口返回错误
	}
	
	$("#self-total-no").on("tap",function() {
		session.setItem("settle","no");
		window.location.href = "incomes.html?type=self";
	});
	
	$("#self-total-settle").on("tap",function() {
		session.setItem("settle","settle");
		window.location.href = "incomes.html?type=self";
	});
	
	$("#self-total-yes").on("tap",function() {
		session.setItem("settle","yes");
		window.location.href = "incomes.html?type=self";
	});
});