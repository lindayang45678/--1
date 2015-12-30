$(function() {
	//获取订单来源去掉
	var url = window.location.href;
	var source = getParamValueByName(url, "source");
	storage.setItem("source", source);

	//计算屏幕高度
	storage.setItem("windowHeight", $(window).height());

	//获取加密所需参数--modulus和exponent
	getSecurityEM();

	$("#btnLogin").on("touchend", function(e) {
		$(this).removeClass("focus");

		var mobile = $.trim($("#mobile").val());
		var pwd = $.trim($("#pwd").val());
		var terminalType = "1"; //0:其他, 1:安卓, 2:苹果
		if(mobile == "") {
			showAlertMsg(msgMobileNotEmpty);
			return false;
		}
		if(mobile.length != 11) {
			showAlertMsg(msgMobileError);
			return false;
		}
		var patrn = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if(!patrn.test(mobile)) {
			showAlertMsg(msgMobileError);
			return false;
		}
		if(pwd == "") {
			showAlertMsg(msgPawFormat);
			return;
		}
		if(!pwd.match(/^[A-Za-z0-9]{6,20}$/)) {
			showAlertMsg(msgPawFormat);
			return;
		}

		var os = osType().toLowerCase();
		if(os.toLowerCase() === "ios") {
			terminalType = "2";
		}

		var data = {
			"mobile":mobile,
			"pwd":pwdEncrpt(pwd),
			"terminalCode":"APP-M-Test",
			"terminalType":terminalType,
			"encFlag":"js"
		};
		toLogin(data);

		e.preventDefault();
	});
});

/* 登录*/
function toLogin(data) {
	var resultJson = ajaxCommon(urlLogin, data);
	resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode === returnCode100001){
		//很抱歉，该手机号未开通通身边小店，请先开通
		showAlertMsg(msgReturn100001);
	}else if(_ReturnCode === returnCode100004) {
		//很抱歉，您输入的手机号或密码错误，请检查后重新输入
		showAlertMsg(msgReturn100004);
	}else if(_ReturnCode === returnCode100008) {
		//登录成功
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
		
		shipAddress(psam);

		var url = window.location.href;
		var refer = getParamValueByName(url, "refer");
		if(refer != "") {
			window.location.href = refer;
		} else {
			window.location.href = "../homepage/index.html?t=" + t;
		}
	}else if(_ReturnCode === returnCode100011){
		//webService服务出错了
		showAlertMsg(msgReturn100011);
	}else if(_ReturnCode === returnCode100017){
		//拒绝访问
		showAlertMsg(msgReturn100017);
	}
}