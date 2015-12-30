$(function() {
	//获取加密所需参数--modulus和exponent
	getSecurityEM();

	//点击获取验证码
	var wait = 60;//设定时间
	function verificationCodetTime(obj) {
		if (wait == 0) {
			obj.removeAttr("disabled");
			obj.html("重新发送验证码");
			wait = 60;
			obj.removeClass("getVerificationCode");
		} else {	
			obj.addClass("getVerificationCode");
			obj.attr("disabled", "disabled");
			obj.html(wait + "秒" + "获取验证码");
			wait--;
			setTimeout(function() {
				verificationCodetTime(obj);
			},1000);
		}
	} 
	$("#getVerificationCode").on("tap", function(event) {
		if(wait == 60 || wait == 0){
			var phone = $.trim($("#mobile").val());
			var data = {"phone":phone, "type":"6"};
			var verificationCode = $.trim($("#verificationCode").val());
			var resultJson = ajaxCommon(urlGetCode,data);
			if(phone == ""){
				showAlertMsg(msgMobileNotEmpty);
				return;
			}
			if(!phone.match(/^1\d{10}$/) || phone.match(/^1[0-2]\d{9}$/)){
				$("#mobile").val("");
				showAlertMsg(msgMobileError);
				return;
			}	
			if(resultJson._ReturnCode === returnCodeSuccess){
				verificationCodetTime($("#getVerificationCode"));	
			}
		} 
		event.stopPropagation();
	});
	
	//进入小店
	$("#goin-shop").on("touchend", function(e) {
		$(this).removeClass("focus");

		var mobile = $.trim($("#mobile").val());
		var verificationCode = $.trim($("#verificationCode").val());
		var pwd = $.trim($("#pwd").val());
		var terminalType = "1"; //0:其他, 1:安卓, 2:苹果

		if(mobile == ""){
			showAlertMsg(msgMobileNotEmpty);
			return ;
		}
		if(!mobile.match(/^1\d{10}$/)||mobile.match(/^1[0-2]\d{9}$/)){
			$('#mobile').val('');
			showAlertMsg(msgMobileError);
			return ;
		}
		if(verificationCode == ""){	
			showAlertMsg(msgVerificationEmpty);
			return ;
		}
		if(pwd == ""){	
			showAlertMsg(msgPawFormat);
			return ;
		}
		if(!pwd.match(/^[A-Za-z0-9]{6,20}$/)){	
			showAlertMsg(msgPawFormat);
			return ;
		}

		var os = osType().toLowerCase();
		if(os.toLowerCase() === "ios") {
			terminalType = "2";
		}

		var data = {
			"mobile":mobile,
			"verificationCode":verificationCode,
			"pwd":pwdEncrpt(pwd),
			"terminalCode":"APP-M-Test",
			"terminalType":terminalType,
			"encFlag":"js"
		};
		toResetPwd(data);

		e.preventDefault();
	});
});

/* 忘记密码*/
function toResetPwd(data) {
	var resultJson = ajaxCommon(urlForgetpwd, data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode === returnCode100001){
		//很抱歉，该手机号未开通通身边小店，请先开通
		showAlertMsg(msgReturn100001);
	}else if(_ReturnCode === returnCode100007){
		//很抱歉，您输入的验证码错误，请确认后重新输入
		showAlertMsg(msgReturn100007);
	}else if(_ReturnCode === returnCode100011){
		//webService服务出错了
		showAlertMsg(msgReturn100011);
	}else if(_ReturnCode === returnCode100012){
		//更新密码成功
		var token = resultJson._ReturnData.token;
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
	}
}