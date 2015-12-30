var w1280 = 1280;
var w1024 = 1024;
var w800 = 800;
/**
 * 图片轮播--slidesjs
 * @param iObj
 * @param iWidth
 * @param iHeight
 * @param isNav
 * @param isPag
 * @param isAuto
 * @return
 */
function slideImgPlay(iObj, iWidth, iHeight, isNav, isPag, isAuto) {
	iObj.slidesjs({
		width: iWidth,
		height: iHeight,
		navigation: {
			active: isNav,
			effect: "slide"
		},
		pagination: {
			active: isPag,	
		    effect: "slide"
		},
		play: {
			active: false,
			effect: "slide",
			auto: isAuto,
			interval: 4000,
			swap: false,
			pauseOnHover: true,
			restartDelay: 2500
		}
	});
}

/**
 * 图片轮播--touchSlider
 * @param iObj
 * @return
 */
function touchSliderImgPlay(iObj) {
	var $dragBln = false;
	iObj.touchSlider({
		flexible : true,
		speed : 300,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".pagination a"),
		counter : function (e) {
			$(".pagination a").removeClass("active").eq(e.current-1).addClass("active");
		}
	});
	iObj.bind("mousedown", function() {
		$dragBln = false;
	});
	iObj.bind("dragstart", function() {
		$dragBln = true;
	});
	iObj.click(function(){
		if($dragBln) {
			return false;
		}
	});
	var timer = setInterval(function(){
		$("#btn_next").click();
	}, 4000);
	iObj.hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		}, 4000);
	});
	iObj.bind("touchstart",function(){
		clearInterval(timer);
	}).bind("touchend", function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		}, 4000);
	});
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
			var arrayParam = arrayUrl[i].split("=");
			if(arrayParam[0] == sName) {
				return unescape(arrayParam[1]);
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
 * 获取时间戳
 * @return
 */
function getTimeStamp() {
	var nowDate = new Date();
	var t = nowDate.getTime();
	return t;
}