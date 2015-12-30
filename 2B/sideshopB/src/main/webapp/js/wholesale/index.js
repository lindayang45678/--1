/* 初始化iScroll*/
var cateScroll;
function scrollCate() {
    cateScroll = new iScroll("menu-l",{ hScrollbar: false, vScrollbar: false });
}
//加载无更多商品
var scroll = "";
function pullUpAction() {	
	scroll = setTimeout(function () {
		pullUpHide();
	},200);
}
//加载无更多商品,2秒隐藏提示
function pullUpHide() {
	setTimeout(function () {
		var oldsroll = parseInt($('.sroll').css("top"));
		var sroll = oldsroll + scrollstap;
		if (maxy > goodswrap) {
			if (scrollstap <= 12) {
				$('.sroll').css('top',sroll);
				scrollstap += 2;
				pullUpHide();
			}
		}
		$(".pull-Up-Label").text('');
	},36);
}

var goodScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset;
var scrollstap = 0;//收起提示步长变量

pullUpEl = document.getElementById('pull-Up');
pullUpOffset = pullUpEl.offsetHeight;
var maxy = 0;
function scrollGood(){
    goodScroll = new iScroll("goods-wrap",{
    	topOffset: pullDownOffset,
        hScrollbar:false,
        vScrollbar:false,
        hScroll:false,
        useTransition:false,
        useTransform:false,
        checkDOMChanges:false,
        onScrollStart: function () {
        	goodScroll.refresh();
        },
    	onRefresh: function () {
    		maxy = $(".wholesale-goods").height();
    		if (goodswrap > maxy) {
    			$('.pull-Up-Label').text('');
    		}
    	},
        onScrollMove:function(){
        	clearTimeout(scroll);
            $(".num").blur();
            $(".wholesale-goods li a.proImg").live("click", function(e) {
                return false;
            });
            $(".wholesale-goods li h3").live("click", function(e) {
                return false;
            });
            if (this.y < (this.maxScrollY - 2) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				if (!(total >= count)) {
					pullUpEl.querySelector('.pull-Up-Label').innerHTML = '松手开始更新...';
				} else{
					pullUpEl.querySelector('.pull-Up-Label').innerHTML = '没有更多商品了';
				}
				this.maxScrollY = this.maxScrollY;
			}
        },
        onScrollEnd:function(){
    		if (maxy > goodswrap && this.maxScrollY >= this.y && hasNextPage == "false") {
    			pullUpAction();
    			return;
    		}
			if(getParamValueByName(window.location.href, "back") == "index"){
				cat_Id = session.getItem("catId");
			}
			if (pullUpEl.className.match('flip')) {
        		scrollstap = 0;
        		pullUpEl.className = 'loadon';
        		if (!(total >= count)) {
        			pullUpEl.querySelector('.pull-Up-Label').innerHTML = '玩命加载中...';
        		}
	            getGoodsList(cat_Id);
	            goodScroll.refresh();
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

//定义购物接收参数
var goodsSkuO2OList = "";
var skuIndex = 0;
var goodName = "";
var goodBigPic = "";
var supplierId = "";
var supplierName = "";
var distributionflag = "";
var goodswrap = null;
$(function(){
	//根据浏览器的高度计算iScroll的高度
    $("#menu-l").css("height",windowHeight-$("#wholesale-foot1").height()-$("header").height());
    goodswrap = windowHeight-$("#wholesale-foot1").height()-$("header").height();
    $(".central").css({"height":windowHeight-108,"overflow":"hidden","box-sizing":"border-box"});
    $("#goods-wrap").css({"height":goodswrap,"box-sizing":"border-box"});
	
	var url = window.location.href;
	var back = getParamValueByName(url, "back");
	var catId = getParamValueByName(url, "catid");
	var source = getParamValueByName(url, "source");
	var level = session.getItem("level") || "first";
	if(back == "index") {
		//商品详情返回列表页
		catId = session.getItem("catId");
		if(catId != null) {
			leftMenu(catId);
			//显示被选中的分类的父级分类
			if(level == "fourth"){
		         $(".left-l1 li").find(".white").parent().parent().removeClass("hidden");
		         $(".left-l1 li").find(".white").parent().parent().parent().parent().removeClass("hidden");
		         $(".left-l1 li").find(".white").parent().parent().parent().parent().parent().parent().removeClass("hidden");
			}else if(level == "third"){
		         $(".left-l1 li").find(".white").parent().parent().removeClass("hidden");
		         $(".left-l1 li").find(".white").parent().parent().parent().parent().removeClass("hidden");
			}else if(level == "second"){
				$(".left-l1 li").find(".white").parent().parent().removeClass("hidden");
			}
		}else {
			leftMenu("");
		}
	}else {
		if(catId != "") {
			//广告链接到分类
			if(storage.getItem(hasLogined) == null || storage.getItem(hasLogined) == "" || storage.getItem(hasLogined) == "0") {
				//没有登录时，跳转到登录页面
				window.location.href = "../accounts/login.html?source=" + source + "&refer=" + url + "&t=" + t;
			}else {
				session.setItem("catId",catId);
				session.setItem("level",level);
				leftMenu(catId);
			}
		}else {
			leftMenu("");
		}
	}
		
    storage.removeItem("searchparm");//清除搜索查询
    scrollGood();
    checkCoupon();
    useTip();
    osFixed();
});

/* 用户提示*/
function useTip(){
    var use_tip = storage.getItem("use_tips") || "";
    if(use_tip != "1"){
        $("#use-tips").removeClass("hidden");
    }else{
        $("#use-tips").addClass("hidden");
    }

    $("#use-tips").on("click",function(e){
        storage.setItem("use_tips","1");
        $(this).addClass("hidden");
        e.preventDefault();
    });
}

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
function leftMenu(catId){
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
        if(catId == ""){
            catId = _returnData[0].virtualCateId;
            session.setItem("catId",catId);
            session.setItem("level","first");
        }
        cat_Id = _returnData[0].virtualCateId;
        if(_returnData.length > 0){
            for(var i = 0; i< _returnData.length; i ++){
                var firstCate = _returnData[i];//一级虚拟分类
                var firstId = firstCate.virtualCateId;//一级虚拟分类ID
                var firstDisc = firstCate.virtualCateDisc;//一级虚拟分类名称
                content += "<li>";
                if(catId == firstId){
                    content += "<a href='javascript:;' catid=" +firstId+ " class='white' level='first'>" +firstDisc+ "<i class='sj-redr'></i></a>";
                }else{
                    content += "<a href='javascript:;' catid=" +firstId+ " level='first'>" +firstDisc+ "<i></i></a>";
                }
                content += "<ul class='left-l2 hidden'>";
                if(firstCate.child != null){
                    for(var j = 0;j < firstCate.child.length; j ++){
                        var secondCate = (firstCate.child)[j];//二级虚拟分类
                        var secondId = secondCate.virtualCateId;//二级虚拟分类ID
                        var secondDisc = secondCate.virtualCateDisc;//二级虚拟分类名称
                        content += "<li>";
                        if(catId == secondId){
                            content += "<a href='javascript:;' catid=" +secondId+ " class='white' level='second'>" +secondDisc+ "<i class='sj-redr'></i></a>";
                        }else{
                            content += "<a href='javascript:;' catid=" +secondId+ " level='second'>" +secondDisc+ "<i></i></a>";
                        }
                        /*隐藏第3级、4级菜单
                        content += "<ul class='left-l3 hidden'>";
                        if(secondCate.child != null){
                            for(var k = 0;k <  secondCate.child.length; k ++){
                                var thirdCate = (secondCate.child)[k];//三级虚拟分类
                                var thirdId = thirdCate.virtualCateId;//三级虚拟分类ID
                                var thirdDisc = thirdCate.virtualCateDisc;//三级虚拟分类名称
                                content += "<li>";
                                if(catId == thirdId){
                                    content += "<a href='javascript:;' catid=" +thirdId+ " class='white' level='third'>" +thirdDisc+ "<i class='sj-redr'></i></a>";
                                }else{
                                    content += "<a href='javascript:;' catid=" +thirdId+ " level='third'>" +thirdDisc+ "<i></i></a>";
                                }
                                content += "<ul class='left-l4 hidden'>";
                                if(thirdCate.child != null){
                                    for(var l = 0; l < thirdCate.child.length; l ++){
                                        var fourthCate = (thirdCate.child)[l];//四级虚拟分类
                                        var fourthId = fourthCate.virtualCateId;//四级虚拟分类ID
                                        var fourthDisc = fourthCate.virtualCateDisc;//四级虚拟分类名称
                                        content += "<li>";
                                        if(catId == fourthId){
                                            content += "<a href='javascript:;' catid=" +fourthId+ " class='white' level='fourth'>" +fourthDisc+ "<i class='sj-redr'></i></a>";
                                        }else{
                                            content += "<a href='javascript:;' catid=" +fourthId+ " level='fourth'>" +fourthDisc+ "<i></i></a>";
                                        }
                                        content += "</li>";
                                    }
                                }
                                content += "</ul>";
                                content += "</li>";
                            }
                        }
                        content += "</ul>";
                        */
                        content += "</li>";
                    }
                }
                content += "</ul>";
                content += "</li>";
                container.html("").append(content);
                //scrollGood();
            }
            getGoodsList(catId);
            //显示被选中的分类的子分类
            $(".left-l1 li").find(".white").siblings("ul").removeClass("hidden");
        }else{
            //无左侧分类
            //显示暂无批发进货商品图标
            $('.wholesale').hide();
            showNoDataMsg(msgNoWholesale);
        }
    }else{
        //接口返回错误
    }
    
}

/* 批发进货虚拟分类点击事件*/
$(function(){
    scrollCate();
    $("#menu-l a").live("click", function() {
    	total = 0;
    	$(".pull-Up-Label").text("");
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
var pageSize = 50;
var hasNextPage = true;//是否有下一页
var cat_Id = "";//虚拟分类ID
var total = null;//累计请求回来的条数
var count = null;//分类下商品总算
/* 点击虚拟分类显示相应商品列表*/
$(function(){
    $("#menu-l a").live("click", function() {
        if(goodScroll){
            goodScroll.destroy();
        }
        var virtualCateId = $(this).attr("catid");
        cat_Id = virtualCateId;
        page = 0;
        hasNextPage = true;
        $(".wholesale-goods").html("");
        scrollGood();
        session.setItem("catId",cat_Id);
        session.setItem("level",$(this).attr("level"));
        getGoodsList(cat_Id);
    });
})

/* 获取批发进货商品列表*/
function getGoodsList(catid){
    var container = $(".wholesale-goods");
    var content = "";
    var timer = 1000;
    if(page == 0){
    	timer = 0;
    }
    setTimeout(function () {
        if(hasNextPage){
        	pullUpEl.querySelector('.pull-Up-Label').innerHTML = '';
        	var level = session.getItem("level") || "first";
            page ++;
            var data = {
                "psam":psam,
                "channelid":channelcodePF,
                "virtualcatid":catid,
                "searchparm":"",
                "page":page,
                "pageSize":pageSize,
                "level":level
            };
            var resultJson = ajaxCommon(urlWholesaleList, data);
            resultJson = convertNullToEmpty(resultJson);
            if (resultJson._ReturnCode === returnCodeSuccess) {
            	count = resultJson._ReturnData.count;
                var goodList = resultJson._ReturnData.list;
                total += goodList.length;
                if(goodList.length > 0){
                	$(".none-data").hide();
                    goodList = sortGoodsAndAdvert(goodList);//商品排序
                    for(var i = 0; i < goodList.length; i ++){
                    	content += "<li class='clearfix'>";
                    	content += "<a class='proImg fl' href='javascript:;' goodsid='" +goodList[i].tgoodinfoid+ "'>";
                    	content += 	"<img src='" +urlImage+ "/" +goodList[i].tgoodinfoid+ "/" +imgw300+ "/" +goodList[i].goodbigpic.split(",")[0]+ "' onerror='productErrImg(this);'/>";
                    	if(goodList[i].issoldout == 0){
                            content += "<i class='soldout'></i>";
                        }
                        content += "</a>";
                        content += "<div class='goods-right fr'>";
                        content += "<h3 goodsid='" +goodList[i].tgoodinfoid+ "'>";
                        if(goodList[i].type == promoTypeZhiJiang) {
                        	content += "<span class='color drop'>直降</span>";
                        }
                        content += goodList[i].goodname;
                        content += "</h3>";
                        if(goodList[i].type == promoTypeZhiJiang) {
                        	content += "<div class='price-box'><i class='salePrice'>¥" +goodList[i].promotionPrice+ "</i><del>¥" +goodList[i].saleprice_o2o+ "</del></div>";
                        }else{
                        	content += "<div class='price-box'><i class='salePrice'>¥" +goodList[i].saleprice_o2o+ "</i><del>¥" +goodList[i].marketprice+ "</del></div>";
                        }
                        if(goodList[i].issoldout == 1){
                            content += "<div class='buttom-jiajian'>";
                            content += "<a href='javascript:;' class='jiajian jia' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' store='" +goodList[i].store+ "' goodsname='" +goodList[i].goodname+"' promotype='" +goodList[i].type+ "' purchasecount='" +goodList[i].purchaseCount+ "'>+</a>";
                            if(goodList[i].ismoresku == "1"){
                                content += "<input type='tel' class='num hidden' id='" +goodList[i].tgoodinfoid+ "' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "' store='" +goodList[i].store+ "' readonly='readonly'/>"
                            }else{
                                content += "<input type='tel' class='num hidden' id='" +goodList[i].tgoodinfoid+ "' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "' ismoresku='" +goodList[i].ismoresku+ "' store='" +goodList[i].store+ "' goodsname='" +goodList[i].goodname+"' promotype='" +goodList[i].type+ "' purchasecount='" +goodList[i].purchaseCount+ "' onfocus='this.focused=true;this.select();' onmouseup='if(this.focused){this.focused=false;return false;}'/>";
                            }

                            content += "<a href='javascript:;' class='jiajian jian hidden' goodsid='" +goodList[i].tgoodinfoid+ "' skuid='" +goodList[i].tgoodskuinfoid+ "'>&minus;</a>";
                            content += "</div>";
                        }
                        content += "</div>";
                        content += "</li>"
                    }
                    container.append(content);
                    refreshCount();
                    if ( total >= count) {
                    	 hasNextPage = false;
                    } else {
                    	 pullUpEl.querySelector('.pull-Up-Label').innerHTML = '上拉加载更多...';
                    }
                    goodScroll.refresh();
                }else{
                	 hasNextPage = false;
                     pullUpAction();
                }
            }else{
            	if(page == 1) {
            		showNoDataMsgtext(msgNoGoodsForCat,$("#goods-wrap"));//该分类下商品！
            	} else {
            		hasNextPage = false;
               	 	pullUpAction();
            	}
            }
        } else {
        	hasNextPage = false;
        	pullUpAction();
        }
    },timer);
    
    var virtualcatid = session.getItem("catId");
	var level = session.getItem("level");
    
    $(".wholesale-goods li a.proImg").live("click", function(e) {
        var goodsid = $(this).attr("goodsid");
        e.preventDefault();
        window.location.href = "prodetail.html?goodsid=" + goodsid + "&virtualcatid=" + virtualcatid + "&level=" + level +"&t=" + t;
    });

    $(".wholesale-goods li h3").live("click", function(e) {
        var goodsid = $(this).attr("goodsid");
        e.preventDefault();
        window.location.href = "prodetail.html?goodsid=" + goodsid + "&virtualcatid=" + virtualcatid + "&level=" + level +"&t=" + t;
    });
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
$(".sku-sure").live("click",function(){
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
$(".sku-cancel").on("click",function(){
    $(".sku-b input").attr("value","1").val("1");
    $("#sku").addClass("hidden");
    $(".masker").addClass("hidden");
});

/* 点击sku中的属性进行选择*/
$(".sku-detail em").live("click",function(){
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
        "virtualcatid":virtualcatid,
        "level":level
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
        /* 传递给购物车参数*/

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
        	sku.eq(index).on("click",function(){
                store_price.addClass("hidden").eq(index).removeClass("hidden");
                $(".sku-b input").attr("value","1");
                $(".sku-b input").val("1");
                $(".sku-b input").attr("store",sku.eq(index).attr("store"));
                
                //直降商品限购数量
                if(sku.eq(index).attr("promotype") == promoTypeZhiJiang) {
                	$(".sku-b input").attr("goodsname",sku.eq(index).attr("goodsname")).attr("promotype",sku.eq(index).attr("promotype")).attr("purchasecount",sku.eq(index).attr("purchasecount"));
                }else {
                	$(".sku-b input").removeAttr("goodsname").removeAttr("promotype").removeAttr("purchasecount");
                }
                
                if(sku.eq(index).attr("store") === "0"){
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

/* 商品搜索*/
$(".wholesale-search").on("click",function(e){
    $(".orders-search").removeClass("hidden");
    e.preventDefault();
})

$(".orders-search span").on("click",function(e){
    $(".orders-search").addClass("hidden");
    $(".orders-search input").val("");
    e.preventDefault();
})

$(".orders-search div").on("click",function(e){
    e.stopPropagation();
})

$(".orders-search").on("click",function(e){
    $(".orders-search").addClass("hidden");
    $(".orders-search input").val("");
    e.preventDefault();
})

/* 键盘搜索事件*/
$(".orders-search input").on("keyup",function(e){
    if(e.keyCode == 13){
        var searchparm = $.trim($('.orders-search input').val());
        if(searchparm == ""){
            showAlertMsg(msgProductNameEmpty);
            return;
        }
        $(".orders-search input").val("");
        storage.setItem("searchparm", searchparm);
        window.location.href = encodeURI(encodeURI("searchsuccess.html?searchparm=" + searchparm + "&t=" + t));
    }
})

/* 点击搜索事件*/
$(".good-search").on("click",function(e){
    $(".orders-search").removeClass("hidden");
    e.preventDefault();
    var searchparm = $.trim($('.orders-search input').val());
    if(searchparm == ""){
    	$('.orders-search input').blur();
        showAlertMsg(msgProductNameEmpty);
        return;
    }
    $(".orders-search input").val("");
    storage.setItem("searchparm", searchparm);
    window.location.href = encodeURI(encodeURI("searchsuccess.html?searchparm=" + searchparm + "&t" + t));
})

$(".orders-search .back").on("click",function(){
	$("input:focus").blur();
});

$(function(){
	/*$(".skunum").on("touchend", function(e) {
		e.preventDefault();
		e.stopPropagation();
	});*/
	
	$(".container").on("touchend",function(){
		$("input:focus").blur();
	});
});