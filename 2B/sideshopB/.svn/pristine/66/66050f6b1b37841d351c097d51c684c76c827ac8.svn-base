$(function() {
	
	var psam = psam || storage.getItem("psam");
	var data = {"psam":psam};
	var resultJson = ajaxCommon(urlShopInfo, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var result = resultJson._ReturnData;
		 //商户类型--基础店:459; 标准店:460; 旗舰店:461; 
		if (result.bizType == bizTypeJMX) {
			$(".shopupdate h2").find("s").text("基础店");
		}
		if (result.bizType == bizTypeBZX) {
			$(".shopupdate h2").find("s").text("标准店");
		}
		if (result.bizType == bizTypeQJX) {
			$(".shopupdate h2").find("s").text("旗舰店");
		}
		$("#shopkeeper").text(result.shoppername);//店主姓名
		$("#phoneno").text(result.phone);//店主手机
		$("#id_no").text(result.identityNo);//身份证号
		$(".id-card .zm").attr("src",result.identityFront); //身份证正面
		$(".id-card .fm").attr("src",result.identityReverse);//身份证反面
		$("#shopname").text(result.shopname);//店铺名称
		$("#address").text(result.province + "  " + result.city + "  " + result.cityarea + "  " + result.address);//小店地址
		var shopType = result.shopType;
		var type = getReturntShopType(shopType);
		$(".shop-type em").text(type);//店铺类型
		var businessLicence = result.businessLicence;
		if($.trim(businessLicence).length>0){
			$(".licence img").attr("src",businessLicence); //营业执照
		} else {
			businessLicence = "../../images/ico-licence.png";
		}
	}
});