var page = 1;
var pageSize = 10;
var hasNextPage = true;
var container = $(".orders-items");
var ordercate = "";  //大类  批发、零售、需店主送货
var querytype = "pfall";  //小类;
var queryordertotal = "yes";  //yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
var searchvalue = "";
var myScroll, pullDownOffset, pullUpEl, pullUpOffset;
var proImgAreaWidth = 86;

/**
 * 初始化iScroll控件
 */
function loaded() {
	pullUpEl = document.getElementById("loading");
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll("search-scroll-main", {//iScroll的对象
		hScrollbar: false,
		vScrollbar: false,
		useTransition: false, 
		checkDOMChanges: false,
		topOffset: pullDownOffset,
		onScrollMove: function () {
			if(this.y < (this.maxScrollY - 5) && !pullUpEl.className.match("flip")) {
				pullUpEl.className = "flip";
				this.maxScrollY = this.maxScrollY;
			} else if(this.y > (this.maxScrollY + 5) && pullUpEl.className.match("flip")) {
				pullUpEl.className = "";
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match("flip")) {
				pullUpEl.className = "hidden";
				getOrderlist(querytype, queryordertotal, searchvalue);  //执行加载后的自定义功能	
			}
		}
	});
}

document.addEventListener("touchmove", function(e) {e.preventDefault();}, false);
document.addEventListener("DOMContentLoaded", function() {setTimeout(loaded, 200);}, false);

$(function() {
	//加载cordova
	loadJs();

	//元素的事件绑定
	eleBindEvent();

	//显示搜索订单列表
	getOrderlist(querytype, queryordertotal, searchvalue);
	
	//#search-scroll-main高度
	$("#search-scroll-main").css({"height":windowHeight-44});
	
});

/**
 * 获取搜索的订单列表
 * @param querytype
 * @param queryordertotal 是否查询满足条件的订单总数:yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
 * @param searchvalue:用于订单搜索，该值匹配以下字段：1、下单手机号 2、商品名 3、供应商订单号torderproviderid 4、支付大订单号tallorderid
 */
function getOrderlist(querytype, queryordertotal, searchvalue) {
	setTimeout(function() {
		if(hasNextPage) {
			var data = {
				"token": userToken,
				"ecnetno": netNo,
				"mobile": mobile,  //该字段用于批发待付款的时候用到，主要查该店主下的订单还没有付款的
				"querytype": querytype,
				"page": page,
				"pageSize": pageSize,
				"queryordertotal": queryordertotal,
				"searchvalue": searchvalue
			};
			var resultJson = ajaxCommon(urlOrderListV2, data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess) {
				var _ReturnData = resultJson._ReturnData;
				var total = _ReturnData.total;  //搜索订单总数
				if(total=="" || total==0) {
					$("#search-scroll-main").hide();
					showNoDataMsg(msgOrderSearchFail);
				} else {
					$("#total").text(total);
					$("#search-scroll-main").show();
					if(querytype=="pfall" || querytype=="pfdfk") {
						generateWholeSaleOrders(_ReturnData);  //生成批发订单列表--全部&待付款
					} else if(querytype == "lsall") {
						generateRetailOrders(_ReturnData);  //生成零售订单列表
					}
				}
			} else {
				hasNextPage = false;
				showAlertMsg(resultJson._ReturnMsg);
			}
			page ++;
		}
	}, 200);	
}

/**
 * 生成批发订单列表--全部&待付款
 * @param _ReturnData
 */
function generateWholeSaleOrders(_ReturnData) {
	var result = "";
	var orderlist = _ReturnData.orderlist;
	if(orderlist.length > 0) {
		if(page == 1) {
			container.find("ul").remove();
		}
		for(var i = 0; i < orderlist.length; i ++) {
			var tallorderid = orderlist[i].tallorderid; //大订单号
			var source = orderlist[i].source;  //来源渠道
			var ispay = orderlist[i].ispay;  //付款状态
			//批发全部||待付款列表 加载三级订单列表
			var actualamount = orderlist[i].actualamount;  //订单金额
			var paytoken = orderlist[i].paytoken;
			var paychanel = orderlist[i].paychanel;
			var custelno = orderlist[i].orderproviderlist[0].custelno;

			var	orderproviderlist = orderlist[i].orderproviderlist;
			if(orderproviderlist.length > 0) {
				//待付款订单显示大订单
				if((ispay==ispayN) && orderSource(source)) {
					var goodsnameArry = [];
					result += "<ul class='orders-list body-white'>";
					for(var j = 0; j < orderproviderlist.length; j ++) {
						var ordertime = orderproviderlist[j].ordertime.split(".")[0];  //下单时间
						var torderproviderid = orderproviderlist[j].torderproviderid;  //供应商级订单号
						var providername = orderproviderlist[j].providername;  //供应商名称
						var state = orderproviderlist[j].state;  //供应商级订单状态
						result += "<li>";
						result += "<h3><span>" + providername + "</span>";
						result += "<em class='fr'>" + ordertime + "</em></h3>";

						result += "<a class='goods' href='wholesaledetail.html?from=search&torderproviderid=" + torderproviderid + "&pfstate=" + state + "&t=" + t + "'>";
						result += "<span>";
						//商品list
						var orderitemslist = orderproviderlist[j].orderitemslist;
						for(var k = 0; k < orderitemslist.length; k ++) {
							var goodsid = orderitemslist[k].goodsid;  //商品ID
							var goodbigpic = orderitemslist[k].goodbigpic.split(";")[0]; //商品主图
							var goodsname = orderitemslist[k].goodsname;  //商品名称
							if(typeof(orderitemslist[k].tgoodinfopoolid)!="undefined" && orderitemslist[k].tgoodinfopoolid!="") {
								goodsid = orderitemslist[k].tgoodinfopoolid;
							}
							result += "<img src='" + urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic + "' onerror='productErrImg(this);' />";
							//订单列表只有一个商品时，应该显示商品图片+商品名称
							if(orderitemslist.length == 1) {
								result += "<em>" + goodsname + "</em>";
							}
							goodsnameArry.push(goodsname);
						}
						result += "</span>";
						result += "</a>";
						if(j == (orderproviderlist.length-1)) {
							result += "<p>总价：¥" + formatNumber(actualamount);
							result += "<a class='fr topayall' tallorderid='" +tallorderid+ "' actualamount='" +actualamount+ "' source='" +source+ "' paytoken='" + paytoken + "' paychanel='" + paychanel + "' mobile='" + custelno + "' goodsname='"+ goodsnameArry +"' href='javascript:;'>去付款</a>";
							result += "</p>";
						}
						result += "</li>";
					}
					result += "</ul>";
				} else {
					//其它情况下显示供应商订单
					for(var j = 0; j < orderproviderlist.length; j ++) {
						var actualamount = orderproviderlist[j].actualamount;  //订单金额
						var logiscalfee = orderproviderlist[j].logiscalfee; // 运费
						var ordertime = orderproviderlist[j].ordertime.split(".")[0];  //下单时间
						var torderproviderid = orderproviderlist[j].torderproviderid;  //供应商级订单号
						var providername = orderproviderlist[j].providername;  //供应商名称
						var state = orderproviderlist[j].state;  //供应商级订单状态
						result += "<ul class='orders-list body-white'>";
						result += "<li>";
						result += "<h3><span>" + providername + "</span>";
						result += "<em class='fr'>" + ordertime + "</em></h3>";

						result += "<a class='goods' href='wholesaledetail.html?from=search&torderproviderid=" + torderproviderid + "&pfstate=" + state + "&t=" + t + "'>";
						result += "<span>";
						//商品list
						var orderitemslist = orderproviderlist[j].orderitemslist;
						var logno = "";  //物流单号
						var showDZSH = false;  //是否显示店主收货
						for(var k = 0; k < orderitemslist.length; k ++) {
							var goodsid = orderitemslist[k].goodsid;  //商品ID
							var goodbigpic = orderitemslist[k].goodbigpic.split(";")[0]; //商品主图
							var goodsname = orderitemslist[k].goodsname;  //商品名称
							//店主收货按钮物流单号取有物流单号的商品&商品订单状态未取消
							if(orderitemslist[k].logisticscode!="" && orderitemslist[k].cancelstate==cancelstateN) {
								logno = orderitemslist[k].logisticscode;  //物流单号
								showDZSH = true;
							}
							if(typeof(orderitemslist[k].tgoodinfopoolid)!="undefined" && orderitemslist[k].tgoodinfopoolid!="") {
								goodsid = orderitemslist[k].tgoodinfopoolid;
							}
							result += "<img src='" + urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic + "' onerror='productErrImg(this);' />";
							//订单列表只有一个商品时，应该显示商品图片+商品名称
							if(orderitemslist.length == 1) {
								result += "<em>" + goodsname + "</em>";
							}
						}
						result += "</span>";
						result += "</a>";
						result += "<p>总价：¥" + formatNumber(actualamount + logiscalfee);
						//配送中（部分发货：101，已发货：102）--待收货显示【店主收货】按钮
						if((state==stateBFFH || state==stateYFH || state==stateBFQS) && showDZSH) {
							result += "<a class='fr toshopreceipt' href='javascript:;' logno='" + logno + "' state='" + state + "'>店主收货</a>";
						}
						//else if(state == stateBFQS) {
							//部分收货（部分签收：103）--部分收货显示【全部收货】按钮
							//result += "<a class='fr toconfirmall' href='javascript:void(0)' id='" + torderproviderid + "'>全部收货</a>";
						//}
						result += "</p>";
						result += "</li>";
						result += "</ul>";
					}
				}
			}
		}
		$(".none-data").hide();
		container.append(result);
		myScroll.refresh();
	} else {
		hasNextPage = false;
	}
}

/**
 * 生成零售订单列表
 * @param _ReturnData
 */
function generateRetailOrders(_ReturnData) {
	var result = "";
	var	orderproviderlist = _ReturnData.orderproviderlist;

	if(orderproviderlist.length > 0) {
		for(var j = 0; j < orderproviderlist.length; j ++) {
			var torderproviderid = orderproviderlist[j].torderproviderid;  //供应商级订单号
			var providername = orderproviderlist[j].providername;  //供应商名称
			var ordertime = orderproviderlist[j].ordertime.split(".")[0];  //下单时间
			var actualamount = orderproviderlist[j].actualamount;  //订单金额
			var source = orderproviderlist[j].source;  //来源渠道
			var isdelivertohome = orderproviderlist[j].isdelivertohome;  //配送方式(86:快递到店;87:快递到家)

			result += "<ul class='orders-list body-white'>";
			result += "<li>";
			result += "<h3><span>" + providername + "</span>";
			result += "<em class='fr'>" + ordertime + "</em></h3>";
			result += "<a class='goods' href='javascript:;' torderproviderid='" + torderproviderid + "'>";
			result += "<span>";
			//商品list
			var orderitemslist = orderproviderlist[j].orderitemslist;
			var state = "";  //订单状态
			var logno = "";  //物流单号
			var showDZQR = false;  //是否显示店主确认
			var showGKQH = false;  //是否显示顾客取货
			var showCKWL = false;  //是否显示查看物流
			for(var k = 0; k < orderitemslist.length; k ++) {
				var goodsid = orderitemslist[k].goodsid;  //商品ID
				var goodbigpic = orderitemslist[k].goodbigpic.split(";")[0]; //商品主图
				var goodsname = orderitemslist[k].goodsname;  //商品名称
				var ispay = orderitemslist[k].ispay;  //付款状态(149:未支付;150:已支付)
				state = orderitemslist[k].state;  //订单状态
				var cancelstate = orderitemslist[k].cancelstate;  //取消状态(136:未取消;137:已取消)
				var paychanel = orderitemslist[k].paychanel;  //付款方式
				var platorself = orderitemslist[k].platorself;  //452-自营；453-平台
				logno = orderitemslist[k].logisticscode;  //物流单号

				if(typeof(orderitemslist[k].tgoodinfopoolid)!="undefined" && orderitemslist[k].tgoodinfopoolid!="") {
					goodsid = orderitemslist[k].tgoodinfopoolid;
				}
				result += "<img src='" + urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic + "' onerror='productErrImg(this);' />";
				//订单列表只有一个商品时，应该显示商品图片+商品名称
				if(orderitemslist.length == 1) {
					result += "<em>" + goodsname + "</em>";
				}

				//待确认显示【店主确认】按钮
				if((ispay==ispayY || paychanel==paychanelCod) && 
						(platorself == selfGoods) && 
						(state == stateWFH) && 
						(cancelstate == cancelstateN)) {
					showDZQR = true;
				}
				//待送货、待自提：显示【顾客取货】按钮
				if((ispay==ispayY || paychanel==paychanelCod) && 
						(platorself == selfGoods) && 
						(state == stateYFH) && 
						(cancelstate == cancelstateN) && 
						(logno != "")) {
					showGKQH = true;
				}
				//待签收、已签收：显示【查看物流】按钮
				if(logno!="" && cancelstate==cancelstateN && platorself==platGoods) {
					showCKWL = true;
				}
			}
			result += "</span>";
			result += "</a>";
			result += "<p>总价：¥" + formatNumber(actualamount);
			//待确认，操作：店主确认、店主取消
			if((querytype=="lsdqr") || (source==sourceSbApp2C && showDZQR)) {
				result += "<a class='fr tobeconfirm' querytype='lsdqr' href='javascript:;' id='" + torderproviderid + "'>店主确认</a>";
			}
			//待送货、待自提，操作：顾客取货
			if((querytype=="lsdzt") || (querytype=="lsdsh") || (source==sourceSbApp2C && showGKQH)) {
				result += "<a class='fr tocusteceipt' querytype='lsdzt' href='javascript:;' sincecode='" + logno + "'>顾客取货</a>";
			}
			//待签收、已签收，操作：查看物流
			if((querytype=="lsyfh") || (querytype=="lsyqs") || showCKWL) {
				result += "<a class='fr todelivery hidden' querytype='lsyfh' href='javascript:;' style='display:none;'>查看物流</a>";
			}
			result += "</p>";
			result += "</li>";
			result += "</ul>";
		}
		$(".none-data").hide();
		container.append(result);
		myScroll.refresh();
	} else if(page == 1) {
		showNoDataMsg(msgNoOrder);
	} else {
		hasNextPage = false;
	}
}

/**
 * 元素的事件绑定
 */
function eleBindEvent() {
	var sUrl = window.location.href;
	querytype = getParamValueByName(sUrl, "querytype");
	searchvalue = getParamValueByName(sUrl, "searchvalue");
	searchvalue = decodeURI(searchvalue);
	$(".orders-search input").val(searchvalue);

	//【店主收货】按钮的点击事件
	$(".toshopreceipt").live("click", function() {
		var state = $(this).attr("state");
		if(state == stateBFFH) {
			sessionStorage.setItem("sessionquerytype", "pfbfsh");
		}
		if(state == stateYFH) {
			sessionStorage.setItem("sessionquerytype", "pfyfh");
		}
		var logno = $(this).attr("logno");  //店主快递单号
		var data = {"mobile":mobile, "logno":logno, "netno":netNo};
		var resultJson = ajaxCommon(urlreceipt, data);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			resultJson = JSON.stringify(resultJson);
			storage.setItem("shopReceiptQuery", resultJson);
			window.location.href = "../receipt/shopreceiptinfo.html?logno=" + logno + "&t=" + t;	
		} else {
			window.location.href = "../receipt/shopreceiptfail.html?logno=" + logno + "&t=" + t;
		}	
	});

	//【全部收货】按钮的点击事件，弹窗提示
	$(".toconfirmall").live("touchend", function(e) {
		$(".pop-up-box").show();
		$("#confirmsignallbtn").attr("torderproviderid", $(this).attr("id"));
		e.preventDefault();
	});
	$(".pop-up-box .btn-gray").live("touchend", function(e) {
		$(".pop-up-box").hide();
		e.preventDefault();
	});

	//【全部收货--确认】按钮的点击事件
	$("#confirmsignallbtn").live("tap", function() {
		var orderproviderid = $(this).attr("torderproviderid");
		var data = {"orderproviderid":orderproviderid,"psam":psam};
		var resultJson = ajaxCommon(urlConfirmShAll, data);
		resultJson = convertNullToEmpty(resultJson);
		$(".pop-up-box").hide();

		if(resultJson._ReturnCode === returnCodeSuccess) {
			showAlertMsg(msgOrderAllSigned);
			//成功的时候，将该节点从dom中删除，因为此时该订单所有商品都已收货，应该出现在已收货列表中
			$("#" + orderproviderid).parent().parent().parent().remove();
		} else {
			showAlertMsg(resultJson._ReturnMsg);
		}

	});

	//【去付款】按钮的点击事件
	$(".topayall").live("tap",function() {
		var goodsname = $(this).attr("goodsname");
		var tallorderid = $(this).attr("tallorderid");
		var actualamount = $(this).attr("actualamount");
		var paychanel = $(this).attr("paychanel");
		var payurl = $(this).attr("paytoken");
		var mobile = $(this).attr("mobile");
		var source = $(this).attr("source");
		var paydata = {
				"goodsname":goodsname,
				"actualamount":actualamount,
				"tallorderid":tallorderid,
				"mobile":mobile
		};
		storage.setItem("paydata",JSON.stringify(paydata));
		window.location.href = "paytype.html?t="+ t;
		
		/*if(source == sourceSbApp2BShenbian) {
			showAlertMsg(msgPayForApp);
			return;
		}
		storage.setItem("payurl",payurl);
		window.location.href = payurl;*/
	});

	//搜索--头部返回
	$("#back").on("touchend", function(e) {
		e.preventDefault();
		if(querytype == "pfall") {
			window.location.href = "wholesaleorders.html?t=" + t;
		} else {
			window.location.href = "retailorders.html?t=" + t;
		}
	});
	//键盘搜索事件
	$(".orders-search input").on("keyup",function(e) {
		if(e.keyCode == 13) {
			searchvalue = $.trim($(".orders-search input").val());
			if(searchvalue == "") {
				showAlertMsg(msgOrderSearchEmpty);
				return;
			}
			window.location.href = encodeURI(encodeURI("querysuccess.html?querytype=" + querytype + "&searchvalue=" + searchvalue + "&t=" + t));
		}
	});
	//点击搜索事件
	$("#orderSearch").on("touchend", function(e) {
		e.preventDefault();
		searchvalue = $.trim($(".orders-search input").val());
		if(searchvalue == "") {
			showAlertMsg(msgOrderSearchEmpty);
			return;
		}
		window.location.href = encodeURI(encodeURI("querysuccess.html?querytype=" + querytype + "&searchvalue=" + searchvalue + "&t=" + t));
	});

	//商品区域的点击事件
	$(".goods").live("click", function(e) {
		//e.preventDefault();
		//如果有操作按钮时，把订单状态放到sessionquerytype中
		if($(this).parent().find("p").find("a").length > 0) {
			var querytype = $(this).parent().find("p").find("a").attr("querytype");
			sessionStorage.setItem("sessionquerytype", querytype);
		} else {
			sessionStorage.setItem("sessionquerytype", "");
		}
		var torderproviderid = $(this).attr("torderproviderid");  //供应商订单号
		window.location.href = "retaildetail.html?from=search&torderproviderid=" + torderproviderid + "&t=" + t;
	});

	//【店主确认】按钮的点击事件
	$(".tobeconfirm").live("touchend", function(e) {
		e.preventDefault();
		var querytype = $(this).attr("querytype");
		var torderproviderid = $(this).attr("id");  //供应商订单号
		sessionStorage.setItem("sessionquerytype", querytype);
		window.location.href = "retaildetail.html?from=search&torderproviderid=" + torderproviderid + "&t=" + t;
	});

	//【顾客取货】按钮的点击事件
	$(".tocusteceipt").live("touchend", function(e) {
		var sincecode = $(this).attr("sincecode");
		var data = {"netno":netNo, "sincecode":sincecode};
		var resultJson = ajaxCommon(urlCustreceipt, data);
		resultJson = convertNullToEmpty(resultJson);
		e.preventDefault();
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
	});
}

/**
 * 得到订单商品区域商品图片的宽度
 * @param imgsLen
 */
function getProImgsWidth(result, imgsLen) {
	if(imgsLen >= 4) {
		var imgsAreaLen = parseInt(imgsLen * proImgAreaWidth) + 10;
		result += "<span style='width:" + imgsAreaLen + "px;overflow-x:auto;'>";
	} else {
		result += "<span>";
	}
	return result;
}