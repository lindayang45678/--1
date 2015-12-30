$(function() {
	if(storage.getItem(hasLogined)==null || storage.getItem(hasLogined)=="" || storage.getItem(hasLogined)=="0") {
		//没有登录时，跳转到登录页面
		window.location.href = "../accounts/login.html?t=" + t;
	}

	showFuncByBizType();

	autoScreen();
	$(window).resize(function() {
		autoScreen();
	});

	//得到需店主送货订单总数
	var orderNum = getShoperOrderNum();
	if(orderNum > 0) {
		$("#ddgl a").append("<em>" + orderNum + "</em>");
	}

	shipAddress();
	
	//获取localStorage中系统时间与当前时间比较，如果大于1天，清空localStorage
	var sysDate = storage.getItem("sysDate");
	if(storageFailDay(JSON.stringify(sysDate)) > 0){
		clearCart();//清空购物车localStorage
		storage.removeItem("couponlist");//清空优惠券列表
		storage.removeItem("promfee");//清空优惠金额
	}
	//清楚清除订单状态的
	$("#ddgl").live("tap", function(){
		  sessionStorage.removeItem("sessionquerytype");
	})

});

/**
 * 根据不同的商户类型显示不同的功能模块
 * 商户类型--加盟型:459; 标准型:460; 旗舰型:461;
 * 2015-04-04---加盟型小店关闭"我的小店"功能模块
 */
function showFuncByBizType() {
	if(bizType == bizTypeJMX) {
		$("#sbxd a").attr("href", "javascript:showAlertMsg(msgFuncNonOpened);").append("<s class='non-opened'></s>");
	}
}

/**
 * 身边小店page自适应
 */
function autoScreen() {
	windowHeight = storage.getItem("windowHeight");
	var wrapWidth = windowWidth - 26;
	var wrapHeight = windowHeight - 88;
	var cellWidth = wrapWidth / 2;
	var cellHeight = wrapHeight / 4;
	$(".wrap").width(windowWidth - 20).height(windowHeight - 76);
	$("#sbxd a").width(cellWidth + "px").height(cellHeight*2 + "px");
	$("#sycx a").width(cellWidth + "px").height((cellHeight-3) + "px");
	$("#ddgl a").width(cellWidth + "px").height((cellHeight-3) + "px");
	$("#pfjh a").width(cellWidth + "px").height(cellHeight + "px");
	$("#wylq a").width(cellWidth + "px").height(cellHeight + "px");
	$("#qhsh a").width(cellWidth + "px").height(cellHeight + "px");
	$("#khgl a").width(cellWidth + "px").height(cellHeight + "px");
	$("td span").width(cellWidth + "px");
}

/**
 * 得到需店主送货订单总数
 */
function getShoperOrderNum() {
	var ordercate = "pfjh";
	var orderNum = "0";

	var data = {"token":userToken, "ecnetno":netNo, "mobile":mobile, "ordercate":ordercate};
	var resultJson = ajaxCommon(urlOrderCnt, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		orderNum = _ReturnData.sxneedbsendnum;
	}

	return orderNum;
}