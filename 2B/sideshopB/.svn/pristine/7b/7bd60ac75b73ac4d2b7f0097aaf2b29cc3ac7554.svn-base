/*初始化iScroll*/
var addgoodsScroll;
function scrollAddGoods() {
	addgoodsScroll = new iScroll("menu-left",{ hScrollbar: false, vScrollbar: false });
}

/*批发进货首页菜单*/
//以此id获取右侧商品
var objid;
$(function() {
	scrollAddGoods();
	$("#menu-left a").live("tap", function() {
		$(".add-content ul").html("");
		page = 0;//每次点击从第一个商品开始
		$("#menu-left").find("a").removeClass("bgwhite").find("i").removeClass("red-icon"); //去掉所有选中样式
		$(this).addClass("bgwhite").find("i").addClass("red-icon"); //添加当前选中样式
		
		if($(this).siblings("ul").hasClass("hidden")) {
			$(this).siblings("ul").removeClass("hidden");
			addgoodsScroll.refresh();
		} else {
			$(this).siblings("ul").addClass("hidden");
			$(this).parent().find("ul").addClass("hidden");
			if($(this).siblings("ul").length != 0) {
				$(this).removeClass("bgwhite").find("i").removeClass("red-icon"); //去掉当前选中样式
			}
			addgoodsScroll.refresh();
		}
		$(this).parent().siblings("li").find("a").siblings("ul").addClass("hidden"); //同级隐藏
		objid = $(this).attr("catid");
		$(".add-content ul").html("");
		if(objid == "shop") {
			getLatestWholesale();// 执行加载后的自定义功能
		} else {
			laststage(objid);
		}
		scrollGood(objid);
	});
	addgoodsScroll.refresh();
});

$(function() {
	//小店管理获取添加商品左侧分类
	getLeftCatas();
	//本店近期批发
	getLatestWholesale();
})

/**
 * 本店近期批发
 */
function getLatestWholesale() {
	var data = {"psam":psam, "page":1, "pageSize":20};
	var resultJson = ajaxCommon(urlGetGoodslistByPsam, data);
	resultJson = convertNullToEmpty(resultJson);
	//该店铺近期没有批发商品
	if(resultJson._ReturnData.length == 0) {
		$(".promotion").parent().remove();
		//$(".add-menu1 li:eq(1)").find("a").append("<i class='red-icon'></i>").addClass("bgwhite");
		laststage(objid);
	} else {
		var result = getProductsList(resultJson);
		$(".content1").html(result);
	}
}

function getProductsList(resultJson) {
	var result = "";
	var _ReturnData = resultJson._ReturnData;
	for(var i = 0; i < _ReturnData.length; i ++) {
		var tGoodInfoPoolId = _ReturnData[i].tGoodInfoPoolId; //商品池商品id
		var distributionFlag = _ReturnData[i].distributionFlag; //配送标识
		var goodBigPic = _ReturnData[i].goodBigPic; //商品主图
		var goodName = _ReturnData[i].goodName; //商品名称
		result += "<li>";
		result += "<dl>";
		result += "<dt><img src='" + urlImage + "/" + tGoodInfoPoolId + "/" + imgw100 + "/" + goodBigPic + "' onerror='productErrImg(this);' /></dt>";
		result += "<dd>";
		result += "<h3>" + goodName + "</h3>";
		result += "</dd>";
		result += "</dl>";
		result += "<a href='addgoods1.html?tGoodsInfoId=" + tGoodInfoPoolId + "&t=" + t + "' class='added'>上架到小店</a>";
		result += "</li>";
	}

	return result;
}

/* 小店管理获取添加商品左侧分类 */
function getLeftCatas() {
	var container = $(".add-menu1");
	var content = "";
	var data = {};
	var resultJson = ajaxCommon(urlgoodsqueryvirtualcat, data);
	resultJson = convertNullToEmpty(resultJson);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		if(_returnData.length > 0) {
			objid = _returnData[0].tVirtualCateId;
			for(var i = 0; i < _returnData.length; i ++){
				var firstCate = _returnData[i];//一级虚拟分类
				var firstfather = firstCate.fatherVirtualCateId//一级虚拟分类父ID
				var firstId = firstCate.tVirtualCateId;//一级虚拟分类ID
				var firstDisc = firstCate.virtualCateDisc;//一级虚拟分类名称
				if(firstfather == 0){
					content += "<li>";
					content += "<a href='javascript:;' catid=" +firstId+ ">" +firstDisc+ "<i></i></a>";
					content += "<ul class='add-menu2 hidden'>";
					for(var j = 0;j < _returnData.length; j ++){
						var secondCate = _returnData[j];//二级虚拟分类
						var secondfather = secondCate.fatherVirtualCateId//二级虚拟分类父ID
						var secondId = secondCate.tVirtualCateId;//二级虚拟分类ID
						var secondDisc = secondCate.virtualCateDisc;//二级虚拟分类名称
						if(secondfather == firstId){
							content += "<li>";
							content += "<a href='javascript:;' catid=" +secondId+ ">" +secondDisc+ "<i></i></a>";
							content += "<ul class='add-menu3 hidden'>";
							for(var k = 0;k <  _returnData.length; k ++){
								var thirdCate = _returnData[k];//三级虚拟分类
								var thirdfather = thirdCate.fatherVirtualCateId//三级虚拟分类父ID
								var thirdId = thirdCate.tVirtualCateId;//三级虚拟分类ID
								var thirdDisc = thirdCate.virtualCateDisc;//三级虚拟分类名称
								if(thirdfather == secondId){
									content += "<li>";
									content += "<a href='javascript:;' catid=" +thirdId+ ">" +thirdDisc+ "<i></i></a>";
									content += "<ul class='add-menu4 hidden'>";
									for(var l = 0; l < _returnData.length; l ++){
										var fourthCate = _returnData[l];//四级虚拟分类
										var fourthfather = fourthCate.fatherVirtualCateId//四级虚拟分类父ID
										var fourthId = fourthCate.tVirtualCateId;//四级虚拟分类ID
										var fourthDisc = fourthCate.virtualCateDisc;//四级虚拟分类名称
										if(fourthfather == thirdId){
											content += "<li>";
											content += "<a href='javascript:;' catid=" +fourthId+ ">" +fourthDisc+ "<i></i></a>";
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
				}
			}
			container.append(content);
			addgoodsScroll.refresh();
		}else{
			//小店管理获取本店无左侧分类
		}
	}else{
		//接口返回错误
	}
	/*根据浏览器的高度计算iScroll的高度*/
	var sHeight = $(window).height();
	$("#menu-left").css("height", sHeight-87);
	$("#add-content").css("height",sHeight-87);
	
	$(".add-menu1>li>a").first().addClass("bgwhite").find("i").addClass("red-icon");
	$(".add-menu1>li>a").first().siblings("ul").removeClass("hidden");
}

//根据虚拟id，返回数据,右侧商品
var page = 0;
var pageSize = 5;
var hasNextPage = true;
var goodScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset;

//初始化iScroll控件 
function scrollGood() {
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	goodScroll = new iScroll('add-content', {//iScroll的对象
		hScrollbar: false, 
		vScrollbar: false,
		hScroll:false,
		checkDOMChanges: true,
		useTransition: false, 
		topOffset: pullDownOffset,
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'hidden';	
				if(objid != "shop") {
					laststage(objid);// 执行加载后的自定义功能
				}	
			}
		}
	});
}
document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(scrollGood, 200); }, false);

function laststage(objid) {
	var laststagevirtual = $(".add-content ul");
	var laststageret = "";
	page ++;
	var data = {
		"token" : userToken,
		"tVirtualCateId":objid,
		"page":page,
		"pageSize":pageSize
	}
	var resultJson = ajaxCommon(urlgoodspoollist, data);
	resultJson = convertNullToEmpty(resultJson);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var lastresult = resultJson._ReturnData;
		if(lastresult.length > 0) {
			$(".none-data").hide();
			for(var p = 0; p < lastresult.length; p++) {
				var tgoodinfopoolid = lastresult[p].tGoodInfoPoolId
				laststageret +=	"<li>";
				laststageret +=		"<dl>";
				laststageret +=			"<dt><img src='" +urlImage+ "/" +tgoodinfopoolid+ "/" +imgw100+ "/" +lastresult[p].goodBigPic.split(";")[0]+ "' onerror='productErrImg(this);'/></dt>";
				laststageret +=			"<dd>";
				laststageret +=				"<h3>" + lastresult[p].goodName+ "</h3>";
				laststageret +=			"</dd>";
				laststageret +=		"</dl>";
				laststageret +=		"<a href='addgoods1.html?tGoodsInfoId=" + tgoodinfopoolid +"' class='added'>上架到小店</a>";
				laststageret +=	"</li>";
			}
		} else if(page == 1){
			showNoDataMsg(msgNoTemplateGoods);//该分类下无模板商品！
		}
		laststagevirtual.append(laststageret);
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}