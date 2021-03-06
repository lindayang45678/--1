$(function() {
	//选中查询分类
	$(".orderquery span").on("tap", function() {
		$(this).addClass("btn-red").siblings().removeClass();
	});

	//订单查询
	$("#btnOrderQuery").on("tap", function() {
		var page = 1;
		var pageSize = 10;
		//下单手机号
		var custelno = $.trim($("#custelno").val());
		//下单开始时间
		var starttime = $.trim($("#starttime").val());
		//下单结束时间
		var endtime = $.trim($("#endtime").val());
		//订单类型
		var channelcode = $("#channelcode span[class='btn-red']").attr("data-item");
		//批发进货订单
		var ispfchannelcode = "";
		//非批发进货订单
		var nopfchannelcode = "";
		switch(channelcode) {
			case "0":
				//批发订单
				ispfchannelcode = "true";
				break;
			case "1":
				//零售订单
				nopfchannelcode = "true";
				break;
		}
		//订单状态
		var state = $("#state span[class='btn-red']").attr("data-item");
		switch(state) {
			case "0":
				//全部
				state = "";
				break;
			case "1":
				//待发货
				state = stateWFH;
				break;
			case "2":
				//已发货
				state = stateYFH;
				break;
			case "3":
				//部分签收
				state = stateBFQS;
				break;
			case "4":
				//已签收
				state = stateYQS;
				break;
		}
		//来源渠道
		var source = $("#source span[class='btn-red']").attr("data-item");
		switch(source) {
			case "0":
				//全部
				source = "";
				break;
			case "1":
				//开店宝
				source = sourceKdb;
				break;
			case "2":
				//收款宝
				source = sourceSkb;
				break;
			case "3":
				//wap商城
				source = sourceWap;
				break;
			case "4":
				//身边小店
				source = sourceSbApp2C;
				break;
			case "5":
				//身边小店商户
				source = sourceSbApp2B;
				break;	
		}
		//配送方式
		var devicetype = $("#devicetype span[class='btn-red']").attr("data-item");
		switch(devicetype) {
			case "0":
				//全部
				devicetype = "";
				break;
			case "1":
				//送货上门
				devicetype = devicetypeHome;
				break;
			case "2":
				//到店自提
				devicetype = devicetypeShop;
				break;
		}
		//付款方式
		var paychannel = $("#paychannel span[class='btn-red']").attr("data-item");
		switch(paychannel) {
			case "0":
				//全部
				paychannel = "";
				break;
			case "1":
				//拉卡拉
				paychannel = paychannelLkl;
				break;
			case "2":
				//微信支付
				paychannel = paychanelWx;
				break;
			case "3":
				//支付宝
				paychannel = paychanelZfb;
				break;
			case "4":
				//贷到付款
				paychannel = paychanelCod;
				break;
		}
		//付款状态
/*		var ispay = $("#ispay span[class='btn-red']").attr("data-item");
		switch(ispay) {
			case "0":
				//全部
				ispay = "";
				break;
			case "1":
				//已付款
				ispay = ispayY;
				break;
			case "2":
				//未付款
				ispay = ispayN;
				break;
		}*/
		//是否查询满足条件的订单总数:yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
		var queryordertotal = "yes";
		var data = {
				"ecnetno":netNo,
				"page":1,
				"pageSize":10,
				"custelno":custelno,
				"state":state,
				"source":source,
				"isdelivertohome":devicetype,
				"paychannel":paychannel,
				//"ispay":ispay,
				"starttime":starttime,
				"endtime":endtime,
				"ispfchannelcode":ispfchannelcode,
				"nopfchannelcode":nopfchannelcode,
				"queryordertotal":queryordertotal
		};
		
		window.location.href = "querysucceed.html?data=" + JSON.stringify(data) + "&t=" + t;
	});
});