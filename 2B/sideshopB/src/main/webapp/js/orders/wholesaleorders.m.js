var page = 1;
var pageSize = 10;
var hasNextPage = true;
var container = $(".orders-items");
var ordercate = "all";  //大类  批发（pfjh）、零售、需店主送货
var querytype = initQuerytype();  //小类;
var myScroll, pullDownOffset, pullUpEl, pullUpOffset;
var proImgAreaWidth = 86;

/**
 * 初始化iScroll控件
 */
function loaded() {
	pullUpEl = document.getElementById("loading");
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll("wholesaleorders-scroll", {//iScroll的对象
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
				getwholesaleorderlist(querytype, "", "");  //执行加载后的自定义功能	
			}
		}
	});
}

document.addEventListener("touchmove", function(e) {e.preventDefault();}, false);
document.addEventListener("DOMContentLoaded", function() {setTimeout(loaded, 200);}, false);

$(function() {
	//加载cordova
	loadJs();

	//默认显示批发进货订单下各订单状态的数量
	getwholesaleordercnt(ordercate);

	//取批发订单下各状态的订单列表
	getwholesaleorderlist(querytype, "", "");

	//元素的事件绑定
	eleBindEvent();
	
});

/**
 * 取批发订单下各状态的订单总数
 */
var this_x = null;//滑动位置
function getwholesaleordercnt(ordercate) {
	var headerList = $("#header-list-slide ul");
	var result = "";
	
	var thisx = parseInt(sessionStorage.getItem("this_x"));
	if(thisx!=undefined && thisx!="") {
		this_x = thisx;
	} else {
		this_x = 0;
	}
	//mobile参数该字段用于批发待付款的时候用到，主要查该店主下的订单还没有付款的
	var data = {"token":userToken, "ecnetno":netNo, "mobile":mobile, "ordercate":ordercate};
	var resultJson = ajaxCommon(urlOrderCnt, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		var pf2needpaynum = _ReturnData.pf2needpaynum;	//待付款总数
		var pf2nodelivernum = _ReturnData.pf2nodelivernum;	//待发货总数
		var pf2deliverednum = _ReturnData.pf2deliverednum;  //待收货总数
		var pf2partreceivenum = _ReturnData.pf2partreceivenum;  //部分收货总数
		var pf2receivednum = _ReturnData.pf2receivednum;  //已收货总数
		var sxneedbsendnum = _ReturnData.sxneedbsendnum;  //需店主送货订单总数
		var ls2waitconfirmnum = _ReturnData.ls2waitconfirmnum;	//待确认总数
		var ls2waittakenum = _ReturnData.ls2waittakenum;	//待自提总数
		var ls2waitsendnum = _ReturnData.ls2waitsendnum;  //待送货总数
		var ls2nodelivernum = _ReturnData.ls2nodelivernum;  //待发货总数
		var ls2deliverednum = _ReturnData.ls2deliverednum;  //待签收总数
		var ls2signednum = _ReturnData.ls2signednum;  //已签收总数
		var ls2calcelednum = _ReturnData.ls2calcelednum;  //已取消总数

		//批发订单有【待付款、待发货、待收货】时，零售订单有【待确认、待自提、待送货】订单时，批发订单和零售订单右上角，增加小圆点
		if(parseInt(pf2needpaynum)+parseInt(pf2nodelivernum)+parseInt(pf2deliverednum) > 0){
			$("#head h1").find("a").eq(0).append("<s></s>");
		}
		if(parseInt(ls2waitconfirmnum)+parseInt(ls2waittakenum)+parseInt(ls2waitsendnum) > 0){
			$("#head h1").find("a").eq(1).append("<s></s>");
		}

		result += "<li><a id='pfall' href='javascript:;' class='f-14'><span>全部</span></a></li>";
		result += "<li><a id='pfdfk' href='javascript:;' class='f-14'><span>待付款</span>";
		if(parseInt(pf2needpaynum) > 0) {
			result += "<i>" + pf2needpaynum + "</i>";
		}
		result += "</a></li>";
		result += "<li><a id='pfdfh' href='javascript:;' class='f-14'><span>待发货</span>";
		if(parseInt(pf2nodelivernum) > 0) {
			result += "<i>" + pf2nodelivernum + "</i>";
		}
		result += "</a></li>";
		result += "<li><a id='pfyfh' href='javascript:;' class='f-14'><span>待收货</span>";
		if(parseInt(pf2deliverednum) > 0) {
			result += "<i>" + pf2deliverednum + "</i>";
		}
		result += "</a></li>";
		//result += "<li><a id='pfbfsh' href='javascript:;' class='f-14'><span>部分收货</span>";
		//if(parseInt(pf2partreceivenum) > 0) {
		//	result += "<i>" + pf2partreceivenum + "</i>";
		//}
		//result += "</a></li>";
		result += "<li><a id='pfysh' href='javascript:;' class='f-14'><span>已收货</span>";
		headerList.append(result);

		//状态选项选中样式置换
		$("#" + querytype).addClass("active");

		//订单状态支持左右滑动
		var liWidth = null;
		$("#header-list-slide li").each(function(index) {
			liWidth += parseInt($(this).width());
		});
		$("#header-list-slide").css("width", liWidth + 1 + "px");
		var menuScroll = new iScroll("header-list", {
			hScroll: true,
			vScroll: false,
			hScrollbar: false,
			vScrollbar: false,
			bocune:true,
			onScrollEnd: function () {
				sessionStorage.setItem("this_x",this.x);
				$("#header-list-slide").css({"position":"absolute","top":0,"left":sessionStorage.getItem("this_x")});	
			},
		});
		if(liWidth>windowWidth){
			$("#header-list-slide").css({"position":"absolute","top":"0", "left":this_x});
		}
		$("#wholesaleorders-scroll").css({"height":windowHeight-87,"margin-top":"87px"});
		$("#header-list-slide").css("margin","auto");
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}

function initQuerytype() {
	var value = sessionStorage.getItem("pfquerytype");
	var initvalue = "";
	if(value!=undefined && value!="") {
		initvalue = value;
	} else {
		//默认显示批发全部订单
		initvalue = "pfall";
		$("#pfall").addClass("active");
	}
	return initvalue;
}

/**
 * 取批发订单下各状态的订单列表
 * @param querytype
 * @param queryordertotal 是否查询满足条件的订单总数:yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
 * @param searchvalue:用于订单搜索，该值匹配以下字段：1、下单手机号 2、商品名 3、供应商订单号torderproviderid 4、支付大订单号tallorderid
 */
function getwholesaleorderlist(querytype, queryordertotal, searchvalue) {
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
				if(querytype=="pfall" || querytype=="pfdfk") {
					generateAllOrPayOrderHtml(_ReturnData);  //生成批发进货订单列表--全部&待付款
				} else {
					generateOthersOrderHtml(_ReturnData);  //生成批发进货订单列表--其他状态的订单列表
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
 * 生成批发进货订单列表--全部&待付款
 * @param _ReturnData
 */
function generateAllOrPayOrderHtml(_ReturnData) {
	var result = "";
	var orderlist = _ReturnData.orderlist;
	if(orderlist.length > 0) {
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

						result += "<a class='goods' href='wholesaledetail.html?torderproviderid=" + torderproviderid + "&pfstate=" + state + "&t=" + t + "'>";
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
						var favorrulemoney = orderproviderlist[j].favorrulemoney; //优惠金额
						var ordertime = orderproviderlist[j].ordertime.split(".")[0];  //下单时间
						var torderproviderid = orderproviderlist[j].torderproviderid;  //供应商级订单号
						var providername = orderproviderlist[j].providername;  //供应商名称
						var state = orderproviderlist[j].state;  //供应商级订单状态
						result += "<ul class='orders-list body-white'>";
						result += "<li>";
						result += "<h3><span>" + providername + "</span>";
						result += "<em class='fr'>" + ordertime + "</em></h3>";

						result += "<a class='goods' href='wholesaledetail.html?torderproviderid=" + torderproviderid + "&pfstate=" + state + "&t=" + t + "'>";
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
							//result += "<a class='fr toconfirmall' href='javascript:;' id='" + torderproviderid + "'>全部收货</a>";
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
	} else if(page == 1) {
		showNoDataMsg(msgNoOrder);
	} else {
		hasNextPage = false;
	}
}

/**
 * 生成批发进货订单列表--其他状态的订单列表
 * @param _ReturnData
 */
function generateOthersOrderHtml(_ReturnData) {
	var result = "";
	var	orderproviderlist = _ReturnData.orderproviderlist;

	if(orderproviderlist.length > 0) {
		for(var j = 0; j < orderproviderlist.length; j ++) {
			var state = orderproviderlist[j].state;  //订单状态
			var torderproviderid = orderproviderlist[j].torderproviderid;  //供应商级订单号
			var providername = orderproviderlist[j].providername;  //供应商名称
			var ordertime = orderproviderlist[j].ordertime.split(".")[0];  //下单时间
			var actualamount = orderproviderlist[j].actualamount;  //订单金额
			result += "<ul class='orders-list body-white'>";
			result += "<li>";
			result += "<h3><span>" + providername + "</span>";
			result += "<em class='fr'>" + ordertime + "</em></h3>";
	
			result += "<a class='goods' href='wholesaledetail.html?torderproviderid=" + torderproviderid + "&pfstate=" + state + "&t=" + t + "'>";
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
			result += "<p>总价：¥" + formatNumber(actualamount);
			//配送中（部分发货：101，已发货：102）--待收货显示【店主收货】按钮
			if(querytype == "pfyfh" && showDZSH) {
				result += "<a class='fr toshopreceipt' href='javascript:;' logno='" + logno + "'>店主收货</a>";
			}
			//else if(querytype == "pfbfsh") {
				//部分收货（部分签收：103）--部分收货显示【全部收货】按钮
				//result += "<a class='fr toconfirmall' href='javascript:;' id='" + torderproviderid + "'>全部收货</a>";
			//}
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
	//各订单状态的点击事件
	$("#header-list-slide ul li").live("tap", function() {
		//每次调用清空数据,主要用于状态Tab点击
		container.empty();
		$(".none-data").hide();

		$(this).siblings().find("a").removeClass("active");
		$(this).find("a").addClass("active");
		querytype = $(this).find("a").attr("id");
		page = 1;
		hasNextPage = true;
		getwholesaleorderlist(querytype, "", "");
		sessionStorage.setItem("sessionquerytype", querytype);
		sessionStorage.setItem("pfquerytype", querytype);
		//window.location.reload(true);
	});

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

		//不刷新当前页，部分收货数量减一
		var pfbfsh_num = $("#pfbfsh i").text();  //部分收货数量
		if(Number(pfbfsh_num) == 1) {
			//订单数量为0,提示订单为空
			$("#pfbfsh i").remove();
			showNoDataMsg(msgNoOrder);
		} else {
			$("#pfbfsh i").text(Number(pfbfsh_num) + 1);
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
		window.location.href = "paytype.html?t=" +t;
		/*if(source == sourceSbApp2BShenbian) {
			showAlertMsg(msgPayForApp);
			return;
		}
		storage.setItem("payurl",payurl);
		window.location.href = payurl;*/
	});
	
	//搜索按钮点击事件
	$(".order-search").live("touchend", function(e) {
		$(".orders-search").removeClass("hidden");
		e.preventDefault();
	});
	//隐藏搜索层
	$(".orders-search span").on("touchend", function(e) {
		$(".orders-search").addClass("hidden");
		$(".orders-search input").val("");
		e.preventDefault();
	});
	$(".orders-search").live("touchend", function(e) {
		$(".orders-search").addClass("hidden");
		$(".orders-search input").val("");
		e.preventDefault();
	});
	$(".orders-search div").on("touchend", function(e) {
		e.stopPropagation();
	});
	//键盘搜索事件
	$(".orders-search input").on("keyup",function(e) {
		if(e.keyCode == 13) {
			var searchvalue = $.trim($(".orders-search input").val());
			if(searchvalue == "") {
				showAlertMsg(msgOrderSearchEmpty);
				return;
			}
			$(".orders-search input").val("");
			window.location.href = encodeURI(encodeURI("querysuccess.html?querytype=pfall&searchvalue=" + searchvalue));
		}
	});
	//点击搜索事件
	$(".orders-search .head-right").on("touchend", function(e) {
		$(".orders-search").removeClass("hidden");
		e.preventDefault();
		var searchvalue = $.trim($(".orders-search input").val());
		if(searchvalue == "") {
			showAlertMsg(msgOrderSearchEmpty);
			return;
		}
		$(".orders-search input").val("");
		window.location.href = encodeURI(encodeURI("querysuccess.html?querytype=pfall&searchvalue=" + searchvalue));
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