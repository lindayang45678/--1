var couponlist = [];//优惠券列表
var billtype = "";//发票类型
var billtitle = "";//发票台头
var paychanel = "";//支付方式
var custelno = "";//收货人手机号
var cusname = "";//收货人姓名
var provinceid = "";//省代码
var cityid = "";//市代码
var areaid = "";//区代码
var province = "";//省
var city = "";//市
var area = ""//区
var addressfull = "";//详细地址
var saleOutArray = [];//下架商品Array

$(function(){
	if(getGoodsCount() == 0){
		window.location.href = "payfail.html";
	}
	//遮罩层高度
	$(".masker").css("top",$("header").height()).height(windowHeight - $("header").height());
})

$(function(){
	loadJs();
	//osFixed();
})

//iscroll
var myScroll;
function loaded() {
	myScroll = new iScroll('wrapper', {
		hScrollbar:false,
		vScrollbar:false,
		hScroll:false,
		useTransition:false,
		useTransform:false,
		checkDOMChanges:false,
		onBeforeScrollStart: function (e) {
			var target = e.target;
			while (target.nodeType != 1) target = target.parentNode;
			if (target.tagName != 'SELECT' && target.tagName != 'INPUT' && target.tagName != 'TEXTAREA')
			e.preventDefault();
		},
		onScrollMove:function(){
			$("input[type='tel']").blur();
		}
	});
}
//跳到当前输入框
var jd=0;
$(function(){
	loaded();
	$("input[type=tel]").live("focus", function(){
		var num = $(this).parents("dl").index();
		myScroll.scrollTo(0,-152*num, 200);
	}); 
})

$(function(){
	var url = window.location.href;
	var back = getParamValueByName(url, "back");
	if(back == "detail"){
		$(".back").attr("href","javascript:window.history.go(-1)");
		$(".go-again").attr("href","index.html?back=index");
	}else{
		$(".back").attr("href","index.html?back=index");
		$(".go-again").attr("href","index.html?back=index");
	}
})

/* 判断购物车，如果为空，结算不可用*/
function cartIsEmpty(){
	//如果购物车为空，结算不可用
	if(getGoodsCount() === 0){
		$("#to-pay").addClass("add-cart-gray");
	}else{
		$("#to-pay").removeClass("add-cart-gray");
	}
}

/* 加载收货地址、发票、优惠券、支付方式*/
$(function(){
	cartIsEmpty();//如果购物车为空，结算不可用
	
	//发票
	billtype = storage.getItem("billtype") || billtypeNone;
	billtitle = storage.getItem("billtitle") || "";
	var billDesc = "";
	if(billtype == billtypeBusiness){
		if(billtitle === ""){
			billDesc = "&nbsp;"
		}else{
			billDesc = billtitle;
		}
	}else if(billtype == billtypePersonal){
		billDesc = "个人";
	}else{
		billDesc = "不需要";
	}
	$(".fill-invoice").html("").append(billDesc);
	
	//优惠券
	couponlist = $.parseJSON(storage.getItem("couponlist")) || [];
	var promfee = storage.getItem("promfee") || "";
	if(promfee === ""){
		promfee = "请选择";
	}else{
		promfee = "共抵扣￥"+promfee;
	}
	$("#coupon").html(promfee);
	
	//支付方式
	paychanel = storage.getItem("paychanel") || JSON.stringify(paychannelLklCard);
	var payDesc = getPayDesc(parseInt(paychanel));
	$(".btn-circle").html("").append(payDesc);
	storage.setItem("paychanel",paychanel);
	//收货地址
	var shipaddress = storage.getItem("shipaddress");
	var customaddress = storage.getItem("customaddress");
	if(customaddress){
		customaddress = $.parseJSON(customaddress);
		cusname = customaddress.shipname;
		custelno = customaddress.shipmobile;
		provinceid = customaddress.shipprovinceid;
		cityid = customaddress.shipcityid;
		areaid = customaddress.shipareaid;
		province = customaddress.shipprovince;
		city = customaddress.shipcity;
		area = customaddress.shiparea;
		addressfull = customaddress.shipaddr;
	}else{
		shipaddress = $.parseJSON(shipaddress);
		cusname = shipaddress.shipname;
		custelno = shipaddress.shipmobile;
		provinceid = shipaddress.shipprovinceid;
		cityid = shipaddress.shipcityid;
		areaid = shipaddress.shipareaid;
		province = shipaddress.shipprovince;
		city = shipaddress.shipcity;
		area = shipaddress.shiparea;
		addressfull = shipaddress.shipaddr;
	}
	var content = "";
	content += "<i>" +cusname+ "</i>&nbsp;&nbsp;<i>" +custelno+ "</i><br/>";
	if(customaddress){
		content += "<i>" +province+ "&nbsp;" +city+ "&nbsp;" +area+ "</i><br/>";
	}
	content += "<i>" +addressfull+ "</i><br/>";
	content += "<i class='r-arrow'></i>";
	$("#shipaddress").html("").append(content);
	checkCoupon();//校验优惠券
})

/* 显示购物车商品*/
$(function(){
	var container = $(".fill-content");
	var content = "";
	//供应商Array
	var supplierArry = [];
	for(var i = 0; i < storage.length; i ++){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			var supplier = {
					"supplierId": proVal.supplierId,
					"supplierName": proVal.supplierName
			}
			supplierArry.push(supplier);
		}
	}
	supplierArry = uniqueSupplierArry(supplierArry);
	//for(var j = 0; j < supplierArry.length; j ++){
		//content += "<dt>" +supplierArry[j].supplierName+ "</dt>";
		for(var k = storage.length-1; k >= 0; k --){
			var key = storage.key(k);
			if(key.indexOf(productPrefix) >= 0){
				var proVal = $.parseJSON(storage.getItem(key));
				//if(proVal.supplierId == supplierArry[j].supplierId){
					content += "<dl class='hidden'>";
					content += "<dd class='clearfix' id='"+key+"'>";
					content += "<div class='fill-pic'><a href='prodetail.html?goodsid=" + proVal.goodsId + "'><img src='" +urlImage+ "/" +proVal.goodsId+ "/" +imgw100+ "/" +proVal.goodBigPic+ "' onerror='productErrImg(this);''/></a></div>";
					content += "<div class='fill-close'></div>";
					content += "<div class='fill-pro-title c-black'><em></em><i class='f-14'>";
					if(proVal.promoType == promoTypeZhiJiang) {
						content += "<span class='color drop'>直降</span>";
					}
					content += proVal.goodName;
					content += "</i></div>";
					if(proVal.skuFrontDisNameStr != ""){
						content += "<div class='c-gray f-12'>" +proVal.skuFrontDisNameStr+ "</div>";
					}
					content += "<div class='c-gray f-12'>售价：¥" +proVal.salePrice+ "</div>";
					content += "<div class='fill-change'><em class='f-14 c-black'>数量：</em><i class='fill-minus'>&minus;</i><input type='tel' value='" +proVal.goodscount+ "' store='" +proVal.store+ "' goodsname='" +proVal.goodName+"' promotype='" +proVal.promoType+ "' purchasecount='" +proVal.purchaseCount+ "' onfocus='this.focused=true;this.select();' onmouseup='if(this.focused){this.focused=false;return false;}'/><i class='fill-plus'>+</i></div>";
					content += "<div class='c-gray f-14 show amount'>小计：<em class='color f-16' id='" +proVal.tGoodSkuInfoId+ "'></em></div>";
					content += "</dd>";
					content += "</dl>";
				//}
			}
		}
	//}
	container.html("").append(content);
	
	$(".fill-content dl:eq(0)").removeClass("hidden");
	var showContent = "<div id='fill-show' class='open'><i class='f-14'>展开</i><i class='f-14 hidden'>收起</i><em>&nbsp;</em></div>";
	if($(".fill-content dl").length > 1){
		$(".fill-content dl:eq(0)").find(".show").after(showContent);
	}
	if($(".fill-content dl").length == 1){
		//$(".fill-close").remove();
	}
	myScroll.refresh();
	checkCoupon();
	
})

/* 商品加减号*/
$(".fill-plus").live("tap", function() {
	var n = parseInt($(this).prev().attr("value"));
	var promotype = $(this).prev().attr("promotype");

	if((n+1) > $(this).prev().attr("store")) {
		showAlertMsg(msgMoreProductStore);//库存不足提示
	} else {
		//直降商品数量校验
		if(promotype == promoTypeZhiJiang) {
			var goodsname = $(this).prev().attr("goodsname");
			var purchasecount = $(this).prev().attr("purchasecount");
			//限购数为0时，表示不限购
			if(parseInt(purchasecount)>0 && (n+1)>purchasecount) {
				showAlertMsg(goodsname + " 限购" +purchasecount+"件！");
			} else {
				$(this).prev().attr("value", n + 1);
				$(this).prev().val(n + 1);
				checkStorage($(this).parent().parent().attr("id"), $(this).prev().attr("value"));
			}
		} else {
			$(this).prev().attr("value", n + 1);
			$(this).prev().val(n + 1);
			checkStorage($(this).parent().parent().attr("id"), $(this).prev().attr("value"));
		}
	}
});

$(".fill-minus").live("tap", function() {
	var n = parseInt($(this).next().attr("value"));
	if (n > 1) {
		$(this).next().attr("value", n - 1);
		$(this).next().val(n - 1);
	}
	checkStorage($(this).parent().parent().attr("id"), $(this).next().attr("value"));
});

$("input[type=tel]").live("keyup", function(){
	var store = $(this).attr("store");
	var count = $(this).val();
	if(count < 1){
		$(this).val("1");
		$(this).attr("value","1");
		checkStorage($(this).parent().parent().attr("id"), $(this).attr("value"));
	}else{
		$(this).val(count);
		$(this).attr("value",count);
		checkStorage($(this).parent().parent().attr("id"), $(this).attr("value"));
	}
	if( !count || isNaN(count)){
		$(this).val("");
		$(this).attr("value","0");
		checkStorage($(this).parent().parent().attr("id"), "0");
	}
	var promotype = $(this).attr("promotype");
	//直降商品数量校验
	if(promotype == promoTypeZhiJiang) {
		var goodsname = $(this).attr("goodsname");
		var purchasecount = $(this).attr("purchasecount");
		//限购数为0时，表示不限购
		if(parseInt(purchasecount)>0 && parseInt(count)>parseInt(purchasecount)){
			$(this).val(purchasecount);
			$(this).attr("value",purchasecount);
			checkStorage($(this).parent().parent().attr("id"), $(this).attr("value"));
		}
	}else {
		if(parseInt(count) > parseInt(store)){
			$(this).val(store);
			$(this).attr("value",store);
			checkStorage($(this).parent().parent().attr("id"), $(this).attr("value"));
		}
	}
})

$("input[type=tel]").live("blur", function(){
	if($(this).val() == ""){
		$(this).val("1");
		$(this).attr("value","1");
		checkStorage($(this).parent().parent().attr("id"), "1");
	}
})

var keyIndex = 0;

$(".fill-close").live("tap",function(){
	$(".pop-up-box").show();
	keyIndex = $(this).parent().attr("id");
});

$("#confirm").on("click",function(e){
	var showContent = "<div id='fill-show' class='open'><i class='f-14'>展开</i><i class='f-14 hidden'>收起</i><em>&nbsp;</em></div>";
	if($(".fill-content dl").length > 0){
		$("#"+keyIndex).parent().remove();
		checkStorage(keyIndex, "");
		if(getGoodsCount() == 0) {
			//如果localStorage无商品，则清除优惠券
			storage.removeItem("couponlist");
			storage.removeItem("promfee");
			window.location.href = "index.html";
		}
		$(".fill-content dl:eq(0)").removeClass("hidden");
		if(!$(".fill-content dl:eq(0)").find("div").hasClass("open")){
			$(".fill-content dl:eq(0)").find(".show").after(showContent);
		}
		if($(".fill-content dl:eq(1)").hasClass("hidden")){
			$("#fill-show i").eq("0").removeClass("hidden");
			$("#fill-show i").eq("1").addClass("hidden");
			$("#fill-show em").removeClass("fill-rotation");
		}else{
			$("#fill-show i").eq("0").addClass("hidden");
			$("#fill-show i").eq("1").removeClass("hidden");
			$("#fill-show em").addClass("fill-rotation");
		}
	}
	//如果购物车只有一件商品，不允许删除
	if($(".fill-content dl").length == 1){
		$("#fill-show").remove();
		//$(".fill-close").remove();
	}
	$(".pop-up-box").hide();
	e.preventDefault();
});

$("#cancel").on("click",function(e){
	$(".pop-up-box").hide();
	e.preventDefault();
});

/* 点击展开旋转箭头展开隐藏*/
$('#fill-show').live('tap',function (){	
	$('#fill-show em').toggleClass('fill-rotation');
	$('.fill-content dl').toggleClass('hidden');
	$('#fill-show i').toggleClass('hidden');
	$('.fill-content dl:nth-child(1)').removeClass('hidden');
	myScroll.refresh();
});

/* 根据localStorage验证优惠券
 * if 增加商品数量
 * else 删除商品
 * */
function checkStorage(key,count){
	var proVal = $.parseJSON(storage.getItem(key));
	if(count != ""){
		proVal.goodscount = count;
		storage.removeItem(key);
		storage.setItem(key, JSON.stringify(proVal));
	}else{
		storage.removeItem(key);
	}
	cartIsEmpty();//判断购物车是否为空
	checkCoupon();
}

/* 请求优惠券接口，加载页面底部购物车金额，优惠情况*/
function checkCoupon(){
	//下架商品Array
	saleOutArray = [];
	//商品列表
	var items = [];
	for(var i = 0; i < storage.length; i ++){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			var item = {
				"skuo2oid":	proVal.o2oId,
				"skuid": proVal.tGoodSkuInfoId,
				"goodsname": proVal.goodName,
				"goodssaleprice": proVal.salePrice,
				"goodscount": proVal.goodscount,
				"channel": proVal.channelcode
			}
			items.push(item);
		}
	}

	var data = {
			"token":userToken,
		    "mobile":mobile,
		    "pasmid":psam,
		    "source":sourceSbApp2B,
		    "custelno":mobile,
		    "wholesale":"2B",
		    "couponlist":couponlist,
		    "items":items,
		    "paychannel":storage.getItem("paychanel") || "1"
	};
	var resultJson = ajaxCommon(urlCheckCoupon, data);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var coupondata = resultJson._ReturnData.coupondata;
		$(".goodscart").html(getGoodsCount());
		$(".fill-freight").html("￥" +coupondata.logisfee)
		$(".amount-payable").html("￥" +coupondata.total);
		
		//计算商品小计
		var items = coupondata.items;
		for(var i = 0; i < storage.length; i ++){
			var key = storage.key(i);
			if(key.indexOf(productPrefix) >= 0){
				var proVal = $.parseJSON(storage.getItem(key));
				for(var j = 0; j < items.length; j ++){
					if(proVal.tGoodSkuInfoId == parseInt(items[j].skuid)){
						if(items[j].goodtotalprice < 0){
							$("#"+proVal.tGoodSkuInfoId).parent().html("<p class='color'>已下架，请删除后再结算！</p>");
							saleOutArray.push(proVal.goodName);
						}else{
							$("#"+proVal.tGoodSkuInfoId).html("￥"+parseFloat(items[j].goodtotalprice).toFixed(2));
						}
					}
				}
			}
		}

		if (resultJson._ReturnData.retcode == "0000") {
			//优惠券区域
			var promfee = coupondata.promfee;
			if(promfee == "0.00"){
				$("#coupon").html("请选择");
				couponlist = [];
			}else{
				$("#coupon").html("共抵扣￥"+promfee);
			}
			return true;
		} else {
			//接口返回错误--9000
			var msg = "";
			if(resultJson._ReturnData.directDropFlag == "1") {
				var directdropmap = resultJson._ReturnData.directdropmap;
				for (var mapKey in directdropmap) {
					for(var j = 0; j < storage.length; j ++) {
						var key = storage.key(j);
						if(key.indexOf(productPrefix) >= 0){
							var proVal = $.parseJSON(storage.getItem(key));
							if(parseInt(directdropmap[mapKey])>0 && proVal.tGoodSkuInfoId==mapKey) {
								msg += proVal.goodName + " 限购" + directdropmap[mapKey] +"件！<br/>";
							}
						}
					}
				}
				showAlertMsg(msg);
				return false;
			}

			if(resultJson._ReturnData.priceflag == "1") {
				var pricemap = resultJson._ReturnData.pricemap;
				for (var mapKey in pricemap) {
					for(var j = 0; j < storage.length; j ++){
						var key = storage.key(j);
						if(key.indexOf(productPrefix) >= 0){
							var proVal = $.parseJSON(storage.getItem(key));
							if(proVal.tGoodSkuInfoId == mapKey) {
								msg += proVal.goodName + "，";
							}
						}
					}
				}
				msg += "价格不符，请重新进入订单确认页购买！";
				showAlertMsg(msg);
				return false;
			}

			return false;
		}
	} else {
		//网络不通
	}
}

/* 去结算 */
$("#to-pay").on("click",function(){
	//校验优惠券接口
	if(!checkCoupon()) {
		return;
	}
	if($(this).hasClass("add-cart-gray")){
		return;
	}
	if(provinceid === "" || cityid === "" || areaid === "" || addressfull === ""){
		showAlertMsg(msgAddressNotChoose);
		return;
	}
	//下架商品提示
	if(saleOutArray.length > 0) {
		showAlertMsg(saleOutArray[0] + "已下架");
		return;
	}

	$("input[type=tel]").each(function(){
		if($(this).val() == ""){
			$(this).val("1");
			$(this).attr("value","1");
			checkStorage($(this).parent().parent().attr("id"), "1");
		}
	})

	$(".masker").removeClass("hidden");
	var items = [];
	for(var i = 0; i < storage.length; i ++){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			var item = {
				"skuid": proVal.tGoodSkuInfoId,
				"skuo2oid":	proVal.o2oId,
				"goodscount": proVal.goodscount,
				"channelcode": proVal.channelcode,
				"is3h": proVal.distributionflag
			}
			items.push(item);
		}
	}
	var password = storage.getItem("password") || "";
	if(parseInt(paychanel) == paychannelLklWallet){
		paychanel = JSON.stringify(paychannelLkl);
	}
	var data = {
			"token":userToken,
			"mobile":mobile,
			"password":password,
			"source":sourceSbApp2B,
			"custelno":custelno,
			"cusname":cusname,
			"phonename":"",
			"phoneidcard":"",
			"deviceno":psam,
			"billtype":billtype,
			"billtitle":billtitle,
			"paychanel":paychanel,
			"paytype":paytypeFull,
			"paystage":"1",
			"bankid":"",
			"provinceid":provinceid,
			"cityid":cityid,
			"areaid":areaid,
			"addressfull":addressfull,
			"weekdevice":weekdeviceY,
			"ispay":ispayN,
			"devicetype":devicetypeHome,
			"items":items,
			"coulist":couponlist
	};
	
	
	//请求接口
	$.ajax({
        type: "POST",
        url: urlAddOrder,
        data: data,
        dataType: "json",
        async: true,
        cache: false,
        success: function(result) {
        	var resultJson = result;
        	if (resultJson._ReturnCode === returnCodeSuccess) {
        		//生产订单后清空购物车
        		clearCart();//清空购物车localStorage
        		storage.removeItem("couponlist");//清空优惠券列表
        		storage.removeItem("promfee");//清空优惠金额
        		
        		var ispay = resultJson._ReturnData.ispay;
        		if(ispay == ispayY){
        			window.location.href = "paysuccess.html";
        		}else{
        			//订单生成，调用支付App
        			var payurl = resultJson._ReturnData.payurl;
        			if(paychanel == paychanelWx){
        				payurl = {"payurl":$.parseJSON(JSON.stringify(payurl))};
        			}
        			if(paychanel == paychanelZfb){
        				payurl = {"payurl":$.parseJSON(JSON.stringify(payurl))};
        			}
        			if(paychanel == paychannelLklCard){
        				payurl = $.parseJSON(payurl);
        				payurl.mobile = mobile;
        				payurl = {"payurl":payurl};
        			}
        			storage.setItem("payurl",JSON.stringify(payurl));
        			if(paychanel == paychannelLkl || paychanel == paychannelLklWallet){
        				storage.setItem("payurl",payurl);
        				window.location.href = payurl;
        			}else{
        				cordova._native.pay.pay(
        						paychanel,
        						JSON.stringify(payurl),
        						function(){
        							window.location.href = "paysuccess.html";
        						},
        						function(e){
        							// 1.支付失败 2.支付确认中
        							if(e == "2"){
        								window.location.href = "payconfirm.html";
        							}else{
        								window.location.href = "payfail.html";
        							}
        						}
        				)
        			}
        		}
        	}else{
        		//接口返回错误
        		showAlertMsg(resultJson._ReturnMsg);
        		$(".masker").addClass("hidden");
        		//优惠券不可用的情况
        		storage.removeItem("couponlist");//清空优惠券列表
        		storage.removeItem("promfee");//清空优惠金额
        		couponlist = [];
        		$("#coupon").html("请选择");
        	}
        },
        error: function(xhr, errorType, error) {
        	$(".masker").addClass("hidden");
        }
	});
})