var terminalType = "1"; //0:其他, 1:安卓, 2:苹果

/* 点击获取验证码60s文字切换*/
var wait = 60;//时间
function verificationCodetTime(obj) {//obj为按钮的对象
	if(wait == 0) {
		obj.removeAttr("disabled");
		obj.html("重新发送验证码");//改变按钮中value的值
		wait = 60;
		obj.removeClass("getVerificationCode");
	} else {	
		obj.addClass("getVerificationCode");
		obj.attr("disabled", "disabled");//倒计时过程中禁止点击按钮有问题？
		obj.html(wait + "秒" + "获取验证码");//改变按钮中value的值
		wait --;
		setTimeout(function() {
			verificationCodetTime(obj);//循环调用
		}, 1000);
	}
} 
/* 点击获取验证码发送手机号*/
$(function() {
	$("#getVerificationCode").on("tap", function(event) {
		var mobile = $.trim($("#mobile").val());
		//校验手机号格式
		if(mobile == "") {
			showAlertMsg(msgMobileNotEmpty);
			return;
		}
		if(!mobile.match(/^1\d{10}$/) || mobile.match(/^1[0-2]\d{9}$/)) {
			$("#mobile").val("");
			showAlertMsg(msgMobileError);
			return;
		}
		//只有刚开始和60后才能点击获取验证码按钮
		if(wait==60 || wait==0) {
			verificationCodetTime($("#getVerificationCode"));
			var mobile = $.trim($("#mobile").val());
			var data = {"phone":mobile, "type":"4"};
			var resultJson = ajaxCommon(urlGetCode, data);
		} else {
			return;
		}
		//阻止点击获取验证码时候点到输入框
		event.stopPropagation();
	});
	
	//点击注册按钮时候提交信息
	$("#btnRegister").on("touchend", function(e) {
		$(this).removeClass("focus");

		var mobile = $.trim($("#mobile").val());
		var verificationCode=$.trim($("#verificationCode").val()); //验证码

		var mobile = $("#mobile").val();
		if(mobile == "") {
			showAlertMsg(msgMobileNotEmpty);
			return;
		}
		if(!mobile.match(/^1\d{10}$/) || mobile.match(/^1[0-2]\d{9}$/)) {
			$("#mobile").val("");
			showAlertMsg(msgMobileError);
			return;
		}
		if(verificationCode == "") {
			showAlertMsg(msgVerificationEmpty);
			return;
		}

		var os = osType().toLowerCase();
		var data = {"mobile":mobile, "verificationCode":verificationCode};
		toRegister(data);

		e.preventDefault();
	});
});

/* 开通-下一步*/
function toRegister(data) {
	var mobile = $.trim($("#mobile").val());
	var verificationCode = $.trim($("#verificationCode").val());
	var resultJson = ajaxCommon(urlRegister, data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode === returnCode100007){
		//很抱歉，您输入的验证码错误，请确认后重新输入！
		showAlertMsg(msgReturn100007);
	}else if(_ReturnCode === returnCode100011) {
		//webService服务出错了
		showAlertMsg(msgReturn100011);
	}else if(_ReturnCode === returnCode100018){
		//开通业务-集团未注册
		window.location.href = "shopopen.html?mobile=" + mobile + "&verificationCode=" + verificationCode + "&flag=0&t=" + t;
	}else if(_ReturnCode === returnCode100020) {
		//该手机号已开通，请到登陆页登陆
		showAlertMsg(msgReturn100020);
	}else if(_ReturnCode === returnCode100021) {
		//开通业务-集团已经注册
		window.location.href = "shopopen.html?mobile=" + mobile + "&verificationCode=" + verificationCode + "&flag=1&t=" + t;
	}
}