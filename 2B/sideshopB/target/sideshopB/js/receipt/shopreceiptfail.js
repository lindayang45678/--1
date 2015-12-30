$(function() {
	var url = window.location.href;
	var verificationCode = getParamValueByName(url, "logno");
	var ret='';
	ret+='<div>很抱歉，未找到与物流单号<span>'+verificationCode+'</span>匹配的订单号！请核对物流单号后，重新输入！</div>';
	$('.receipt-info').html('').append(ret);
});