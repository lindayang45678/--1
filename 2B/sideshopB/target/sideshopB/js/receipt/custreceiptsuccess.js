$(function() {
	var url = window.location.href;
	var orderDetail = storage.getItem("custReceiptConfirm");
	var resultJson = $.parseJSON(orderDetail);
	resultJson = convertNullToEmpty(resultJson);
	var ret = '';
	var returnData = resultJson._ReturnData;
	ret+= '<h2 class="f-18 color center">恭喜您，取货成功！</h2>';
	ret+= '<div class="receipt-info">';
	ret+= '<div><i>订单号：</i><em>'+returnData.orderproviderid+'</em>';
	if(returnData.state == 103) {
		ret+= '<em>（部分取货）</em>';
	}
	ret+= '</div>';
	ret+= '<div><i>收货人姓名：</i><em>'+returnData.cusname+'</em></div>';
	ret+= '<div><i>收货人手机：</i><em>'+returnData.custelno+'</em></div>';
	ret+= '<div>';
	ret+= '<i>本订单，已取货的商品有：</i>';
	ret+= '<ul>';
	for(var i=0;i<returnData.qoclist.length;i++) {	
		ret+= '<li><em>'+returnData.qoclist[i].goodsname+'</em><p>'+returnData.qoclist[i].norms+'</p><p>数量：'+returnData.qoclist[i].goodsnum+'</p></li>';	
	}
	ret+= '</ul>';
	ret+= '</div>';
	ret+= '</div>';
	$('.receipt').html('').append(ret);
});