/*初始化iScroll*/
var addgoodsScroll;
function scrollAddGoods() {
	addgoodsScroll = new iScroll("menu-left",{hScrollbar:false, vScrollbar:false});
}

/*批发进货首页菜单*/
//以此id获取右侧商品
var objid = "";//虚拟分类ID
$(function() {
	scrollAddGoods();
	$("#menu-left a").live("tap", function() {
		$(".add-content ul").html("");
		$(".pull-Up-Label").text("");
		page = 0;//每次点击从第一个商品开始
		hasNextPage = true;
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
		/*if(objid == "shop") {
			getLatestWholesale();// 执行加载后的自定义功能
		} else {*/
			laststage(objid);
//		}
		scrollGood(objid);
		//记录所选级数
		session.setItem("objid",objid);
        session.setItem("level",$(this).attr("level"));
	});
	addgoodsScroll.refresh();
});

$(function() {
	scrollGood();
	//小店管理获取添加商品左侧分类
	//getLeftCatas();
	//本店近期批发
	//getLatestWholesale();
	//获取右侧商品信息列表
	//laststage(objid);

	var url = window.location.href;
	var back = getParamValueByName(url, "back");
	var level = session.getItem("level") || "first";
	if(back == "index") {
		//商品详情返回列表页
		objid = session.getItem("objid");
		if(objid != null) {
			getLeftCatas(objid);
			//显示被选中的分类的父级分类
			if(level == "fourth"){
		         $(".add-menu1 li").find(".bgwhite").parent().parent().removeClass("hidden");
		         $(".add-menu1 li").find(".bgwhite").parent().parent().parent().parent().removeClass("hidden");
		         $(".add-menu1 li").find(".bgwhite").parent().parent().parent().parent().parent().parent().removeClass("hidden");
			}else if(level == "third"){
		         $(".add-menu1 li").find(".bgwhite").parent().parent().removeClass("hidden");
		         $(".add-menu1 li").find(".bgwhite").parent().parent().parent().parent().removeClass("hidden");
			}else if(level == "second"){
				$(".add-menu1 li").find(".bgwhite").parent().parent().removeClass("hidden");
			}
		}else {
			getLeftCatas("");
		}
	}else {
		if(objid != "") {
			//广告链接到分类
			if(storage.getItem(hasLogined) == null || storage.getItem(hasLogined) == "" || storage.getItem(hasLogined) == "0") {
				//没有登录时，跳转到登录页面
				window.location.href = "../accounts/login.html?source=" + source + "&refer=" + url + "&t=" + t;
			}else {
				session.setItem("objid",objid);
				session.setItem("level",level);
				getLeftCatas(objid);
			}
		}else {
			getLeftCatas("");
		}
	}
	
})

/**
 * 本店近期批发
 */
/*function getLatestWholesale() {
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
		$(".none-data").hide();
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
*/
/* 小店管理获取添加商品左侧分类 */
function getLeftCatas(objid) {
	var container = $(".add-menu1");
	var content = "";
	var data = {"psam": psam};
	var resultJson = ajaxCommon(urlgoodsqueryvirtualcat, data);
	resultJson = convertNullToEmpty(resultJson);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		if(_returnData.length > 0) {
			if(objid == ""){
				objid = _returnData[0].tVirtualCateId;
	            session.setItem("objid",objid);
	            session.setItem("level","first");
	        }
			for(var i = 0,maxi = _returnData.length; i < maxi; i ++){
				var firstCate = _returnData[i];//一级虚拟分类
				var firstfather = firstCate.fatherVirtualCateId//一级虚拟分类父ID
				var firstId = firstCate.tVirtualCateId;//一级虚拟分类ID
				var firstDisc = firstCate.virtualCateDisc;//一级虚拟分类名称
				if(firstfather == 0){
					content += "<li>";
					if (objid == firstId){
						content += "<a href='javascript:;' class='bgwhite' level='first' catid=" +firstId+ ">" +firstDisc+ "<i class='red-icon'></i></a>";	
					} else {
						content += "<a href='javascript:;' level='first' catid=" +firstId+ ">" +firstDisc+ "<i></i></a>";	
					}
					content += "<ul class='add-menu2 hidden'>";
					for(var j = 0,maxj = _returnData.length; j < maxj; j ++){
						var secondCate = _returnData[j];//二级虚拟分类
						var secondfather = secondCate.fatherVirtualCateId//二级虚拟分类父ID
						var secondId = secondCate.tVirtualCateId;//二级虚拟分类ID
						var secondDisc = secondCate.virtualCateDisc;//二级虚拟分类名称
						if(secondfather == firstId){
							content += "<li>";
							if(objid == secondId){
								content += "<a href='javascript:;' class='bgwhite' level='second' catid=" +secondId+ ">" +secondDisc+ "<i class='red-icon'></i></a>";
							} else {
								content += "<a href='javascript:;' level='second' catid=" +secondId+ ">" +secondDisc+ "<i></i></a>";
							}
							content += "<ul class='add-menu3 hidden'>";
							for(var k = 0,maxk = _returnData.length; k < maxk; k ++){
								var thirdCate = _returnData[k];//三级虚拟分类
								var thirdfather = thirdCate.fatherVirtualCateId//三级虚拟分类父ID
								var thirdId = thirdCate.tVirtualCateId;//三级虚拟分类ID
								var thirdDisc = thirdCate.virtualCateDisc;//三级虚拟分类名称
								if(thirdfather == secondId){
									content += "<li>";
									if(objid == thirdId){
										content += "<a href='javascript:;' class='bgwhite' level='third' catid=" +thirdId+ ">" +thirdDisc+ "<i class='red-icon'></i></a>";	
									} else {
										content += "<a href='javascript:;' level='third' catid=" +thirdId+ ">" +thirdDisc+ "<i></i></a>";
									}
									/*content += "<ul class='add-menu4 hidden'>";
									for(var l = 0,maxl = _returnData.length; l < maxl; l ++){
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
									content += "</ul>";*/
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
	$("#menu-left").css("height", windowHeight - $("header").height() - 10);
	goodswrap =  windowHeight - $("header").height() - 10;
	$("#add-content").css("height",goodswrap);
	/*$(".add-menu1>li>a").first().addClass("bgwhite").find("i").addClass("red-icon");
	$(".add-menu1>li>a").first().siblings("ul").removeClass("hidden");*/
	 $(".add-menu1 li").find(".bgwhite").siblings("ul").removeClass("hidden");
	laststage(objid);
	addgoodsScroll.refresh();
}

//加载无更多商品
var scroll = "";
function pullUpAction() {
	scroll = setTimeout(function () {
		pullUpHide();
	},3400);
}
//加载无更多商品,2秒隐藏提示
function pullUpHide() {
	setTimeout(function () {
		$(".pull-Up-Label").text('');
		var oldsroll = parseInt($('.sroll').css("top"));
		var sroll = oldsroll + scrollstap;
		if ((-oldsroll) > goodswrap){
			if (scrollstap <= 12) {
				$('.sroll').css('top',sroll);
				scrollstap += 2;
				pullUpHide();
			}
		}
	},36);
}

//根据虚拟id，返回数据,右侧商品
var page = 0;
var pageSize = 50;
var hasNextPage = true;//是否有下一页
var goodScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset;
var scrollstap = 0;//收起提示步长变量
var goodswrap = null;

//初始化iScroll控件 
function scrollGood() {
	pullUpEl = document.getElementById('pull-Up');
	pullUpOffset = pullUpEl.offsetHeight;
	goodScroll = new iScroll('add-content', {//iScroll的对象
		topOffset: pullDownOffset,
		hScrollbar: false, 
		vScrollbar: false,
		hScroll: false,
		checkDOMChanges: true,
		useTransition: false,
		useTransform: false,
		topOffset: pullDownOffset,
		onScrollStart: function () {
			goodScroll.refresh();
        },
        onRefresh: function () {
    		var maxy = -parseInt(this.maxScrollY);
            if (goodswrap > maxy) {
            	$('.pull-Up-Label').text('');            	
            }
    	},
		onScrollMove: function () {
			clearTimeout(scroll);
			if (this.y < (this.maxScrollY - 2) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pull-Up-Label').innerHTML = '松手开始更新...';
				this.maxScrollY = this.maxScrollY;
			}
		},
		onScrollEnd: function () {
			/*if(getParamValueByName(window.location.href, "back") == "index"){
				cat_Id = session.getItem("objid");
			}*/
			if (pullUpEl.className.match('flip')) {
        		scrollstap = 0;
        		pullUpEl.className = 'loadon';
				pullUpEl.querySelector('.pull-Up-Label').innerHTML = '玩命加载中...';	
				laststage(objid);// 执行加载后的自定义功能
	            goodScroll.refresh();
			}
		}
	});
}

function laststage(objid) {
	var laststagevirtual = $(".add-content ul");
	var laststageret = "";
	var timeout = 0;
	if (page != 0) {
		timeout = 1000;
	}
	setTimeout(function () {	
        if(hasNextPage){
        	var level = session.getItem("level") || "first";
			page ++;
			var data = {
				"token":userToken,
				"tVirtualCateId":objid,
				"page":page,
				"pageSize":pageSize,
				"psam":psam
			};
			var resultJson = ajaxCommon(urlgoodspoollist, data);
			resultJson = convertNullToEmpty(resultJson);
			if (resultJson._ReturnCode === returnCodeSuccess) {
				var lastresult = resultJson._ReturnData;
				if(lastresult.length > 0) {
					$(".none-data").hide();
					for(var p = 0,maxp = lastresult.length; p < maxp; p++) {
						var tgoodinfopoolid = lastresult[p].tGoodInfoPoolId;
						var issaleflag = lastresult[p].issaleflag;  //判断产品池产品是否已上架到小店  0-未上架，1-已上架
						var url = "addgoods1.html?tGoodsInfoId=" + tgoodinfopoolid;
						laststageret +=	"<li>";
						laststageret +=		"<dl>";
						laststageret +=			"<dt><img src='" + urlImage + "/" + tgoodinfopoolid + "/100/" + lastresult[p].goodBigPic.split(";")[0] + "' onerror='productErrImg(this);'/></dt>";
						laststageret +=			"<dd>";
						laststageret +=				"<h3>" + lastresult[p].goodName+ "</h3>";
						laststageret +=			"</dd>";
						if(issaleflag == "0") {
							//未上架到小店
							laststageret +=		"<a href='" +url+"' class='added'>上架到小店</a>";
						} else {
							//已上架到小店
							laststageret +=		"<a href='javascript:;' class='added disabled'>商品已上架</a>";
						}
						laststageret +=		"</dl>";
						
						laststageret +=	"</li>";
					}
					laststagevirtual.append(laststageret);
					pullUpEl.querySelector('.pull-Up-Label').innerHTML = '上拉加载更多...';
                    goodScroll.refresh();
				} else if(page == 1){
					showNoDataMsg(msgNoTemplateGoods);//该分类下无模板商品！
				} else {
					hasNextPage = false;
                    pullUpEl.querySelector('.pull-Up-Label').innerHTML = '没有更多商品了！';
                    pullUpAction ();
				}
				
			} else {
				hasNextPage = false;
                pullUpEl.querySelector('.pull-Up-Label').innerHTML = '没有更多商品了！';
                pullUpAction ();
			}
        } else {
        	hasNextPage = false;
            pullUpEl.querySelector('.pull-Up-Label').innerHTML = '没有更多商品了！';
            pullUpAction ();
        }
    },timeout);
	var virtualcatid = session.getItem("objid");
	var level = session.getItem("level");
}


/* 商品搜索*/
$(".head-search").on("touchend",function(e){
	$(".orders-search").removeClass("hidden");
	e.preventDefault();
})

$(".orders-search span").on("touchend",function(e){
	$(".orders-search").addClass("hidden");
	$(".orders-search input").val("");
	e.preventDefault();
})

$(".orders-search div").on("touchend",function(e){
	e.stopPropagation();
})

$(".orders-search").on("touchend",function(e){
	$(".orders-search").addClass("hidden");
	$(".orders-search input").val("");
	e.preventDefault();
})

 //键盘搜索事件
$(".orders-search input").on("keyup",function(e){
	if(e.keyCode == 13){
		var searchparm = $.trim($('.orders-search input').val());
		if(searchparm == ""){
			showAlertMsg(msgProductNameEmpty);
			return;
		}
		$(".orders-search input").val("");
		storage.setItem("searchparm", searchparm);
		window.location.href = encodeURI(encodeURI("searchgoods.html?searchparm=" + searchparm + "&t=" + t));
	}
})

 //点击搜索事件
$(".good-search").on("touchend",function(e){
	$(".orders-search").removeClass("hidden");
	e.preventDefault();
	var searchparm = $.trim($('.orders-search input').val());
	if(searchparm == ""){
		showAlertMsg(msgProductNameEmpty);
		return;
	}
	$(".orders-search input").val("");
	storage.setItem("searchparm", searchparm);
	window.location.href = encodeURI(encodeURI("searchgoods.html?searchparm=" + searchparm + "&t=" + t));
})