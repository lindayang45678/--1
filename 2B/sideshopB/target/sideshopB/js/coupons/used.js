var disabled = 1;
var page = 0;
var pageSize = 10;
var hasNextPage = true;
var myScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

//初始化iScroll控件 
function loaded() {
	pullUpEl = document.getElementById('loading');
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('coupon-scroll', {//iScroll的对象
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
				getCouponList();	// 执行加载后的自定义功能	
			}
		}
	});
}
document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);

/* 根据浏览器的高度计算iScroll的高度*/
$(function() {
	$("#coupon-scroll").css("height",windowHeight-91);
	$(".coupon-container dl").css("width","100%");
	getCouponList();
});

//优惠券列表
function getCouponList() {
	var container = $(".coupon-container .coupon-items");
	var content ="";
	setTimeout(function () {
		if(hasNextPage){
			page ++;
			var data = {
					"token":userToken,
					"mobile":mobile,
					"status":"used",
					"disabled": disabled,
					"page": page,
					"pageSize": pageSize
				};
			var resultJson = ajaxCommon(urlFindCoupon,data);
			resultJson = convertNullToEmpty(resultJson);	
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _ReturnData = resultJson._ReturnData;
				var	couponList = _ReturnData.couponList;
				if(_ReturnData.unusedCount > 0){
					$("#unusecount").append("<em>" + _ReturnData.unusedCount + "</em>");
				}
				if(_ReturnData.usedCount > 0) {
					$("#usedcount").append("<em>" + _ReturnData.usedCount + "</em>");
				}
				if(couponList.length > 0) {
					for (var i = 0; i < couponList.length; i++) {
						var url = encodeURI(encodeURI("details.html?favorablecode=" +couponList[i].favorablecode+ "&startflag="+ couponList[i].startflag + "&couponType="+ couponList[i].couponType +"&cost="+ parseInt(couponList[i].cost) +"&orderamount="+ couponList[i].orderamount + "&usednum=" + couponList[i].usednum + "&usenum=" + couponList[i].usenum + "&endtime=" + couponList[i].endtime + "&frequencys=" + couponList[i].frequencys +""));
						var favorablecode = couponList[i].favorablecode;
						var startflag = couponList[i].startflag;
						var usednum = parseInt(couponList[i].usednum);//已经使用次数
						var usenum = parseInt(couponList[i].usenum);//可用次数
						var remaining = usenum - usednum;
						var startflag1stChar = startflag.charAt(0);
						content +="<li class='coupon-list'>";
						content +="<a href='" +url+ "'>";
						content +="<dl class='elapsed clearfix'>";
						if(startflag1stChar == "Q" || startflag1stChar == "q") {	
							content +="<dt><p>券&nbsp;&nbsp;号："+ QGPF_Coupon + favorablecode +"</p>";
						} else if(startflag1stChar == "B" || startflag1stChar == "b") {
							content +="<dt><p>券&nbsp;&nbsp;号："+ BDPF_Coupon + favorablecode +"</p>";
						} else {
							content +="<dt><p>券&nbsp;&nbsp;号："+ favorablecode +"</p>";
						}
						content +="<p><span>已&nbsp;&nbsp;用："+ usednum +"次</span><span>剩&nbsp;&nbsp;余："+ remaining +"次</span></p></dt>";
						content +="<dd class='fl c-white "+'coupon1'+"'><strong>"+parseInt(couponList[i].cost)+"</strong><span>满"+couponList[i].orderamount+"元可用</span></dd>";
						content +="</dl>";
						content +="</a>";
						content +="</li>";
					};
					container.append(content);
					myScroll.refresh();	
				} else if (page == 1){
					showNoDataMsg(msgCouponused);
				}
			} else {
				hasNextPage = false;
				showAlertMsg(resultJson._ReturnMsg);
			}
		}
	},400);	
}		




