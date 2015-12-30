
var selectedrealcatobj = null;//选中的是实分类数据
$(function(){	
	objsort = {};//实类id和虚类id
	objlastresultJson = {};//对应实类商品
	objidconnection = "";//虚拟id和末级实类id返回
	objthishtml = "";//选择类的名称
	sortlenght  = null;//分类长度
	$('.first').delegate('li','tap',function() {
		$('.third').hide('slow');
		$('.fourth').hide('slow');
		$('.fifth').hide('slow');
		$('.second').show('slow');
		$('.second').find('li').removeClass('color');
		$('.second').find('li:first-child').addClass('color');
		$('.first').find('li').removeClass('color');
		$(this).addClass('color');
		var virtualid =$(this).attr("virtualid");
		objsort[""+0] = virtualid;
		sortlenght = 1;
		var laststagevirtual = $(".second ul");
		objthishtml = $(this).html();
		secondVirtualfun(virtualid,laststagevirtual,objthishtml);
	});
	$('.second').delegate("li",'tap',function() {
		$('.fourth').hide('slow');
		$('.fifth').hide('slow');
		$('.third').show('slow');
		$('.third').find('li').removeClass('color');
		$('.third').find('li:first-child').addClass('color');
		$('.second').find('li').removeClass('color');
		$(this).addClass('color');
		var virtualid =$(this).attr("virtualid");
		objsort[""+1] = virtualid;
		sortlenght = 2;
		var laststagevirtual = $(".third ul");
		objthishtml = $(this).html();
		thirdVirtualfun(virtualid,laststagevirtual,objthishtml);
	});
	$('.third').delegate("li",'tap',function() {
		$('.fourth').show('slow');
		$('.fifth').hide('slow');
		$('.fourth').find('li').removeClass('color');
		$('.fourth').find('li:first-child').addClass('color');
		$('.third').find('li').removeClass('color');
		$(this).addClass('color');
		var virtualid =$(this).attr("virtualid");
		objsort[""+2] = virtualid;
		sortlenght = 3;
		var laststagevirtual = $(".fourth ul");
		objthishtml = $(this).html();
		fourtVirtualfun(virtualid,laststagevirtual,objthishtml);
	});
	$('.fourth').delegate("li",'tap',function() {
		$('.fifth').show('slow');
		$('.fifth').find('li').removeClass('color');
		$('.fifth').find('li:first-child').addClass('color');
		$('.fourth').find('li').removeClass('color');
		$(this).addClass('color');
		var virtualid = $(this).attr("virtualid");
		objsort[""+3] = virtualid;
		sortlenght = 4;
		var laststagevirtual = $(".fifth ul");
		objthishtml = $(this).html();
		laststage(virtualid,laststagevirtual,objthishtml);
	});
	$('.fifth').delegate("li",'tap',function(){
		$('.fifth').find('li').removeClass('color');
		$(this).addClass('color');
		var virtualid = $(this).attr("virtualid");
		objsort[""+4] = virtualid;
		sortlenght = 5;
		objthishtml = $(this).html();
		datalastresul(virtualid,objthishtml,sortlenght);
	});
	$(".first ul li").first().addClass("color");
})

//获取所选的末级分类数据
function datalastresul(virtualid,objthishtml,sortlenght) {
	var objlength = count(objlastresultJson);//统计末级长度
	var objlastresul = {};//存储末级对象                实分类ID		父分类ID		分类描述		完整分类描述
	objidconnection = "";//选择的id链
	for(var z = 0; z < objlength; z++){
		if(virtualid == objlastresultJson[z].tRealCateId) {
			objlastresul = objlastresultJson[z];
			selectedrealcatobj = objlastresultJson[z];
		}
	};
	for (var r = 0; r < sortlenght - 1; r++) {
		objidconnection += objsort[""+r] + "_";
	}
	//选择分类，获取属性 zhiziwei
	goodsProUtils.init(selectedrealcatobj.tRealCateId);
	$(".choi-sort div").html(objthishtml);
	$("#schose-choi-sort").hide().siblings("#add-choi-sort").show();
	$(".schose-choi-sort-header").addClass("hidden");
}

//计算对象长度
function count(h){
    var o = typeof h;
    if(o == 'string'){
            return h.length;
    }else if(o == 'object'){
            var p = 0;
            for(var q in h){
                    p++;
            }
            return p;
    }
    return false;
};	
//获取2C使用的所有虚分类（即营销分类）
var firstvirtual = $(".first ul");//一级分类
var secondvirtual = $(".second ul");//二级分类
var thirdvirtual = $(".third ul");//三级分类
var fourthvirtual = $(".fourth ul");//四级分类
var laststagevirtual = $(".fifth ul");//实分类
var connectionid = [];
var connection = "";
var secondret = "";
var thirdret = "";
var fourthret = "";
var laststageret = "";
var data = {"token":"1234"};
var resultJson = ajaxCommon(urlgoodsqueryvirtualcat, data);
resultJson = convertNullToEmpty(resultJson);
if(resultJson._ReturnCode === returnCodeSuccess) {
	var virtualclass = resultJson._ReturnData;
	if(virtualclass.length > 0) {
		//一级分类
		firstret ="";
		for(var i = 0; i < virtualclass.length; i++){
			if(virtualclass[i].fatherVirtualCateId == 0){
				firstret += "<li virtualid='" + virtualclass[i].tVirtualCateId + "'>" + virtualclass[i].virtualCateDisc + "</li>"
			}
		}
		firstvirtual.append(firstret);
	}
	//二级分类
	function secondVirtualfun (virtualid,laststagevirtual,objthishtml) {
		secondvirtual.html("");
		secondret = "";
		for(var j = 0; j < virtualclass.length; j++) {
			if(virtualid == virtualclass[j].fatherVirtualCateId) {
				secondret += "<li virtualid='" + virtualclass[j].tVirtualCateId + "'>"+ virtualclass[j].virtualCateDisc + "</li>";
			}
		}
		if(secondret == "") {
			laststage(virtualid,laststagevirtual,objthishtml);
		} else {
			secondvirtual.html("").append(secondret);
		}
	}
	//三级分类
	function thirdVirtualfun (virtualid,laststagevirtual,objthishtmll) {
		thirdvirtual.html("");
		thirdret = "";
		for(var k = 0; k < virtualclass.length; k++) {
			if(virtualid == virtualclass[k].fatherVirtualCateId) {
				thirdret += "<li virtualid='" + virtualclass[k].tVirtualCateId + "'>"+ virtualclass[k].virtualCateDisc + "</li>";
			}
		}
		if(thirdret == "") {
			laststage(virtualid,laststagevirtual,objthishtmll);
		} else {
			thirdvirtual.html("").append(thirdret);
		}
	}
	//四级分类
	function fourtVirtualfun (virtualid,laststagevirtual,objthishtmll){
		fourthvirtual.html("");
		fourthret = "";
		for(var l = 0; l < virtualclass.length; l++){
			if(virtualid == virtualclass[l].fatherVirtualCateId){
				fourthret += "<li virtualid='" + virtualclass[l].tVirtualCateId+"'>" + virtualclass[l].virtualCateDisc + "</li>";
			}	
		}
		if(fourthret == "") {
			laststage(virtualid,laststagevirtual,objthishtml);
		} else {
			fourthvirtual.append(fourthret);
		}
	}	
}
//判断末级，返回数据
function laststage(virtualid,laststagevirtual,objthishtml) {
	var data = {"tVirtualCateId":virtualid};
	var lastresultJson = ajaxCommon(urlgoodsqueryrealcatbyvirtualcat, data);
	lastresultJson = convertNullToEmpty(lastresultJson);
	if (lastresultJson._ReturnCode === returnCodeSuccess) {
		laststagevirtual.html("");
		laststageret = "";
		if(lastresultJson._ReturnData.length > 0) {//请求实类的接口返回数据长度是否非空
			for(var m = 0; m < lastresultJson._ReturnData.length; m++) {
				laststageret += "<li virtualid='" + lastresultJson._ReturnData[m].tRealCateId + "'>" + lastresultJson._ReturnData[m].realCateDisc + "</li>";
				objlastresultJson[m] = lastresultJson._ReturnData[m];
			}
			laststagevirtual.append(laststageret);
		} else {
			datalastresul(virtualid,objthishtml,sortlenght);
			laststagevirtual.parent().hide("show");//无值隐藏当前级
		}
	}
}	

function getSelectedrealcatobj(){
	return selectedrealcatobj;
}

function setSelectedrealcatobj(o){
	selectedrealcatobj = o;
}

function getObjidconnection(){
	return objidconnection;
}