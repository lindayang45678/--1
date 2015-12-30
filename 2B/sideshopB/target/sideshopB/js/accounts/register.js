$(function() {
	loadJs();
});

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
		obj.html(wait + "s获取验证码");//改变按钮中value的值
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
		var pushToken = ""; //推送Token

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
		if(os != "other") {
			cordova._native.system.deviceInfo(function(res) {
				platform = res.platform;
				deviceId = res.deviceId;
				terminalCode = deviceId;
				subChannelId = res.subChannelId;
				objectId = res.objectId;
				pushToken = res.token;

				if(platform.toLowerCase() === "ios") {
					terminalType = "2";
				}

				storage.setItem("platform", platform);
				storage.setItem("deviceId", deviceId);
				storage.setItem("subChannelId", subChannelId);
				storage.setItem("objectId", objectId);
				storage.setItem("pushToken", pushToken);

				//向后端验证注册
				var data = {"mobile":mobile, "verificationCode":verificationCode};
				toRegister(data);
			});
		} else {
			var data = {"mobile":mobile, "verificationCode":verificationCode};
			toRegister(data);
		}

		e.preventDefault();
	});
});

/* 激活*/
function toRegister(data) {
	var resultJson = ajaxCommon(urlRegister, data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode === returnCode100001){
		//很抱歉，该手机号未开通电商业务，请联系拉卡拉客户经理开通
		showAlertMsg(msgReturn100001);
	}else if(_ReturnCode === returnCode100002) {
		//很抱歉，该手机号不是店主手机号，请检查后重新输入
		showAlertMsg(msgReturn100002);
	}else if(_ReturnCode === returnCode100007){
		//很抱歉，您输入的验证码错误，请确认后重新输入
		showAlertMsg(msgReturn100007);
	}else if(_ReturnCode === returnCode100011){
		//webService服务出错了
		showAlertMsg(msgReturn100011);
	}else if(_ReturnCode === returnCode100013){
		//允许激活-激活成功，并可设置登录密码
		$(".register-pop-ok").addClass("hidden");
		$("#register-psd").removeClass("hidden");
	}else if(_ReturnCode === returnCode100014){
		//该手机号已激活，无需重复激活
		$(".register-pop-ok").addClass("hidden");
		$("#register-already").removeClass("hidden");
	}else if(_ReturnCode === returnCode100015){
		//恭喜您，激活成功！您可用拉卡拉的账号密码登录
		$(".register-pop-ok").addClass("hidden");
		$("#register-ok").removeClass("hidden");
	}
}

/* 激活成功，设置登录密码*/
$("#register-password").live("tap",function(){
	var mobile = $.trim($("#mobile").val());
	var pwd = $.trim($("#pwd").val());
	if(pwd == "") {
		showAlertMsg(msgPawFormat);
		return;
	}
	if(!pwd.match(/^[A-Za-z0-9]{6,20}$/)){	
		showAlertMsg(msgPawFormat);
		return ;
	}
	var platform = "";
	var terminalType = "1"; //0:其他, 1:安卓, 2:苹果
	cordova._native.system.deviceInfo(function(res) {
		platform = res.platform;
		pushToken = res.token;
		if(platform.toLowerCase() === "ios") {
			terminalType = "2";
		}
	});
	//密码加密
	cordova._native.encrypt.pwdEncrpt(pwd, function(encryptPwd) {
		var data = {
				"mobile":mobile, 
				"pwd":encryptPwd, 
				"terminalCode":pushToken, 
				"terminalType":terminalType
		};
		toSetPwd(data);
	});
})

function toSetPwd(data){
	var resultJson = ajaxCommon(urlRegisterpwd, data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode === returnCode100001){
		//很抱歉，该手机号未开通电商业务，请联系拉卡拉客户经理开通
		showAlertMsg(msgReturn100001);
	}if(_ReturnCode === returnCode100002){
		//很抱歉，该手机号不是店主手机号，请检查后重新输入
		showAlertMsg(msgReturn100002);
	}else if(_ReturnCode === returnCode100009){
		var userToken = resultJson._ReturnData.token;
		var userName = resultJson._ReturnData.userName;
		var userType = resultJson._ReturnData.userType;
		var bizType = resultJson._ReturnData.bizType;  //商户类型--加盟型:459; 标准型:460; 旗舰型:461;
		var ecNetNo = resultJson._ReturnData.ecNetNo;  //电商网店编号
		var psam = resultJson._ReturnData.psam;  //PSAM编号

		storage.setItem("userToken", userToken);
		storage.setItem("mobile", data.mobile);
		storage.setItem("password", data.pwd);
		storage.setItem(hasLogined, "1");
		storage.setItem("bizType", bizType);  //商户类型存放到storage中
		storage.setItem("psam", psam);  //PSAM存放到storage中
		storage.setItem("netNo", ecNetNo);  //网点编号存放到storage中

		if(typeof(cordova)!=undefined && typeof(cordova)!="undefined") {
			//记录登录状态--true:代表已登录，false:代表未登录
			cordova._native.saveInfo.set(hasLogined, true);
			cordova._native.saveInfo.set("mobile", data.mobile);
			cordova._native.saveInfo.set("userToken", userToken);
		}
		window.location.href = "../homepage/index.html?t=" + t;
	}else if(_ReturnCode === returnCode100011){
		//webService服务出错了
		showAlertMsg(msgReturn100011);
	}
}