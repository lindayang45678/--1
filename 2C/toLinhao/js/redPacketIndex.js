// JavaScript Document
/*手机号为空和非11位提示*/
$(function(
	var phone=$('.phone-num').val();
	if(phone === ''){
		alert('手机号不能为空！');
	}
	else if(phone !=11){
		alert('手机号填写有误，请核对后重新输入号！');
	}
));