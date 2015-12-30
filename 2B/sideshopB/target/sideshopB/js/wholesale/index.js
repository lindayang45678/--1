/* 初始化iScroll*/
var cateScroll;
function scrollCate() {
	cateScroll = new iScroll("menu-l",{ hScrollbar: false, vScrollbar: false });
}
var goodScroll;
function scrollGood(){
	goodScroll = new iScroll("goods-wrap",{ 
		hScrollbar: false, 
		vScrollbar: false,
		hScroll:false,
		checkDOMChanges: true,
		onScrollEnd: function(){
			getGoodsList(catId);
		}
	});
}

//定义购物接收参数
var goodsSkuO2OList = "";
var skuIndex = 0;
var goodName = "";
var goodBigPic = "";
var supplierId = "";
var supplierName = "";
var distributionflag = "";

/* 根据浏览器的高度计算iScroll的高度*/
$(function(){
	$("#menu-l").css("height",windowHeight-$("#wholesale-foot").height()-$("header").height());
	$("#goods-wrap").css("height",windowHeight-$("#wholesale-foot").height()-$("header").height());
	checkCoupon();
});

$(function(){
	storage.removeItem("searchparm");//清除搜索查询
	var use_tip = storage.getItem("use_tips") || "";
	if(use_tip != "1"){
		$("#use-tips").removeClass("hidden");
	}else{
		$("#use-tips").addClass("hidden");
	}
})

/* 用户提示*/
$("#use-tips").on("touchend",function(e){
	storage.setItem("use_tips","1");
	$(this).addClass("hidden");
	e.preventDefault();
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

/* 批发进货左侧分类*/
$(function(){
	var data = {
			"psam":psam,
			"channelid":channelcodePF,
			"token":userToken,
			"mobile":mobile
	};
	var container = $("#menu-l ul");
	var content = "";
	var resultJson = ajaxCommon(urlWholesaleLeft, data);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		if(_returnData.length > 0){
			catId = _returnData[0].virtualCateId;
			for(var i = 0; i< _returnData.length; i ++){
				var firstCate = _returnData[i];//一级虚拟分类
				var firstId = firstCate.virtualCateId;//一级虚拟分类ID
				var firstDisc = firstCate.virtualCateDisc;//一级虚拟分类名称
				content += "<li>";
				content += "<a href='javascript:;' catid=" +firstId+ ">" +firstDisc+ "<i></i></a>";
				content += "<ul class='left-l2 hidden'>";
				if(firstCate.child != null){
					for(var j = 0;j < firstCate.child.length; j ++){
						var secondCate = (firstCate.child)[j];//二级虚拟分类
						var secondId = secondCate.virtualCateId;//二级虚拟分类ID
						var secondDisc = secondCate.virtualCateDisc;//二级虚拟分类名称
						content += "<li>";
						content += "<a href='javascript:;' catid=" +secondId+ ">" +secondDisc+ "<i></i></a>";
						content += "<ul class='left-l3 hidden'>";
						if(secondCate.child != null){
							for(var k = 0;k <  secondCate.child.length; k ++){
								var thirdCate = (secondCate.child)[k];//三级虚拟分类
								var thirdId = thirdCate.virtualCateId;//三级虚拟分类ID
								var thirdDisc = thirdCate.virtualCateDisc;//三级虚拟分类名称
								content += "<li>";
								content += "<a href='javascript:;' catid=" +thirdId+ ">" +thirdDisc+ "<i></i></a>";
								content += "<ul class='left-l4 hidden'>";
								if(thirdCate.child != null){
									for(var l = 0; l < thirdCate.child.length; l ++){
										var fourthCate = (thirdCate.child)[l];//四级虚拟分类
										var fourthId = fourthCate.virtualCateId;//四级虚拟分类ID
										var fourthDisc = fourthCate.virtualCateDisc;//四级虚拟分类名称
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
				container.html("").append(content);
				scrollGood();
			}
			getGoodsList(catId);
		}else{
			//无左侧分类
			//显示暂无批发进货商品图标
			$('.wholesale').hide();
			showNoDataMsg(msgNoWholesale);
		}
	}else{
		//接口返回错误
	}
})

/* 批发进货虚拟分类点击事件*/
$(function(){
	scrollCate();
	$("#menu-l a").live("tap", function() {
		$("#menu-l").find("a").removeClass("white").find("i").removeClass("sj-redr");//去掉所有选中样式
		$(this).addClass("white").find("i").addClass("sj-redr");//添加当前选中样式
		if($(this).siblings("ul").hasClass("hidden")){
			$(this).siblings("ul").removeClass("hidden");
			cateScroll.refresh();
		}else{
			$(this).siblings("ul").addClass("hidden");
			$(this).parent().find("ul").addClass("hidden");
			if($(this).siblings("ul").length != 0){
				$(this).removeClass("white").find("i").removeClass("sj-redr");//去掉当前选中样式
			}
			cateScroll.refresh();
		}
		$(this).parent().siblings("li").find("a").siblings("ul").addClass("hidden");//同级隐藏
		
		if($(this).hasClass('promotion')){	
			$('.wholesale-ad').removeClass('hidden');
			$('.wholesale-goods').addClass('hidden');
		}else{	
			$('.wholesale-ad').addClass('hidden');
			$('.wholesale-goods').removeClass('hidden');
		}
	});
	cateScroll.refresh();
})

var page = 0;
var pageSize = 10;
var hasNextPage = true;//是否有下一页
var catId = "";//虚拟分类ID

/* 点击虚拟分类显示相应商品列表*/
$(function(){
	$("#menu-l a").live("tap", function() {
		if(goodScroll){
			goodScroll.destroy();
		}
		var virtualCateId = $(this).attr("catid");
		catId = virtualCateId;
		page = 0;
		hasNextPage = true;
		$(".wholesale-goods").html("");
		scrollGood();
		getGoodsList(catId);
	});
})

/* 获取批发进货商品列表*/
function getGoodsList(catid){
	var container = $(".wholesale-goods");
	var content = "";
	setTimeout(function () {
		if(hasNextPage){
			page ++;
			console.log("page: "+page);
			var data = {
					"psam":psam,
					"channelid":channelcodePF,
					"virtualcatid":catid,
					"searchparm":"",
					"page":page,
					"pageSize":pageSize
			};
			var resultJson = ajaxCommon(urlWholesaleList, data);
			resultJson = convertNullToEmpty(resultJson);
			if (resultJson._ReturnCode === returnCodeSuccess) {
				var goodList = resultJson._ReturnData.list;
				if(goodList.length > 0){
					goodList = sortGoodsAndAdvert(goodList);//商品排序
					for(var i = 0; i < goodList.length; i ++){
						content += "<li>";
						content += "<a href='prodetail.html?goodsid=" +goodList[i].tgoodinfoid+ "'>";
						content += 	"<img src='" +urlImage+ "/" +goodList[i].tgoodinfoid+ "/" +imgw300+ "/" +goodList[i].goodbigpic.split(";")[0]+ "' onerror='productErrImg(this);'/>";
						if(goodList[i].issoldout == 0){
							content += "<i class='soldout'></i>";
						}
						content += "</a>";
						content += "<h3>" +goodList[i].goodname+ "</h3>";
						content += "<div class='price-box'><i class='salePrice'>¥" +goodList[i].saleprice_o2o+ "</i><del>¥" +goodList[i].marketprice+ "</del></div>";
						if(goodList[i].issoldout == 1){
							content += "<a href='javascript:;' class='jiajian jia' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' store='" +goodList[i].store+ "'>+</a>";
							content += "<a href='javascript:;' class='jiajian jian hidden' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "'>&minus;</a>";
						}
						content += "<em class='number hidden' goodsid='" +goodList[i].tgoodinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "'>0</em>";
						content += "</li>";
					}
					container.append(content);
					refreshCount();
					goodScroll.refresh();
				}else{
					hasNextPage = false;
				}
			}
		}
	},400);
}

/* 遍历localStorage显示每个商品数量*/
function refreshCount(){
	var count = 0;
	for(var i = 0; i < storage.length; i ++){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = JSON.parse(storage.getItem(key));
			$(".number").each(function(index){
				if($(this).attr("goodsid") == proVal.goodsId){
					if($(this).attr("ismoresku") == "0"){
						$(this).text(proVal.goodscount);
					}else{
						count += parseInt(proVal.goodscount);
						$(this).text(count);
					}
					$(this).removeClass("hidden");
					$(this).siblings(".jian").removeClass("hidden");//显示减号
				}
			})
		}
	}
}

/* 点击加号事件*/
$(".jia").live("touchend", function(e){
	$(this).removeClass("focus");

	var goodsid = $(this).attr("goodsid");
	if(!hasMultisku(goodsid)){
		var key = productPrefix+$(this).attr("goodsid")+"_"+$(this).attr("skuid");
		var proVal = $.parseJSON(storage.getItem(key));
		var count = 0;
		if(proVal){
			count = proVal.goodscount;
		}
		if((count+1) > $(this).attr("store")){
			showAlertMsg(msgFailProductStore);//库存不足提示
		}else{
			addCart(1);//加入购物车
			refreshCount();
			if(count+1 > 0){
				$(this).siblings(".jian").removeClass("hidden");
			}
		}
	}else{
		$('#sku').removeClass('hidden');
		$('.masker').removeClass('hidden');
	}

	e.preventDefault();
});

/* 点击减号事件*/
$(".jian").live("touchend",function(e){
	$(this).removeClass("focus");

	var goodsid = $(this).attr("goodsid");
	if(!hasMultisku(goodsid)){
		var key = productPrefix+$(this).attr("goodsid")+"_"+$(this).attr("skuid");
		var proVal = $.parseJSON(storage.getItem(key));
		var count = proVal.goodscount;
		if(count-1 > 0){
			addCart(-1);//加入购物车
			refreshCount();
		}else{
			storage.removeItem(key);
			$(this).siblings(".number").addClass("hidden");
			$(this).addClass("hidden");
			checkCoupon();
		}
	}else{
		var goodscount = goodsSku(goodsid,-1);
		if(goodscount == 0){
			$(this).siblings(".number").addClass("hidden");
			$(this).addClass("hidden");
		}else{
			$(this).siblings(".number").text(goodscount);
		}
	}

	e.preventDefault();
})

/* 同一商品不同sku商品数量减少*/
function goodsSku(goodsid,count){
	var goodscount = 0;
	var skuItems = [];
	for(var i = storage.length-1; i >= 0; i --){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			if(parseInt(proVal.goodsId) == goodsid){
				goodscount += parseInt(proVal.goodscount);
			}
			if(key.indexOf("_" + goodsid + "_") >= 0){
				skuItems.push($.parseJSON(storage.getItem(key)))
			}
		}
	}
	
	//最后一个加入购物车的商品数量递减，如果数量为0，从localStorage中删除此商品
	var skuVal = skuItems[0];
	var skukey = productPrefix+skuVal.goodsId+"_"+skuVal.tGoodSkuInfoId;
	var num = parseInt(skuVal.goodscount)+count;
	if(num == 0){
		storage.removeItem(skukey);
	}else{
		skuVal.goodscount = num;
		storage.removeItem(skukey);
		storage.setItem(skukey, JSON.stringify(skuVal));
	}
	
	checkCoupon();
	goodscount += count;
	return goodscount;
}

/* sku商品数量增加*/
$(".sku-plus").on("tap",function(){
	var n=parseInt($(this).prev().attr("value"));
	if((n+1) > $(this).prev().attr("store")){
		showAlertMsg(msgFailProductStore);//库存不足提示
	}else{
		$(this).prev().attr("value",n+1);
	}		
});

/* sku商品数量减少*/
$(".sku-minus").on("tap",function(){
	var n=parseInt($(this).next().attr("value"));
	if(n>1){
		$(this).next().attr("value",n-1);
	}		
});

/* 选择sku事件*/
$(".sku-sure").live("tap",function(){
	if($(this).hasClass("add-cart-gray")){
		return;
	}
	$("#sku").addClass("hidden");
	$(".masker").addClass("hidden");
	addCart(0,"sku");//添加到购物车
	refreshCount();
	$(".sku-b input").attr("value","1");
})

/* 点击sku取消或加入购物车按钮*/
$(".sku-cancel").on("tap",function(){
	$(".sku-b input").attr("value","1");
	$("#sku").addClass("hidden");
	$(".masker").addClass("hidden");
});

/* 点击sku中的属性进行选择*/
$(".sku-detail em").live("tap",function(){
	$(this).siblings().removeClass("sku-red");
	$(this).addClass("sku-red");	
});

/* 商品是否有多个sku*/
function hasMultisku(goodsid){
	skuIndex = 0;
	var hasMultisku = true;
	var data = {
			"psam":psam,
			"channelid":channelcodePF,
			"goodsid":goodsid
	};
	var resultJson = ajaxCommon(urlWholesaleDetail, data);
	resultJson = convertNullToEmpty(resultJson);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		
		/* 传递给购物车参数*/
		var goods = _returnData.goods;//商品详情
		goodName = goods.goodname;
		goodBigPic = goods.goodbigpic.split(";")[0];
		supplierId = goods.supplierid;
		supplierName = goods.suppliername;
		distributionflag  = goods.distributionflag;
		/* 传递给购物车参数*/
		
		goodsSkuO2OList = _returnData.goodsSkuO2OList;//skuList
		//sku信息
		var skuContent = "";//规格
		var storeContent = "";
		if(goodsSkuO2OList.length > 1){
			skuContent += "<div class='sku-detail'>";
			for(var i = 0 ;i < goodsSkuO2OList.length; i ++){
				skuContent += "<em store='" +goodsSkuO2OList[i].store+ "'>" +goodsSkuO2OList[i].skuFrontDisNameStr+ "</em>";
				storeContent += "<div class='store-wrap'>";
				storeContent += "<div class='sku-a'>";
				storeContent += "<i class='stock'>库存：<em>" +goodsSkuO2OList[i].store+ "</em>件</i>";
				storeContent += "<i class='sku-solded'>已售：<em>" +goodsSkuO2OList[i].soldStore+ "</em></i>";
				storeContent += "</div>";
				storeContent += "<div class='sku-a'>";
				storeContent += "<i class='sku-sale'>拉卡拉价：<em class='color'>￥" +goodsSkuO2OList[i].salePrice+ "</em></i>";
				storeContent +=	"<i class='sku-market'>批发市场价：<em>￥" +goodsSkuO2OList[i].marketPrice+ "</em></i>";
				storeContent += "</div>";
				storeContent += "</div>";
			}
			skuContent += "</div>";
			$(".sku-wrap").html("").append(skuContent).after(storeContent);
		}else{
			hasMultisku = false;
		}
		
		//点击sku显示相应的库存和价格
		var sku = $(".sku-detail em");
		var store_price = $(".store-wrap");
		sku.eq("0").addClass("sku-red");//默认sku第一项被选中
		store_price.addClass("hidden").eq("0").removeClass("hidden");//默认第一个sku的售价和库存被显示
		$(".sku-b input").attr("store",sku.eq("0").attr("store"));//默认为选择商品数量添加库存属性
		$(".sku-sure").removeClass("add-cart-gray");
		if(sku.eq("0").attr("store") === "0"){
			$(".sku-sure").addClass("add-cart-gray");//如果库存为0，加入购物车按钮不可用
		}
		//点击sku显示相应的库存、价格、库存
		sku.each(function(index) {
			$(this).on("tap",function(){
				store_price.addClass("hidden").eq(index).removeClass("hidden");
				$(".sku-b input").attr("value","1");
				$(".sku-b input").attr("store",$(this).attr("store"));
				if($(this).attr("store") === "0"){
					$(".sku-sure").addClass("add-cart-gray");//如果库存为0，加入购物车按钮不可用
				}else{
					$(".sku-sure").removeClass("add-cart-gray");
				}
				skuIndex = index;
			});
		});
	}else{
		//接口返回错误
	}
	return hasMultisku;
}

/* 添加到购物车*/
function addCart(count,sku){
	var goodsId = goodsSkuO2OList[skuIndex].tgoodInfoId;
	var o2oId = goodsSkuO2OList[skuIndex].o2oId;
	var marketPrice = goodsSkuO2OList[skuIndex].marketPrice;
	var salePrice = goodsSkuO2OList[skuIndex].salePrice;
	var skuFrontDisNameStr = goodsSkuO2OList[skuIndex].skuFrontDisNameStr;
	var tGoodSkuInfoId = goodsSkuO2OList[skuIndex].tgoodSkuInfoId;
	var store = goodsSkuO2OList[skuIndex].store;
	var goodscount = count;
	if(sku){
		goodscount = $("input[type=text]").val();
	}
	goodscount = uniqueStorage(goodsId,tGoodSkuInfoId,goodscount);//同一sku商品数量做累加
	var key = "";
	key = productPrefix+goodsId+"_"+tGoodSkuInfoId;
	var value = {
			"goodsId":goodsId,
			"goodName":goodName,
			"goodBigPic":goodBigPic,
			"supplierId":supplierId,
			"supplierName":supplierName,
			"o2oId":o2oId,
			"marketPrice":marketPrice,
			"salePrice":salePrice,
			"skuFrontDisNameStr":skuFrontDisNameStr,
			"tGoodSkuInfoId":tGoodSkuInfoId,
			"store":store,
			"goodscount":goodscount,
			"channelcode":channelcodePF,
			"distributionflag":distributionflag
	};
	storage.setItem(key, JSON.stringify(value));
	checkCoupon();
	storage.setItem("sysDate",new Date());//保存系统时间
}

/* 计算商品数量及金额*/
function checkCoupon(){
	cartIsEmpty();//判断购物车是否为空
	$(".goodscart").html(getGoodsCount());
	$(".goodscart-total").html("共计" +getGoodsCount()+ "件");
	var goodstotal = 0;
	for(var i = storage.length-1; i >= 0; i --){
		var key = storage.key(i);
		if(key.indexOf(productPrefix) >= 0){
			var proVal = $.parseJSON(storage.getItem(key));
			var goodscount = parseInt(proVal.goodscount);
			var salePrice = parseFloat(proVal.salePrice);
			goodstotal += goodscount*salePrice;
		}
	}
	$(".goodscart-price").html("￥"+parseFloat(goodstotal).toFixed(2));
}

/* 去结算*/
$(function(){
	$("#to-pay").on("tap",function(){
		if($(this).hasClass("add-cart-gray")){
			return;
		}
		window.location.href = "fill.html";
	});
})