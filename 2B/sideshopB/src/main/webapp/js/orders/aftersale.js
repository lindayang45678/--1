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
	$("#aftersaleorders-scroll").css({"height":windowHeight-121,"margin-top":"6px"});//容器高度
	
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
	
	$("#head h1 a").live("tap", function() {
		  sessionStorage.removeItem("sessionquerytype");
	});
});

//取批发订单下各状态的订单数
function getaftersaleordercnt(ordercate){
	var header = $(".header");
	header.empty();
	var headerlist = $(".header-list");
	headerlist.empty();
	//var header_content ="";
	var headerlist_content ="";
	var data = {"token":userToken,"mobile":mobile,"ecnetno":netNo,"ordercate":ordercate,"deviceno":psam};
	var resultJson = ajaxCommon(urlOrderCnt,data);
	resultJson = convertNullToEmpty(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess){
		var _ReturnData = resultJson._ReturnData;
		var ls2nodelivernum = _ReturnData.ls2nodelivernum;	//零售待发货总数
		var ls2deliverednum = _ReturnData.ls2deliverednum;	//零售已发货总数
		var ls2signednum = _ReturnData.ls2signednum;		//零售已签收总数
		var ls2aftersalenum = _ReturnData.ls2aftersalenum;	//零售售后总数
		var sxneedbsendnum = _ReturnData.sxneedbsendnum;	//需店主送货订单总数
		
		/*header_content =" <a href='wholesaleorders.html' class='new-tbl-cell f-16'><span>批发订单</span></a> " +
			" <a href='retailorders.html' class='new-tbl-cell f-16'><span class='active'>零售订单</span></a> " +
			" <a href='ownership.html' class='new-tbl-cell f-16'><span class='delivery'>需店主送货";
		if(sxneedbsendnum > 0) {
			header_content += "<em>" + sxneedbsendnum + "</em>";
		}
		header_content += "</span></a>";*/
		headerlist_content =" <div id='header-list-slide' class='header-list-slide' style='margin: auto;'> " +
							" <ul class='clearfix'> " +
							" <li><a id='lsdfh' href='retailorders.html' class='c-gray f-12'><span>待发货<em>("+ls2nodelivernum+")</em></span></a></li> " +
							" <li><a id='lsyfh' href='retailorders.html' class='c-gray f-12'><span>已发货<em>("+ls2deliverednum+")</em></span></a></li> " +
							" <li><a id='lsyqs' href='retailorders.html' class='c-gray f-12'><span>已签收<em>("+ls2signednum+")</em></span></a></li> " +
							" <li><a id='lssh' href='aftersale.html' class='c-gray f-12 active'><span>售后<em>("+ls2aftersalenum+")</em></span></a></li> " +
							" </ul> " +
							" </div> ";
		
		//header.append(header_content);
		headerlist.append(headerlist_content);
		
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}
//显示头部订单列表
getaftersaleordercnt(ordercate);
//
getaftersaleorderlist();
//判断是否是对货状态
var goodstatusArry = [301,309,347,348];
function IsInArray(arr,val){
    var testStr=','+arr.join(",")+",";
    return testStr.indexOf(","+val+",")!=-1;
}
//获取售后订单列表
function getaftersaleorderlist(){
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			var data =  {
				psam:psam,
				/*ecnetno:netNo,*/
				orderid:orderid,
				page:page,
				pageSize:pageSize
			}
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
						var returntime = getLocalTime(_ReturnData[i].returntime);//提交时间
						var ordertime = getLocalTime(_ReturnData[i].ordertime);
						var goodsid = _ReturnData[i].goodsid;
						var goodbigpic = _ReturnData[i].goodbigpic.split(";")[0];//商品主图	
						//console.log(_ReturnData);
						//支付类型
						var paychanel = getPaychanelDesc(_ReturnData[i].paychanel);
						//订单状态
						var statedesc = getOrderStateDesc(_ReturnData[i].state);
						//售后状态
						if(_ReturnData[i].returngoodstatus != 301) {
							returntstate = getReturntState(Number(_ReturnData[i].returngoodstatus));
						} else if(_ReturnData[i].returnmoneystatus != ""){
							returntstate = getReturntState(Number(_ReturnData[i].returnmoneystatus));
						} else {
							returntstate = getReturntState(Number(_ReturnData[i].returngoodstatus));
						}
						content +="	<section class='orders-items clearfix'>";
						content +="		<div class='order-number f-14 bt bb'><div><span class='nobr'><s class='color'>["+ returntstate +"]</s>售后申请单号："+ _ReturnData[i].treturnitemsid +"</span><i></i></div></div>";
						content +="		<section class='orders-content hidden'>";
						content +="			<div class='consignee-infor bb'>";
						content +="				<div>订单号：<span>"+ _ReturnData[i].orderproviderid +"</span></div>";
						content +="				<div>提交时间：<span>"+returntime+"</span></div>";
						content +="				<div>售后类型：<span>"+_ReturnData[i].returntypename+"</span></div>";					
						content +="				<div>售后状态：<span>"+returntstate+"</span></div>";					
						content +="				<div>售后商品：</div>";
						if(!IsInArray(goodstatusArry,_ReturnData[i].returngoodstatus)){
							content +="				<a class='btn-red after-sale' href='javascript:void(0);' treturnid="+ _ReturnData[i].treturnid +" treturnitemsid="+ _ReturnData[i].treturnitemsid +">处理售后</a>";
						}
						content +="			</div>";
						content +="			<div class='commodity-infor clearfix'>";
						content +="				<dl class='clearfix'>";
						content +="					<dd><img src='" + urlImage + "/" + goodsid + "/" + imgw100 + "/" + goodbigpic + "'></dd>";
						content +="					<dt class='commodity-content'>";
						content +="						<strong>"+ _ReturnData[i].goodsname +"</strong>";
						var skufrontdisnamestr = _ReturnData[i].skufrontdisnamestr;
						if(skufrontdisnamestr!=''&&skufrontdisnamestr!=undefined){
							content += "						<span>规格："+ _ReturnData[i].skufrontdisnamestr +"</span>";
						}
						content +="						<span>数量：x"+_ReturnData[i].goodscount+"</span>";
						content +="					</dt>";
						content +="				</dl>";
						content +="			</div>";
						content +="			<div class='order-infor bb'>";
						content +="				<dl>";
						content +="					<dt>下单时间：</dt>";
						content +="					<dd>"+ordertime+"</dd>";
						content +="					<dt>订单金额：</dt>";
						content +="					<dd>￥"+ formatNumber(_ReturnData[i].goodsfinalprice) +"<span class='freight'>运费：<em> ￥"+ formatNumber(_ReturnData[i].logisticsfee) +"</em></span></dd>";
						content +="					<dt>付款方式：</dt>";
						content +="					<dd>"+paychanel+"</dd>";
						content +="					<dt>付款状态：</dt>";
						content +="					<dd>"+getIspayDesc(_ReturnData[i].ispay)+"</dd>";
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
				page++;
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
	//点击处理售后,弹窗提示
	var treturnid ="";
	var treturnitemsid = "";
	$(".after-sale").live("tap", function(event) {
		if($(this).attr("disabled")) {
			return ;
		}
		$(".pop-up-box").show();
		$this = $(this);
		treturnid = $(this).attr("treturnid");
		treturnitemsid = $(this).attr("treturnitemsid");
		event.stopPropagation();
	});
	//选择同意或不同意
	$(".not-agree").live("tap", function() {
		$(".reasons").html("");
		$(".msg").html("");
		var retreasons = $(".reasons");
		var reasons = "";
		reasons +="<p>您不同意该售后申请，请输入理由：</p>";
		reasons +="<textarea></textarea>";
		retreasons.append(reasons);
		$(".not-agree").addClass("on").siblings().removeClass("on");
		$(".reasons").show().siblings(".msg").hide();
		event.stopPropagation();
	});
	$(".agree").on("tap", function(event) {
		$(".reasons").html("");
		$(".msg").html("");
		var retmsg = $(".msg");
		var msg = "";
		msg +="<p>您将同意该售后申请，请确认是否收到顾客的退货？</p>";
		msg +="<p>如已收到，请点击确认按钮；</p>";
		msg +="<div class='description'>";
		msg +="<h4>说明：</h4>";
		msg +="<p>1、在线支付的订单：系统将在3-7个工作日内，自动给顾客支付的银行卡退款！</p>";
		msg +="<p>2、在线支付的订单：如该订单的款项已打给您，那下个结算 日将扣除该售后订单退款款项；</p>";
		msg +="<p>3、货到付款订单，需要您退款给顾客；</p>";
		retmsg.append(msg);
		$(".agree").addClass("on").siblings().removeClass("on");
		$(".msg").show().siblings(".reasons").hide();
		event.stopPropagation();
	});
	//售后确认
	$(".pop-up-box .btn-red").live("tap", function(event) {
		var isagree = $(".types .on").text();
		var remarks = "";
		//选择是否同意
		if(isagree == "不同意"){
			isagree = "false";
			remarks = "不同意"+$(".reasons textarea").val();
		} else if(isagree == "同意"){
			isagree = "true";
			remarks = "同意";
		} else {
			isagree = "";
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
		if(resultJson._ReturnCode === returnCodeSuccess) {
			$(".after-sale").each(function(){
				if($this.attr("treturnid") == treturnid) {
					$this.attr("disabled","disabled").addClass("btn-gray");
				}
			});
			showAlertMsg(resultJson._ReturnMsg);
		}else{
			showAlertMsg(msgAfterType);
		}
		$(".pop-up-box").hide();		
		event.stopPropagation();
	});
	//稍后处理
	$(".after-sale-infor .btn-gray").live("tap", function(event) {
		$(".pop-up-box").hide();
		event.stopPropagation();
	});
	
});