var isModel = false;//标记是否是模板商品，默认非模板
$(function(){
//	mobile = "13269081819";
//	userToken = "122321";
	
	var url = window.location.href;
	var name = getParamValueByName(url, "tGoodsInfoId");
	//初始化商品页面
	writeGoodsInfo.initPage(name);
	
	//绑定sku事件：选择、添加、删除
	bindSkuEvent();

	//选择分类
	$('.choi-sort').on('tap', function(){
		if (writeGoodsInfo.catEnable) {
			$("#schose-choi-sort").show();
			$("#add-choi-sort").hide();
			$(".schose-choi-sort-header").removeClass("hidden");
		}
	});
	
	//保存按钮绑定事件
	$(".head-right").on("tap", function (){
		if(!preventRealClick()) {return;}
		submitAddGoods();
	});
	
	//上传图片按钮事件绑定
	loadJs();
	$(".nonstock-part1 > ul").find("a").on("tap",function(){
		invokePhoto();
	});

	//显示店铺的支付方式和配送方式
	showPayAndDelivery();
	
	//添加模板商品时，只允许编辑规格
	if (isModel) {
		$(".max-3").attr("contenteditable", "false");
		$(".max-n").attr("contenteditable", "false");
		$("#addimg").css("display", "none");
	} 

})

/**
 * 绑定sku事件
 * */
function bindSkuEvent(){
	//选择规格
	selectSku();
	//添加规格
	addSkus();
	//删除规格
	removeSku();
}

//添加规格
function addSkus(){
	$('.add').on('tap',function(){
		$('.increase-content').after(goodsProUtils.parseJson());
		selectSku();
		removeSku();
	});
}

//选择sku
function selectSku(){
	$('.select-sku').on('tap',function(){
		if($(this).find('ul').hasClass('hidden')){
			$(this).find('ul').removeClass('hidden');
		}else{
			$(this).find('ul').addClass('hidden');
		}
	});
	
	$('.select-sku').find('li').on('tap',function(){
		$(this).parent().siblings('em').html($(this).html());
		$(this).parent().siblings('em').attr("id",$(this).attr("id"));//修改em属性，记录属性值ID zhiziwei
		$(this).find('ul').addClass('hidden');
	});
	
}

function removeSku(){
	/*删除规格*/
	$('.del').on('tap',function(){
		$(this).parent().parent().remove();
	});
}
/*删除照片	*/
$(function(){
	$('.nonstock-part1').find('del').on('tap',function(){
		$(this).parent().remove();
		
	});
	
});

/** =========================================================================================================== */

/**
 * 商品信息实体 zhiziwei
 * */
var goodsInfo = {
	
	tgoodinfoid:'', 
	
	trealcateid:'',
	
	trealcatedisc:'',
	
	trealcatetreedisc:'',
	
	mobile:'',
	
	tbrandid:'',
	
	brandname:'',
	
	goodname:'',
	
	goodintroduce:'',
	
	gooddisc:'',
		
	goodbarcode:'',
	
	ispayafterarrival : storage.getItem("isHomepay"),
	
	isexpresstohome:'',
	
	skuidstr:'',
	
	imgInfoList:'',
				
	virtualcateids:'',
	
	tGoodInfoPoolId : '',
	
	deviceno : psam,
	
	_isModel : isModel,
	
	_requestData : function (){
		return {"token" : userToken, "tgoodinfoid" : this.tgoodinfoid, "trealcateid" : this.trealcateid, 
		"trealcatedisc" : this.trealcatedisc, "trealcatetreedisc" : this.trealcatetreedisc, "mobile" : this.mobile,
		"tbrandid" : this.tbrandid, "brandname" : this.brandname, "goodname" : this.goodname, "goodintroduce" : this.goodintroduce,
		"gooddisc" : this.gooddisc, "goodbarcode" : this.goodbarcode, "ispayafterarrival" : this.ispayafterarrival,
		"isexpresstohome" : this.isexpresstohome, "skuidstr" : this.skuidstr, "imgInfoList" : this.imgInfoList,
		"virtualcateids" : this.virtualcateids, "tGoodInfoPoolId" : this.tGoodInfoPoolId, "deviceno" : this.deviceno,
		"isModel" : this._isModel}
	}
}

//调用相机/相册，并上传图片
function invokePhoto(){
	//调用相册/相机
	cordova._native.camera.picker({width:300, height:300, quality:.5}, function(res){
		$("#addimg").before("<li><img id='photo' alt=''/></li>");
		//新上传图片容器
			
		var imgView = document.getElementById('photo');
		imgView.src = res.data;
			
	    //调用图片上传接口，需要写Timage表
		var data = {"token" : userToken, "isWriteTimage" : "1", "data":res.data, "fileName" : res.path, "osType" : osType(), "mobile" : mobile};
		var resultJson = ajaxCommon(urlUploadImg, data);
		resultJson = convertNullToEmpty(resultJson);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			
			$.each(resultJson._ReturnData.success, function(index, img){
				//将图片ID，记录在li的id属性里
				$("#photo").parent().attr("id", img.split("_")[0]);
			});
			
		}else{
			showAlertMsg(resultJson._ReturnMsg);
		}
		
		//删除容器ID属性，为下张上传图片做准备
		$("#photo").removeAttr("id");
	});
}

/**
 *  商品属性工具 zhiziwei
 * */
var goodsProUtils = {
	
	trealcateid : "", //结算分类ID
	
	resultJson : null, //接口返回结果
	
	skus : "", //"上架到商品"时，赋值，平台商品已经存在的sku
	
	_salePrice : 0, //统一售价
	
	//初始化
	init : function (trealcateid){
		this.trealcateid = trealcateid;
		//读取分类下属性、属性值
		this.getProperties();
		if (this.skus == "") {
			$(".nonstock-part3").remove();
			$(".nonstock-part2").after(this.parseJson());
			
			//绑定sku事件：选择、添加、删除
			bindSkuEvent();
		} else {
			$(".nonstock-part2").after(this.displayOnPage(this.skus));
		}
	},
	
	//请求指定分类下的属性，及属性值
	getProperties : function (){
		//调用接口
		var data = {"token" : userToken, "tRealCateId" : this.trealcateid};
		var resultJson = ajaxCommon(urlQuerygoodproperty, data);
		this.resultJson = convertNullToEmpty(resultJson);
		
		if(this.resultJson._ReturnCode !== returnCodeSuccess) {
			showAlertMsg(this.resultJson._ReturnMsg);
		}
	},
	
	//判断是否使用sku属性
	isUsedSkuPro : function (){
		var _this = this;
		var flag = true;//默认使用
		
		$.each(_this.skus, function(i, sku){
			//未使用sku的情况
			if (sku.skuIdStr == "NONE") {
				flag = false;
			} 
		});

		return flag;
	},
	
	//解析接口返回JSON
	parseJson : function (){
		var _this = this;
		
		//判断页面是否已经有规格
		var i = $("#add-choi-sort").find(".nonstock-part3").length;
		
		if (!this.isUsedSkuPro()) {
			return "";
		} 

		var content = "";
		//遍历属性
		content += "<div class='nonstock-part3 clearfix " + (i > 0 ? "del-content" : "increase-content") + "'>";
		$.each(this.resultJson._ReturnData, function(index, pk){
			if (index % 2 == 0) {
				content += "	<div class='item clearfix'>";
			}

			content += "<div class='half sele'>";
//			content += "	<span>" + pk.propertyName + "：</span><input type='hidden' value='" + pk.tPropertyKeyId + "'/><div class='select-sku'><em>不选择</em><ul class='hidden'>";
			content += "	<span>" + pk.propertyName + "：</span><input type='hidden' value='" + pk.tPropertyKeyId + "'/><select class='select-sku'>";
			content += "		<option>不选择</option>";
			//遍历属性值
			$.each(pk.propertyValues, function(_index, pv){
				content += "<option value='" + pv.tPropertyValueId + "'>" + pv.propertyValue + "</option>";
			});
			content += "</select></div>"
			if (index == _this.resultJson._ReturnData.length - 1 || index % 2 == 1) {
				content += "	</div>"
			}
		});
		content += "	<div class='item clearfix'>";
		content += "		<div class='half'><span>销售价：</span><input type='number' value=''></div>";
		content += "		<div class='half'><span>市场价：</span><input type='number' value=''></div>";
		content += "	</div>";
		content += "	<div class='item clearfix'>";
		content += "		<div class='half'><span>库存：</span><input type='number' value=''></div>";
		if (this.resultJson._ReturnData.length != 0) {
			content += "		<div class='half " + (i > 0 ? "del'>删除规格" : "add'>添加规格") + "</div>";
		}
		content += "	</div>";
		content += "</div>";
		
		return content;
	},
	
	//读取页面选择结果
	selectedProperties : function (){
		var proArr = new Array();
		//页面规格集合
		var ggs = $(".nonstock-part3");
		//遍历页面规格
		$.each(ggs, function (index, gg){
			//获取规格的属性集合
			var pros = $($(gg).find(".item")[0]).find(".half");
			var kv_id = "";//属性、属性值键值对
			var v_disc = "";//属性值描述
			$.each(pros, function (i, pro){
				//属性ID
				var kid = $(pro).find("input[type='hidden']").val();
				//属性描述
				var ks = $(pro).find("span").html();
				
				//选中的属性值id
				var vid =  $(pro).find(".select-sku").val();
				//选中的属性值描述
				var vs =  $(pro).find(".select-sku").find("option:selected").text();
				
				if (!isNullString(kid) && !isNullString(ks) && !isNullString(vid) && !isNullString(vs)) {
					//kv_id ：属性1:属性值1,属性2:属性值2
					//v_disc ：属性值1x属性值2
					if (vs != "不选择") {
						if (i == pros.length - 1) {
							kv_id += kid + ":" + vid;
							v_disc += vs;
						} else {
							kv_id += kid + ":" + vid + ",";
							v_disc += vs + "x";
						}
					} else {
						if (i == pros.length - 1) {
							if (kv_id.charAt(kv_id.length - 1) == ",") {
								kv_id = kv_id.substr(0, kv_id.length - 1);
							} 
							if (v_disc.charAt(v_disc.length - 1) == "x") {
								v_disc = v_disc.substr(0, v_disc.length - 1);
							} 
						} 
					}
				}
			});
			//销售价
			var salePrice = $($(gg).find("input[type='number']")[0]).val();
			//市场价
			var marketPrice = $($(gg).find("input[type='number']")[1]).val();
			//库存
			var stock = $($(gg).find("input[type='number']")[2]).val();
			
			//缓存选中的属性及属性值
			//格式：属性ID键值对（属性1:属性值1...）_属性描述（描述1x描述2...）_销售价_市场价_库存
			if (!isNullString(kv_id) && !isNullString(v_disc)) {
				proArr.push(kv_id + "_" + v_disc + "_" + salePrice + "_" + marketPrice + "_" + stock);
			} else {
				proArr.push("NONE" + "_" + " " + "_" + salePrice + "_" + marketPrice + "_" + stock);
			}

		});
		
		var temp = "";
		$.each(proArr, function(m, pro){
			if (m == proArr.length - 1) {
				temp += pro;
			} else {
				temp += pro + "#";
			}
		});
		return temp;
	},
	
	/**
	 *根据传入的sku信息，反写页面参数格式如下：
	 *[
	 *     {
	 *          "tGoodSkuInfoPoolId": 120,
	 *          "skuIdStr": "NONE",
	 *          "skuFrontDisNameStr": null
	 *      }
	 *]
	 */
	displayOnPage : function (skus){
		var flag = 0;//占位符标记
		var _this = this;
		//显示在页面的html
		var content = ""; 
		//遍历规格
		$.each(skus, function(j, sku){
			//未使用sku的情况，此时不允许添加删除sku
			if (sku.skuIdStr == "NONE") {
				content += "<div class='nonstock-part3 clearfix increase-content'>";
				content += "	<div class='item clearfix'>";
				if (parseFloat(_this._salePrice) > 0) {
					content += "		<div class='half'><span>销售价：</span><input type='number' value='" + _this._salePrice + "' readonly></div>";
				} else {
					content += "		<div class='half'><span>销售价：</span><input type='number' value=''></div>";
				}
				content += "		<div class='half'><span>市场价：</span><input type='number' value=''></div>";
				content += "	</div>";
				content += "	<div class='item clearfix'>";
				content += "		<div class='half'><span>库存：</span><input type='number' value=''></div>";
				content += "	</div>";
				content += "</div>";
				
				return content;
			} 
			//使用sku的情况
			content += "<div class='nonstock-part3 clearfix " + (j != 0 ? "del-content" : "increase-content") + "'>";
			
			//遍历该分类下的属性
			var _flag = 0; //标记是否选中：0-不选中，1-选中属性值
			$.each(_this.resultJson._ReturnData, function(index, pk){
				flag++;
				if (index % 2 == 0) {
					content += "	<div class='item clearfix'>";
				} 
				content += "<div class='half sele'>";
				content += "	<span>" + pk.propertyName + "：</span><input type='hidden' value='" + pk.tPropertyKeyId + "'/><select class = 'select-sku'>";
				content += "<option >不选择</option>";
				//遍历属性值
				$.each(pk.propertyValues, function(_index, pv){
					var _skuIdStr = sku.skuIdStr.split(",");
					for(var i = 0; i < _skuIdStr.length; i++) {
						//分割属性名与属性值：0-属性名ID，1-属性值ID
						var pros = _skuIdStr[i].split(":");
						if ((pros[0] == pk.tPropertyKeyId) && (pros[1] == pv.tPropertyValueId)) {
//							content = content.replace(new RegExp("\\{" + flag + "\\}","g"), "<em id='" + pv.tPropertyValueId + "'>" + pv.propertyValue + "</em>");  
							_flag = 1;
						} else if ((pros[0] != pk.tPropertyKeyId)){
//							content = content.replace(new RegExp("\\{" + flag + "\\}","g"), "<em>不选择</em>");  
						}
					}
					
//					content += "<li id='" + pv.tPropertyValueId + "'>" + pv.propertyValue + "</li>";
					content += "<option " + (_flag == 1 ? "selected='selected'" : "") + " value='" + pv.tPropertyValueId + "'>" + pv.propertyValue + "</option>";
					//遍历下个属性值时，回复选中标记的默认值
					_flag = 0;
				});
				content += "</select></div>"
				
				if (index == _this.resultJson._ReturnData.length - 1 || index % 2 == 1) {
					content += "	</div>";
				}

			});
			content += "	<div class='item clearfix'>";
			if (parseFloat(_this._salePrice) > 0) {
				content += "		<div class='half'><span>销售价：</span><input type='number' value='" + _this._salePrice + "' readonly></div>";
			} else {
				content += "		<div class='half'><span>销售价：</span><input type='number' value=''></div>";
			}
			content += "		<div class='half'><span>市场价：</span><input type='number' value=''></div>";
			content += "	</div>";
			content += "	<div class='item clearfix'>";
			content += "		<div class='half'><span>库存：</span><input type='number' value=''></div>";
			content += "		<div class='half " + (j != 0 ? "del'>删除规格" : "add'>添加规格") + "</div>";
			content += "	</div>";
			content += "</div>";
		});
		
		return content;
	}
	
}

/**
 * 商品图片工具
 */
var imgUtils = {
	
	//获取页面图片数据，数据格式：图片ID_排序;图片ID_排序...
	getImgsFromPage : function (){
		var content = "";
		//获取页面图片
		var imgs = $(".nonstock-part1 > ul").find("li");
		//遍历页面图片，最后一张是图片新增按钮，遍历时，排除掉
		$.each(imgs, function (index, img){
			var imgId = $(img).attr("id");
			if(index == imgs.length - 2){
				content += imgId + "_" + (index + 1);
			} else if (index < imgs.length - 2){
				content += imgId + "_" + (index + 1) + ";";
			}
		});
		
		return content;
	}
}

/**
 * 反写平台商品数据 zhiziwei
 */
var writeGoodsInfo = {
	
	catEnable : true, //分类插件是否可用，默认可用
	
	initPage : function (tgoodsinfoid){
		if (!isNullString(tgoodsinfoid)) {
			isModel = true;
			goodsInfo.tGoodInfoPoolId = tgoodsinfoid;
		}
		this.getGoodsData(tgoodsinfoid);
	},
	
	//调接口，获取商品数据
	getGoodsData : function (tgoodsinfoid){
		if (!isNullString(tgoodsinfoid)) {
			//调用接口
			var data = {"token" : userToken, "tGoodsInfoId" : tgoodsinfoid, "mobile" : mobile};
			var resultJson = ajaxCommon(urlGetgoodsdetail, data);
			resultJson = convertNullToEmpty(resultJson);
				
			//解析结果
			if(resultJson._ReturnCode === returnCodeSuccess) {
				this.parseBaseData(resultJson);
			} else {
				showAlertMsg(resultJson._ReturnMsg);
			}
		} 
	},
	
	//解析接口返回商品基本数据
	parseBaseData : function (resultJson){
		//商品名称
		var goodName = resultJson._ReturnData.goodName;
		$(".max-3").html(goodName);
		//商品简介
		var goodIntroduce = resultJson._ReturnData.goodIntroduce;
		$(".max-n").html(goodIntroduce);
		//商品分类信息
		var tRealCateId = resultJson._ReturnData.tRealCateId;
		if (!isNullString(tRealCateId)) {
			$(".choi-sort").html("<div>" + resultJson._ReturnData.tRealCateDisc + "</div>");
			setSelectedrealcatobj({
				tRealCateId:resultJson._ReturnData.tRealCateId,
				realCateDisc:resultJson._ReturnData.tRealCateDisc,
				tRealCateTreeDisc:resultJson._ReturnData.tRealCateTreeDisc
			});
			//禁用分类选择插件
			this.catEnable = false;
		}
		//初始化该商品属性模块
		goodsProUtils.skus = resultJson._ReturnData.skus;
		goodsProUtils._salePrice = resultJson._ReturnData.salePrice;//统一售价
		goodsProUtils.init(tRealCateId);
		//初始商品图片数据
		var imgStr = this.parseImgData1(resultJson._ReturnData.images);
		$(".nonstock-part1 > ul > li").before(imgStr);
		//新增（批发进货）商品提交参数
		goodsInfo.trealcateid = resultJson._ReturnData.tRealCateId;
		goodsInfo.trealcatedisc = resultJson._ReturnData.tRealCateDisc;
		goodsInfo.trealcatetreedisc = resultJson._ReturnData.tRealCateTreeDisc;
		goodsInfo.tbrandid = resultJson._ReturnData.tBrandId;
		goodsInfo.brandname = resultJson._ReturnData.brandName;
		goodsInfo.goodname = resultJson._ReturnData.goodName;
		goodsInfo.goodintroduce = resultJson._ReturnData.goodIntroduce;
		goodsInfo.goodbigpic = resultJson._ReturnData.goodBigPic;
		goodsInfo.goodbarcode = resultJson._ReturnData.goodBarCode;
		goodsInfo.ispayafterarrival = $("#dx2").attr("checked") == "checked" ? "1" : "0";
		goodsInfo.isexpresstohome = $("#dx4").attr("checked") == "checked" ? "1" : "0";
		goodsInfo.skuidstr = goodsProUtils.selectedProperties();
		goodsInfo.deviceno = psam;
		goodsInfo.mobile = mobile;
		goodsInfo.virtualcateids = "";
	},
	
	//解析商品图片数据（用于页面显示）
	parseImgData1 : function (imgs){
		var content = "";
		$.each(imgs, function (index, img){
			content += "<li id='" + img.timageId + "'><img src='" + img.url + "'/>" + (isModel || index == 0 ? "" : "<del></del>") + "</li>";
		});
		return content;
	},
	
	//解析商品图片数据（用于提交后台数据）
	parseImgData2 : function (imgs){
		var content = "";
		$.each(imgs, function (index, img){
			if (index == imgs.length - 1) {
				content += img.timageId + "_" + img.sort;
			} else {
				content += img.timageId + "_" + img.sort + "#";
			}
		});
		return content;
	}
}

/**
 * 新增商品
 * */
function submitAddGoods(){
	goodsInfo._isModel = isModel;
	
	//选中的实分类数据	
	var o1 = getSelectedrealcatobj();
	if (o1 != null) {
		goodsInfo.trealcateid = o1.tRealCateId;
		goodsInfo.trealcatedisc = o1.realCateDisc;
		goodsInfo.trealcatetreedisc = o1.tRealCateTreeDisc;
	} 
	goodsInfo.goodname = $(".nonstock-part2").find(".max-3").html();
	goodsInfo.goodintroduce = $(".nonstock-part2").find(".max-n").html();
	goodsInfo.ispayafterarrival = $("#dx2").attr("checked") == "checked" ? "1" : "0";
	goodsInfo.isexpresstohome = $("#dx4").attr("checked") == "checked" ? "1" : "0";
	//获取页面选中的sku
	goodsInfo.skuidstr = goodsProUtils.selectedProperties();
	//获取页面的图片
	goodsInfo.imgInfoList = imgUtils.getImgsFromPage();
	goodsInfo.deviceno = psam;
	goodsInfo.mobile = mobile;
	//虚分类信息
	goodsInfo.virtualcateids = getObjidconnection();
	//数据校验
	var msg = new Array();
	if (null == o1) {
		msg.push("请选择分类信息！")
	}
	if (isNullString(goodsInfo.goodname)) {
		msg.push("请输入商品名称！")
	} 
	if (isNullString(goodsInfo.goodintroduce)) {
		msg.push("请输入商品描述！")
	} 
	if (isNullString(goodsInfo.imgInfoList)) {
		msg.push("请至少上传一张图片！")
	} 
	if (isNullString(goodsInfo.skuidstr)) {
		msg.push("请添加商品规格！")
	} else {
		var temp = goodsInfo.skuidstr.split("#");
		$.each(temp, function(index, t){
			//页面添加两个以上规格是，属性必选
			if (temp.length > 1) {
				var _temp = t.split("_");
				if ((isNullString(_temp[0]) || "NONE" == _temp[0] || isNullString(_temp[1])) && !arrNoReapet(msg, "请选择属性！") ) {
					msg.push("请选择属性！")
				}
				if ((isNullString(_temp[2]) || isNaN(_temp[2]) || parseFloat(_temp[2]) <= 0) && !arrNoReapet(msg, "销售价必填，且必须为大于0的数字！")) {
					msg.push("销售价必填，且必须为大于0的数字！")
				} 
	
				if ((isNullString(_temp[3]) || isNaN(_temp[3]) || parseFloat(_temp[3]) <= 0) && !arrNoReapet(msg, "市场价必填，且必须为大于0的数字！")) {
					msg.push("市场价必填，且必须为大于0的数字！")
				} 
	
				if ((isNullString(_temp[4]) || isNaN(_temp[4]) || parseFloat(_temp[4]) <= 0) && !arrNoReapet(msg, "库存必填，且必须为大于0的数字！")) {
					msg.push("库存必填，且必须为大于0的数字！")
				} 
			} else {
				var _temp = t.split("_");
				if ((isNullString(_temp[2]) || isNaN(_temp[2]) || parseFloat(_temp[2]) <= 0) && !arrNoReapet(msg, "销售价必填，且必须为大于0的数字！")) {
					msg.push("销售价必填，且必须为大于0的数字！")
				} 
	
				if ((isNullString(_temp[3]) || isNaN(_temp[3]) || parseFloat(_temp[3]) <= 0) && !arrNoReapet(msg, "市场价必填，且必须为大于0的数字！")) {
					msg.push("市场价必填，且必须为大于0的数字！")
				} 
	
				if ((isNullString(_temp[4]) || isNaN(_temp[4]) || parseFloat(_temp[4]) <= 0) && !arrNoReapet(msg, "库存必填，且必须为大于0的数字！")) {
					msg.push("库存必填，且必须为大于0的数字！")
				} 
			}
		});

		
	}
	
	//打印校验结果
	if(msg.length > 0){
		var temp = "";
		for(var i = 0; i < msg.length; i++){
			if(i == msg.length - 1){
				temp += (i + 1) + "、" + msg[i];
			}else{
				temp += (i + 1) + "、" + msg[i] + "</br>";
			}
		}
		showAlertMsg(temp);
		return;
	}
	
	//调用接口
	var data = goodsInfo._requestData();
	var resultJson = ajaxCommon(urlAddGoods, data);
	this.resultJson = convertNullToEmpty(resultJson);
	//解析结果
	if(resultJson._ReturnCode === returnCodeSuccess) {
		location.href = "../shops/index.html";
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}

//数组去重(数组元素为字符串)
function arrNoReapet(arr,str){
	var flag = false;
	$.each(arr,function(i,s){
		if(s == str){
			flag = true;
		}
	});
	return flag;
}

//字符串非空校验
function isNullString(str){
	if ( str == "" || str == null || typeof str == "undefined") {
		return true;
	} 
	return false;
}

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