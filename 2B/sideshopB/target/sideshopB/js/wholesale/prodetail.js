var picCount = 0;// 动态获取商品图片的数量
function scrollWidth(){
	var sWidth = $(window).width()-20;
	$("#wrapper").css("width", sWidth + "px");
	$("#scroller").css("width", sWidth * picCount + "px");
	$("#product-pic li").css("width", sWidth + "px");
	$("#product-pic img").css("width", sWidth + "px");
}

var prodetailScroll;
function scrollProdetail(){
	prodetailScroll = new iScroll('wrapper', {
		snap : true,
		momentum : false,
		hScrollbar : false,
		onScrollEnd : function() {
			$("#pagination a").removeClass("active").eq(this.currPageX).addClass("active");
		},
		onBeforeScrollStart:function(e){
	        if(this.absDistX > (this.absDistY + 5 )){
	            e.preventDefault();
	        }
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

$(function(){
	osFixed();
	var url = window.location.href;
	var goodsid = getParamValueByName(url, "goodsid");
	var data = {
			"psam":psam,
			"channelid":channelcodePF,
			"goodsid":goodsid
	};
	var resultJson = ajaxCommon(urlWholesaleDetail, data);
	resultJson = convertNullToEmpty(resultJson);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _returnData = resultJson._ReturnData;
		goodsSkuO2OList = _returnData.goodsSkuO2OList;//skuList
		var goods = _returnData.goods;//商品详情
		
		/* 传递给购物车参数*/
		supplierId = goods.supplierid;
		goodName = goods.goodname;
		goodBigPic = goods.goodbigpic.split(";")[0];
		supplierName = goods.suppliername;
		distributionflag  = goods.distributionflag;
		/* 传递给购物车参数*/
		
		//加载商品图片及图片导航
		var picContent = "";
		var pageContent = "";
		var goodbigpic = goods.goodbigpic;
		picCount = goodbigpic.split(";").length;
		for(var i = 0; i < goodbigpic.split(";").length; i ++){
			picContent += "<li><img src='" +urlImage+ "/" +goods.tgoodinfoid+ "/" +imgw600+ "/" +goodbigpic.split(";")[i]+ "' onerror='productErrImg(this);'/></li>";
			pageContent += "<li><a href='javascript:;'>&nbsp;</a></li>";
		}
		$("#product-pic").html("").append(picContent);
		$("#pagination").html("").append(pageContent).find("a").eq(0).addClass("active");
		scrollWidth();//初始化图片滚动宽度
		scrollProdetail();//初始化iScroll
		
		$("#pro-title").html(goods.goodname);//商品名称
		
		//sku信息
		var skuContainer = $("#sku-wrap");
		var storeContent = "";//库存、售价
		var skuContent = "";
		if(goodsSkuO2OList.length > 0){
			for(var i = 0 ;i < goodsSkuO2OList.length; i ++){
				storeContent += "<div id='pro-price' class='pro-price clearfix'>";
				storeContent += "<div class='promo-price'>批发价<br/><i id='saleprice_o2o'>￥" +goodsSkuO2OList[i].salePrice+ "</i></div>";
				storeContent += "<div class='market-price'>市场价<br/><del id='marketprice'>￥" +goodsSkuO2OList[i].marketPrice+ "</del></div>";
				storeContent += "<div class='sale-info'>已售：<i id='soldStore' class='color f-14'>" +goodsSkuO2OList[i].soldStore+ "</i><br/>";
				storeContent += "库存：<i id='store'>" +goodsSkuO2OList[i].store+ "</i>";
				storeContent += "</div>";
				storeContent += "</div>";
				skuContent += "<option value='"+i+"' store='" +goodsSkuO2OList[i].store+ "'>" +goodsSkuO2OList[i].skuFrontDisNameStr+ "</option>";
			}
			skuContainer.html("").append(storeContent);
			$("#propertyId").html("").append(skuContent);
		}
		if(goodsSkuO2OList.length == 1 || goodsSkuO2OList.length == 0){
			$("#pro-tase div").addClass("hidden");
		}
		
		//点击sku显示相应的库存和价格
		var sku = $("#propertyId option");
		var store_price = $(".pro-price");
		$(".pro-price").addClass("hidden").eq("0").removeClass("hidden");
		
		//商品详情
		$("#pro-condetail").html(goods.goodname);
		
		//售后服务
		$("#pro-customer span:eq(0)").html("").append("供应商：" +goods.suppliername+ "");
		$("#pro-customer span:eq(1)").html("").append("售后电话：" +goods.clientServiceTel+ "&nbsp;" +goods.clientServicePhone+ "");
		$("#pro-customer span:eq(2)").html("").append("售后地址：" +goods.returnprovincename+goods.returncityname+goods.returncountyname+goods.returnaddress+ "");
		
		//点击sku显示相应的库存、价格、库存
		$("#pro-num").attr("store",sku.eq("0").attr("store"));//默认为选择商品数量添加库存属性
		if(goodsSkuO2OList.length == 0 || $("#pro-num").attr("store") == 0){
			$("#pro-panic").addClass("add-cart-gray");
		}
		
		$("#propertyId").on("change",function(){
			var index = $(this).val();
			store_price.addClass("hidden").eq(parseInt(index)).removeClass("hidden");
			var skuOption = $(this).find("option");
			$("#pro-num").attr("value","1").attr("store",skuOption.eq(index).attr("store"));
			if(skuOption.eq(index).attr("store") === "0"){
				$("#pro-panic").addClass("add-cart-gray");
			}else{
				$("#pro-panic").removeClass("add-cart-gray");
			}
			skuIndex = index;
		})
	}else{
		//接口返回错误
	}
})

/* 切换商品详情、售后服务*/
$("#pro-tab a").each(function(index){
	$(this).on("tap",function(){
		$(this).addClass("color").siblings("a").removeClass("color");
		if(index === 0){
			$("#pro-condetail").removeClass("hidden").siblings("div").addClass("hidden");
		}else{
			$("#pro-customer").removeClass("hidden").siblings("div").addClass("hidden");
		}
	})
})

/* 商品数量增加*/
$("#pro-jia").on("tap",function(){
	var n=parseInt($(this).prev().attr("value"));
	if((n+1) > $(this).prev().attr("store")){
		showAlertMsg(msgFailProductStore);//库存不足提示
	}else{
		$(this).prev().attr("value",n+1);
		$(this).prev().val(n+1);
	}	
});

/* 商品数量减少*/
$("#pro-jian").on("tap",function(){
	var n=parseInt($(this).next().attr("value"));
	if(n > 1){
		$(this).next().attr("value",n-1);
		$(this).next().val(n-1);
	}		
});

/* 手动输入商品数量*/
$("#pro-num").on("keyup",function(){
	var store = $("#pro-num").attr("store");
	var count = $("input[type=tel]").val();
	if(count < 1 || !count || isNaN(count)){
		$(this).val("1");
		$(this).attr("value","1");
	}else{
		$(this).val(count);
		$(this).attr("value",count);
	}
	if(parseInt(count) > parseInt(store)){
		$(this).val(store);
		$(this).attr("value",store);
	}
})

/* 添加到购物车*/
$("#pro-panic").on("tap",function(){
	if($(this).hasClass("add-cart-gray")){
		return;
	}
	var goodsId = goodsSkuO2OList[skuIndex].tgoodInfoId;
	var o2oId = goodsSkuO2OList[skuIndex].o2oId;
	var marketPrice = goodsSkuO2OList[skuIndex].marketPrice;
	var salePrice = goodsSkuO2OList[skuIndex].salePrice;
	var skuFrontDisNameStr = goodsSkuO2OList[skuIndex].skuFrontDisNameStr;
	var tGoodSkuInfoId = goodsSkuO2OList[skuIndex].tgoodSkuInfoId;
	var store = goodsSkuO2OList[skuIndex].store;
	var goodscount = $("input[type=tel]").val();
	goodscount = uniqueStorage(goodsId,tGoodSkuInfoId,goodscount);//统一sku商品数量做累加
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
	storage.setItem("sysDate",new Date());//保存系统时间
	window.location.href = "fill.html?t=" +t +"&key=detail";
})
