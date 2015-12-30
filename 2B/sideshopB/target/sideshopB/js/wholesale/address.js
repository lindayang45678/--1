var shipaddress = storage.getItem("shipaddress");
shipaddress = $.parseJSON(shipaddress);
var shipname = shipaddress.shipname;
var shipmobile = shipaddress.shipmobile;
var shipprovinceid = shipaddress.shipprovinceid;
var shipprovince = shipaddress.shipprovince;
var shipcityid = shipaddress.shipcityid;
var shipcity = shipaddress.shipcity;
var shipareaid = shipaddress.shipareaid;
var shiparea = shipaddress.shiparea;
var shipaddr = shipaddress.shipaddr;
var shipid = "";

/* 添加地址超过5个的时候弹窗否则添加*/
$(".fill-add-address").on("tap",function(){
	var n=$(".fill-address li").length;
	if(n >= 5) {	
		$(".popup").removeClass("hidden");
		$(".masker").removeClass("hidden");
	} else {	
		$(".fill-edit-address").removeClass("hidden");
		$(".fill-add-address").addClass("hidden");
		$(".btn-save-addressa").removeClass("hidden");
		$(".btn-save-addressb").addClass("hidden");
		$(".fill-edit-address div").html("");
		$(".edit-area div").html(shipprovince+" "+shipcity+" "+shiparea+"");
	};
});
/* 弹窗点击确定的时候弹窗消失*/
$(".popup-btn").on("tap",function(){	
	$(".popup").addClass("hidden");
	$(".masker").addClass("hidden");
});

/* 常用联系人收货地址*/
$(function(){
	getShipAddress();
});

/* 获取收货地址列表*/
function getShipAddress(){
	var data = {
			"mobile":mobile,
			"token":userToken,
			"netno":netNo
	};
	var container = $(".fill-address");
	var content = "";
	content += "<li class='f-14'>";
	content += "<a href='javascript:;' class='use-address' id='default'>";
	content += "<em><i class='address-name'>" +shipname+ "</i>&nbsp;<i class='address-phone'>" +shipmobile+ "</i></em>";
	content += "<em><i class='address-area'>" +shipcity+ "&nbsp;" +shiparea+ "&nbsp;" +shipprovince+ "</i></em>";
	content += "<em><i class='address-detail'>" +shipaddr+ "</i></em>";
	content += "</a>";
	content +="</li>";
	var resultJson = ajaxCommon(urlShopCommAddr, data);
	if (resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		if(_ReturnData.length > 0){
			for (var i = 0; i < _ReturnData.length; i++) {
				content += "<li class='f-14'>";
				content += "<a href='javascript:;' class='use-address'>";
				content += "<em><i class='address-name'>"+_ReturnData[i].shipname+ "</i>&nbsp;<i class='address-phone'>"+_ReturnData[i].shipmobile+"</i></em>";
				content += "<em><i class='address-area'>"+_ReturnData[i].shipcity+ "&nbsp;"+_ReturnData[i].shiparea+"&nbsp;"+_ReturnData[i].shipprovince+"</i></em>";
				content += "<em><i class='address-detail'>"+_ReturnData[i].shipaddr+"</i></em>";
				content += "</a>";
				content += "<div class='fill-close' id='"+_ReturnData[i].id+"'></div>";
				content += "<a href='javascript:;' class='fill-edit' id='" +_ReturnData[i].id+ "'>编辑</a>";
				content +="</li>";
			}
		}else{
			//暂无收货地址
		}
	} else {
		//接口返回错误
	}
	container.html("").append(content);
}

$(".fill-edit").live("tap",function(){	
	shipid = $(this).attr("id");
	//编辑的是第几个进行标记
	$(".fill-edit").parent().removeClass("mark");
	$(this).parent().addClass("mark");
	$(".fill-edit-address").removeClass("hidden");
	$(".fill-add-address").addClass("hidden");
	$(".btn-save-addressb").removeClass("hidden");
	$(".btn-save-addressa").addClass("hidden");
	$(".edit-name div").text($(".mark").find(".address-name").text());
	$(".edit-phone div").text($(".mark").find(".address-phone").text());
	$(".edit-area div").text(shipprovince+" "+shipcity+" "+shiparea+"");
	$(".edit-address div").text($(".mark").find(".address-detail").text());
});

/* 添加收货人地址*/
$(".btn-save-addressa").on("tap",function(){
	var shipname = $(".edit-name div").text();
	var shipaddr = $(".edit-address div").text();
	var shipmobile = $(".edit-phone div").text();
	if(validatePhone(shipmobile)){
		var data = {
				"netno":netNo,
				"shipareaid":shipareaid,
				"shiparea":shiparea,
				"shipprovinceid":shipprovinceid,
				"shipprovince":shipprovince,
				"shipcityid":shipcityid,
				"shipcity":shipcity,
				"shipaddr":shipaddr,
				"shipname":shipname,
				"shipmobile":shipmobile,
				"shipinvoicetitle":"",
				"shipinvoicetype":"",
				"longitude":"",
				"latitude":""
		};
		var resultJson = ajaxCommon(urlshopAddAddress, data);
		if(resultJson._ReturnCode === returnCodeSuccess){
			getShipAddress();
		}
		$(".fill-edit-address").addClass("hidden");
		$(".fill-add-address").removeClass("hidden");
	}
})

/* 编辑收货人地址*/
$(".btn-save-addressb").on("tap",function(){
	var shipname = $(".edit-name div").text();
	var shipaddr = $(".edit-address div").text();
	var shipmobile = $(".edit-phone div").text();
	if(validatePhone(shipmobile)){
		var data = {
				"id":shipid,
				"netno":netNo,
				"shipareaid":shipareaid,
				"shiparea":shiparea,
				"shipprovinceid":shipprovinceid,
				"shipprovince":shipprovince,
				"shipcityid":shipcityid,
				"shipcity":shipcity,
				"shipaddr":shipaddr,
				"shipname":shipname,
				"shipmobile":shipmobile,
				"shipinvoicetitle":"",
				"shipinvoicetype":"",
				"longitude":"",
				"latitude":""
		};
		var resultJson = ajaxCommon(updateAddress, data);
		if(resultJson._ReturnCode === returnCodeSuccess){
			getShipAddress();
		}
		$(".fill-edit-address").addClass("hidden");
		$(".fill-add-address").removeClass("hidden");
		$(".mark").removeClass("mark");
	}
})

/* 删除收货地址*/
$(".fill-close").live("tap",function(){
	$(this).parent().remove();
	var id = $(this).attr("id");
	var data = {
		"id":id	
	};
	var resultJson = ajaxCommon(deleteAddress, data);
	if(resultJson._ReturnCode === returnCodeSuccess){
		getShipAddress();
	}else{
		getShipAddress();
	}
});

/* 使用收货地址*/
$(".use-address").live("touchend",function(e){
	if($(this).attr("id") != "default"){
		var shipname = $(this).find(".address-name").text();
		var shipmobile = $(this).find(".address-phone").text();
		var shipaddr = $(this).find(".address-detail").text();
		var address = {
				"shipname":shipname,
				"shipmobile":shipmobile,
				"shipprovinceid":shipprovinceid,
				"shipprovince":shipprovince,
				"shipcityid":shipcityid,
				"shipcity":shipcity,
				"shipareaid":shipareaid,
				"shiparea":shiparea,
				"shipaddr":shipaddr
		}
		storage.setItem("customaddress",JSON.stringify(address));
	}else{
		storage.removeItem("customaddress");
	}
	e.preventDefault();
	window.location.href = "fill.html?t="+t;
})

/*手机校验*/
function validatePhone(phone){
	phone = $.trim(phone);
	var reg= /^1[3,4,5,8]{1}\d{9}$/; //校验手机号
	if(phone == ""){
		showAlertMsg(msgAddressPhoneNotEmpty);
		return false;
	}else if(phone.length != 11 || !reg.test(phone)){
		showAlertMsg(msgAddressPhoneError);
		return false;
	}else{
		return true;
	}
}