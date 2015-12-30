// 默认省份code
var defaultProvCode = session.getItem("defaultProvCode") || "110000";
// 默认市Code
var defaultCityCode = session.getItem("defaultCityCode") || "110100";
// 默认区Code
var defaultAreaCode = session.getItem("defaultAreaCode") || "110101";
//手机号
var mobile = "";
//验证码
var verificationCode = "";
//集团是否注册 0：未注册 ,1:已注册
var flag = "0";

$(function() {
	//获取加密所需参数--modulus和exponent
	getSecurityEM();

	var url = window.location.href;
	mobile = getParamValueByName(url, "mobile");
	verificationCode = getParamValueByName(url, "verificationCode");
	flag = getParamValueByName(url, "flag");
	$("#mobile").val(mobile);
	$("#verificationCode").val(verificationCode);
	if(flag == "1"){
		//集团已注册
		$("#pwd").removeAttr("placeholder").attr("type","text").attr("value",msgRegisterPwd).attr("readonly","readonly");
	}

	var txtshopername = session.getItem("txtshopername") || "";
	var txtshopaddress = session.getItem("txtshopaddress") || "";
	var txtopencode = session.getItem("txtopencode") || "";
	$("#shopername").val(txtshopername);
	$("#shopaddress").val(txtshopaddress);
	$("#opencode").val(txtopencode);
	if(txtshopaddress != "") {
		$("#shopadd").hide();
		$("#address").removeClass("address").addClass("line");
		$("#shopaddress").parent().show();
		initProvCityArea();
	}

	//协议详情点击事件
	$("#terms").on("click", function() {
		session.setItem("txtshopername", $("#shopername").val());
		session.setItem("txtshopaddress", $("#shopaddress").val());
		session.setItem("txtopencode", $("#opencode").val());
		window.location.href = "terms.html?mobile=" + mobile + "&verificationCode=" + verificationCode + "&flag=" + flag;
	});
});

$("#shopadd").on("click", function(){
	$(this).hide();
	$("#address").removeClass("address").addClass("line");
	$("#shopaddress").parent().show();
	initProvCityArea();
})

function initProvCityArea() {
	//初始化 省市区
	getCityNameByCode("provinceid", "0", defaultProvCode);
	getCityNameByCode("cityid", $("#provinceid").val(), defaultCityCode);
	getCityNameByCode("areaid", $("#cityid").val(), defaultAreaCode);
	$("#provincename").attr("value",$("#provinceid").find("option:selected").text());
	$("#cityname").attr("value",$("#cityid").find("option:selected").text());
	$("#areaname").attr("value",$("#areaid").find("option:selected").text());
	$("#provinceid").change(function() {
		getCityNameByCode("cityid", $(this).children('option:selected').val(), "");
		getCityNameByCode("areaid", $("#cityid").children('option:selected').val(), "");
		$("#provincename").attr("value",$(this).find("option:selected").text());
		$("#cityname").attr("value",$("#cityid").find("option:selected").text());
		$("#areaname").attr("value",$("#areaid").find("option:selected").text());

		session.setItem("defaultProvCode", $("#provinceid").val());
		session.setItem("defaultCityCode", $("#cityid").val());
		session.setItem("defaultAreaCode", $("#areaid").val());
	});
	$("#cityid").change(function() {
		getCityNameByCode("areaid", $(this).children('option:selected').val(), "");
		$("#cityname").attr("value",$(this).find("option:selected").text());
		$("#areaname").attr("value",$("#areaid").find("option:selected").text());

		session.setItem("defaultProvCode", $("#provinceid").val());
		session.setItem("defaultCityCode", $("#cityid").val());
		session.setItem("defaultAreaCode", $("#areaid").val());
	});
	$("#areaid").change(function() {
		$("#areaname").attr("value",$(this).find("option:selected").text());

		session.setItem("defaultProvCode", $("#provinceid").val());
		session.setItem("defaultCityCode", $("#cityid").val());
		session.setItem("defaultAreaCode", $("#areaid").val());
	});
}

$("#goin-shop").on("click", function(e) {
	if(!preventRealClick()) {return;}  //防止二次点击
	if($(this).hasClass("disabled")) {
		return;
	}

	var mobile = $.trim($("#mobile").val());
	var pwd = $.trim($("#pwd").val());
	var shopperName = $.trim($("#shopername").val());
	var shopTitle =  $.trim($("#shoptitle").val());
	var provinceCode = $("#provinceid").attr("value");
	var provinceName = $("#provincename").attr("value");
	var cityCode = $("#cityid").attr("value");
	var cityName = $("#cityname").attr("value");
	var areaCode = $("#areaid").attr("value");
	var areaName = $("#areaname").attr("value");
	var address = $.trim($("#shopaddress").val());
	var registerCode = $.trim($("#opencode").val());
	var pushToken = "";
	var terminalType = "1"; //0:其他, 1:安卓, 2:苹果
	var os = osType().toLowerCase();
	if(os.toLowerCase() === "ios") {
		terminalType = "2";
	}

	if(flag == "0"){
		if(pwd == ""){
			showAlertMsg(msgPwdNotEmpty);
			return;
		}
		if(!pwd.match(/^[A-Za-z0-9]{6,20}$/)) {
			showAlertMsg(msgPawFormat);
			return;
		}
	}
	if(shopperName == "") {
		showAlertMsg(msgShoperNameEmpty);
		return;
	}
	if(shopTitle == ""){
		showAlertMsg(msgShopeTitleEmpty);
		return;
	}
	if(address == "") {
		showAlertMsg(msgShopAddressEmpty);
		return;
	}

	$(this).html("开通中...");
	$(this).addClass("disabled");

	var data = {
		"mobile":mobile,
		"pwd":pwdEncrpt(pwd),
		"shopperName":shopperName,
		"shopTitle":shopTitle,
		"provinceCode":provinceCode,
		"provinceName":provinceName,
		"cityCode":cityCode,
		"cityName":cityName,
		"areaCode":areaCode,
		"areaName":areaName,
		"address":address,
		"registerCode":registerCode,
		"terminalCode":pushToken,
		"terminalType":terminalType,
		"encFlag":"js"
	};
	toRegister(data);

	e.preventDefault();
});

function toRegister(data) {
	var openBtnObj = $("#goin-shop");

	var resultJson = ajaxCommon(urlRegisterpwd, data);
	//resultJson = convertNullToEmpty(resultJson);
	var _ReturnCode = resultJson._ReturnCode;
	if(_ReturnCode === returnCode100011) {
		//webService服务出错了
		showAlertMsg(msgReturn100011);
		openBtnObj.html("开&nbsp;&nbsp;通");
		openBtnObj.removeClass("disabled");
	}else if(_ReturnCode === returnCode100015) {
		//恭喜您，注册成功！
		$("#register-already").show();
		openBtnObj.html("已开通");
	}else if(_ReturnCode === returnCode100019) {
		//开通编码输入错误，请重新输入
		showAlertMsg(msgReturn100019);
		openBtnObj.html("开&nbsp;&nbsp;通");
		openBtnObj.removeClass("disabled");
	}else if(_ReturnCode === returnCode100022) {
		//很抱歉，该地址正在维护，暂不能开通，请1天后再开通
		showAlertMsg(msgReturn100022);
		openBtnObj.html("开&nbsp;&nbsp;通");
		openBtnObj.removeClass("disabled");
	}
}