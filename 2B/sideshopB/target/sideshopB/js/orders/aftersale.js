var page = 1;
var pageSize = 10;
var orderid = "";
var hasNextPage = true;
var container = $("#scroll-items");
var ordercate = 'lsdd';  //大类  批发、零售、需店主送货
var querytype = 'lssh';  //小类
var myScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

//初始化iScroll控件 
function loaded() {
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('aftersaleorders-scroll', {//iScroll的对象
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
				getaftersaleorderlist();	// 执行加载后的自定义功能	
			}
		}
	});
	setTimeout(function () { document.getElementById('aftersaleorders-scroll').style.left = '0'; }, 800);
}

document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);

$(function() {
	//订单展开
	$(".order-number").live("tap",function() {
		$(this).find("div").toggleClass("down").parent().siblings().toggle();
		$(this).parent().siblings().find(".orders-content").hide();
		$(this).parent().siblings().find(".order-number div").removeClass("down");
		myScroll.refresh();
	});
	//订单菜单列表
	//获取每个li的长度
	var liWidth = null;
	$("#header-list-slide li").each(function(){
		liWidth += parseInt($(this).width());
	});
	$("#header-list-slide").css("width", liWidth + 1 + "px");
	$("#header-list-slide").css("margin","auto");
	var menuScroll = new iScroll("header-list", {
		hScroll : true,
		vScroll : false
	});
	$("#aftersaleorders-scroll").css({"height":windowHeight-163,"margin-top":"6px"});//容器高度
	
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
		if(querytype=='lssh'){
			getaftersaleorderlist();
		}else{
			sessionStorage.setItem("sessionquerytype", querytype);
		}
	});
	
	$("#head a").live("tap", function() {
		  sessionStorage.removeItem("sessionquerytype");
	});
});

//取批发订单下各状态的订单数
function getaftersaleordercnt(ordercate){
	var header = $(".header");
	header.empty();
	var headerlist = $(".header-list");
	headerlist.empty();
	var header_content ="";
	var headerlist_content ="";
	var data = {"token":userToken,"mobile":mobile,"ecnetno":netNo,"ordercate":ordercate};
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
		headerlist_content =" <div id='header-list-slide' class='header-list-slide' style='margin: auto;'> " +
							" <ul> " +
							" <li><a id='lsdfh' href='retailorders.html' class='c-gray f-12'><span>待发货<em>("+ls2nodelivernum+")</em></span></a></li> " +
							" <li><a id='lsyfh' href='retailorders.html' class='c-gray f-12'><span>已发货<em>("+ls2deliverednum+")</em></span></a></li> " +
							" <li><a id='lsyqs' href='retailorders.html' class='c-gray f-12'><span>已签收<em>("+ls2signednum+")</em></span></a></li> " +
							" <li><a id='lssh' href='aftersale.html' class='c-gray f-12 active'><span>售后<em>("+ls2aftersalenum+")</em></span></a></li> " +
							" </ul> " +
							" </div> ";
		
		header.append(header_content);
		headerlist.append(headerlist_content);
		
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}
//显示头部订单列表
getaftersaleordercnt(ordercate);
//
getaftersaleorderlist();
//获取售后订单列表
function getaftersaleorderlist(){
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			var data =  {
				mobile:mobile,
				ecnetno:netNo,
				orderid:orderid,
				page:page,
				pageSize:pageSize
			}
			console.log(data);
			var resultJson = ajaxCommon(urlReturnList,data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _ReturnData = resultJson._ReturnData;
				if (_ReturnData.length>0) {
					
					//若上一个TAB无数据显示，在该页展示的时候先删除上TAB页无记录提示
					if($(".none-data")){
					  $(".none-data").remove();
					}
					
					for (var i = 0; i < _ReturnData.length; i++) {
						console.log(_ReturnData);
						//支付类型
						var paychanel = getPaychanelDesc(_ReturnData[i].paychanel);
						//订单状态
						var statedesc = getOrderStateDesc(_ReturnData[i].state);
						//售后状态
						if(_ReturnData[i].returngoodstatus != null || _ReturnData[i].returngoodstatus != ""){
							returntstate = _ReturnData[i].returngoodstatus;
						}
						if(_ReturnData[i].returnmoneystatus != null || _ReturnData[i].returnmoneystatus != ""){
							returntstate = _ReturnData[i].returnmoneystatus;
						}
						content +="	<section class='orders-items clearfix'>";
						content +="		<div class='order-number f-14 bt bb'><div><span class='nobr'><s class='color'>["+ getReturntState(returntstate) +"]</s>售后申单号："+ _ReturnData[i].treturnitemsid +"</span><i></i></div></div>";
						content +="		<section class='orders-content hidden'>";
						content +="			<div class='consignee-infor bb'>";
						content +="				<div>订单号：<span>"+ _ReturnData[i].orderproviderid +"</span></div>";
						content +="				<div>提交时间：<span>"+_ReturnData[i].returntime+"</span></div>";
						content +="				<div>售后类型：<span>"+_ReturnData[i].returntypename+"</span></div>";					
						content +="				<div>售后状态：<span>"+getReturntState(returntstate)+"</span></div>";					
						content +="				<div>售后商品：</div>";
						content +="				<a class='btn-red after-sale' href='javascript:void(0);' treturnid="+ _ReturnData[i].treturnid +" treturnitemsid="+ _ReturnData[i].treturnitemsid +">处理售后</a>";
						content +="			</div>";
						content +="			<div class='commodity-infor clearfix'>";
						content +="				<dl class='clearfix'>";
						content +="					<dd><img src='../../images/goods-img.jpg'></dd>";
						content +="					<dt class='commodity-content'>";
						content +="						<strong>"+ _ReturnData[i].goodsname +"</strong>";
						content +="						<span>尺寸类型："+ _ReturnData[i].skufrontdisnamestr +"</span>";
						content +="						<span>数量：x"+_ReturnData[i].goodscount+"</span>";
						content +="					</dt>";
						content +="				</dl>";
						content +="			</div>";
						content +="			<div class='order-infor bb'>";
						content +="				<dl>";
						content +="					<dt>下订时间：</dt>";
						content +="					<dd>"+_ReturnData[i].ordertime+"</dd>";
						content +="					<dt>订单金额：</dt>";
						content +="					<dd>￥"+ formatNumber(_ReturnData[i].goodsfinalprice) +"<span class='freight'>运费：<em> ￥"+ formatNumber(_ReturnData[i].logisticsfee) +"</em></span></dd>";
						content +="					<dt>付款方式：</dt>";
						content +="					<dd>"+paychanel+"</dd>";
						content +="					<dt>付款状态：</dt>";
						content +="					<dd>"+_ReturnData[i].ispay+"</dd>";
						content +="					<dt>订单状态：</dt>";
						content +="					<dd>"+ statedesc +"</dd>";
						content +="				</dl>";
						content +="			</div>";
						content +="		</section>";
						content +=" </section>";	
					}
					container.append(content);
					myScroll.refresh();
				}else if(page == 1) {
					showNoDataMsg(msgNoOrder);
					$(".tips").html("&nbsp;");
				}else {
					hasNextPage = false;
				}
			}else{
			
			}	
		}
	},400);	
}
//店主审批参数
var treturnid ="",
	treturnitemsid = "";
	isagree = "";
	remarks = "";
$(function() {
	//选择同意或不同意
	$(".not-agree").on("tap", function() {
		$(".not-agree").addClass("on").siblings().removeClass("on");
		$(".reasons").show().siblings(".msg").hide();
	});
	$(".agree").on("tap", function() {
		$(".agree").addClass("on").siblings().removeClass("on");
		$(".msg").show().siblings(".reasons").hide();
	});
	//点击处理售后,弹窗提示
	$(".after-sale").live("tap", function() {
		$(".pop-up-box").show();
		event.stopPropagation();
	});
	//售后确认
	$(".pop-up-box .btn-red").live("tap", function() {
		var treturnid = $(this).attr("treturnid");
		var treturnitemsid =$(this).attr("treturnitemsid");
		var isagree = $(".types .on").text();
		var remarks = "";
		//选择是否同意
		if(isagree == "不同意"){
			isagree = "false";
			remarks = "不同意"+$(".reasons textarea").val();
		}else{
			isagree = "true";
			remarks = "";
		}
		var data = {
			mobile:mobile,
			treturnid:treturnid,
			treturnitemsid:treturnitemsid,
			isagree:isagree,
			remarks:remarks
		}
		console.log(data);
		var resultJson = ajaxCommon(urlreturnapproval,data);
		resultJson = convertNullToEmpty(resultJson);
		if(resultJson._ReturnCode === returnCodeSuccess){
			showAlertMsg(resultJson._ReturnMsg);
		}else{
			showAlertMsg(resultJson._ReturnMsg);
		}
		$(".pop-up-box").hide();
		window.location.href = "aftersale.html?";
	});
	//稍后处理
	$(".after-sale-infor .btn-gray").live("tap", function() {
		$(".pop-up-box").hide();
	});
});