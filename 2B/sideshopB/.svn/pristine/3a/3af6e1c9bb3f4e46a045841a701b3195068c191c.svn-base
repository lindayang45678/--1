var searchparm = "";//搜索参数
var count = "";
var page = 0;
var pageSize = 10;
var hasNextPage = true;//是否有下一页
var virtualcatid = "all";//虚拟分类ID
var myScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset;
//初始化iScroll控件 
function loaded() {
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('wholesale-search', {//iScroll的对象
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
				if(searchparm == ''){
					return;
				}
				getGoodsList(virtualcatid);	// 执行加载后的自定义功能	
			}
		}
	});
}
document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);
//设定显示商品列表高度
$(function() {
	$("#wholesale-search").css("height",windowHeight-51-44-33);
	var getsearchparm = storage.getItem("searchparm") || "";
	if(getsearchparm != "") {//判断是否从批发进货进来
		searchparm = getsearchparm;
		getGoodsList(virtualcatid);
	}
});
//请求接口获取搜索的商品
function getGoodsList(virtualcatid){
	var container = $(".search-result");
	var content = "";
	var searchTitle = $(".search-title");
	var countList = "";
	setTimeout(function () {
		if(hasNextPage){
			page ++;
			var data = {
				"psam":psam,
				"channelid":channelcodePF,
				"virtualcatid":virtualcatid,
				"searchparm":searchparm,
				"page":page,
				"pageSize":pageSize
			};
			var resultJson = ajaxCommon(urlWholesaleList, data);
			resultJson = convertNullToEmpty(resultJson);
			if (resultJson._ReturnCode === returnCodeSuccess) {
				goodList = resultJson._ReturnData.list;
				if(goodList.length > 0){
					$('.w-search-fail').hide();
					$('.w-search-failimg').hide();
					for(var i = 0; i < goodList.length; i ++){
						content +="<li class='clearfix'>";
						content +="<a href='prodetail.html?goodsid=" +goodList[i].tgoodinfoid+ "'>";
						content +="<i class='box-picture'><img src='" +urlImage+ "/" +goodList[i].tgoodinfoid+ "/" +imgw100+ "/" +goodList[i].goodbigpic.split(";")[0]+ "' onerror='productErrImg(this);'/><i></i></i>";
						content +="<i class='box-detail'>";
						content +="<i class='detail-head'>" +goodList[i].goodname+ "</i>";
						content +="<i class='sale-price'>¥" +goodList[i].saleprice_o2o+ "</i>";
						content +="<del class='market-price'>¥" +goodList[i].marketprice+ "</del>";
						content +="<i class='add-cart' goodsid='" +goodList[i].tgoodinfoid+ "' goodIndex="+i+">立即购买</i>";
						content +="</i>";
						content +="</a>";
						content +="</li>";
					}
					container.append(content);
					myScroll.refresh();
					countList = "搜索到和【"+ searchparm +"】相关的商品，共 <em>"+ resultJson._ReturnData.count +"</em>个";
					searchTitle.html(countList);
				}else{
					if(page == 1){
						searchTitle.html('');
						$('.w-search-fail').show().html('没有搜索到和【'+ searchparm +'】相关的商品');
						$('.w-search-failimg').show();
					}else{
						hasNextPage = false;
					}
				}
				
			}else{
				//接口返回错误
				showAlertMsg(resultJson._ReturnMsg);
			}
		}
	},400);
}
//确认查询
$('.w-searchbtn').on('tap',function	(){
	searchparm = "";//搜索参数
	count = "";
	page = 0;
	pageSize = 10;
	hasNextPage = true;//是否有下一页
	/* 根据浏览器的高度计算iScroll的高度*/
	$(function(){
		searchparm = $('.search-wrap input').val();
		if(searchparm == ''){
			showAlertMsg("请您输入搜索内容！");
			return;
		}
		$('.search-result').html('');
		storage.setItem("searchparm", searchparm);
		getGoodsList(virtualcatid,searchparm);
	});
});
	
	///* 判断购物车，如果为空，结算不可用*/
	//function cartIsEmpty(){
	//	//如果购物车为空，结算不可用
	//	if(getGoodsCount() === 0){
	//		$("#to-pay").addClass("add-cart-gray");
	//	}else{
	//		$("#to-pay").removeClass("add-cart-gray");
	//	}
	//}
	
	/* 获取批发进货商品列表*/
	
	
	//定义购物接收参数
	/*var goodsSkuO2OList = "";
	var skuIndex = 0;
	var goodName = "";
	var goodBigPic = "";
	var supplierId = "";
	var supplierName = "";
	var distributionflag = "";*/
	
	///* 点击加入购物车显示选择sku*/
	//$(function(){
	//	$('.add-cart').live('tap',function(){
	//		var goodsid = $(this).attr("goodsid");
	//		var data = {
	//			"psam":psam,
	//			"channelid":channelcodePF,
	//			"goodsid":goodsid
	//		};
	//		var resultJson = ajaxCommon(urlWholesaleDetail, data);
	//		if (resultJson._ReturnCode === returnCodeSuccess) {
	//			var _returnData = resultJson._ReturnData;
	//			
	//			/* 传递给购物车参数*/
	//			var goods = _returnData.goods;//商品详情
	//			goodName = goods.goodname;
	//			goodBigPic = goods.goodbigpic.split(";")[0];
	//			supplierId = goods.supplierid;
	//			supplierName = goods.suppliername;
	//			distributionflag  = goods.distributionflag;
	//			/* 传递给购物车参数*/
	//			
	//			goodsSkuO2OList = _returnData.goodsSkuO2OList;//skuList
	//			//sku信息
	//			var skuContent = "";//规格
	//			var storeContent = "";
	//			if(goodsSkuO2OList.length > 1){
	//				skuContent += "<div class='sku-detail'>";
	//				for(var i = 0 ;i < goodsSkuO2OList.length; i ++){
	//					skuContent += "<em store='" +goodsSkuO2OList[i].store+ "'>" +goodsSkuO2OList[i].skuFrontDisNameStr+ "</em>";
	//					storeContent += "<div class='store-wrap'>";
	//					storeContent += "<div class='sku-a'>";
	//					storeContent += "<i class='stock'>库存：<em>" +goodsSkuO2OList[i].store+ "</em>件</i>";
	//					storeContent += "<i class='sku-solded'>已售：<em>" +goodsSkuO2OList[i].soldStore+ "</em></i>";
	//					storeContent += "</div>";
	//					storeContent += "<div class='sku-a'>";
	//					storeContent += "<i class='sku-sale'>拉卡拉价：<em class='color'>￥" +goodsSkuO2OList[i].salePrice+ "</em></i>";
	//					storeContent +=	"<i class='sku-market'>批发市场价：<em>￥" +goodsSkuO2OList[i].marketPrice+ "</em></i>";
	//					storeContent += "</div>";
	//					storeContent += "</div>";	
	//				}
	//				skuContent += "</div>";
	//				$(".sku-wrap").html("").append(skuContent).after(storeContent);
	//				$('#sku').removeClass('hidden');
	//				$('.masker').removeClass('hidden');
	//			}else{
	//				//如果只有一个sku,直接添加到购物车
	//				addCart();
	//			}
	//			
	//			//点击sku显示相应的库存和价格
	//			var sku = $(".sku-detail em");
	//			var store_price = $(".store-wrap");
	//			sku.eq("0").addClass("sku-red");//默认sku第一项被选中
	//			store_price.addClass("hidden").eq("0").removeClass("hidden");//默认第一个sku的售价和库存被显示
	//			$(".sku-b input").attr("store",sku.eq("0").attr("store"));//默认为选择商品数量添加库存属性
	//			$(".sku-sure").removeClass("add-cart-gray");
	//			if(sku.eq("0").attr("store") === "0"){
	//				$(".sku-sure").addClass("add-cart-gray");//如果库存为0，加入购物车按钮不可用
	//			}
	//			//点击sku显示相应的库存、价格、库存
	//			sku.each(function(index) {
	//				$(this).on("tap",function(){
	//					store_price.addClass("hidden").eq(index).removeClass("hidden");
	//					$(".sku-b input").attr("value","1");
	//					$(".sku-b input").attr("store",$(this).attr("store"));
	//					if($(this).attr("store") === "0"){
	//						$(".sku-sure").addClass("add-cart-gray");//如果库存为0，加入购物车按钮不可用
	//					}else{
	//						$(".sku-sure").removeClass("add-cart-gray");
	//					}
	//					skuIndex = index;
	//				});
	//			});
	//		}else{
	//			//接口返回错误
	//		}
	//	});
	//});
	
	///* 临时的跳入搜索成功和失败页*/
	//$('.w-searchbtn').on('tap',function(){
	//	var searchparm = $.trim($('.search-wrap input').val());
	//	if(searchparm){	
	//		window.location.href = encodeURI(encodeURI("searchsuccess.html?searchparm=" + searchparm));
	//	}else{	
	//		$('.w-searchbtn').attr('href','searchfail.html');	
	//	}
	//});
	/* 选择sku事件*/
	/*$(function(){
		//点击sku取消或加入购物车按钮
		$('.sku-cancel').live('tap',function(){
			$('#sku').addClass('hidden');
			$('.masker').addClass('hidden');	
		});
		$('.sku-sure').live('tap',function(){
			$('#sku').addClass('hidden');
			$('.masker').addClass('hidden');	
		});
		//点击sku中的属性进行选择
		$('.sku-detail em').live('tap',function(){
			$(this).siblings().removeClass('sku-red');
				$(this).addClass('sku-red');
		});
		 点击sku进行加减数量
		$(".sku-plus").on("tap",function(){
			var n=parseInt($(this).prev().attr("value"));
			if((n+1) > $(this).prev().attr("store")){
				showAlertMsg(msgFailProductStore);//库存不足提示
			}else{
				$(this).prev().attr("value",n+1);
			}		
		});
		$(".sku-minus").on("tap",function(){
			var n=parseInt($(this).next().attr("value"));
			if(n>1){
				$(this).next().attr("value",n-1);
			}		
		});
	});*/
	
	//加入购物车
	//function addCart(){
	//	var goodsId = goodsSkuO2OList[skuIndex].tgoodInfoId;
	//	var o2oId = goodsSkuO2OList[skuIndex].o2oId;
	//	var marketPrice = goodsSkuO2OList[skuIndex].marketPrice;
	//	var salePrice = goodsSkuO2OList[skuIndex].salePrice;
	//	var skuFrontDisNameStr = goodsSkuO2OList[skuIndex].skuFrontDisNameStr;
	//	var tGoodSkuInfoId = goodsSkuO2OList[skuIndex].tgoodSkuInfoId;
	//	var goodscount = $("input[type=text]").val() || "1";
	//	goodscount = uniqueStorage(goodsId,tGoodSkuInfoId,goodscount);//同一sku商品数量做累加
	//	var key = "";
	//	key = productPrefix+goodsId+"_"+tGoodSkuInfoId;
	//	var value = {
	//		"goodsId":goodsId,
	//		"goodName":goodName,
	//		"goodBigPic":goodBigPic,
	//		"supplierId":supplierId,
	//		"supplierName":supplierName,
	//		"o2oId":o2oId,
	//		"marketPrice":marketPrice,
	//		"salePrice":salePrice,
	//		"skuFrontDisNameStr":skuFrontDisNameStr,
	//		"tGoodSkuInfoId":tGoodSkuInfoId,
	//		"goodscount":goodscount,
	//		"channelcode":channelcodePF,
	//		"distributionflag":distributionflag
	//	};
	//	storage.setItem(key, JSON.stringify(value));
	//}
	
	 //更新批发进货首页底部/验证优惠券
	//function showCart(){
	//	cartIsEmpty();//判断购物车是否为空
	//	$(".goodscart").html(getGoodsCount());
	//	$(".goodscart-total").html("共计" +getGoodsCount()+ "件");
	//	var couponlist = [];
	//	var items = [];
	//	for(var i = 0; i < storage.length; i ++){
	//		var key = storage.key(i);
	//		if(key.indexOf(productPrefix) >= 0){
	//			var proVal = JSON.parse(storage.getItem(key));
	//			var item = {
	//				"skuo2oid":	proVal.o2oId,
	//				"skuid": proVal.tGoodSkuInfoId,
	//				"goodsname": proVal.goodName,
	//				"goodssaleprice": proVal.salePrice,
	//				"goodscount": proVal.goodscount,
	//				"channel": proVal.channelcode
	//			}
	//			items.push(item);
	//		}
	//	}
	//	var data = {
	//		"token":userToken,
	//	    "mobile":mobile,
	//	    "pasmid":psam,
	//	    "source":sourceSbApp2B,
	//	    "custelno":mobile,
	//	    "wholesale":"2B",
	//	    "couponlist":couponlist,
	//	    "items":items
	//	}
	//	var resultJson = ajaxCommon(urlCheckCoupon, data);
	//	if (resultJson._ReturnCode === returnCodeSuccess) {
	//		var goodstotal = resultJson._ReturnData.coupondata.goodstotal;
	//		$(".goodscart-price").html(goodstotal);
	//	}else{
	//		//接口返回错误
	//	}
	//}
	//













