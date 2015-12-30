
$(function(){
	var url = window.location.href;
	var verificationCode = getParamValueByName(url, "logno");
	var detail = storage.getItem("shopReceiptQuery");
	var resultJson = $.parseJSON(detail);
	resultJson = convertNullToEmpty(resultJson);
	var ret = '';
	var returnData = resultJson._ReturnData;
	ret+= '<h2 class="f-18 center c-black">验证通过，可以收货！</h2>';
	ret+=  '<div class="receipt-info">';
	ret+= '<div><i class="c-black">该订单是本店批发商品订单：</i></div>';
	ret+= '<div><i>快递公司：</i><em>'+ returnData.deliverycom +'</em></div>';
	ret+= '<div><i>物流单号：</i><em>'+ returnData.logno +'</em></div>';
	ret+= '<div><i>收货人姓名：</i><em>'+ returnData.cusname +'</em></div>';
	ret+= '<div><i>收货人手机：</i><em>'+ returnData.custelno +'</em></div>';
	ret+= '</div>';
	ret+= '<div class="receipt-info">';
	ret+= '<div><i class="c-black">本订单，可收货的商品有：</i></div>';
	for (var i = 0;i<returnData.shouhuoList.length;i++) {
		var lista=returnData.shouhuoList[i];//显示的每组订单号
		ret+='<div><i>订单号：</i><em>'+ lista.torderproviderid +'('+ lista.providername +')</em></div>';
		ret+='<ul>';
		for (var j = 0;j<lista.sopglist.length;j++) {
			var listb=lista.sopglist[j];//子订单号
			ret+= '<li orderitemid="'+listb.orderitemid+'">';
			ret+= '<span>'+ listb.goodsname +'</span>';
			ret+= '<p>'+ listb.norms +'</p>';
			ret+= '<p><i>数量：</i><span>'+ listb.goodsnum +'</span></p>';
			if(returnData.channelcode == 27 || returnData.channelcode == channelcodePF) {
				ret+= '<p class="receipt-actual">';
				ret+= '<i>实收：</i>';
				ret+= '<a href="javascript:;">&#8722;</a>';
				ret+= '<input type="text" readonly="readonly" value="'+ listb.goodsnum +'"/>';
				ret+= '<a href="javascript:;">+</a>';
				ret+= '</p>';
			}
			ret+= '</li>';
		}
		ret+= '</ul>';
	}
	ret+= '</div>';
	$('.receipt').html('').append(ret);
	//点击确认取货按钮
	$('#custreceiptconfirm').on("tap", function() {
		var subOrders=new Array();//存放子订单号和实收数量
		$('li').each(function(i){
			var json={"orderitemid":$(this).attr('orderitemid'),"actualnum":$(this).find('input').val()};
			subOrders.push(json);
		});
		var bat = JSON.stringify(subOrders);
		var data = {"netno":netNo,"mobile":mobile,"logno":verificationCode,"data":bat};
		var resultJson = ajaxCommon(urlReceiptConfirm, data);
		//var resultJson = {"_ReturnCode":"000000","_ReturnData":{"shouhuoList":[{"torderproviderid":"150130180026845000011434","sopglist":[{"goodsname":"测试生鲜半成品","goodsnum":"3","norms":"骷髅","actualnum":null,"orderitemid":"1533117"}],"providername":"测试供应商_海燕","providerstate":102},{"torderproviderid":"150129152352592000011434","sopglist":[{"goodsname":"测试生鲜运费","goodsnum":"2","norms":"咖啡色","actualnum":null,"orderitemid":"1532954"}],"providername":"测试供应商_海燕","providerstate":102}],"custelno":"15010108727","cusname":"戚明明","logno":"11161220","channelcode":"25","deliverycom":null},"_ReturnMsg":null};	
		if(resultJson._ReturnCode === returnCodeSuccess) {
			resultJson =JSON. stringify(resultJson);
			storage.setItem("shopReceiptConfirm",resultJson);
			window.location.href = "shopreceiptsuccess.html?&t=" + t;	
		} else{
			showAlertMsg(resultJson._ReturnMsg);
		}
	});	
});
//收货数量加减
$(function() {
	$(".receipt-actual").find("a:eq(0)").on("tap", function() {
		var num = $(this).next().attr("value");
		if (num >= 1) {
			$(this).next().attr("value", num - 1);
		}
	});
	$(".receipt-actual").find("a:eq(1)").on("tap", function() {
		var num = parseInt($(this).prev().attr("value"));
		if(num<$(this).parent().prev().find('span').html()){
			$(this).prev().attr("value", (1 + num));
		}
	});
});