var myScroll, pullDownOffset, pullUpEl, pullUpOffset;
/**
 * 初始化iScroll控件
 */
function loaded() {
	pullUpEl = document.getElementById("loading");
	pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll("items", {//iScroll的对象
		hScrollbar: false,
		vScrollbar: false,
		useTransition: false, 
		checkDOMChanges: false,
		topOffset: pullDownOffset,
		onScrollMove: function () {
			if(this.y < (this.maxScrollY - 5) && !pullUpEl.className.match("flip")) {
				pullUpEl.className = "flip";
				this.maxScrollY = this.maxScrollY;
			} else if(this.y > (this.maxScrollY + 5) && pullUpEl.className.match("flip")) {
				pullUpEl.className = "hidden";
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match("flip")) {
				pullUpEl.className = "hidden";
				getCustomerList();//执行加载后的自定义功能	
			}
		}
	});
}

document.addEventListener("touchmove", function(e) {e.preventDefault();}, false);
document.addEventListener("DOMContentLoaded", function() {setTimeout(loaded, 200);}, false);


$(function() {
	loaded();
	getCustomerList();
	$("#items").css({"height":windowHeight-88});
});

var page = 1;
var pageSize = 10;
var hasNextPage = true;
var container = $(".container");
var contentUp = $(".head em");
var contentDown = "";
function getCustomerList() {
	var id = shopId;
	var items = $(".items");
	var ul = $(".items ul");
	if(hasNextPage) {
		var data = {
			"id": id,
			"page": page,
			"pageSize": pageSize
		};
		var resultJson = ajaxCommon(urlCustomerList, data);
		resultJson = convertNullToEmpty(resultJson);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			var counts = resultJson._ReturnData.count;
			if (page == 1) {
				contentUp.html(counts);
			}
			var	list = resultJson._ReturnData.customerList;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					contentDown += "<li class='f-16'>";
					contentDown += "<img src='../../images/customer_default.png' />"; //list[i].icon 暂时使用默认图片
					contentDown += "<h4>"+list[i].customerName;
					if(list[i].phone != ""){
						contentDown += "<s style='display:inline-block;text-indent:12px;'>("+list[i].phone+")</s>";
					}
					contentDown += "<p class='f-16 c-gray nobr'>"+list[i].nickName;
					contentDown += "</p>";
					contentDown += "</h4>";
					contentDown += "</li>";
				}
				ul.append(contentDown);
				myScroll.refresh();
				page++;
			} else {
				hasNextPage = false;
			}
		} else {
			contentUp.hide();
			showAlertMsg(resultJson._ReturnMsg);
		}
		
	}
}