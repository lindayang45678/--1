$(function(){
	var sUrl = window.location.href;
	var torderproviderid = getParamValueByName(sUrl, "torderproviderid");
    var data = {"ecnetno":netNo,"torderproviderid":torderproviderid};//两级订单详情
	var resultJson = ajaxCommon(urlOrderView,data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnDataInfo = resultJson._ReturnData.orderproviderqueryinfo;//订单信息
	var orderitemslist = _ReturnDataInfo.orderitemslist;//商品列表
	var actualamount = _ReturnDataInfo.actualamount;//实付金额
	var providerOrderState = _ReturnDataInfo.state;  //供应商订单级别的订单状态
	var orderdatequeue = resultJson._ReturnData.orderproviderqueryinfo.orderdatequeue;//时间轴
	var isdelivertohome = _ReturnDataInfo.isdelivertohome;//配送方式
	var custremark = _ReturnDataInfo.custremark;//买家备注 
	var telHtml = "<a id='tel' class='c-black' href='javascript:void(0);'tel:"+_ReturnDataInfo.custelno +">"+_ReturnDataInfo.custelno +"</a>";
	$(".goods-receipt .fr").append(telHtml);//取货人电话
	if (resultJson._ReturnCode === returnCodeSuccess) {
		if (isdelivertohome == devicetypeShop && (_ReturnDataInfo.source == sourceSbApp2C || _ReturnDataInfo.source == sourceKdb)){
			$(".goods-receipt strong").text("");
			$(".goods-receipt strong").append(telHtml);
			$(".goods-receipt .fr").hide();
		} else { 
			$(".goods-receipt strong").text(_ReturnDataInfo.cusname.split("(")[0]);
		}
		$("#tel").live("click",function(e){
			$(this).attr("href","tel:"+_ReturnDataInfo.custelno);
		})
		$(".goods-receipt p em").text(_ReturnDataInfo.addressprovincename + _ReturnDataInfo.addresscityname + _ReturnDataInfo.addressareaname + _ReturnDataInfo.addressfull);//收货人地址

		if (isdelivertohome == devicetypeShop){
			$(".goods-receipt p").hide();
			$(".goods-receipt div em").text("到店自提");
		} else if (isdelivertohome == devicetypeHome){
			$(".goods-receipt div em").text("送货上门");
			$(".goods-receipt h4 s").text("收货人：");
		}
		$(".order-status span em").text(_ReturnDataInfo.torderproviderid);//订单号
		//订单状态时间轴
		var statuscontent = $(".order-status ul");
		var ret = "";
		//配送各个状态的时间列表
		function orderdate(msg,objorderdate){
			ret += "<li>";
			ret += "<s></s>";
			ret += "<p><i>"+ msg +"</i><br/><span>"+ getLocalTime(objorderdate) +"</span></p>";
			ret += "</li>";
			return ret;
		}
		var querytype = sessionStorage.getItem("sessionquerytype");//批发、零售
		var platorself = orderdatequeue.platorself;//自营或平台
		var source = orderdatequeue.source;//订单来源,备注:当订单是自营商品订单452并且来源是467的时候，前端详情页面取确认时间代替发货时间
		
		//取消时间
		if (orderdatequeue.qxsj != "") {
			var qxfl = parseInt(orderdatequeue.qxfl);
			var msgTimeLine = "";
			switch (qxfl) {
				case cancelOperater:msgTimeLine = msgTimelineCancelOperater;
					break;
				case cancelShoper:msgTimeLine = msgTimelineCancelShoper;
					break;
				case cancelProvider:msgTimeLine = msgTimelineCancelProvider;
					break;
				case cancelCustomer:msgTimeLine = msgTimelineCancelCustomer;
					break;
				case cancelSystem:msgTimeLine = msgTimelineCancelSystem;
					break;
			}
			orderdate(msgTimeLine, orderdatequeue.qxsj);
		}
		//拒收时间
		if (orderdatequeue.rejectsj != "") {
			orderdate(msgTimelineReject, orderdatequeue.rejectsj);
		}
		//签收时间
		if (orderdatequeue.qssj != "") {
			if (querytype == "pfyfh" || querytype == "pfbfsh") {
				orderdate(msgSectionSign, orderdatequeue.fhsj);
			} else {
				orderdate(msgTimelineSign, orderdatequeue.qssj);
			}
		}
		
		if (platorself == selfGoods) {
			//自营商品--显示
			//当订单是自营商品订单452并且来源是467的时候，前端详情页面取确认时间代替发货时间
			if (platorself == selfGoods && source == sourceSbApp2C) {
				//是否货到付款
				if(orderdatequeue.paychanel != paychanelCod) {//非货到付款
					if (orderdatequeue.qrfl != "" && orderdatequeue.qrsj != "") {
						if (orderdatequeue.isdelivertohome == devicetypeHome){//店主已确认，待店主送货
							orderdate(msgTimelineHome, orderdatequeue.qrsj);
						} else {//店主已确认，待顾客自提
							orderdate(msgTimelineShop, orderdatequeue.qrsj);
						}
					} else {
						if(orderdatequeue.fhsj != "") {//发货时间
							orderdate(msgTimelineIncoming, orderdatequeue.fhsj);	
						}
					}
					//支付时间
					if (orderdatequeue.zfsj != "") {
						orderdate(msgTimelinePaid, orderdatequeue.zfsj);
					} 
					//下单时间
					if (orderdatequeue.xdsj != "") {
						orderdate(msgTimelineNextSingle, orderdatequeue.xdsj);
					}	
				} else {
					//货到付款
					if (orderdatequeue.qrfl != "" && orderdatequeue.qrsj != "") {
						if (orderdatequeue.isdelivertohome == devicetypeHome){//店主已确认，待店主送货
							orderdate(msgTimelineHome, orderdatequeue.qrsj);
						} else {//店主已确认，待顾客自提
							orderdate(msgTimelineShop, orderdatequeue.qrsj);
						}
					}
					//下单时间
					if (orderdatequeue.xdsj != "") {
						orderdate(msgTimelineConfirmed, orderdatequeue.xdsj);
					}			
				}
			}
		} else {
			//平台商品--显示
			//发货时间
			if(orderdatequeue.fhsj != "") {
				orderdate(msgTimelineIncoming, orderdatequeue.fhsj);	
			}
			//支付时间
			if (orderdatequeue.zfsj != "") {
				orderdate(msgTimelineDistribution, orderdatequeue.zfsj);
			} 
			//下单时间
			if (orderdatequeue.xdsj != "") {
				orderdate(msgTimelineNextSingle, orderdatequeue.xdsj);
			}
		}

		statuscontent.prepend(ret);
		$(".order-status ul li:first-child").addClass("active");

		$(".orders-list h3 span").text(_ReturnDataInfo.providername);//供应商名称
		$(".orders-list .fr s").text(_ReturnDataInfo.clientservicetel);//供应商售后电话
		//商品信息列表
		var goodscontent = $(".orders-list li");
		var goodsret ="";
		var logisticscode = "";
		var state = "";
		var torderitemsid = "";

		if (orderitemslist.length > 0) {
			for(var j = 0; j < orderitemslist.length; j++) {
				var goodbigpic = orderitemslist[j].goodbigpic;//商品图片
				var goodsid = orderitemslist[j].goodsid;//商品id
				if(typeof(orderitemslist[j].tgoodinfopoolid)!="undefined" && orderitemslist[j].tgoodinfopoolid!="") {
					goodsid = orderitemslist[j].tgoodinfopoolid;
				}
				goodsret += "<dl class='goods'>";
				goodsret += "<dt><img src='" +urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic.split(";")[0] + "' onerror='productErrImg(this);'/></dt>";
				goodsret += "<dd>";
				goodsret += "<span class='title'><i class='c-black'>" + orderitemslist[j].goodsname + "</i><u class='nobr'>" + orderitemslist[j].norms + "</u></span>";
				goodsret += "<span class='total'><i class='c-black'>¥" + formatNumber(orderitemslist[j].goodssaleprice) + "</i><u>x" + orderitemslist[j].goodscount + "</u></span>";
				goodsret += "</dd>";
				goodsret += "</dl>"; 
				
				if (orderitemslist[j].logisticscode != "" && orderitemslist[j].cancelstate == cancelstateN) {
					state = orderitemslist[j].state;
					if (_ReturnDataInfo.state == state) {
						logisticscode = orderitemslist[j].logisticscode;
						torderitemsid = orderitemslist[j].torderitemsid;
					}
					if(state == stateYFH) {
						querytype = "lsdsh";
					}
				}
			}
			goodscontent.append(goodsret);

			//确认订单属性绑定
			$("#dzqr").attr("torderproviderid",torderproviderid);
			//取消订单属性绑定
			$("#dzqx").attr("torderproviderid",torderproviderid);
			//顾客取货属性绑定
			$("#gkqh").attr("sincecode",logisticscode);
			//查看物流属性绑定
			$("#ckkd").attr("logno",logisticscode).attr("state",state).attr("orderitemsid",torderitemsid);
		}
		$(".fee p:nth-child(1) span").html("-¥"+formatNumber(_ReturnDataInfo.favorrulemoney));//优惠金额
		$(".fee p:nth-child(2) span").html("¥"+formatNumber(_ReturnDataInfo.logiscalfee));//运费
		$(".fee p:nth-child(3) span").html("¥"+formatNumber(actualamount));//实付金额
		$(".fee p:nth-child(4) span").html(getPaychanelDesc(_ReturnDataInfo.paychanel));//支付方式
		//买家备注
		if(custremark != "") {
			$(".orders-items").css("margin-bottom","10px");
			$(".orders-custremark").removeClass("hidden");
			$(".orders-custremark span").html(custremark);
			$("#orderconfirm div").attr("id","custremark");
			$("#orderconfirm p:eq(1)").text("买家备注：" + custremark);
		}
		//零售待确认
		if (querytype == "lsdqr") {
			$("#dzqr").removeClass("hidden");
			$("#dzqx").removeClass("hidden");
		}
		//零售待送货--只有自营商品订单，才有顾客取货操作
		if ((querytype == "lsdsh" || querytype == "lsdzt") && 
				providerOrderState != stateYQS && 
				platorself == selfGoods) {
			$("#gkqh").removeClass("hidden");
		}
		//零售待签收,零售已签收--只有平台商品订单，才有查看物流操作
		if ((querytype == "lsyfh" || querytype == "lsyqs") && platorself == platGoods) {
			$("#ckkd").removeClass("hidden");
		}
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}

	lsOrders();//零售订单操作
});

/* 零售订单操作*/
function lsOrders() {
	//零售订单-店主lsOrders确认订单
	$("#dzqr").on("tap", function() {
		$("#orderconfirm").removeClass("hidden");
		if ($(".goods-receipt div em").text() == "到店自提") {
			$("#orderconfirm p:eq(0)").text("该订单是否有货，并可到店自提");
		} else {
			$("#orderconfirm p:eq(0)").text("该订单是否有货，并可送货上门");
		}
	});
	$("#order-confirm-confirm").on("touchend",function(e) {
		var torderproviderid = $("#dzqr").attr("torderproviderid");
		var url = urlConfirmOrder;
		e.preventDefault();
		confirmOrCancelOrder(url,torderproviderid,"confirm");
	});
	$("#order-confirm-cancel").on("touchend", function(e) {
		$("#orderconfirm").addClass("hidden");
		e.preventDefault();
	})
	//零售订单-店主取消订单
	$("#dzqx").on("tap", function() {
		$("#ordercancel").removeClass("hidden");
	});
	$("#order-cancel-confirm").on("tap",function() {
		var torderproviderid = $("#dzqx").attr("torderproviderid");
		var url = urlCancelOrder;
		confirmOrCancelOrder(url,torderproviderid,"cancel");
	});
	$("#order-cancel-cancel").on("tap",function(){
		$("#ordercancel").addClass("hidden");
	});
	//查看物流
	$("#ckkd").on("touchend", function(e) {
		var logno = $(this).attr("logno");
		var state = $(this).attr("state");
		var orderitemsid = $(this).attr("orderitemsid");
		e.preventDefault();
		directDeliveryHtml(logno,state,orderitemsid);
	});
	//零售订单-顾客取货
	$("#gkqh").on("touchend", function(e) {
		e.preventDefault();
		window.location.href = "../receipt/custreceipt.html?from=order&t=" + t;
		/*
		var sincecode = $(this).attr("sincecode");
		var data = {"netno":netNo, "sincecode":sincecode};
		var resultJson = ajaxCommon(urlCustreceipt, data);
		resultJson = convertNullToEmpty(resultJson);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			if(resultJson._ReturnData.state == stateWFH) {
				showAlertMsg("未发货，不能取货");
				return;
			}
			if(resultJson._ReturnData.state == stateBFQS) {
				showAlertMsg("已取货，不能重复取货");
				return;
			}
			if(resultJson._ReturnData.state == stateYQS) {
				showAlertMsg("已取货，不能重复取货");
				return;
			}
			if(resultJson._ReturnData.returnstate == 114) {
				showAlertMsg("已拒收，不能取货");
				return;
			}
			resultJson = JSON.stringify(resultJson);
			storage.setItem("custReceiptQuery", resultJson);
			window.location.href = "../receipt/custreceiptinfo.html?sincecode=" + sincecode + "&t=" + t;
		} else {
			window.location.href = "../receipt/custreceiptfail.html?sincecode=" + sincecode + "&t=" + t;
		}
		*/
	});

	//零售订单--头部返回  back
	$("#back").on("touchend", function(e) {
		e.preventDefault();
		var sUrl = window.location.href;
		var from = getParamValueByName(sUrl, "from");
		if(from == "search") {
			window.history.go(-1);
		} else {
			window.location.href = "retailorders.html?t=" + t;
		}
	});
}

/* 店主确认或取消订单*/
function confirmOrCancelOrder(url,torderproviderid,flag) {
	var data = {"torderproviderid":torderproviderid};
	var resultJson = ajaxCommon(url, data);
	resultJson = convertNullToEmpty(resultJson);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		if (flag == "cancel") {
			sessionStorage.setItem("sessionquerytype","lsyqx");
		}
		window.location.href = "retaildetail.html?torderproviderid=" +torderproviderid;
	} else {
		showAlertMsg(resultJson._ReturnMsg);
		$("#orderconfirm").addClass("hidden");
		$("#ordercancel").addClass("hidden");
	}
}