/* 获取小店信息*/
$(function(){
	loadJs();
	osFixed();

	// 显示店铺的支付方式和配送方式
	showPayAndDelivery();

	var data = {"id":shopId};
	var resultJson = ajaxCommon(urlShopInfo, data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		var _ReturnData = resultJson._ReturnData;
		$("#shopname").html("").html(_ReturnData.shopname);
		$("#wechat").html("").html(_ReturnData.weixin_no);
		var announcement = _ReturnData.announcement;
		if(announcement == ""){
			announcement = msgShopAnnouncement;
		}
		$("#xd-announce").html("").html(announcement);
		$("#service-name1").html("").html(_ReturnData.shoppername);
		$("#service-phone1").html("").html(_ReturnData.phone);
		$("#shopadress").html("").html(_ReturnData.address);
		$("#shopper-name").html("").html(_ReturnData.shoppername);
		$("#shopper-phone").html("").html(_ReturnData.phone);
		if(_ReturnData.imagePath != ""){
			var imgContent = "<img src='" +_ReturnData.imagePath+ "' />";
			$(".editshop-bigimg").html(imgContent);
		}
		
		//配送客服
		var dispatcherlist = _ReturnData.dispatcherlist;
		var content = "";
		if(dispatcherlist.length > 0){
			for(var i = 0; i < dispatcherlist.length; i ++){
				content += "<div class='add-del'>";
				content += "<a href='javascript:;' class='del-image'></a>";
				content += "<div class='shopedit-2item del'>";
				content += "<div class='editline-n'>";
				content += "<span class='left1'>配送员:</span>";
				content += "<div class='leftborder'>";
				content += "<div contenteditable='true' class='max-1'>" +dispatcherlist[i].name+ "</div>";
				content += "<em class='edit'></em>";
				content += "</div>";
				content += "</div>";
				content += "<div class='editline-n'>";
				content += "<span class='left1'>联系电话:</span>";
				content += "<div class='leftborder'>";
				content += "<div contenteditable='true' class='max-1'>" +dispatcherlist[i].phone+ "</div>";
				content += "<em class='edit'></em>";
				content += "</div>";
				content += "</div>";
				content += "</div>";
				content += "</div>";
			}
			$(".pright-per7").before(content);
		}
	}else{
		//接口返回错误
	}
});

/* 增加配送客服*/
$(function(){
	$(".add-service").live("tap",function(){
		var content = "";
		content += "<div class='add-del'>";
		content += "<a href='javascript:;' class='del-image'></a>";
		content += "<div class='shopedit-2item del'>";
		content += "<div class='editline-n'>";
		content += "<span class='left1'>配送员:</span>";
		content += "<div class='leftborder'>";
		content += "<div contenteditable='true' class='max-1'></div>";
		content += "<em class='edit'></em>";
		content += "</div>";
		content += "</div>";
		content += "<div class='editline-n'>";
		content += "<span class='left1'>联系电话:</span>";
		content += "<div class='leftborder'>";
		content += "<div contenteditable='true' class='max-1'></div>";
		content += "<em class='edit'></em>";
		content += '</div>';
		content += '</div>';
		content += '</div>';
		content += '</div>';
		$(this).siblings(".pright-per7").before(content);
	});
	$(".del-image").live("tap",function(){
		$(this).parent(".add-del").remove();
	});
});

/* 小店门面图片*/
var imgInfoList = "";
/* 调用现相机上传小店门面图片*/
$(".upload-panels").on("tap",function(){
	cordova._native.camera.picker({width:300, height:300, quality:.5}, function(res){
		var imgContent = "<img src='" +res.data+ "' />";
		$(".editshop-bigimg").html("").html(imgContent);
		var data = {
				"token":userToken, 
				"isWriteTimage":"1", 
				"data":res.data, 
				"fileName":res.path, 
				"osType":osType(),
				"mobile":mobile,
				"targetType" : 8
		}
		var resultJson = ajaxCommon(urlUploadImg, data);
		if(resultJson._ReturnCode === returnCodeSuccess){
			imgInfoList = resultJson._ReturnData.success[0];
		}else{
			$(".editshop-bigimg").html("");
			showAlertMsg(resultJson._ReturnMsg);
		}
	})
})

/* 保存小店信息*/
$(".head-right").on("touchend",function(e){
	var shopname = $.trim($('#shopname').text());
	var shoppername = $.trim($('#shopper-name').text());
	var weixin_no = $.trim($('#wechat').text());
	var announcement = $.trim($('#xd-announce').text());
	if(shopname == ""){
		showAlertMsg(msgShopNameEmpty);
		return;
	}
	if(announcement.length > 200){
		showAlertMsg(msgMoreShopAnnouncement);
		return;
	}
	/*push数组*/
	var dispatcherlist = [];
	$(".add-del").each(function() {
		var pskfName = $.trim($(this).find("div[class='max-1']").eq("0").text());
		var pskfPhone = $.trim($(this).find("div[class='max-1']").eq("1").text());
		var kf = {"name":pskfName,"phone":pskfPhone};
		dispatcherlist.push(kf);
	});
	
	if(dispatcherlist.length > 0){
		for(var i = 0; i < dispatcherlist.length; i ++){
			var name = dispatcherlist[i].name;
			var phone = dispatcherlist[i].phone;
			var reg= /^1[3,4,5,8]{1}\d{9}$/; //校验手机号
			if(name == ""){
				showAlertMsg(msgServiceNameEmpty);
				return;
			}
			if(phone == ""){
				showAlertMsg(msgServicePhoneEmpty);
				return;
			}
			if(phone.length != 11 || !reg.test(phone)){
				showAlertMsg(msgServicePhoneError); 	
				return;
			}
		}
	}
	var data = {
			"id":shopId,
			"shopname":shopname,
			"shoppername":shoppername,
			"phone":mobile,
			"weixin_no":weixin_no,
			"announcement":announcement,
			"dispatcherlist":dispatcherlist,
			"imgInfoList":imgInfoList
	};
	var resultJson = ajaxCommon(urlEditshop, data);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		e.preventDefault();
		window.location.href="index.html?t="+t;
	}else{
		//信息格式填写有误，保存不了
	}
});

/**
 * 显示店铺的支付方式和配送方式
 */
function showPayAndDelivery() {
	//获取默认收货地址、支付方式和配送方式
	shipAddress();

	var homeDeliver = storage.getItem("homeDeliver");  //是否支持送货到家(0：否； 1：是)
	var isPickup = storage.getItem("isPickup");  //是否支持到店自提(0：否； 1：是)
	var isHomepay = storage.getItem("isHomepay");  //是否货到付款 (0：否； 1：是)
	var isOnehour = storage.getItem("isOnehour");  //是否支持1小时送货(0：否； 1：是)

	if(isHomepay == "1") {
		$("#dx2").attr("checked", "checked");
	}
	if(homeDeliver == "1") {
		$("#dx3").attr("checked", "checked");
	}
	if(isPickup == "1") {
		$("#dx4").attr("checked", "checked");
	}
}