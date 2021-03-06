$(function() {
	var data = {"phone":mobile};
	var resultJson = ajaxCommon(urlShopList, data);
	resultJson = convertNullToEmpty(resultJson);
	//resultJson = {"_ReturnCode":"000000","_ReturnMsg":"SUCCESS","_ReturnData":[{"address":"大兴区兴丰大街18号星城商厦五层","id":180355,"token":"gLRPVaFrrzGFicYzmllNluk31cT3ebXDcfxaJ+bGM8c=","shoppername":"李颖","homeDeliver":0,"psam":"CBC8D8A800000001","phone":"13001021253","shopname":"国美电器有限公司","netNo":"DCDCD0001","city":"北京","latitude":null,"longitude":null,"distince":0,"province":"北京市","cityarea":"大兴区"}]};
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		var shopId = "";  //小店ID
		var shoppername = "";  //店主姓名
		var phone = "";  //店主手机号
		var shopname = "";  //小店名称
		var province = "";  //小店地址--省
		var city = "";  //小店地址--市
		var cityarea = "";  //小店地址--区
		var address = "";  //小店地址--具体地址
		var psam = "";  //PSAM
		var netNo = "";  //网点编号

		//该店主只有一个小店
		if(_ReturnData.length == 1) {
			shopId = _ReturnData[0].id;
			psam = _ReturnData[0].psam;
			netNo = _ReturnData[0].netNo;
			storage.setItem("hasMoreShops", "0");
			window.location.href = "../homepage/index.html?shopId=" + shopId + "&psam=" + psam + "&netNo=" + netNo + "&t=" + t;
		} else {
			var result = "";
			for(var i = 0; i < _ReturnData.length; i ++) {
				shopId = _ReturnData[i].id;
				shoppername = _ReturnData[i].shoppername;
				phone = _ReturnData[i].phone;
				shopname = _ReturnData[i].shopname;
				province = _ReturnData[i].province;
				city = _ReturnData[i].city;
				cityarea = _ReturnData[i].cityarea;
				address = _ReturnData[i].address;
				psam = _ReturnData[i].psam;
				netNo = _ReturnData[i].netNo;

				result += "<li><a href='../homepage/index.html?shopId=" + shopId + "&psam=" + psam + "&netNo=" + netNo + "&t=" + t + "'>";
				result += "店主姓名：<i>" + shoppername + "</i><br/>";
				result += "店主手机号：<i>" + phone + "</i><br/>";
				result += "小店名称：<i>" + shopname + "</i><br/>";
				result += "<i>小店地址：" + province + "&nbsp;&nbsp;" + city + "&nbsp;&nbsp;" + cityarea + "&nbsp;&nbsp;" + address + "</i>";
				result += "</a></li>";
			}
			$("#my-shops").html(result);
			storage.setItem("hasMoreShops", "1");
		}
	} else {
		showAlertMsg(msgGetShopsFail);
	}
});