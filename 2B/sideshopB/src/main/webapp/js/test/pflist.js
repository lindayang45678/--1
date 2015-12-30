var url = window.location.href;
var back = getParamValueByName(url, "back");
var catId = getParamValueByName(url, "catid");
var source = getParamValueByName(url, "source");
var level = session.getItem("level") || "first";
psam = getParamValueByName(url, "psam");
mobile = getParamValueByName(url, "mobile");

//定义购物接收参数
var goodsSkuO2OList = "";
var skuIndex = 0;
var goodName = "";
var goodBigPic = "";
var supplierId = "";
var supplierName = "";
var distributionflag = "";

$(function() {	
	leftMenu("");
});

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
                        content += "</li>";
                    }
                }
                content += "</ul>";
                content += "</li>";
                container.html("").append(content);
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
    $("#menu-l a").live("click", function() {
    	$(".pull-Up-Label").text("");
        $("#menu-l").find("a").removeClass("white").find("i").removeClass("sj-redr");//去掉所有选中样式
        $(this).addClass("white").find("i").addClass("sj-redr");//添加当前选中样式
        if($(this).siblings("ul").hasClass("hidden")){
            $(this).siblings("ul").removeClass("hidden");
        }else{
            $(this).siblings("ul").addClass("hidden");
            $(this).parent().find("ul").addClass("hidden");
            if($(this).siblings("ul").length != 0){
                $(this).removeClass("white").find("i").removeClass("sj-redr");//去掉当前选中样式
            }
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
})

var page = 0;
var pageSize = 50;
var hasNextPage = true;//是否有下一页
var cat_Id = "";//虚拟分类ID

/* 点击虚拟分类显示相应商品列表*/
$(function(){
    $("#menu-l a").live("click", function() {
        var virtualCateId = $(this).attr("catid");
        cat_Id = virtualCateId;
        page = 0;
        hasNextPage = true;
        $(".wholesale-goods").html("");
        session.setItem("catId",cat_Id);
        session.setItem("level",$(this).attr("level"));
        getGoodsList(cat_Id);
    });
})

/* 获取批发进货商品列表*/
function getGoodsList(catid){
    var container = $(".wholesale-goods");
    var content = "";

    if(hasNextPage){
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
            var goodList = resultJson._ReturnData.list;
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
                    content += "<h3 goodsid='" +goodList[i].tgoodinfoid+ "'>" +goodList[i].goodname+ "</h3>";
                    content += "<div class='price-box'><i class='salePrice'>¥" +goodList[i].saleprice_o2o+ "</i><del>¥" +goodList[i].marketprice+ "</del></div>";
                    content += "</div>";
                    content += "</li>"
                }
                container.append(content);
            }else{
            	 hasNextPage = false;
            }
        }else{
        	if(page == 1) {
        		showNoDataMsgtext(msgNoGoodsForCat,$("#goods-wrap"));//该分类下商品！
        	} else {
        		hasNextPage = false;
        	}
        }
    } else {
    	hasNextPage = false;
    }
}