$(function() {
	loadJs();
	shipAddress();
	var shipaddress = $.parseJSON(storage.getItem("shipaddress"));
	data = {"phoneno": mobile};
	var resultJson = ajaxCommon(urlGetshopupdate,data);
	resultJson = convertNullToEmpty(resultJson);
	var result = resultJson._ReturnData;
	$(".shopupdate .sb s").html(resultJson._ReturnMsg);
	var shopkeeper = result.shopkeeper;
	$("#shopkeeper").text(shopkeeper);//店主姓名
	var phoneno = result.phoneno;
	$("#phoneno").text(phoneno);//店主手机
	var id_no = result.id_no;
	$("#id_no").attr("value",id_no);//身份证号
	var idimage_positive = $(".id-card .zm").attr("src",result.idimage_positive); //身份证正面
	//var idimage_positive = "../../ico-idcard-p.png";
	var idimage_negative = $(".id-card .fm").attr("src",result.idimage_negative);//身份证反面
	//var idimage_negative = "../../ico-idcard-r.png"
	var positive_file = ""; //身份证正面
	var negative_file = "";//身份证反面
	var shopname = result.shopname;
	$("#shopname").text(shopname);//店铺名称
	var district = result.district;//店铺地区
	var community = result.community;//店铺社区
	var address = result.address;
	$(".shop-address em").text(district + "  " + community+ "  " + address );
	var businessLicence = result.businessLicence;
	if($.trim(businessLicence).length>0) {
		$(".licence img").attr("src",businessLicence); //营业执照
	} else {
		businessLicence = "../../images/ico-licence.png";
	}
	//var businessLicence = "../../ico-licence.png";
	var licence_file = "";//营业执照
	var longitude = "";//经度
	var latitude = "";//纬度
	var remark = storage.getItem("bizType");/* 商户类型--加盟型:459; 标准型:460; 旗舰型:461; */
	var imgdata = {"zm":"","fm":"","licence":""};
	var imgpath = {"zm":"","fm":"","licence":""};
	var shopType = result.shopType;//店铺类型
	$(".shop-type select").val(shopType);
	$(".shop-type em").text(getReturntShopType(shopType));
	$(".xg").on("click", function () {
		$(this).hide();
		$(".shopupdate footer").removeClass("hidden");
		$(".disabled").addClass("hidden");
		$(".shopupdate .sb").addClass("hidden");
		$(".shop-position").removeClass("hidden");
		$(".shop-position").show();
		var baiduMapFrame = document.getElementById("baiduMapFrame");
		baiduMapFrame.src = baiduMapAPI;

		$(".shop-map").removeClass("hidden");
		$(".shop-type em").remove();
		$(".shop-type select").removeClass("hidden");
		/*$(".shop-address1").removeClass("hidden");
		$(".shop-address").addClass("hidden");*/
		/*$(".shop-address1 em").text(district + "  " + community);*/
		$("#address").parent().parent().removeClass("hidden");
		$("#address").val(address);//小店地址
		loadmap();//地图调用
	});

	//调用相机,可改成绑定事件
	$("#zm").on("click", function() {
		invokePhoto("zm");
	});
	$("#fm").on("click", function() {
		invokePhoto("fm");
	});
	$("#licence").on("click", function() {
		invokePhoto("licence");
	});
	$(".goto").on("tap", function() {
		$(".shop-position iframe").remove();
	});
	$(".protocol").on("click", function() {
		$("#protocol").removeClass("hidden");
		$("#protocol header").removeClass("hidden");
		$(this).attr("href","#protocol");
	});
	$("#back").on("click", function(e) {
		$("#protocol").addClass("hidden");
		$("#protocol header").addClass("hidden");
		e.preventDefault();
	});
	//按钮颜色
	function changeColor() {
		var bottomColor = true;
		if (!document.getElementById) return false;
		if (!document.getElementsByTagName) return false;
		var oMain = document.getElementById("shopupdate");
		var oInput = oMain.getElementsByTagName("input");
		for (var i = 0; i < oInput.length; i++) {
			var thisinput = oInput[i].value;
			console.log(thisinput);
			if (thisinput == "") {
				bottomColor = false;
			}
		}
		if (imgdata.fm == "" || imgdata.zm == "" || imgdata.licence == "") {
			bottomColor = false;
		}
		/*var oImg = oMain.getElementsByTagName("img");
		for (var j = 0; j < oImg.length; j++) {
			var objSrc = oImg[j].src;
			console.log(objSrc.indexOf("ico-idcard-p.png"));
			if (objSrc.indexOf("ico-idcard-p.png")>=0 || objSrc.indexOf("ico-idcard-r.png")>=0 || objSrc.indexOf("ico-licence.png")>=0) {
				bottomColor = false;
			}
		}*/
		/*if (bottomColor) {
			$(".tj").removeClass("btn-gray").addClass("btn-red");
		}*/
		
	}
	//调用相机/相册，并上传图片
	function invokePhoto(photo) {
		if(parseInt($(window).height()) < parseInt(windowHeight)) {
			//隐藏键盘
			cordova._native.navigation.hideKeyboard();
		}

		//调用相册/相机
		cordova._native.camera.picker({width:300, height:300, quality:.5}, function(res){
			//新上传图片容器
			var imgView = document.getElementById(photo);
			imgView.src = res.data;
			if (photo == "zm") {
				imgdata.zm = res.data;
				imgpath.zm = res.path;
			}
			if (photo == "fm") {
				imgdata.fm = res.data;
				imgpath.fm = res.path;
			}
			if (photo == "licence") {
				imgdata.licence = res.data;
				imgpath.licence = res.path;
			}
			idimage_positive = imgdata.zm;
			idimage_negative = imgdata.fm;
			businessLicence = imgdata.licence;
			positive_file = imgpath.zm;
			negative_file = imgpath.fm;
			licence_file = imgpath.licence;
		});
	}
	//地图
	function loadmap(){
		var ifm = document.getElementById("baiduMapFrame");
		var src = ifm.src;
		if(src.indexOf("?psam=") > -1) {
			ifm.src = src.substring(0,src.lastIndexOf("?psam=")+6) + psam + "#save";
		} else {
			ifm.src = src + "?psam=" + psam + "#save";
		}
	}
	//提交
	$(".submit").on("tap", function () {
		//小店类型
		shopType = $(".shop-type option:selected").val();
		id_no = $("#id_no").val();//身份证号
		idimage_positive = $(".id-card .zm").attr('src'); //身份证正面
		idimage_negative = $(".id-card .fm").attr('src');//身份证反面
		address = $("#address").val();//小店地址
		businessLicence = $(".licence img").attr("src");//营业执照
		if (shopkeeper == "") {
			showAlertMsg(msgShoperNameEmpty);
			return false;
		}
		var patrn = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		if(!patrn.test(phoneno)) {
			showAlertMsg(msgMobileError);
			return false;
		}
		if (!CheckID(id_no)){
			return false;
		}
		if (idimage_positive == "../../images/ico-idcard-p.png" ) {
			showAlertMsg(msgPositiveEmpty);
			return false;
		}
		if (idimage_negative == "../../images/ico-idcard-r.png") {
			showAlertMsg(msgNegativeEmpty);
			return false;
		}
		if (shopname == "") {
			showAlertMsg(msgShopNameEmpty);
			return ;
		}
		if (address == "") {
			showAlertMsg(msgShopAddressEmpty);
			return false;
		}
		//if (businessLicence == "../../images/ico-licence.png") {
			//showAlertMsg(msgBusinessLicenceEmpty);
			//return false;
		//}
		//$(".pop-up-box").removeClass("hidden");
		//请求接口
		$(".submit").attr("disabled", "disabled");
		var data = {
			"id_no":id_no,
			"idimage_positive": idimage_positive,
			"idimage_negative": idimage_negative,
			"positive_file": positive_file,
			"negative_file": negative_file,
			"phoneno": phoneno,
			"shopkeeper": shopkeeper,
			"shopname": shopname,
			"district": district,
			"community": community,
			"address": address,
			"businessLicence": businessLicence,
			"licence_file":licence_file,
			"longitude": longitude,
			"latitude": latitude,
			"remark": bizTypeBZX,
			"shopType":shopType
		};
		var resultJson = ajaxCommon(urlShopupdate,data);
		resultJson = convertNullToEmpty(resultJson);
		if (resultJson._ReturnCode === returnCodeSuccess) {//升级数据提交
			$(".shop-position iframe").remove();
			showAlertMsg(msgSubmitSuccess);
			window.location.href = "setting.html";
		} else {
			showAlertMsg(resultJson._ReturnMsg);
		}
	});
	/*//请求接口
	$("#confirm").on("click", function() {
		$("#confirm").attr("disabled", "disabled");
		var data = {
			"id_no":id_no,
			"idimage_positive": idimage_positive,
			"idimage_negative": idimage_negative,
			"positive_file": positive_file,
			"negative_file": negative_file,
			"phoneno": phoneno,
			"shopkeeper": shopkeeper,
			"shopname": shopname,
			"district": district,
			"community": community,
			"address": address,
			"businessLicence": businessLicence,
			"licence_file":licence_file,
			"longitude": longitude,
			"latitude": latitude,
			"remark": bizTypeBZX,
			"shopType":shopType
		};
		var resultJson = ajaxCommon(urlShopupdate,data);
		resultJson = convertNullToEmpty(resultJson);
		if (resultJson._ReturnCode === returnCodeSuccess) {//升级数据提交
			$(".shop-position iframe").remove();
			showAlertMsg(msgSubmitSuccess);
			window.location.href = "setting.html";
		} else {
			showAlertMsg(resultJson._ReturnMsg);
		}
	});

	//取消--重新定位
	$("#cancel").on("click", function() {
		$(".pop-up-box").addClass("hidden");
		var baiduMapFrame = document.getElementById("baiduMapFrame");
		baiduMapFrame.src = baiduMapAPI;
	});*/

});