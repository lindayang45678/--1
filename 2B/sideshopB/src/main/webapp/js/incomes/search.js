$(function() {
	$(".incomes tr").each(function(index) {
		$(this).find("div").on("tap", function() {
			$(this).addClass("active").siblings().removeClass("active");
		});
	});
})
var page = 0;
var pageSize = 10;

$(function(){
	$(".btn-red").on("tap",function(){
		var orderId = $.trim($("#orderId").val());
		var startTime = $.trim($("#startTime").val());
		var endTime = $.trim($("#endTime").val());
		var orderSource = $("#order-source div[class='active']").attr("source");
		var isAfterSale = $("#after-sale div[class='active']").attr("service");
		var earningsType = $("#earnings-type div[class='active']").attr("type");
		var isSettle = $("#is-settle div[class='active']").attr("type");
		//订单来源
		if(orderSource === "0"){
			orderSource = "";
		}else if(orderSource === "1"){
			orderSource = sourcePhone;
		}else if(orderSource === "2"){
			orderSource = sourceWebchat;
		}else if(orderSource === "3"){
			orderSource = sourceKdb;
		}else if(orderSource === "4"){
			orderSource = sourceSkb;
		}else if(orderSource === "5"){
			orderSource = sourceWap;
		}
		//收益类型
		if(earningsType === "0"){
			earningsType = platGoods;
		}else{
			earningsType = selfGoods;
		}
		//结算状态
		if(isSettle === "0"){
			isSettle = "";
		}else if(isSettle === "1"){
			isSettle = settleYes;
		}else if(isSettle === "2"){
			isSettle = settleNo;
		}
		var data = {
				"mobile":mobile,
				"orderId":orderId,
				"orderSource":orderSource,
				"isAfterSale":isAfterSale,
				"isSettle":isSettle,
				"startTime":startTime,
				"endTime":endTime,
				"earningsType":earningsType,
				"ecNetNo":netNo,
				"page":page,
				"pageSize":pageSize
		};
		window.location.href = "searchresult.html?data=" + JSON.stringify(data) + "&t=" + t;
	});
})
