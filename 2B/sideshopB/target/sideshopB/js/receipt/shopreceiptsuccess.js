$(function() {
	var url = window.location.href;
	var resultconfirm = storage.getItem("shopReceiptConfirm");
	var resultJson = $.parseJSON(resultconfirm);
	resultJson = convertNullToEmpty(resultJson);
	var ret = '';
	var returnData = resultJson._ReturnData;
	ret += '<div><i>物流单号：</i><em>' +returnData.logno+ '</em></div>';
	for(var i = 0; i < returnData.shouhuoList.length; i ++) {
		var lista=returnData.shouhuoList[i];//供应商订单
		ret+= '<div class="receipt-infosuccessnub"><i>订单号：</i><em>' +lista.torderproviderid+ '</em>';
		if((returnData.channelcode == 27 || returnData.channelcode == channelcodePF) && lista.providerstate == 103) {
			ret += '<em>（部分收货）</em></div>';
		}
		if((returnData.channelcode == 27 || returnData.channelcode == channelcodePF) && lista.providerstate == 104) {
			ret += '<em>（全部收货）</em></div>';
		}
		ret += '<div class="c-black">本快递，已收货的商品有：</div>';
		for(var j = 0;j < lista.sopglist.length; j ++) {
			var listb=lista.sopglist[j];//子订单
			ret+= '<ul>';
			ret+= '<li>';
			ret+= '<i>' +listb.goodsname+ '</i>';
			ret+= '<p>' +listb.norms+ '</p>';
			ret+= '<p>';
			ret+= '<i>数量：</i><em>' +listb.goodsnum+ '</em>';
			if(returnData.channelcode == 27 || returnData.channelcode == channelcodePF) {	
				ret+= '<i>实收：</i><em>' +listb.actualnum+ '</em>';
			}
			ret+= '</p>';
			ret+= '</li>';
			ret+= '</ul>';
		}	
	}
	$('.receipt-info').html('').append(ret);
});