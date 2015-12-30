var page = 1;
var pageSize = 10;
var hasNextPage = true;
var ordercate = 'lsdd';  //大类  批发、零售、需店主送货
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
	myScroll = new iScroll('retailorders-scroll', {//iScroll的对象
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
				getretailorderlist(querytype);	// 执行加载后的自定义功能	
			}
		}
	});
}

document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);

$(function() {
	
	
	//点击该订单已全部收货,弹窗提示
	$(".all-receipts").live("tap", function() {
		$(".pop-up-box").show();
	});
	$(".pop-up-box .btn-gray").live("tap", function() {
		$(".pop-up-box").hide();
	});
    
	//默认显示批发进货的页面
	getretailordercnt(ordercate);
	
	//默认显示批发待发货的订单列表
	getretailorderlist(querytype);
	
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
		getretailorderlist(querytype);
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
	$("#retailorders-scroll").css("height",windowHeight-124);
	$("#header-list-slide").css("margin","auto");
});

//取零售订单下各状态的订单数
function getretailordercnt(ordercate){
	var header = $(".header");
	header.empty();
	var headerlist = $(".header-list");
	headerlist.empty();
	var header_content ="";
	var headerlist_content ="";
	var data = {"token":userToken,"ecnetno":netNo,"ordercate":ordercate};
	var resultJson = ajaxCommon(urlOrderCnt,data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess){
		var _ReturnData = resultJson._ReturnData;
		var ls2nodelivernum = _ReturnData.ls2nodelivernum;	//零售待发货总数
		var ls2deliverednum = _ReturnData.ls2deliverednum;	//零售已发货总数
		var ls2signednum = _ReturnData.ls2signednum;		//零售已签收总数
		var ls2aftersalenum = _ReturnData.ls2aftersalenum;	//零售售后总数
		var sxneedbsendnum = _ReturnData.sxneedbsendnum;	//需店主送货订单总数
		
		header_content =" <a href='wholesaleorders.html' class='new-tbl-cell f-16'><span>批发订单</span></a> " +
			" <a href='retailorders.html' class='new-tbl-cell f-16'><span class='active'>零售订单</span></a> " +
			" <a href='ownership.html' class='new-tbl-cell f-16'><span class='delivery'>需店主送货";
		if(sxneedbsendnum > 0) {
			header_content += "<em>" + sxneedbsendnum + "</em>";
		}
		header_content += "</span></a>";
		headerlist_content =" <div id='header-list-slide' class='header-list-slide'> " +
							" <ul> " +
							" <li><a id='lsdfh' href='#' class='c-gray f-12'><span>待发货<em>("+ls2nodelivernum+")</em></span></a></li> " +
							" <li><a id='lsyfh' href='#' class='c-gray f-12'><span>已发货<em>("+ls2deliverednum+")</em></span></a></li> " +
							" <li><a id='lsyqs' href='#' class='c-gray f-12'><span>已签收<em>("+ls2signednum+")</em></span></a></li> " +
							" <li><a id='lssh' href='aftersale.html' class='c-gray f-12'><span>售后<em>("+ls2aftersalenum+")</em></span></a></li> " +
							" </ul> " +
							" </div> ";
		
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
		initvalue = 'lsdfh';
	}
	return initvalue;
}


//取零售订单下各状态的订单列表
function getretailorderlist(querytype){
	//状态选项选中样式置换
	$('#'+querytype+'').addClass("active");
	var content ="";
	setTimeout(function () {
		if(hasNextPage){
			var data = {
					"token":userToken,
					"ecnetno":netNo,
					"querytype":querytype,
					"page": page,
					"pageSize": 10
				};
			var resultJson = ajaxCommon(urlOrderList,data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _ReturnData = resultJson._ReturnData;
				var	orderproviderlist = _ReturnData.orderproviderlist;
				if (orderproviderlist.length>0) {
					
					//若上一个TAB无数据显示，在该页展示的时候先删除上TAB页无记录提示
					if($(".none-data")){
					  $(".none-data").remove();
					}
					
					for (var i = 0; i < orderproviderlist.length; i++) {
						var statedesc = getOrderStateDesc(orderproviderlist[i].state);
						var deliverystatecss = '';
						var ordertime = getLocalTime(orderproviderlist[i].ordertime);
						var sourcedesc = getOrderSourceDesc(orderproviderlist[i].source);
						var isdelivertohomedesc = getDevicetypeDesc(orderproviderlist[i].isdelivertohome);
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
						
						    content += "<section class='orders-items clearfix'> ";
						    content += "	<div class='order-number color f-14 bt bb'> ";
							content += "		<div "+downcss+"><span>订单号："+orderproviderlist[i].torderproviderid+"</span><em>￥"+formatNumber(actualamount)+"</em><i></i></div> ";
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
							
						    var orderitemslist = orderproviderlist[i].orderitemslist; 
						    content += "			<dl> ";
							content += "				<dt class='contact'><span>所属供应商："+orderproviderlist[i].providername+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>售后电话："+orderproviderlist[i].clientservicetel+"</span></dt> ";
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
				
			} else {
				hasNextPage = false;
				showAlertMsg(resultJson._ReturnMsg);
			}
			page ++;
		}
	},400);	
}


