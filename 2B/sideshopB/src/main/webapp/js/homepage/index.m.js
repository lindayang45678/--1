$(function() {
	if(storage.getItem(hasLogined)==null || storage.getItem(hasLogined)=="" || storage.getItem(hasLogined)=="0") {
		//没有登录时，跳转到登录页面
		var source = storage.getItem("source");
		window.location.href = "../accounts/login.html?source=" + source + "&t=" + t;
	}

	showFuncByBizType();

	var windowHeight = $(window).height();
	autoScreen();

	//得到需店主送货订单总数
	var orderNum = getShoperOrderNum();
	if(orderNum > 0) {
		$("#ddgl a").append("<em></em>");
	}

	shipAddress();
	
	//获取localStorage中系统时间与当前时间比较，如果大于1天，清空购物车localStorage
	var sysDate = storage.getItem("sysDate");
	if(storageFailDay(JSON.stringify(sysDate)) > 0){
		clearCart();//清空购物车localStorage
		storage.removeItem("couponlist");//清空优惠券列表
		storage.removeItem("promfee");//清空优惠金额
	}
	//获取localStorage中系统时间与当前时间比较，如果大于1天，清空localStorage
	if(storageFailDay(JSON.stringify(sysDate)) > 30){
		clearStorage();
	}
	//清除订单状态的
	$("#ddgl").live("tap", function(){
		sessionStorage.removeItem("sessionquerytype");
	});
	//清除所有sessionStorage
	sessionStorage.clear();
});

/**
 * 根据不同的商户类型显示不同的功能模块
 * 商户类型--加盟型:459; 标准型:460; 旗舰型:461;
 * 2015-04-04---加盟型小店关闭"我的小店"功能模块
 */
function showFuncByBizType() {
	if(bizType == bizTypeJMX) {
		$("#sbxd a").attr("href", "javascript:showAlertMsg(msgFuncOpenedForBZX);").append("<s class='non-opened'></s>");
		$("#sycx a").attr("href", "javascript:showAlertMsg(msgFuncOpenedForBZX);").append("<s class='non-opened'></s>");
	    $("#khgl a").attr("href", "javascript:showAlertMsg(msgFuncOpenedForQJX);").append("<s class='non-opened'></s>");
	}
	if(bizType == bizTypeBZX) {
		$("#khgl a").attr("href", "javascript:showAlertMsg(msgFuncOpenedForQJX);").append("<s class='non-opened'></s>");
	}
}

/**
 * 身边小店page自适应
 */
function autoScreen() {
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

function getShoperOrderNum() {
	var ordercate = "all";
	var orderNum = "0";

	var data = {"token":userToken, "ecnetno":netNo, "mobile":mobile, "ordercate":ordercate};
	var resultJson = ajaxCommon(urlOrderCnt, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		var pf2needpaynum = _ReturnData.pf2needpaynum;	//待付款总数
		var pf2nodelivernum = _ReturnData.pf2nodelivernum;	//待发货总数
		var pf2deliverednum = _ReturnData.pf2deliverednum;  //待收货总数
		var ls2waitconfirmnum = _ReturnData.ls2waitconfirmnum;	//待确认总数
		var ls2waittakenum = _ReturnData.ls2waittakenum;	//待自提总数
		var ls2waitsendnum = _ReturnData.ls2waitsendnum;  //待送货总数

		//订单管理模块，只要有批发订单【待付款、待发货、待收货】和零售订单【待确认、待自提、待送货】其中任一一个或多个
		orderNum = pf2needpaynum + pf2nodelivernum + pf2deliverednum + ls2waitconfirmnum + ls2waittakenum + ls2waitsendnum;
	}

	return orderNum;
}

/**
 * 判断手机横竖屏状态
 */
function orientationChange() {
	//竖屏状态
	if(window.orientation==180 || window.orientation==0) {
		windowHeight = $(window).height();
		windowWidth = screen.width;
		autoScreen();
	}
	//横屏状态
	if(window.orientation==90 || window.orientation==-90) {
		windowHeight = 500;
		windowWidth = $(window).width();
		autoScreen();
    }
	$(".homepage").width(windowWidth + "px");
}
window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", orientationChange, false);