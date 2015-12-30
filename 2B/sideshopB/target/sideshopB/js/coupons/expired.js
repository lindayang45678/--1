/*var disabled = 1;
var page = 1;
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
		useTransition: false, 
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
			}
		},
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';	
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';	
				getCouponList();	// 执行加载后的自定义功能	
			}
		}
	});
	setTimeout(function () { document.getElementById('coupon-scroll').style.left = '0'; }, 800);
};
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);

 根据浏览器的高度计算iScroll的高度
$(function(){
	$("#coupon-scroll").css("height",windowHeight-137);
	$(".coupon-container dl").css("width","100%");
	$("#loading").hide();
});

getCouponList();
//优惠券列表
function getCouponList(){
	$("#loading").show();
	var container = $(".coupon-container .coupon-items");
	var content ="";
	setTimeout(function () {
		if(hasNextPage){
			page ++;
			var data = {
					"token":userToken,
					"mobile":mobile,
					"status":"outTime",
					"disabled": disabled,
					"page": page,
					"pageSize": pageSize
				};
			var resultJson = ajaxCommon(urlFindCoupon,data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _ReturnData = resultJson._ReturnData;
				var	couponList = _ReturnData.couponList;
				if (couponList.length>0) {
					for (var i = 0; i < couponList.length; i++) {
						var url = encodeURI(encodeURI("details.html?favorablecode=" +couponList[i].favorablecode+ "&couponType="+ couponList[i].couponType +"&cost="+ couponList[i].cost +"&orderamount="+ couponList[i].orderamount + "&usednum=" + couponList[i].usednum + "&usenum=" + couponList[i].usenum + "&endtime=" + couponList[i].endtime + "&frequencys=" + couponList[i].frequencys +""));						
						var favorablecode = couponList[i].favorablecode;
						var favcodelecode1stChar = favorablecode.charAt(0);
						content +="<li class='coupon-list'>";
						content +="<a href='" +url+ "'>";
						content +="<dl class='expired clearfix'>";
						if(favcodelecode1stChar == "Q") {	
							content +="<dt><p>券&nbsp;&nbsp;号："+ QGPF_Coupon + couponList[i].favorablecode +"</p>";
						} else if(favcodelecode1stChar == "B") {
							content +="<dt><p>券&nbsp;&nbsp;号："+ BDPF_Coupon + couponList[i].favorablecode +"</p>";
						} else {
							content +="<dt><p>券&nbsp;&nbsp;号："+ couponList[i].favorablecode +"</p>";
						}
						content +="<p><span>已&nbsp;&nbsp;用："+couponList[i].usednum+"次</span><span>剩&nbsp;&nbsp;余："+couponList[i].usednum+"次</span></p></dt>";
						content +="<dd class='fl c-white "+'coupon1'+"'><strong>"+couponList[i].cost+"</strong><span>满"+couponList[i].orderamount+"元可用</span></dd>";
						content +="</dl>";
						content +="</a>";
						content +="</li>";
					};
					container.append(content);
					myScroll.refresh();	
				}else{
					showAlertMsg(msgCouponexpired);
				}
			} else {
				hasNextPage = false;
				showAlertMsg(resultJson._ReturnMsg);
			}
		}
	},1000);	
	$("#loading").hide();	
};
*/

$(function(){
	var disabled = 1;
	var page = 1;
	var pageSize = 10;
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
	console.log(resultJson);
	if(resultJson._ReturnCode === returnCodeSuccess){
		var unusedCount = resultJson._ReturnData.unusedCount;
		var usedCount = resultJson._ReturnData.usedCount;
		if(unusedCount > 0){
			$("#unusecount").append("<em>" + unusedCount + "</em>");
		}
		if(usedCount > 0) {
			$("#usedcount").append("<em>" + usedCount + "</em>");
		}
	}
	//验证优惠券
	$(".coupon-test-btn").on("tap", function(){
		if($(".fill-coupon-use input").val() == "") {
			showAlertMsg(msgCouponsNotEmpty);
		} else {
			showAlertMsg(msgCouponsNotthrough);
		}
	});
})