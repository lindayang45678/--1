var page = 1;
var pageSize = 10;
var hasNextPage = true;
var ordercate = 'pfjh';  //大类  批发、零售、需店主送货
var querytype = initquerytype();  //小类;
var container = $(".scroll-items");
var myScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

//初始化iScroll控件 
function loaded() {
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('wholesaleorders-scroll', {//iScroll的对象
		hScrollbar : false,
		vScrollbar : false,
		useTransition: false, 
		checkDOMChanges:false,
		topOffset: pullDownOffset,
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'hidden';
				getwholesaleorderlist(querytype);	// 执行加载后的自定义功能	
			}
		}
	});
}

document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);

$(function() {
	//加载cordova
	loadJs();
	
	//点击该订单已全部收货,弹窗提示
	$(".all-receipts").live("tap", function() {
		$(".pop-up-box").show();
	});
	$(".pop-up-box .btn-gray").live("tap", function() {
		$(".pop-up-box").hide();
	});

	//已发货但部分签收的订单可点击,点击全部收货按钮并确定全部收货
	$("#confirmsignallbtn").live("tap", function() {
		var orderproviderid = $('#signallorderproviderid').val();
		var data = {"orderproviderid":orderproviderid};
		var resultJson = ajaxCommon(urlConfirmShAll,data);
		resultJson = convertNullToEmpty(resultJson);
		$(".pop-up-box").hide();
		if(resultJson._ReturnCode === returnCodeSuccess){
			showAlertMsg(msgOrderAllSigned);
		}else{
			showAlertMsg(resultJson._ReturnMsg);
		}
		
		//成功的时候，将该节点从dom中删除,因为此时该订单所有商品都已收货，应该出现在已收货列表中
		if(resultJson._ReturnCode === returnCodeSuccess){
			$('#'+orderproviderid+'section').remove();
		}	
		
		//不刷新当前页,部分收货数量减一，已收货数量加一
		var pfbfsh_num = $('#pfbfsh span em').text().replace('(','').replace(')','');  //部分收货数量
		var pfysh_num = $('#pfysh span em').text().replace('(','').replace(')','');    //已收货数量
		$('#pfbfsh span em').text('('+(Number(pfbfsh_num)-1)+')');  //部分收货数量减一
		$('#pfysh span em').text('('+(Number(pfysh_num)+1)+')');    //已收货数量加一
		if(Number(pfbfsh_num)==1){
			//订单数量为0,提示订单为空
			showNoDataMsg(msgNoOrder);
		}
		
	});
	
	//默认显示批发进货的页面
	getwholesaleordercnt(ordercate);
	
	//默认显示批发待发货的订单列表
	getwholesaleorderlist(querytype);
	
	
	
	$("#header-list-slide ul li").live("tap", function() {
		//每次调用清空数据,主要用于状态Tab点击
		container.empty();
		//滚动条刷新
		myScroll.refresh();
		//debugger;
		$.each($("#header-list-slide ul li"), function(index){
			$(this).find('a').removeClass("active");
		});
		$(this).find('a').addClass("active");
		querytype = $(this).find('a').attr('id');
		page = 1;
		hasNextPage = true;
		getwholesaleorderlist(querytype);
		sessionStorage.setItem("sessionquerytype", querytype);
	});
	
	$("#head a").live("tap", function() {
		  sessionStorage.removeItem("sessionquerytype");
	});
	//订单展开
	$(".order-number").live("tap",function() {
		$(this).find("div").toggleClass("down").parent().siblings().toggle();
		$(this).parent().siblings().find(".orders-content").hide();
		$(this).parent().siblings().find(".order-number div").removeClass("down");
		myScroll.refresh();
	});
	//菜单多是可滑动
	var liWidth = null;
	$("#header-list-slide li").each(function(){
		liWidth += parseInt($(this).width());
	});
	$("#header-list-slide").css("width", liWidth + 1 + "px");
	var menuScroll = new iScroll("header-list", {
		hScroll : true,
		vScroll : false,
		hScrollbar : false,
		vScrollbar : false
	});
	$("#wholesaleorders-scroll").css("height",windowHeight-126);
	$("#header-list-slide").css("margin","auto");
});

//点击全部收货按钮设置全部收货的订单ID
function setsignallorderid(torderproviderid){
	$('#signallorderproviderid').val(torderproviderid);
}

//取批发订单下各状态的订单数
function getwholesaleordercnt(ordercate){
	var header = $(".header");
	header.empty();
	var headerlist = $(".header-list");
	headerlist.empty();
	var header_content ="";
	var headerlist_content ="";
	//mobile参数该字段用于批发待付款的时候用到，主要查该店主下的订单还没有付款的
	var data = {"token":userToken,"ecnetno":netNo,"mobile":mobile,"ordercate":ordercate};
	var resultJson = ajaxCommon(urlOrderCnt,data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess){
		var _ReturnData = resultJson._ReturnData;
		var pf2needpaynum = _ReturnData.pf2needpaynum;	//待发货总数
		var pf2nodelivernum = _ReturnData.pf2nodelivernum;	//待发货总数
		var pf2deliverednum = _ReturnData.pf2deliverednum;		//已发货总数
		var pf2partreceivenum = _ReturnData.pf2partreceivenum;		//部分收货总数
		var pf2receivednum = _ReturnData.pf2receivednum;		//已收货总数
		var sxneedbsendnum = _ReturnData.sxneedbsendnum;		//需店主送货订单总数
		header_content =" <a href='wholesaleorders.html' class='new-tbl-cell f-16'><span class='active'>批发订单</span></a> " +
						" <a href='retailorders.html' class='new-tbl-cell f-16'><span>零售订单</span></a> " +
						" <a href='ownership.html' class='new-tbl-cell f-16'><span class='delivery'>需店主送货";
		if(sxneedbsendnum > 0) {
			header_content += "<em>" + sxneedbsendnum + "</em>";
		}
		header_content += "</span></a>";
		headerlist_content =" <div id='header-list-slide' class='header-list-slide'><ul class='clearfix'> " +
		    " <li><a id='pfdfk' href='#' class='c-gray f-12'><span>待付款<em>("+pf2needpaynum+")</em></span></a></li> " +
			" <li><a id='pfdfh' href='#' class='c-gray f-12'><span>待发货<em>("+pf2nodelivernum+")</em></span></a></li> " +
			" <li><a id='pfyfh' href='#' class='c-gray f-12'><span>已发货<em>("+pf2deliverednum+")</em></span></a></li> " +
			" <li><a id='pfbfsh' href='#' class='c-gray f-12'><span>部分收货<em>("+pf2partreceivenum+")</em></span></a></li> " +
			" <li><a id='pfysh' href='#' class='c-gray f-12'><span>已收货<em>("+pf2receivednum+")</em></span></a></li> " +
			" </ul> </div> ";
		
		header.append(header_content);
		headerlist.append(headerlist_content);
		
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}


function initquerytype(){
	var value = sessionStorage.getItem("sessionquerytype");
	var initvalue = '';
	if(value!=undefined&&value!=''){
		initvalue = value;
	}else{
		//默认显示批发待付款的订单
		initvalue = 'pfdfk';
	}
	return initvalue;
}

//取批发订单下各状态的订单列表
function getwholesaleorderlist(querytype){
	//状态选项选中样式置换
	$('#'+querytype+'').addClass("active");
	setTimeout(function () {
		if(hasNextPage){
			var data = {
					"token":userToken,
					"ecnetno":netNo,
					"mobile":mobile,  //该字段用于批发待付款的时候用到，主要查该店主下的订单还没有付款的
					"querytype":querytype,
					"page": page,
					"pageSize": 10
				};
			var resultJson = ajaxCommon(urlOrderList,data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				
				var _ReturnData = resultJson._ReturnData;
				if(querytype!='pfdfk'){
					//批发已经支付的或者货到付款的列表，从供应商这级加载
					generatepaidorderhtml(_ReturnData);
				}else{
					//批发待付款列表 加载三级订单列表
					generateneedpayorderhtml(_ReturnData);
				}
				
			} else {
				hasNextPage = false;
				showAlertMsg(resultJson._ReturnMsg);
			}
			page ++;
		}
	},400);	
}

//生成批发进货订单列表-不包括待付款的列表数据
function generatepaidorderhtml(_ReturnData){
	var content ="";
	var	orderproviderlist = _ReturnData.orderproviderlist;
	if (orderproviderlist.length>0) {
		
		//若上一个TAB无数据显示，在该页展示的时候先删除上TAB页无记录提示
		if($(".none-data")){
		  $(".none-data").remove();
		}
		
		for (var i = 0; i < orderproviderlist.length; i++) {
			var state = orderproviderlist[i].state;
			var torderproviderid = orderproviderlist[i].torderproviderid;
			var statedesc = getOrderStateDesc(state);
			var deliverystatecss = '';
			var sourcedesc = getOrderSourceDesc(orderproviderlist[i].source);
			var isdelivertohomedesc = getDevicetypeDesc(orderproviderlist[i].isdelivertohome);
			var ordertime = getLocalTime(orderproviderlist[i].ordertime);
			var actualamount = orderproviderlist[i].actualamount;
			var downcss = '';
			var hiddivcss = '';
			if(i==0&&page==1){
				downcss = "class='down'";
				hiddivcss = "class='orders-content'";
			}else{
				downcss = '';
				hiddivcss = "class='orders-content hidden'";
			}
			    content += "<section id='"+torderproviderid+"section' class='orders-items clearfix'> ";
			    content += "	<div class='order-number color f-14 bt bb'> ";
				content += "		<div "+downcss+"><span>订单号："+torderproviderid+"</span><em>￥"+formatNumber(actualamount)+"</em><i></i></div> ";
				content += "	</div> ";
				content += "	<section "+hiddivcss+"> ";
				content += "		<div class='order-infor bb'> ";
				content += "			<h3><u></u>订单信息</h3> ";
				content += "			<dl> ";
				content += "				<dt>下订时间：</dt> ";
				content += "				<dd>"+ordertime+"</dd> ";
				content += "				<dt>订单金额：</dt> ";
				content += "				<dd>￥"+formatNumber(actualamount)+" <span class='freight'>运费：<em> ￥"+formatNumber(orderproviderlist[i].logiscalfee)+"</em></span></dd> ";
				content += "				<dt>订单状态：</dt> ";
				content += "				<dd>"+statedesc+"</dd> ";
				content += "				<dt>订单来源：</dt> ";
				content += "				<dd>"+sourcedesc+"</dd> ";
				content += "			</dl> ";
				if(Number(state)==stateBFQS&&querytype=='pfbfsh'){
					content += "			<a class='btn-red all-receipts' href='#' onclick='setsignallorderid(\""+torderproviderid+"\")'>是否全部收货</a> ";
				}else if((Number(state)==stateBFFH||Number(state)==stateYFH)&&querytype=='pfyfh'){
					content += "			<a class='btn-red ownner toshopreceipt' id='"+torderproviderid+"sh' opid='"+torderproviderid+"' href='javascript:;' >店主收货</a> ";
				}
				content += "		</div> ";
				content += "		<div class='consignee-infor bb'> ";
				content += "			<h3><u></u>收货人信息</h3> ";
				content += "			<div>收货人姓名：<span>"+orderproviderlist[i].cusname+"</span></div> ";
				content += "			<div>收货人手机：<span>"+orderproviderlist[i].custelno+"</span></div> ";
				content += "			<div>配送方式：<span>"+isdelivertohomedesc+"</span></div> ";
				content += "			<div>配置地址：<span class='address'>"+orderproviderlist[i].addressfull+"</span></div> ";
				content += "		</div> ";
				content += "		<div class='commodity-infor clearfix'> ";
				content += "			<h3><u></u>商品信息</h3> ";
				content += "			<dl> ";
				content += "				<dt class='contact'><span>所属供应商："+orderproviderlist[i].providername+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>售后电话："+orderproviderlist[i].clientservicetel+"</span></dt> ";
					
				var orderitemslist = orderproviderlist[i].orderitemslist; 
				   for (var k = 0; k < orderitemslist.length; k++) {
					   console.log(orderitemslist);
							var goodnamedesc;
						    //全局js变量ship3hCode=363
							if(orderitemslist[k].is3h!=undefined&&orderitemslist[k].is3h!=null&&orderitemslist[k].is3h!=''&&orderitemslist[k].is3h==ship3hCode){
							goodnamedesc = "<em class='color'>【生鲜】</em>"+orderitemslist[k].goodsname;
							}else{
								goodnamedesc = orderitemslist[k].goodsname;
							}
							var invalidstatedesc = getInvalidstateDesc(orderitemslist[k].invalidstate);
							var goodsid = orderitemslist[k].goodsid;
							var goodbigpic = orderitemslist[k].goodbigpic.split(";")[0]; //商品主图
							var logno = orderitemslist[k].logisticscode;
							//经需产品沟通，店主收货按钮物流单号取第一个商品订单物流单号
							if(k == 0){
								content += "		<input type='hidden' id='"+torderproviderid+"logno' value='"+logno+"' /> ";
							}
							var itemstate = '';
							var itemstate = orderitemslist[k].state;
							//stateWFH全局变量=99
							if(Number(itemstate)>stateWFH){
								deliverystatecss = "class='btn-red'";
							}else{
								deliverystatecss = "class='btn-red hidden'";
							}
							
							content += "				<dl class='clearfix'> ";
							content += "					<dd><img src='" + urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic + "'></dd> ";
							content += "					<dt class='commodity-content'> ";
							content += "						<strong>"+goodnamedesc+"</strong> ";
							var norms = orderitemslist[k].norms;
							if(norms!=''&&norms!=undefined){
								content += "						<span>规格："+orderitemslist[k].norms+"</span> ";
							}
							content += "						<span>数量：x"+orderitemslist[k].goodscount+"</span> ";
							content += "						<span>单价：￥"+formatNumber(orderitemslist[k].goodssaleprice)+"</span> ";
							content += "						<span>售后状态："+invalidstatedesc+"</span>";
							content += "					</dt> ";
							content += "				</dl> ";
							content += "			    <a href='#' onclick='directDeliveryHtml(\""+logno+"\",\""+itemstate+"\",\""+orderitemslist[k].torderitemsid+"\")' "+deliverystatecss+">查看配送状态</a> ";
				}
				   directDeliveryHtml 
				content += "		</div> ";
				content += "	</section> ";
				content += "</section> ";
		}
		container.append(content);
		myScroll.refresh();		
	}else if(page == 1) {
		showNoDataMsg(msgNoOrder);
	}else {
		hasNextPage = false;
	}
}


//生成批发进货订单列表-仅待付款的列表数据
function generateneedpayorderhtml(_ReturnData){
	var content = "";
	var	orderlist = _ReturnData.orderlist;
	if (orderlist.length>0) {
		//若上一个TAB无数据显示，在该页展示的时候先删除上TAB页无记录提示
		if($(".none-data")){
		  $(".none-data").remove();
		}
		
		for (var i = 0; i < orderlist.length; i++) {
			var state = orderlist[i].state;
			var tallorderid = orderlist[i].tallorderid;
			var statedesc = getOrderStateDesc(state);
			var deliverystatecss = '';
			var sourcedesc = getOrderSourceDesc(orderlist[i].source);
			var isdelivertohomedesc = getDevicetypeDesc(orderlist[i].isdelivertohome);
			var orderproviderlist = orderlist[i].orderproviderlist; 
			var paytoken = orderlist[i].paytoken;
			var ordertime = getLocalTime(orderlist[i].ordertime);
			var paychanel = orderlist[i].paychanel;
			var actualamount = orderlist[i].actualamount;
			var downcss = '';
			var hiddivcss = '';
			if(i==0&&page==1){
				downcss = "class='down'";
				hiddivcss = "class='orders-content'";
			}else{
				downcss = '';
				hiddivcss = "class='orders-content hidden'";
			}
			    content += "<section id='"+tallorderid+"section' class='orders-items clearfix'> ";
			    content += "	<div class='order-number color f-14 bt bb'> ";
				content += "		<div "+downcss+"><span>订单号："+tallorderid+"</span><em>￥"+formatNumber(actualamount)+"</em><i></i></div> ";
				content += "	</div> ";
				content += "	<section "+hiddivcss+"> ";
				content += "		<div class='order-infor bb'> ";
				content += "			<h3><u></u>订单信息</h3> ";
				content += "			<dl> ";
				content += "				<dt>下订时间：</dt> ";
				content += "				<dd>"+ordertime+"</dd> ";
				content += "				<dt>订单金额：</dt> ";
				content += "				<dd>￥"+formatNumber(actualamount)+" <span class='freight'>运费：<em> ￥"+formatNumber(orderlist[i].logiscalfee)+"</em></span></dd> ";
				content += "				<dt>订单状态：</dt> ";
				content += "				<dd>"+statedesc+"</dd> ";
				content += "				<dt>订单来源：</dt> ";
				content += "				<dd>"+sourcedesc+"</dd> ";
				content += "			</dl> ";
				content += "			<a class='btn-red ownner to-pay' href='javascript:;' paytoken='" +paytoken+ "' paychanel='" +paychanel+ "' mobile='" +orderlist[i].custelno+ "'>去付款</a> ";
				content += "		</div> ";
				content += "		<div class='consignee-infor bb'> ";
				content += "			<h3><u></u>收货人信息</h3> ";
				content += "			<div>收货人姓名：<span>"+orderlist[i].cusname+"</span></div> ";
				content += "			<div>收货人手机：<span>"+orderlist[i].custelno+"</span></div> ";
				content += "			<div>配送方式：<span>"+isdelivertohomedesc+"</span></div> ";
				content += "			<div>配置地址：<span class='address'>"+orderlist[i].addressfull+"</span></div> ";
				content += "		</div> ";
				content += "		<div class='commodity-infor clearfix'> ";
				content += "			<h3><u></u>商品信息</h3> ";
				for (var j = 0; j < orderproviderlist.length; j++) {
					    var orderitemslist = orderproviderlist[j].orderitemslist; 
					    content += "			<dl> ";
						content += "				<dt class='contact'><span>所属供应商："+orderproviderlist[j].providername+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>售后电话："+orderproviderlist[j].clientservicetel+"</span></dt> ";
						for (var k = 0; k < orderitemslist.length; k++) {
								var goodnamedesc;
							    //全局js变量ship3hCode=363
							    if(orderitemslist[k].is3h!=undefined&&orderitemslist[k].is3h!=null&&orderitemslist[k].is3h!=''&&orderitemslist[k].is3h==ship3hCode){
							    	goodnamedesc = "<em class='color'>【生鲜】</em>"+orderitemslist[k].goodsname;
							    }else{
							    	goodnamedesc = orderitemslist[k].goodsname;
							    }
							    var invalidstatedesc = getInvalidstateDesc(orderitemslist[k].invalidstate);
							    var goodsid = orderitemslist[k].goodsid;
							    var goodbigpic = orderitemslist[k].goodbigpic.split(";")[0]; //商品主图
							    var logno = orderitemslist[k].logisticscode;
							    var itemstate = '';
							    var itemstate = orderitemslist[k].state;
							    //stateWFH全局变量=99
							    if(Number(itemstate)>stateWFH){
									deliverystatecss = "class='btn-red'";
								}else{
									deliverystatecss = "class='btn-red hidden'";
								}
							    
							    content += "				<dl class='clearfix'> ";
								content += "					<dd><img src='" + urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic + "'></dd> ";
								content += "					<dt class='commodity-content'> ";
								content += "						<strong>"+goodnamedesc+"</strong> ";
								var norms = orderitemslist[k].norms;
								if(norms!=''&&norms!=undefined){
									content += "						<span>规格："+orderitemslist[k].norms+"</span> ";
								}
								content += "						<span>数量：x"+orderitemslist[k].goodscount+"</span> ";
								content += "						<span>单价：￥"+formatNumber(orderitemslist[k].goodssaleprice)+"</span> ";
								content += "						<span>售后状态："+invalidstatedesc+"</span>";
								content += "					</dt> ";
								content += "				</dl> ";
								content += "			    <a href='#' onclick='directDeliveryHtml(\""+logno+"\",\""+itemstate+"\",\""+orderitemslist[k].torderitemsid+"\")' "+deliverystatecss+">查看配送状态</a> ";
						}
					
				}
				content += "		</div> ";
				content += "	</section> ";
				content += "</section> ";
		}
		container.append(content);
		myScroll.refresh();		
	} else if(page == 1) {
		showNoDataMsg(msgNoOrder);
	} else {
		hasNextPage = false;
	}
}

/**
 * 付款调支付接口
 * @param paytoken
 */
$(".to-pay").live("tap",function(){
	var paychanel = $(this).attr("paychanel");
	var payurl = $(this).attr("paytoken");
	var mobile = $(this).attr("mobile");
	if(paychanel == paychanelWx){
		payurl = {"payurl":$.parseJSON(payurl)};
	}
	if(paychanel == paychanelZfb){
		payurl = {"payurl":$.parseJSON(JSON.stringify(payurl))};
	}
	if(paychanel == paychannelLklCard){
		payurl = $.parseJSON(payurl);
		payurl.mobile = mobile;
		payurl = {"payurl":payurl};
	}
	storage.setItem("payurl",JSON.stringify(payurl));
	if(parseInt(paychanel) == paychannelLkl){
		storage.setItem("payurl",$(this).attr("paytoken"));
		window.location.href = $(this).attr("paytoken");
	}else{
		if(typeof(cordova)!=undefined && typeof(cordova)!="undefined") {
			cordova._native.pay.pay(
					paychanel,
					JSON.stringify(payurl),
					function(){
						window.location.href = "../wholesale/paysuccess.html";
					},
					function(){
						window.location.href = "../wholesale/payfail.html?t=" +t+ "&paychanel=" +paychanel;
					}	
				)
		}
	}
})

/**
 * 店主收货
 * @param paytoken
 */
$(".toshopreceipt").live("tap",function(){
	var torderproviderid = $(this).attr("opid");//供应商订单号
	var verificationCode = $('#'+torderproviderid+'logno').val();//店主快递单号
	var data = {"mobile":mobile,"logno":verificationCode};
	var resultJson = ajaxCommon(urlreceipt, data);
	//var resultJson = {"_ReturnCode":"000000","_ReturnData":{"shouhuoList":[{"torderproviderid":"150130180026845000011434","sopglist":[{"goodsname":"测试生鲜半成品","goodsnum":"3","norms":"骷髅","actualnum":null,"orderitemid":"1533117"}],"providername":"测试供应商_海燕","providerstate":102},{"torderproviderid":"150129152352592000011434","sopglist":[{"goodsname":"测试生鲜运费","goodsnum":"2","norms":"咖啡色","actualnum":null,"orderitemid":"1532954"}],"providername":"测试供应商_海燕","providerstate":102}],"custelno":"15010108727","cusname":"戚明明","logno":"11161220","channelcode":"25","deliverycom":'顺丰快递'},"_ReturnMsg":null};
	if(resultJson._ReturnCode === returnCodeSuccess) {
		resultJson = JSON.stringify(resultJson);
		storage.setItem("shopReceiptQuery",resultJson);
		window.location.href = "../receipt/shopreceiptinfo.html?logno="+ verificationCode +"&t=" + t;	
	}
	else {
		window.location.href = "../receipt/shopreceiptfail.html?logno="+ verificationCode +"&t=" + t;
	}	
})