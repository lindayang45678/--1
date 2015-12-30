/**
 * 返回当前设备的类型
 * @returns {String}
 */
function osType() {
	var isiPad = navigator.userAgent.match(/iPad/i) != null;
    var isiPhone = navigator.userAgent.match(/iPhone/i) != null;
    var isAndroid = navigator.userAgent.match(/Android/i) != null;

    if(isiPad){return "ios";}
    if(isiPhone){return "ios";}
    if(isAndroid){return "android";}

    if(!isiPad && !isiPhone && !isAndroid){return "other";}
}
/**
 * 修复IOS设备fixed定位失效
 */
function osFixed(){
	if(osType() == "ios"){
		$(".container").on("focus", "input", function(){
			$("header").addClass("osfixed");
			$(".container").css("margin-top","0");
			//批发进货-订单确认
			$(".fill-content").css("margin-top","6px");
			$("#wholesale-foot").addClass("osfixed");
			$(".fill-head").css("margin-bottom","7px");
		}).on("focusout", "input", function(){
			$("header").removeClass("osfixed");
			$(".container").css("margin-top","44px");
			//批发进货-订单确认
			$(".fill-content").css("margin-top","50px");
			$("#wholesale-foot").removeClass("osfixed");
			$(".fill-head").css("margin-bottom","70px");
		});
		
		//批发进货多sku
		$(".sku-b").on("focus", "input", function(){
			$("header").addClass("osfixed");
			$(".container").css("margin-top","0");
		}).on("focusout", "input", function(){
			$("header").removeClass("osfixed");
			$(".container").css("margin-top","44px");
		});
		
		//新增、编辑我的小店-模板商品
		$(".shopedit-3item").on("focus", "div", function(){
			$("header").addClass("osfixed");
			$(".container").css("margin-top","0");
		}).on("focusout", "div", function(){
			$("header").removeClass("osfixed");
			$(".container").css("margin-top","44px");
		});
		
		//批发进货-新增收货地址
		$(".fill-edit-address").on("focus", "div", function(){
			$("header").addClass("osfixed");
			$(".container").css("margin-top","0");
		}).on("focusout", "div", function(){
			$("header").removeClass("osfixed");
			$(".container").css("margin-top","44px");
		});
	}
}
/**
 * 得到指定sUrl中sName对应的value值
 * @param url
 * @param paraName
 * @return
 */
function getParamValueByName(sUrl, sName) {
	if(sUrl == "" || sUrl == null) {
		return "";
	}
	if(sUrl.indexOf("?") < 0) {
		return "";
	} else {
		sUrl = sUrl.substr(sUrl.indexOf("?")+1, sUrl.length);
		var arrayUrl = sUrl.split("&");
		for(var i = 0; i < arrayUrl.length; i ++) {
			var eqIndex = arrayUrl[i].indexOf("=");
			var key = arrayUrl[i].substr(0, eqIndex);
			var value = arrayUrl[i].substr(eqIndex+1, arrayUrl[i].length);
			if(key == sName) {
				return unescape(value);
			}
		}
	}
	return "";
}

/**
 * 数组去重复
 * @param arr
 * @return
 */
function uniqueArray(arr) {
	var ret = [];
	var hash = {};
	for(var i=0; i<arr.length; i++) {
		var item = arr[i];
	    var key = typeof(item) + item;
	    if(hash[key] !== 1) {
	    	ret.push(item);
	    	hash[key] = 1;
	    }
	}
	return ret;
}

/**
 * 去除相同供应商
 * @param arr
 * @return
 */
function uniqueSupplierArry(arr) {
	var ret = [];
	var hash = {};
	for(var i=0; i<arr.length; i++) {
		var item = arr[i];
		var id = item.supplierId;
	    var key = typeof(id) + id;
	    if(hash[key] !== 1) {
	    	ret.push(item);
	    	hash[key] = 1;
	    }
	}
	return ret;
}

/**
 * 商品、广告排序
 * @param arr
 * @return
 */
function sortGoodsAndAdvert(arr) {
	var arrPrd = arr.concat();
	for(var i = 0 ; i < arrPrd.length-1; i ++){
		for(var j = 0; j < arrPrd.length-1-i; j ++){
			var temp;
			var prevOrder = "";
			var nextOrder = "";
			prevOrder = arrPrd[j].sort;
			nextOrder = arrPrd[j+1].sort;
			if(prevOrder < nextOrder){
				temp = arrPrd[j+1];
				arrPrd[j+1] = arrPrd[j];
				arrPrd[j] = temp;
			}
		}
	}
	return arrPrd;
}

/**
 * 返回sku中价格最低的一项
 * @param skuList
 */
function getMinSku(array){
	var skuList = array.concat();
	if(skuList.length > 0){
		for(var i = 0; i < skuList.length; i ++){
			for(var j = 0; j < skuList.length-i-1; j ++){
				var temp;
				var prevPrice = skuList[j].salePrice;
				var nextPrice = skuList[j+1].salePrice;
				if(prevPrice > nextPrice){
					temp = skuList[j+1];
					skuList[j+1] = skuList[j];
					skuList[j] = temp;
				}
			}
		}
		return skuList;
	}else{
		return "";
	}
}

/**
 * 获取时间戳
 * @return
 */
function getTimeStamp() {
	var nowDate = new Date();
	var t = nowDate.getTime();
	return t;
}

/**
 * 时间戳格式化  yyyy-mm-dd hh24:mi:ss
 * @return
 */
function getLocalTime(nS) {
	if(nS==null || nS=="" || nS=="null") {
		return "";
	}
    var d = new Date(nS);
    var nowm = d.getMonth()+1;
    var mmstr = '';
    var dstr = '';
    var hstr = '';
    var mistr = '';
    var sestr = '';

    if(d.getMonth()<9){
    	mstr = '0'+ nowm
    }else{
    	mstr = nowm;
    }

    if(d.getDate()<10){
    	dstr = '0'+d.getDate();
    }else{
    	dstr = d.getDate();
    }
    
    if(d.getHours()<10){
    	hstr = '0'+d.getHours();
    }else{
    	hstr = d.getHours();
    }

    if(d.getMinutes()<10){
    	mistr = '0'+d.getMinutes();
    }else{
    	mistr = d.getMinutes();
    }

    if(d.getSeconds()<10){
    	sestr = '0'+d.getSeconds();
    }else{
    	sestr = d.getSeconds();
    }

    return d.getFullYear()+"-"+mstr+"-"+dstr+" "+hstr+":"+mistr+":"+sestr;
 }


/**
 * 计算倒计时--还剩xx天xx时xx分xx秒
 * @param startTime
 * @param dayShow
 * @param hourShow
 * @param minuteShow
 * @param secondShow
 */
function countdown(startTime, dayShow, hourShow, minuteShow, secondShow) {
	startTime = new Date(Date.parse(startTime.replace(/\-/g, "\/")));
	var nowTime = sysDate;
	if(typeof(nowTime)=="undefined" || nowTime=="") {
		nowTime = new Date();
	} else {
		nowTime = new Date(Date.parse(nowTime.replace(/\-/g, "\/")));
	}
	var intDiff = parseInt((startTime.getTime() - nowTime.getTime()) / 1000);
	if(nowTime < startTime) {
		window.setInterval(function() {
			var day = 0, hour = 0, minute = 0, second = 0;
			if(intDiff > 0) {
				day = Math.floor(intDiff / (60 * 60 * 24));
				hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
				second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
			}
			if (minute <= 9) minute = '0' + minute;
			if (second <= 9) second = '0' + second;
			$(dayShow).html(day);
			$(hourShow).html(hour);
			$(minuteShow).html(minute);
			$(secondShow).html(second);
			intDiff --;
		}, 1000);
		return "1";
	} else {
		return "0";
	}
}

/**
 * 获取localStorage中系统时间与当前时间比较，如果大于1天，清空localStorage
 * @param sysDate
 */
function storageFailDay(sysDate){
	if(typeof(sysDate)=="undefined" || sysDate==""){
		sysDate = new Date();
	}else{
		sysDate = new Date(Date.parse(sysDate.replace(/\-/g, "\/")));
	}
	var nowDate = new Date();
	var intDiff = parseInt((sysDate.getTime() - nowDate.getTime()) / 1000);
	var day = 0;
	if(intDiff > 0){
		day = Math.floor(intDiff / (60 * 60 * 24));
	}
	return day;
}

/**
 *  获取省市区名称
 *  @param proviceCode
 *  @param cityCode
 *  @param areaCode
 */
function getAreaRegion(proviceCode,cityCode,areaCode,areaAddress,area){
	var areaRegionJsonRoot = "../../json/area_region.json";
	var address = "";
	$.ajax({
        type: "GET",
        async: false,
        url: areaRegionJsonRoot,
        dataType: "json",
        cache: false,
        success: function(result) {
        	for(var index in result){
    			if(proviceCode == result[index].code){
    				address += result[index].name;
    			}
    			if(cityCode == result[index].code){
    				address += result[index].name;
    			}
    			if(areaCode == result[index].code){
    				address += result[index].name;
    			}
    		}
    		address += areaAddress;
    		$(area).html(address);
        }
    });
}

/**
 * 根据父Code和当前Code得到城市列表
 * @param citySelectId
 * @param parentCode
 * @param currCode
 */
function getCityNameByCode(citySelectId, parentCode, currCode) {
	$.ajax({
        type: "GET",
        async: false,
        url: "../../js/lib/area_region.json",
        dataType: "json",
        cache: false,
        success: function(json) {
        	var cityLength = json.length;
    		var ret = '';
    		for(var i = 0; i< cityLength; i ++) {
    			if(json[i].parent == parentCode) {
    				ret += '<option value="' + json[i].code + '"';
    				if(json[i].code == currCode) {
    					ret += ' selected="true"';
    				}
    				ret += '>';
    				ret += json[i].name;
    				ret += '</option>';
    			}
    		}
    		$("#" + citySelectId).html(ret);
        }
    });
}

/**
 * 输出通用【确定】信息
 * @param msg
 */
function showConfirmMsg(msg) {
	$(".mask").remove();
	var sContent = '<div class="mask">';
	sContent += '<div class="popup-msg"><p>';
	sContent += msg;
	sContent += '</p>';
	sContent += '<a href="javascript:;" id="popup-confirm">确定</a>';
	sContent += '</div></div>';
	$('.container').append(sContent);
	$('.mask').show();
	$('.mask').live('click',function() {
		if(!preventRealClick()) {return;}
		$(this).hide();
	});
	$('#popup-confirm').live('click',function() {
		$('.mask').click();
	});
}
/**
 * 输出通用【确定】信息--点击确定后，返回上一页
 * @param msg
 */
function showBackToPrevMsg(msg) {
	$(".mask").remove();
	var sContent = '<div class="mask">';
	sContent += '<div class="popup-msg"><p>';
	sContent += msg;
	sContent += '</p>';
	sContent += '<a href="javascript:;" id="popup-back">确定</a>';
	sContent += '</div></div>';
	$('.container').append(sContent);
	$('.mask').show();
	$('#popup-back').live('click',function() {
		window.history.back();
	});
}
/**
 * 没有相关加载，没有数据时
 * @param msg
 */
function showNoDataMsg(msg) {
	$(".none-data").remove();
	var sContent = "<div class='none-data'>";
	sContent += "<div>";
	sContent += "<img src='../../images/searchno-img.png' />";
	sContent += "<p class='c-gray'>";
	sContent += msg;
	sContent += "</p>";
	sContent += "</div>";
	sContent += "</div>";
	$('.container').append(sContent);
}
/**
 * 没有相关加载，没有数据,可配置容器
 * @param msg
 */
function showNoDataMsgtext(msg,container) {
	$(".none-data").remove();
	var sContent = "<div class='none-data'>";
	sContent += "<div>";
	sContent += "<img src='../../images/searchno-img.png' />";
	sContent += "<p class='c-gray'>";
	sContent += msg;
	sContent += "</p>";
	sContent += "</div>";
	sContent += "</div>";
	if (container == "" || container == undefined) {
		$('.container').append(sContent);
	} else {
		container.append(sContent);
	}
	
}
/**
 * 显示loading图标
 */
function showLoading() {
	$("#loading").width(windowWidth + "px").height(windowHeight + "px");
	$("#loading").show();
}

/**
 * 隐藏loading图标
 */
function hideLoading() {
	$("#loading").hide();
}

/**
 * 清除所有localStorage里的信息
 */
function clearStorage() {
	storage.clear();
}

/**
 * 商品图像加载出错时的处理
 * @param img
 * <img width="32" height="32" src="1.jpg" onerror="productErrImg(this)" />
 */
function productErrImg(img) {
    img.src = "../../images/product_default.jpg";
    img.onerror = null;
}

/**
 * 广告图像加载出错时的处理
 * @param img
 * <img width="32" height="32" src="1.jpg" onerror="adErrImg(this)" />
 */
function adErrImg(img) {
    img.src = "../../images/ad_default.jpg";
    img.onerror = null;
}

/**
 * 防止2次点击
 * @returns {Boolean}
 */
function preventRealClick() {
	if (ghostClickTime1 == null) {
		ghostClickTime1 = new Date().getTime();
    } else {
        var ghostClickTime2 = new Date().getTime();
        if(ghostClickTime2 - ghostClickTime1 < ghostClickTimeout) {
        	ghostClickTime1 = ghostClickTime2;
            return false;
        } else {
        	ghostClickTime1 = ghostClickTime2;
        }
    }
	return true;
}

/**
 * 根据设备加载js
 */
function loadJs(){
	var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    if(isAndroid){
        $("#platJs").attr("src","../../js/lib/cordova-android.js");
    }else{
    	$("#platJs").attr("src","../../js/lib/cordova-ios.js");
    }

    document.addEventListener('deviceready', function(){
        //console.log('loading complete');
    }, false);
}

/**
 * ajax请求方法
 * @param url
 * @param data
 * @param type
 * @param async
 * @param cache
 * @param dataType
 */
function ajaxCommon(url,data,type,async,cache,dataType){
	var resultJson;
	if(!data) data = {};
	if(!type) type = "POST";
	if(!async) async = false;
	if(!cache) cache = false;
	if(!dataType) dataType = "json";
	$.ajax({
        type: type,
        url: url,
        data: data,
        dataType: dataType,
        async: async,
        cache: cache,
        beforeSend: function() {
        	//$(".masker").remove();
        	//$("body").append("<div class='masker'><img src='../../images/loading.png' class='loading'/></div>");
        },
        success: function(result) {
        	//$(".masker").hide();
        	resultJson = result;
        },
        error: function() {
        	console.log('resultJson Error');
        },
        complete: function() {
        	//$(".masker").hide();
        }
	});
	return resultJson;
}

/**
 * 输出通用Alert信息
 * @param msg
 */
function showAlertMsg(msg, url) {
	$("#popup-simple").remove();
	var sContent = '<section id="popup-simple">';
	sContent += '<div><p>';
	sContent += msg;
	sContent += '</p></div>';
	sContent += '</section>';
	$("body").append(sContent);

	var headerHeight = parseInt($("header").height());
	$("#popup-simple").css("top", headerHeight + "px")
	$("#popup-simple").height((parseInt(windowHeight)-headerHeight) + "px");
	$("#popup-simple").show();
	$("#popup-simple").on("touchend", function(e) {
		$(this).hide();
		e.preventDefault();
		if(url != null && url != "") {
			window.location.href = url;
		}
	});
	setTimeout(function() {
		$("#popup-simple").hide();
		if(url != null && url != "") {
			window.location.href = url;
		}
	}, 3000);
}

/**
 * 输出XXXX年XX月XX日
 * @param data
 */
function formatData(data,len){
	if(data != null && data != ""){
		var year = data.split("-")[0];
		var month = data.split("-")[1];
		var day = data.split("-")[2];
		if(len == "4"){
			return year + "年" + month + "月" + day +"日";
		}else if(len == "2"){
			return month + "月" + day + "日";
		}
	}else{
		return "";
	}
}

/**
 * 返回localStorage中相同sku商品的个数
 * @param goodsId
 * @param skuId
 * @param goodscount
 */
function uniqueStorage(goodsId,skuId,goodscount){
	var goodscount = parseInt(goodscount);
	if(storage.getItem(productPrefix+goodsId+"_"+skuId) != null) {
		var proVal = $.parseJSON(storage.getItem(productPrefix+goodsId+"_"+skuId));
		if(proVal.tGoodSkuInfoId === skuId){
			goodscount +=  parseInt(proVal.goodscount);
		}
	}
	return goodscount;
}
/**
 * 返回批发进货商品列表中相同goodsid的数量
 * @param goodsId
 */
function goodsStorage(goodsId){
	var goodscount = 0;
	for(var i = 0; i < storage.length; i ++){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			if(goodsId == proVal.goodsId){
				goodscount += parseInt(proVal.goodscount);
			}
		}
	}
	return goodscount;
}

/**
 * 返回localStorage中商品的数量
 */
function getGoodsCount(){
	var goodscount = 0;
	for(var i = 0; i < storage.length; i ++){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			goodscount += parseInt(proVal.goodscount);
		}
	}
	return goodscount;
}

/**
 * 清空购物车localStorage
 */
function clearCart(){
	var i = 0;
	while(storage.key(i) != null) {
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0) {
			storage.removeItem(key);
		} else {
			i ++;
		}
	}
}

/**
 * 将resultJson中的null值转化为空字符串
 * @param resultJson 带转化的JSON对象
 * @returns 去掉null后的JSON对象
 */
function convertNullToEmpty(resultJson) {
	var result = JSON.stringify(resultJson);
	//result = result.replace("null", "");
	result = result.replace(/null/g, "\"\"");
	return $.parseJSON(result);
}

/**
 * 返回订单类型--批发订单||零售订单
 * @param channelcode
 * @returns {String}
 */
function getChannelcodeDesc(channelcode) {
	var desc = "";
	if(channelcode == channelcodePF) {
		desc = "批发订单";
	} else {
		desc = "零售订单";
	}
	return desc;
}

/**
 * 返回订单状态--99:未发货; 100:发货中; 101:部分发货; 102:已发货; 103:部分签收; 104:已签收; 216:已拒收; 217:物流异常
 * @param state
 * @returns {String}
 */
function getOrderStateDesc(state) {
	var desc = "";
	switch(state) {
		case stateWFH:
			desc = "未发货";
			break;
		case stateFHZ:
			desc = "发货中";
			break;
		case stateBFFH:
			desc = "部分发货";
			break;
		case stateYFH:
			desc = "已发货";
			break;
		case stateBFQS:
			desc = "部分签收";
			break;
		case stateYQS:
			desc = "已签收";
			break;
		case stateYJS:
			desc = "已拒收";
			break;
		case stateWLYC:
			desc = "物流异常";
			break;
	}
	return desc;
}

/**
 * 返回来源渠道--93:手机商城; 94:微信商城; 95:开店宝; 96:PC商城; 97:其他; 334:收款宝; 357:wap商城; 466:身边app-2B; 467:身边app-2C
 * @param source
 * @returns {String}
 */
function getOrderSourceDesc(source) {
	var desc = "";
	switch(source) {
		case sourcePhone:
			desc = "手机商城";
			break;
		case sourceWebchat:
			desc = "微信商城";
			break;
		case sourceKdb:
			desc = "开店宝";
			break;
		case sourcePC:
			desc = "PC商城";
			break;
		case sourceOther:
			desc = "其他";
			break;
		case sourceSkb:
			desc = "收款宝";
			break;
		case sourceWap:
			desc = "wap商城";
			break;
		case sourceSbApp2B:
			desc = "身边小店商户";
			break;
		case sourceSbApp2C:
			desc = "身边小店";
			break;
	}
	return desc;
}

/**
 * 返回配送方式--86:快递到店; 87:快递到家
 * @param devicetype
 * @returns {String}
 */
function getDevicetypeDesc(devicetype) {
	var desc = "";
	switch(devicetype) {
		case devicetypeShop:
			desc = "到店自提";
			break;
		case devicetypeHome:
			desc = "送货上门";
			break;
	}
	return desc;
}

/**
 * 返回付款方式--237:拉卡拉; 238:银行; 239:支付宝; 240:货到付款; 241:其他; 432:微信支付;
 * @param paychanel
 * @returns {String}
 */
function getPaychanelDesc(paychanel) {
	var desc = "";
	switch(paychanel) {
		case paychannelLkl:
			desc = "拉卡拉";
			break;
		case paychanelBnk:
			desc = "银行";
			break;
		case paychanelZfb:
			desc = "支付宝";
			break;
		case paychanelCod:
			desc = "货到付款";
			break;
		case paychanelOther:
			desc = "其他";
			break;
		case paychanelWx:
			desc = "微信支付";
			break;
		case paychanelSkb:
			desc = "收款宝支付";
			break;
		case paychannelLklCard:
			desc = "拉卡拉刷卡支付";
			break;	
	}
	return desc;
}

/**
 返回付款方式--237:拉卡拉快捷/钱包支付; 239:支付宝; 432:微信支付; 490:拉卡拉刷卡支付
 * @param paychanel
 * @returns {String}
 */
function getPayDesc(paychanel){
	var desc = "";
	switch(paychanel) {
		case paychannelLkl:
			desc = "拉卡拉快捷支付";
			break;
		case paychannelLklWallet:
			desc = "拉卡拉钱包支付";
			break;
		case paychannelLklCard:
			desc = "拉卡拉刷卡支付";
			break;
		case paychanelWx:
			desc = "微信支付";
			break;
		case paychanelZfb:
			desc = "支付宝";
			break;
	}
	return desc;
}

/**
 * 返回是否支付-150:是; 149:否
 * @param ispay
 * @returns {String}
 */
function getIspayDesc(ispay) {
	var desc = "";
	switch(ispay) {
		case ispayY:
			desc = "已付款";
			break;
		case ispayN:
			desc = "未付款";
			break;
	}
	return desc;
}

/**
 * 返回售后状态-正常订单:116; 换货中:117; 已换货:118; 退货中:119; 已退货:120
 * @param invalidstate
 * @returns {String}
 */
function getInvalidstateDesc(invalidstate) {
	var desc = "";
	switch(invalidstate) {
		case invalidstateNormal:
			desc = "正常订单";
			break;
		case invalidstateHHZ:
			desc = "换货中";
			break;
		case invalidstateYHH:
			desc = "已换货";
			break;
		case invalidstateTHZ:
			desc = "退货中";
			break;
		case invalidstateYTH:
			desc = "已退货";
			break;
	}
	return desc;
}
/**
 * 售后审核状态   退货待审核298、退货审核不通过299、待退货300、已退货301、退款待审核303、退款审核不通过304、待退款305、已退款306、取消订单309、退还快递费347、货款348
 * @param returntstate
 * @returns {String}
 */
function getReturntState(returntstate) {
	switch(returntstate) {
		case returntstateTksqsp:
			returntstate = "售后退款申请审批";
			break;
		case returntstateThdsh:
			returntstate = "退货待审核";
			break;
		case returntstateThshbtg:
			returntstate = "退货审核不通过";
			break;
		case returntstateDth:
			returntstate = "待退货";
			break;
		case returntstateYth:
			returntstate = "已退货";
			break;
		case returntstateTkdsh:
			returntstate = "退款待审核";
			break;
		case returntstateTkshbtg:
			returntstate = "退款审核不通过";
			break;
		case returntstateDtk:
			returntstate = "待退款";
			break;	
		case returntstateYtk:
			returntstate = "已退款";
			break;
		case returntstateQxdd:
			returntstate = "取消订单";
			break;
		case returntstateThkdf:
			returntstate = "退还快递费";
			break;	
		case returntstateHk:
			returntstate = "货款";
			break;
	}
	return returntstate;
}
/**
 * 店铺类型，夫妻便利店-542、标超-543、烟酒店-544、药店-545、通讯店-546、饭店-547、网吧-548、其他个体经营-549
 * @param type
 * @returns {String}
 */
function getReturntShopType(type) {
	var type = parseInt(type);
	switch(type) {
		case ShopTypeFQBLD:
			type = "夫妻便利店";
			break;
		case ShopTypeBD:
			type = "标超";
			break;
		case ShopTypeYJD:
			type = "烟酒店";
			break;
		case ShopTypeYD:
			type = "药店";
			break;
		case ShopTypeTXD:
			type = "通讯店";
			break;
		case ShopTypeFD:
			type = "饭店";
			break;
		case ShopTypeWB:
			type = "网吧";
			break;
		case ShopTypeQT:
			type = "其他个体经营";
			break;	
	}
	return type;
}
/**
 * 校验优惠券返回
 * @param couponuse
 * @returns
 */
function getCouponuse(couponuse){
	var state = "";
	switch(couponuse) {
		case 0:
			state = msgCouponsNotForPurchaseProduct;
			break;
		case 1:
			state = msgCouponsNotForProduct;
			break;
		case 2:
			state = msgCouponsOverPrice;
			break;
		case 3:
			state = msgCouponsUsed;
		case 4:
			state = msgCouponsNotForPhone;
			break;
		case 5:
			state = msgCouponsNotExist;
			break;
	}
	return state;
}

/**
 * 点击查看物流配送单页面
 * @param logno 物流单号
 * @param state 订单状态--99:未发货; 100:发货中; 101:部分发货; 102:已发货; 103:部分签收; 104:已签收; 216:已拒收; 217:物流异常 
 * @returns
 */
function directDeliveryHtml(logno,state,orderitemsid){
	if(logno != '' && logno != undefined){
		location.href = 'deliverystate.html?logno='+logno+'&state='+state+'&orderitemsid='+orderitemsid+'&t='+t;
	}else{
		showAlertMsg(msgNoDelivery);
	}
}

/**
 * 客户端回调的返回函数
 */
function clientBack() {
	/*
	var url = window.location.href;
	var forbiddenFlag = false;

	for(var i = 0; i < nonBackUrl.length; i ++) {
		if(url.indexOf(nonBackUrl[i]) >= 0) {
			forbiddenFlag = true;
		}
	}
	if(forbiddenFlag) {
		return;
	} else {
		window.history.go(-1);
	}
	*/
}


/**
格式化金额为小数点两位
如：如11->11.00;11.223->11.22
*/
function formatNumber(val) {
	if(val==null || val=='') {
		return '0.00';
	} else if(!val||typeof(Number(val))!='number') {
		return null;
	} else {
		return  Number(val).toFixed(2);
	} 
}

/**
 * 获取默认收货地址、支付方式和配送方式
 */
function shipAddress(psam) {
	var psam = psam || storage.getItem("psam");
	var data = {"psam":psam};
	var resultJson = ajaxCommon(urlShopInfo, data);
	//resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		$("#shopname").html("").html(_ReturnData.shopname);
		var shipaddress = {
			"shopId": _ReturnData.id,
			"shopname": _ReturnData.shopname,
		    "shipname": _ReturnData.shoppername,
		    "shipmobile": _ReturnData.phone,
		    "shipprovinceid": _ReturnData.provinceCode,
		    "shipprovince": _ReturnData.province,
		    "shipcityid": _ReturnData.cityCode,
		    "shipcity": _ReturnData.city,
		    "shipareaid": _ReturnData.cityareaCode,
		    "shiparea": _ReturnData.cityarea,
		    "shipaddr": _ReturnData.address
		};
		storage.setItem("shopId", _ReturnData.id);  //小店ID存放到storage中
		storage.setItem("shipaddress",JSON.stringify(shipaddress));

		var homeDeliver = _ReturnData.homeDeliver;  //是否支持送货到家(0：否； 1：是)
		var isPickup = _ReturnData.is_pickup;  //是否支持到店自提(0：否； 1：是)
		var isHomepay = _ReturnData.is_homepay;  //是否货到付款 (0：否； 1：是)
		var isOnehour = _ReturnData.is_onehour;  //是否支持1小时送货(0：否； 1：是)
		storage.setItem("homeDeliver", homeDeliver);
		storage.setItem("isPickup", isPickup);
		storage.setItem("isHomepay", isHomepay);
		storage.setItem("isOnehour", isOnehour);
	}else{
		//接口返回错误
	}
}

/**
 * 处理点击的效果
 * @param obj
 */
function eleFocusEvnt(obj) {
	$(obj).live("touchstart", function(e) {
		$(this).addClass("focus");
	});
	$(obj).live("touchend", function(e) {
		$(this).removeClass("focus");
	});
}
function eleFocusEvnt(obj,name) {
	var name = name || 'focus';
	$(obj).live("touchstart", function(e) {
		$(this).addClass(name);
	});
	$(obj).live("touchend", function(e) {
		$(this).removeClass(name);
	});
}

$(function(){
	//注册FastClick
	if(typeof(FastClick) != "undefined") {
		FastClick.attach(document.body);
	}

	eleFocusEvnt(".btn-red");
	eleFocusEvnt(".btn-white");
	eleFocusEvnt(".btn-gray");
	eleFocusEvnt("#to-pay");
	eleFocusEvnt(".jia");
	eleFocusEvnt(".jian");
	eleFocusEvnt("#pro-panic");
	eleFocusEvnt(".fill-minus","b-gray");
	eleFocusEvnt(".fill-plus","b-gray");
	eleFocusEvnt(".fill-close");
	eleFocusEvnt(".go-again");
	eleFocusEvnt(".gopay");
	eleFocusEvnt(".receipt-actual a");
	eleFocusEvnt("#pro-jia","b-gray");
	eleFocusEvnt("#pro-jian","b-gray");

	//清除浏览器缓存
	clearBrowserCache();
	//清除js缓存
	//clearAppCache();
});

/**
 * 获取加密所需参数--modulus和exponent
 */
function getSecurityEM() {
	var data = {};
	var resultJson = ajaxCommon(urlGetem, data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode == returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		var modulus = _ReturnData.modulus;
		var exponent = _ReturnData.exponent;
		storage.setItem("modulus", modulus);
		storage.setItem("exponent", exponent);
	}
}

/**
 * 对pwd进行加密
 * @param pwd
 */
function pwdEncrpt(pwd) {
	var modulus = storage.getItem("modulus");
	var exponent = storage.getItem("exponent");
	var key = RSAUtils.getKeyPair(exponent, '', modulus);
	var encryptedPwd = RSAUtils.encryptedString(key, pwd);
	return encryptedPwd;
}
/**
 * 判断当前订单来源
 * @param source
 */
function orderSource(source) {
	var orderBoolean = false;
	for(var i = 0; i < sourceArry.length; i ++) {
		if(sourceArry[i] == source) {
			orderBoolean = true;
		}
	}
	return orderBoolean;
}

/**
 * 清除浏览器缓存--添加时间戳参数
 */
function clearBrowserCache() {
	$("a").live("click", function(e) {
		var thisUrl = $(this).attr("href");
		var newUrl = "";
		if(thisUrl.indexOf("javascript:")<0 && thisUrl.indexOf("t=")<0) {
			if(thisUrl.indexOf("?") >= 0) {
				newUrl = thisUrl + "&t=" + t;
			} else {
				if (thisUrl.indexOf("#protocol") >= 0) {
					newUrl = thisUrl;
				} else {
					newUrl = thisUrl + "?t=" + t;
				}
			}
			$(this).attr("href", newUrl);
		}
	});
}

/**
 * 清除js缓存--添加时间戳参数
 */
function clearAppCache() {
	var jsArray = ["zepto.min.js","security.min.js","setting.js","cordova_plugins.js","iscroll.min.js"];
	$("script").each(function() {
		var jsSrc = $(this).attr("src");
		var js = jsSrc.substring(jsSrc.lastIndexOf("/")+1,jsSrc.length);
		if(js != "" && (js.indexOf("?") < 0) && (jsArray.indexOf(js) < 0)) {
			$(this).attr("src",jsSrc + "?t=" + t);
		}
	});
	var css = $("link").attr("href");
	$("link").attr("href",css + "?t=" + t);
}

/**
 * 身份证号格式校验
 */
function CheckID(phoneidcard) {
	/*phoneidcard = $.trim($("#phoneidcard input").val());*/
	var reg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	//计算校验位
    var idcard_array = new Array();
    idcard_array = phoneidcard.split("");
    var S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
		+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
		+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
		+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
		+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
		+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
		+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
		+ parseInt(idcard_array[7]) * 1 
		+ parseInt(idcard_array[8]) * 6
		+ parseInt(idcard_array[9]) * 3 ;
    var Y = S % 11;
    var M = "F";
    var JYM = "10X98765432";
    M = JYM.substr(Y,1);//判断校验位
	if(phoneidcard == ""){
		showAlertMsg(msgId_noEmpty);
		return false;
	} else if(phoneidcard.length != 18 || !reg.test(phoneidcard) || M != idcard_array[17]){
		showAlertMsg(msgId_NotLegal);
		return false;
	} else {
		return true;
	}
}