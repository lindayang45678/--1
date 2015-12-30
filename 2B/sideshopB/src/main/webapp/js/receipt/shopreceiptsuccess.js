$(function() {
	$("#custreceiptconfirm").css("pointer-events","none");
	var resultconfirm = storage.getItem("shopReceiptConfirm");
	var resultJson = $.parseJSON(resultconfirm);
	resultJson = convertNullToEmpty(resultJson);

	var ret = '';
	var returnData = resultJson._ReturnData;
	var deliverycom = returnData.deliverycom.substr(0, 16);  //物流公司
	var logno = returnData.logno;  //物流单号
	var cusname = returnData.cusname;  //收货人
	var custelno = returnData.custelno;  //电话
	var channelcode = returnData.channelcode;  

	ret += '<div class="receipt-info info">';
	ret += '<div>物流公司<i>' + deliverycom + '</i></div>';
	ret += '<div>物流单号<i>' + logno + '</i></div>';
	ret += '<div>收货人<i>' + cusname + '</i></div>';
	ret += '<div>电话<i>' + custelno + '</i></div>';
	ret += '</div>';

	for(var i = 0; i < returnData.shouhuoList.length; i ++) {
		var shouhuoList = returnData.shouhuoList[i];
		var providername = shouhuoList.providername;  //供应商名称
		var ordertime = shouhuoList.ordertime.split(".")[0];  //下单时间
		var providerstate = shouhuoList.providerstate;  //供应商级订单状态

		ret += '<div class="receipt-goods">';
		ret += '<h3><span>' + providername + '</span><i class="fr">' + ordertime + '</i></h3>';
		ret += '<ul>';

		for (var j = 0; j < shouhuoList.sopglist.length; j ++) {
			var sopglist = shouhuoList.sopglist[j];
			var orderitemid = shouhuoList.orderitemid;  //子订单号
			var goodsid = sopglist.goodsid;  //商品ID
			var goodbigpic = sopglist.goodbigpic.split(";")[0];  //商品图片
			if(typeof(sopglist.tgoodinfopoolid)!="undefined" && sopglist.tgoodinfopoolid!="") {
				goodsid = sopglist.tgoodinfopoolid;
			}
			var goodbigpicUrl = urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic;  //商品图片地址
			var goodsname = sopglist.goodsname;  //商品名称
			var norms = sopglist.norms;  //商品规格
			var goodsnum = sopglist.goodsnum;  //商品数量
			var actualnum = sopglist.actualnum || "0";  //实收数量
			var waitingnum = parseInt(goodsnum) - parseInt(actualnum);  //待收数量
			var saleprice = sopglist.salePrice; //零售价
			var unifyFlg = sopglist.unifyFlg; //是否统一售价 0：否 1：是
			var publishFlg = sopglist.publishFlg; //是否可以发售 0：否 ，1：是  ，2：已发布

			ret += '<li class="clearfix" orderitemid="' + orderitemid + '">';
			//20150422---商品数量与实收数量不一致时，即为【部分收货】
			if(goodsnum != actualnum) {
				ret += '<a href="javascript:;" class="tips">待收货</a>';
			}
			ret += '<img src="' + goodbigpicUrl + '" onerror="productErrImg(this);" />';
			ret += '<div class="items nobr mt6">';
			ret += '<h4 class="c-black">' + goodsname + '</h4>';
			if(norms != "") {
				ret += '<p><i><em>' + norms + '</em></i></p>';
			}
			ret += '<p class="c-black"><i>购买数量：<em>' + goodsnum + '</em></i><i style="margin-left:14px;">待收数量：</i><em>' + waitingnum + '</em></p>';
			if(publishFlg!=0 && bizType!=bizTypeJMX) {
				ret += '<p class="c-black"><i>零售价格：<em>' + saleprice + '</em></i></p>';
			}
			ret += '</div>';
			ret += '</li>';
		}

		ret += '</ul>';
		ret += '</div>';
	}

	$(".receipt").append(ret);
	$("#custreceiptconfirm").css("pointer-events","auto");
});