var page = 1;
var pageSize = 10;
var hasNextPage = true;
var objcatid = "all";
/*初始化iScroll*/
var leftScroll;
function scrollLeft() {
	leftScroll = new iScroll("plat-left", {hScrollbar:false, vScrollbar:false});
}	
/*根据浏览器的高度计算iScroll的高度以及相应的块高度*/
$(function() {
	var sHeight = storage.getItem("windowHeight");
	$("#plat-left").css("height", sHeight-$("header").height()-5);
	$('#plat-content').css("height", sHeight-$("header").height()-5);
	
});
/*收益商品左侧菜单*/
$(function() {
	scrollLeft();
	$("#plat-left a").live("tap", function() {
		$("#plat-content ul").html("");
		page = 1;
		hasNextPage = true;
		$("#plat-left").find("a").removeClass("bgwhite").find("i").removeClass("red-icon"); //去掉所有选中样式
		$(this).addClass("bgwhite").find("i").addClass("red-icon"); //添加当前选中样式

		if($(this).siblings("ul").hasClass("hidden")) {
			$(this).siblings("ul").removeClass("hidden");
			leftScroll.refresh();
		} else {
			$(this).siblings("ul").addClass("hidden");
			$(this).parent().find("ul").addClass("hidden");
			if($(this).siblings("ul").length != 0) {
				$(this).removeClass("bgwhite").find("i").removeClass("red-icon"); //去掉当前选中样式
			}
			leftScroll.refresh();
		}
		$(this).parent().siblings("li").find("a").siblings("ul").addClass("hidden"); //同级隐藏
		objcatid = $(this).attr("catid");
		getSideshopGoodsList(objcatid);
	});
	leftScroll.refresh();
});

$(function() {
	scrollRight();
	//获取商品预期收益左侧菜单
	getLeftCatalogs();

	//根据虚拟分类ID获得商品预期收益列表
	getSideshopGoodsList("all");
});

/**
 * 获取商品预期收益左侧菜单
 */
function getLeftCatalogs() {
	var container = $("#plat-left ul");
	var content = "";

	var data = {
			"psam":psam,
			"channelid":channelcode2C,
			"token":userToken,
			"mobile":mobile
	};
	var resultJson = ajaxCommon(urlWholesaleLeft, data);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		if(_returnData.length > 0) {
			for(var i = 0; i < _returnData.length; i ++) {
				var firstCate = _returnData[i]; //一级虚拟分类
				var firstId = firstCate.virtualCateId; //一级虚拟分类ID
				var firstDisc = firstCate.virtualCateDisc; //一级虚拟分类名称
				content += "<li>";
				content += "<a href='javascript:;' catid=" + firstId + ">" + firstDisc + "<i></i></a>";
				content += "<ul class='plat-menu2 hidden'>";
				if(firstCate.child != null) {
					for(var j = 0; j < firstCate.child.length; j ++) {
						var secondCate = (firstCate.child)[j]; //二级虚拟分类
						var secondId = secondCate.virtualCateId; //二级虚拟分类ID
						var secondDisc = secondCate.virtualCateDisc; //二级虚拟分类名称
						content += "<li>";
						content += "<a href='javascript:;' catid=" + secondId + ">" + secondDisc + "<i></i></a>";
						content += "<ul class='plat-menu3 hidden'>";
						if(secondCate.child != null) {
							for(var k = 0; k < secondCate.child.length; k ++){
								var thirdCate = (secondCate.child)[k]; //三级虚拟分类
								var thirdId = thirdCate.virtualCateId; //三级虚拟分类ID
								var thirdDisc = thirdCate.virtualCateDisc; //三级虚拟分类名称
								content += "<li>";
								content += "<a href='javascript:;' catid=" + thirdId + ">" + thirdDisc + "<i></i></a>";
								content += "<ul class='plat-menu4 hidden'>";
								if(thirdCate.child != null) {
									for(var l = 0; l < thirdCate.child.length; l ++){
										var fourthCate = (thirdCate.child)[l]; //四级虚拟分类
										var fourthId = fourthCate.virtualCateId; //四级虚拟分类ID
										var fourthDisc = fourthCate.virtualCateDisc; //四级虚拟分类名称
										content += "<li>";
										content += "<a href='javascript:;' catid=" + fourthId + ">" + fourthDisc + "<i></i></a>";
										content += "</li>";
									}
								}
								content += "</ul>";
								content += "</li>";
							}
						}
						content += "</ul>";
						content += "</li>";
					}
				}
				content += "</ul>";
				content += "</li>";
				container.html("").append(content);
				leftScroll.refresh();
			}
		} else {
			//无左侧分类
		}
	} else {
		//接口返回错误
	}
}

var rightScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset;
//初始化iScroll控件 
function scrollRight() {
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	rightScroll = new iScroll('plat-content', {//iScroll的对象
		hScrollbar : false,
		vScrollbar : false,
		useTransition: false, 
		checkDOMChanges:false,
		topOffset: pullDownOffset,
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'hidden';
				getSideshopGoodsList(objcatid);	// 执行加载后的自定义功能	
			}
		}
	});
}

document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(scrollRight, 200); }, false);
/**
 * 根据虚拟分类ID获得商品预期收益列表
 * @param virtualcatid
 */
function getSideshopGoodsList(objcatid) {
	var virtualcatid = objcatid;
	if(hasNextPage){
		var data = {
				"psam":psam,
				"channelid":channelcode2C,
				"virtualcatid":virtualcatid,
				"searchparm":"",
				"type":"1",
				"page":page,
				"pageSize":pageSize
		};
		var resultJson = ajaxCommon(urlSideshopGoodsList, data);
		resultJson = convertNullToEmpty(resultJson);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			$(".none-data").hide();
			var _ReturnData = resultJson._ReturnData;
			var goodList = _ReturnData.list;
			if(typeof(goodList) != "undefined" && goodList.length > 0) {
				var result = "";
				for(var i = 0; i < goodList.length; i ++) {
					var tgoodinfoid = goodList[i].tgoodinfoid;
					var goodbigpic = goodList[i].goodbigpic.split(";")[0];
					var goodbigpicUrl = urlImage + "/" + tgoodinfoid + "/" + imgw100 + "/" + goodbigpic;
					var goodname = goodList[i].goodname;
					var saleprice_o2o = goodList[i].saleprice_o2o;
					var marketprice = goodList[i].marketprice;
					var yqsy = goodList[i].yqsy;  //商品分润
					result += "<li>";
					result += "<dl>";
					result += "<dt><img src='" + goodbigpicUrl + "' onerror='productErrImg(this);'/></dt>";
					result += "<dd>";
					result += "<h3>" + goodname + "</h3>";
					result += "<div class='price'><span>¥" + saleprice_o2o + "</span><del>¥" + marketprice + "</del></div>";
					result += "<div class='profit'>商品分润：<span>" + yqsy + "</span></div>";
					result += "</dd>";
					result += "</dl>";
					result += "</li>";
				}
				$("#plat-content ul").append(result);
				rightScroll.refresh();
			} else {
				hasNextPage = false;
			}
		} else if(page == 1) {
			//该分类下暂无商品
			showNoDataMsg(msgNoPlatformGoods);
			//接口返回错误
		}
		page++;
	}
}