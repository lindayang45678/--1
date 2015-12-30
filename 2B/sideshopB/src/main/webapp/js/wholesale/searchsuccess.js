//显示购物车商品
$(function(){
	checkCoupon();
	loaded();
	osFixed();
})

var searchparm = "";//搜索参数
var page = 0;
var pageSize = 10;
var hasNextPage = true;//是否有下一页
var virtualcatid = "all";//虚拟分类ID
var myScroll,pullDownOffset,pullUpEl, pullUpOffset;
//初始化iScroll控件 
function loaded(){
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('wholesale-search', {//iScroll的对象
		hScrollbar: false, 
		vScrollbar: false,
		useTransition: false,
		topOffset: pullDownOffset,
		onScrollStart: function () {
			myScroll.refresh();
        },
		onScrollMove: function () {
			$(".num").blur();
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
		},
        onBeforeScrollStart:function (e) {
            var target = e.target;
            while (target.nodeType != 1) target = target.parentNode;

            if (target.tagName != 'SELECT' && target.tagName != 'INPUT' && target.tagName != 'TEXTAREA') {
                e.preventDefault();
            }
        }
	});
}

//设定显示商品列表高度
$(function() {
	$("#wholesale-search").css("height",windowHeight-$("header").height()-33-$(".search-titlee").height()-$("#wholesale-foot1").height());
	var wrap = $(".container").css("height", windowHeight-$("header").height());
	$(".container").css({"box-sizing":"border-box","-webkit-box-sizing":"border-box"})
	wrap = wrap-$("#wholesale-foot").height();
	$(".wrap").css("height",wrap);
	var url = window.location.href;
	var getsearchparm = storage.getItem("searchparm") || getParamValueByName(url, "searchparm");
	if(getsearchparm != "") {//判断是否从批发进货进来
		searchparm = decodeURI(getsearchparm);
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
				refreshCount();
				if(goodList.length > 0){
					$('.w-search-fail').hide();
					$('.w-search-failimg').hide();
					for(var i = 0; i < goodList.length; i ++){
						content +="<li class='clearfix'>";
						content +="<a href='prodetail.html?goodsid=" +goodList[i].tgoodinfoid+ "&search=search'>";
						content +="<i class='box-picture' id='box-picture'><img src='" +urlImage+ "/" +goodList[i].tgoodinfoid+"/"+imgw300+ "/" +goodList[i].goodbigpic.split(",")[0]+ "' onerror='productErrImg(this);'/>";								          						
						if(goodList[i].issoldout == 0){
                            content += "<i class='soldout'></i>";
                        }
						content += "<i></i></i>";
						content += "</a>";
						content +="<i class='box-detail'>";
						content += "<h3 goodsid='" +goodList[i].tgoodinfoid+ "'>";
						if(goodList[i].type == promoTypeZhiJiang) {
                        	content += "<span class='color drop'>直降</span>";
                        }
						content += goodList[i].goodname;
						content += "</h3>";						
						if(goodList[i].type == promoTypeZhiJiang) {
							content +="<span class='sale-price'>¥" +goodList[i].promotionPrice+ "</span>";
							content +="<del class='market-price'>¥" +goodList[i].saleprice_o2o+ "</del>";
                        }else{
                        	content +="<span class='sale-price'>¥" +goodList[i].saleprice_o2o+ "</span>";
							content +="<del class='market-price'>¥" +goodList[i].marketprice+ "</del>";
                        }											
						content +="</i>";						
                        if(goodList[i].issoldout == 1){
                            content += "<div class='buttom-jiajian'>";
                            content += "<a href='javascript:;' class='jiajian jia' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' store='" +goodList[i].store+ "' goodsname='" +goodList[i].goodname+"' promotype='" +goodList[i].type+ "' purchasecount='" +goodList[i].purchaseCount+ "'>+</a>";
                            var key = productPrefix+goodList[i].tgoodinfoid+"_"+goodList[i].tgoodskuinfoid;
                	        var proVal = $.parseJSON(storage.getItem(key));
                	        var count = 0;
                            if(proVal != null){
                            	count = proVal.goodscount;
	                            if(goodList[i].ismoresku == "1"){
	                                content += "<input type='tel' class='num'  id='" +goodList[i].tgoodinfoid+ "' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "' store='" +goodList[i].store+ "'purchasecount='" +goodList[i].purchaseCount+ "' promotype='" +goodList[i].type +"' value='"+count+"' readonly='readonly'/>"
	                            }else{                         
	                                content += "<input type='tel' class='num'  id='" +goodList[i].tgoodinfoid+ "' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "' store='" +goodList[i].store+ "'purchasecount='" +goodList[i].purchaseCount+ "' promotype='" +goodList[i].type +"' value='"+count+"' onfocus='this.focused=true;this.select();' onmouseup='if(this.focused){this.focused=false;return false;}'/>";                                
	                            }
                            }else{
                            	if(goodList[i].ismoresku == "1"){
	                                content += "<input type='tel' class='num hidden'  id='" +goodList[i].tgoodinfoid+ "' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "' store='" +goodList[i].store+ "' readonly='readonly'/>"
	                            }else{                         
	                                content += "<input type='tel' class='num hidden'  id='" +goodList[i].tgoodinfoid+ "' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "' store='" +goodList[i].store+ "' goodsname='" +goodList[i].goodname+"' promotype='" +goodList[i].type+ "' purchasecount='" +goodList[i].purchaseCount+ "' onfocus='this.focused=true;this.select();' onmouseup='if(this.focused){this.focused=false;return false;}'/>";                                
	                            }
                            }
                            if(proVal != null){
                                content += "<a href='javascript:;' class='jiajian jian' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "'>&minus;</a>";                           
                            }else{
                            	content += "<a href='javascript:;' class='jiajian jian hidden' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "'>&minus;</a>";
                            }
                            content += "</div>";
                        }                                            
						content +="</li>";
					}
					container.append(content);
					myScroll.refresh();
					countList = "搜索到和【"+ searchparm +"】相关的商品，共<em>"+resultJson._ReturnData.count +"</em>个";
					searchTitle.html(countList);
				}
			}else{
				//接口返回错误
				if(page == 1){
					searchTitle.html("");
					$(".w-search-fail").show().html("没有搜索到和【" + searchparm + "】相关的商品");
					$(".w-search-failimg").show();
				}else{
					hasNextPage = false;
				}
			}
		}
	    $(".wholesale-goods li h3").live("click", function(e) {
	        var goodsid = $(this).attr("goodsid");
	        e.preventDefault();
	        window.location.href = "prodetail.html?goodsid=" + goodsid + "&virtualcatid=" + virtualcatid +"&search=search&t=" + t;
	    });
	},400);
}

/* 键盘搜索事件*/
$(".orders-search input").on("keyup",function(e){
	if(e.keyCode == 13){
		page = 0;
		hasNextPage = true;//是否有下一页
		searchparm = $.trim($(".orders-search input").val());
		if(searchparm == ""){
			showAlertMsg(msgProductNameEmpty);
			return;
		}
		$(".search-result").html("");
		storage.setItem("searchparm", searchparm);
		getGoodsList(virtualcatid,searchparm);
	}
});

/* 确认查询*/
$(".good-search").on("tap",function	(){
	window.location.href = "searchsuccess.html?t=" + t;
	page = 0;
	hasNextPage = true;//是否有下一页
	searchparm = $.trim($(".orders-search input").val());
	if(searchparm == ""){
		showAlertMsg(msgProductNameEmpty);
		return;
	}
	$(".search-result").html("");
	storage.setItem("searchparm", searchparm);
	getGoodsList(virtualcatid,searchparm);
});


//定义购物接收参数
var goodsSkuO2OList = "";
var skuIndex = 0;
var goodName = "";
var goodBigPic = "";
var supplierId = "";
var supplierName = "";
var distributionflag = "";
var goodswrap = null;

/* 判断购物车，如果为空，结算不可用*/
function cartIsEmpty(){
    //如果购物车为空，结算不可用
    if(getGoodsCount() === 0){
        $("#to-pay").addClass("add-cart-gray");
    }else{
        $("#to-pay").removeClass("add-cart-gray");
    }
}

/* 遍历localStorage显示每个商品数量*/
function refreshCount(){
	var count = 0;
    for(var i = 0; i < storage.length; i ++){
        var key = storage.key(i);
        if(key.indexOf(productPrefix) >= 0){
            var proVal = JSON.parse(storage.getItem(key));
            var num = $("#"+proVal.goodsId);
            if($(num).attr("ismoresku") == "0") {
            	$(num).attr("value",proVal.goodscount);
            	$(num).val(proVal.goodscount);
            }else {
            	var count = goodsStorage(proVal.goodsId);
            	$(num).attr("value",count);
            	$(num).val(count);
            }
            $(num).removeClass("hidden");
            $(num).siblings(".jian").removeClass("hidden");//显示减号
        }
    }
}

/* 点击加号事件*/
$(".jia").live("touchend", function(e){
    $(this).removeClass("focus");
    var goodsid = $(this).attr("goodsid");
    var hasmultisku = hasMultisku(goodsid);
    if(hasmultisku == "nogoods") {
    	showAlertMsg(msgProductNotForSale);
    } else {
    	if(!hasmultisku){
            var key = productPrefix+$(this).attr("goodsid")+"_"+$(this).attr("skuid");
            var proVal = $.parseJSON(storage.getItem(key));
            var count = 0;
            if(proVal != null){
                count = proVal.goodscount;
            }                       
            var promotype = $(this).attr("promotype");
            //直降商品数量校验
            if(promotype == promoTypeZhiJiang) {
            	var goodsname = $(this).attr("goodsname");
            	var purchasecount = $(this).attr("purchasecount");
            	//限购数为0时，表示不限购
        		if(parseInt(purchasecount)>0 && (count+1)>purchasecount) {
        			showAlertMsg(goodsname + " 限购" +purchasecount+"件！");
        		}
            }
            if((count+1) > $(this).attr("store")){
                showAlertMsg(msgMoreProductStore);//库存不足提示
            }else{
                addCart(1);//加入购物车
                refreshCount();
                if(count+1 > 0){
                    //$(this).siblings(".jian").removeClass("hidden");
                }
             }
        }else{
            $('#sku').removeClass('hidden');
            $('.masker').removeClass('hidden');
        }
    }
    e.preventDefault();
});

/* 点击减号事件*/
$(".jian").live("touchend",function(e){
    $(this).removeClass("focus");

    var goodsid = $(this).attr("goodsid");
    var hasmultisku = hasMultisku(goodsid);
    if(hasmultisku == "nogoods") {
    	showAlertMsg(msgProductNotForSale);
    } else {
    	 if(!hasmultisku){
	        var key = productPrefix+$(this).attr("goodsid")+"_"+$(this).attr("skuid");
	        var proVal = $.parseJSON(storage.getItem(key));
	        var count = 0;
	        if(proVal != null){
	            count = proVal.goodscount;
	        }
	        if(count-1 > 0){
	            addCart(-1);//加入购物车
	            refreshCount();
	        }else{
	            storage.removeItem(key);
	            //$(this).siblings(".number").addClass("hidden");
	            $(this).siblings(".num").addClass("hidden");
	            $(this).addClass("hidden");
	            checkCoupon();
	        }
	    }else{
	        var goodscount = goodsSku(goodsid,-1);
	        if(goodscount == 0){
	            //$(this).siblings(".number").addClass("hidden");
	            $(this).siblings(".num").addClass("hidden");
	            $(this).addClass("hidden");
	        }else{
	            //$(this).siblings(".number").text(goodscount);
	            $(this).siblings(".num").val(goodscount);
	            $(this).siblings(".num").attr("value",goodscount);
	        }
	    }
    }
    e.preventDefault();
})

/*手动输入商品数量*/
$(".num").live("keyup", function(){
    var store = $(this).attr("store");
    var count = $(this).val();
    var goodsid = $(this).attr("goodsid");
    var skuid = $(this).attr("skuid");
    var key = productPrefix+goodsid+"_"+skuid;
    if(!hasMultisku(goodsid)){
        if(count < 1 ){
            storage.removeItem(key);
            addCart(1);//加入购物车
        }else{
            storage.removeItem(key);
            addCart(parseInt(count));//加入购物车
        }
        if(!count || isNaN(count)){
            $(this).attr("value","");
            $(this).val("");
            storage.removeItem(key);
            checkCoupon();
        }
        var promotype = $(this).attr("promotype");
        //直降商品数量校验
        if(promotype == promoTypeZhiJiang) {
        	var goodsname = $(this).attr("goodsname");
        	var purchasecount = $(this).attr("purchasecount");
        	//限购数为0时，表示不限购
        	if(parseInt(purchasecount)>0 && parseInt(count)>parseInt(purchasecount)) {
        		showAlertMsg(goodsname + " 限购" +purchasecount+"件！");
        		storage.removeItem(key);
                addCart(parseInt(purchasecount));//加入购物车
        	}
        }
        if(parseInt(count) > parseInt(store)){
            showAlertMsg(msgMoreProductStore);
            storage.removeItem(key);
            addCart(parseInt(store));//加入购物车
        }
        refreshCount();
    }
})

/* 多sku手动输入商品数量*/
$(".skunum").live("keyup", function(){
    var store = $(this).attr("store");
    var count = $(this).val();
    if(count < 1){
        $(this).val("1");
        $(this).attr("value","1");
    }else{
        $(this).val(count);
        $(this).attr("value",count);
    }
    if(!count || isNaN(count)){
        $(this).attr("value","0");
        $(this).val("");
        $(".sku-sure").addClass("add-cart-gray");
    }else{
        $(".sku-sure").removeClass("add-cart-gray");
    }
    
    var promotype = goodsSkuO2OList[skuIndex].type;
    //限购商品数量校验
    if(promotype == promoTypeZhiJiang) {
    	var goodsname = $(this).attr("goodsname");
    	var purchasecount = $(this).attr("purchasecount");
    	//限购数为0时，表示不限购
    	if(parseInt(purchasecount)>0 && parseInt(count)>parseInt(purchasecount)) {
    		showAlertMsg(goodsname + " 限购" +purchasecount+"件！");
    		$(this).val(purchasecount);
 	        $(this).attr("value",purchasecount);
    	}
    }
    if(parseInt(count) > parseInt(store)) {
		showAlertMsg(msgMoreProductStore);
        $(this).val(store);
        $(this).attr("value",store);
    }
})

/* 同一商品不同sku商品数量减少*/
function goodsSku(goodsid,count) {
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
$(".sku-plus").on("touchend",function(e){
    var n=parseInt($(this).prev().attr("value"));
    $(".sku-sure").removeClass("add-cart-gray");
    var promotype = goodsSkuO2OList[skuIndex].type;
	if(promotype == promoTypeZhiJiang) {
    	var goodsname = $(this).prev().attr("goodsname");
    	var purchasecount = $(this).prev().attr("purchasecount");
    	//限购数为0时，表示不限购
    	if(parseInt(purchasecount)>0 && n>=parseInt(purchasecount)){    		
    		showAlertMsg(goodsname + " 限购" +purchasecount+"件！");
    		return;
        } 
	}
    if((n+1) > $(this).prev().attr("store")){
        showAlertMsg(msgMoreProductStore);//库存不足提示
    }else{
        $(this).prev().attr("value",n+1);
        $(this).prev().val(n+1);
    }
    e.preventDefault();
});

/* sku商品数量减少*/
$(".sku-minus").on("touchend",function(e){
    var n=parseInt($(this).next().attr("value"));
    if(n>1){
        $(this).next().attr("value",n-1);
        $(this).next().val(n-1);
    }
    e.preventDefault();
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
    $(".sku-b input").attr("value","1").val("1");
})

/* 点击sku取消或加入购物车按钮*/
$(".sku-cancel").on("tap",function(){
    $(".sku-b input").attr("value","1").val("1");
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
    var virtualcatid = session.getItem("catId");
    var level = session.getItem("level");
    var data = {
        "psam":psam,
        "channelid":channelcodePF,
        "goodsid":goodsid,
        "virtualcatid":"",
        "level":""
    };
    var resultJson = ajaxCommon(urlWholesaleDetail, data);
    //resultJson = convertNullToEmpty(resultJson);
    if (resultJson._ReturnCode === returnCodeSuccess) {
        var _returnData = resultJson._ReturnData;

        /* 传递给购物车参数*/
        var goods = _returnData.goods;//商品详情
        goodName = goods.goodname;
        goodBigPic = goods.goodbigpic.split(",")[0];
        supplierId = goods.supplierid;
        supplierName = goods.suppliername;
        distributionflag  = goods.distributionflag;

        goodsSkuO2OList = _returnData.goodsSkuO2OList;//skuList
        //sku信息
        var skuContent = "";//规格
        var storeContent = "";
        if(goodsSkuO2OList.length > 1){
            skuContent += "<div class='sku-detail'>";
            for(var i = 0 ;i < goodsSkuO2OList.length; i ++){
                skuContent += "<em store='" +goodsSkuO2OList[i].store+ "' goodsname='" +goodName+"' promotype='" +goodsSkuO2OList[i].type+ "' purchasecount='" +goodsSkuO2OList[i].purchaseCount+ "'>" +goodsSkuO2OList[i].skuFrontDisNameStr+ "</em>";
                storeContent += "<div class='store-wrap'>";
                storeContent += "<div class='sku-a'>";
                storeContent += "<i class='stock'>库存：<em>" +goodsSkuO2OList[i].store+ "</em>件</i>";
                storeContent += "<i class='sku-solded'>已售：<em>" +goodsSkuO2OList[i].soldStore+ "</em></i>";
                storeContent += "</div>";
                storeContent += "<div class='sku-a'>";
                if(goodsSkuO2OList[i].type == promoTypeZhiJiang) {
                	storeContent += "<i class='sku-sale'>促销价：<em class='color'>￥" +goodsSkuO2OList[i].promotionPrice+ "</em></i>";
                    storeContent +=	"<i class='sku-market'>批发价：<em>￥" +goodsSkuO2OList[i].salePrice+ "</em></i>";
                }else{
                	storeContent += "<i class='sku-sale'>批发价：<em class='color'>￥" +goodsSkuO2OList[i].salePrice+ "</em></i>";
                    storeContent +=	"<i class='sku-market'>市场价：<em>￥" +goodsSkuO2OList[i].marketPrice+ "</em></i>";
                }
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
        
      //直降商品限购数量
        if(sku.eq("0").attr("promotype") == promoTypeZhiJiang) {
        	$(".sku-b input").attr("goodsname",sku.eq("0").attr("goodsname")).attr("promotype",sku.eq("0").attr("promotype")).attr("purchasecount",sku.eq("0").attr("purchasecount"));
        }else {
        	$(".sku-b input").removeAttr("goodsname").removeAttr("promotype").removeAttr("purchasecount");
        }
        
        $(".sku-sure").removeClass("add-cart-gray");
        if(sku.eq("0").attr("store") === "0"){
            $(".sku-sure").addClass("add-cart-gray");//如果库存为0，加入购物车按钮不可用
            $(".skunum").attr("readonly","readonly");//库存为0时，不允许手动输入
        }
        //点击sku显示相应的库存、价格、库存
        sku.each(function(index) {
            $(this).on("tap",function(){
                store_price.addClass("hidden").eq(index).removeClass("hidden");
                $(".sku-b input").attr("value","1");
                $(".sku-b input").val("1");
                $(".sku-b input").attr("store",$(this).attr("store"));
                
                //直降商品限购数量
                if(sku.eq(index).attr("promotype") == promoTypeZhiJiang) {
                	$(".sku-b input").attr("goodsname",sku.eq(index).attr("goodsname")).attr("promotype",sku.eq(index).attr("promotype")).attr("purchasecount",sku.eq(index).attr("purchasecount"));
                }else {
                	$(".sku-b input").removeAttr("goodsname").removeAttr("promotype").removeAttr("purchasecount");
                }
                
                if($(this).attr("store") === "0"){
                    $(".sku-sure").addClass("add-cart-gray");//如果库存为0，加入购物车按钮不可用
                    $(".skunum").attr("readonly","readonly");//库存为0时，不允许手动输入
                }else{
                    $(".sku-sure").removeClass("add-cart-gray");
                    $(".skunum").removeAttr("readonly");
                }
                skuIndex = index;
            });
        });
    }else{
        //接口返回错误
    	hasMultisku = "nogoods";
    }
    return hasMultisku;
}

/* 添加到购物车*/
function addCart(count,sku){
	var marketPrice ,salePrice;
    var goodsId = goodsSkuO2OList[skuIndex].tgoodInfoId;
    var o2oId = goodsSkuO2OList[skuIndex].o2oId;
    var skuFrontDisNameStr = goodsSkuO2OList[skuIndex].skuFrontDisNameStr;
    var tGoodSkuInfoId = goodsSkuO2OList[skuIndex].tgoodSkuInfoId;
    var store = goodsSkuO2OList[skuIndex].store;
    var promoType = goodsSkuO2OList[skuIndex].type;
    var purchasecount = goodsSkuO2OList[skuIndex].purchaseCount;
    var goodscount = count;
    if(goodsSkuO2OList[skuIndex].type == promoTypeZhiJiang) {
    	marketPrice = goodsSkuO2OList[skuIndex].salePrice;
        salePrice = goodsSkuO2OList[skuIndex].promotionPrice;
    }else{
    	marketPrice = goodsSkuO2OList[skuIndex].marketPrice;
        salePrice = goodsSkuO2OList[skuIndex].salePrice;
    }
    if(sku){
        goodscount = $(".skunum").val();    	   	
        if(!$(".skunum").val() || isNaN($(".skunum").val())){
        	goodscount = "1";
        }
    }
    goodscount = uniqueStorage(goodsId,tGoodSkuInfoId,goodscount);//同一sku商品数量做累加
	if(promoType == promoTypeZhiJiang){
    	if(parseInt(purchasecount)>0 && parseInt(goodscount) >= parseInt(purchasecount)){
    		showAlertMsg(goodName + " 限购" +purchasecount+"件！");
    		goodscount = purchasecount;  
    	}
    }
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
        "distributionflag":distributionflag,
        "promoType":promoType,
        "purchaseCount":purchasecount
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
    $("#to-pay").on("click",function(){
        if($(this).hasClass("add-cart-gray")){
            return;
        }        
        window.location.href = "fill.html?t=" + t;
    });
})

$(function(){
	$(".container").on("touchend",function(){
		$("input:focus").blur();
	});
})