$(function(){
	StoreInfo.defaultAddress();
	//保存
	$("#goin-shop").on("click", function(e) {
		$(this).attr("disabled", "disabled");
		StoreInfo.validateInfo();
		e.preventDefault();
	});
})

/**
 * 修改店铺信息
 */
var StoreInfo = {
	shopkeeper : "",
	shopname : "",
	shopcity : "",
	shopprovince : "",
	shoparea : "",
	defaultProvCode : "",
	defaultCityCode : "",
	defaultAreaCode : "",
	shopaddr : "",
	resultJson : "",
	shopid : "",
	//取shipAddress的数据
	defaultAddress : function(){
		shipAddress();
		shipaddress = $.parseJSON(storage.getItem("shipaddress"));
		shopkeeper = shipaddress.shipname;//店主姓名
		shopname = shipaddress.shopname;//店铺名称
		shopcityid = shipaddress.shipcityid //默认省份
		shopprovinceid = shipaddress.shipprovinceid;//默认市
		shopareaid = shipaddress.shipareaid;//默认区
		shopprovince= shipaddress.shipprovince;
		shopcity = shipaddress.shopcity;
		shoparea = shipaddress.shoparea;
		shopaddr = shipaddress.shipaddr;//详细地址
		shopid = shipaddress.shopId;
		this.initCityArea();
		this.info();
	},
	//初始化 店铺 地址省市区
	initCityArea : function(){
		getCityNameByCode("provinceid", "0", shopprovinceid);
		getCityNameByCode("cityid", $("#provinceid").val(), shopcityid);
		getCityNameByCode("areaid", $("#cityid").val(), shopareaid);
		$("#provinceid").change(function() {
			getCityNameByCode("cityid", $(this).children('option:selected').val(), "");
			getCityNameByCode("areaid", $("#cityid").children('option:selected').val(), "");
		});
		$("#cityid").change(function() {
			getCityNameByCode("areaid", $(this).children('option:selected').val(), "");
		});
		/*$("#areaid").change(function() {
		
		});*/
	},
	//初始化 店主姓名 店铺名称 详细地址
	info : function() {
		$("#shopername").val(shopkeeper);
		$("#shoptitle").val(shopname);
		$("#shopaddress").val(shopaddr);
	},
	//提交更新的信息
	validateInfo : function() {
		shopkeeper = $.trim($("#shopername").val());
		shopname =  $.trim($("#shoptitle").val());
		shopaddr = $.trim($("#shopaddress").val());
		shopprovince= $("#provinceid").find("option:selected").text();
		shopcity = $("#cityid").find("option:selected").text();
		shoparea = $("#areaid").find("option:selected").text();
		defaultProvCode = $("#provinceid").find("option:selected").val();
		defaultCityCode = $("#cityid").find("option:selected").val();
		defaultAreaCode = $("#areaid").find("option:selected").val();
		if(shopkeeper == ""){
			showAlertMsg(msgShoperNameEmpty);
			return;
		}
		if(shopname == ""){
			showAlertMsg(msgShopeTitleEmpty);
			return;
		}
		if(shopaddr == ""){
			showAlertMsg(msgShopAddressEmpty);
			return;
		} 
		if(shopaddr.length < 3){
			showAlertMsg(msgDetailAddressEmpty);
			return;
		}
		this.submitInfo();
	},
	//接口调用
	submitInfo : function() {
		var data = {
			"psam" : psam,
			"shopId" : shopid,
			"mobile" : mobile,
			"shopTitle" : shopname,
			"shopperName" : shopkeeper,
			"provinceCode" : defaultProvCode,
			"provinceName" : shopprovince,
			"cityCode" : defaultCityCode,
			"cityName" : shopcity,
			"areaCode" : defaultAreaCode,
			"areaName" : shoparea,
			"address" : shopaddr
		}
		showAlertMsg("更新中...");
		resultJson = convertNullToEmpty(ajaxCommon(urlUptShopAddInfo,data));
		if (resultJson._ReturnCode === returnCodeSuccess) {//升级数据提交
			showAlertMsg(resultJson._ReturnMsg);
			window.location.href = "setting.html?t="+ t;
		} else { 
			showAlertMsg(resultJson._ReturnMsg);
		}
	}
}