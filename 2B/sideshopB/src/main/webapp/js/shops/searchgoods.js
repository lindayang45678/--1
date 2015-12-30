var page = 1;
var pageSize = 5;
var hasNextPage = true;
var container = $(".searchok-list");
var searchparm = '';
var myScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset;

//初始化iScroll控件 
function loaded() {
	pullUpEl = document.getElementById('loading');
    pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('search-srocll', {//iScroll的对象
		hScrollbar: false,
		vScrollbar: false,
		useTransition: false,
		useTransform: false,
		checkDOMChanges: false,
		topOffset: pullDownOffset,
		onScrollMove: function () {
			if(this.y < (this.maxScrollY - 5) && !pullUpEl.className.match("flip")) {
				pullUpEl.className = "flip";
				this.maxScrollY = this.maxScrollY;
			} else if(this.y > (this.maxScrollY + 5) && pullUpEl.className.match("flip")) {
				pullUpEl.className = "";
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match("flip")) {
				pullUpEl.className = "hidden";
				getgoodlist();  //执行加载后的自定义功能	
			}
		}
	});
	setTimeout(function () { document.getElementById('search-srocll').style.left = '0'; }, 800);
}

document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);

$(function() {
	var sUrl = window.location.href;
	$("#search-srocll").css("height",windowHeight-44);
	searchparm = decodeURI(getParamValueByName(sUrl, "searchparm"));
	$('.orders-search input').val(searchparm);
	getgoodlist();
});

//根据商品名称，搜索平台商品列表
function getgoodlist(){
	var content ="";
	setTimeout(function () {
		if(hasNextPage){
			var data = {
				"goodsName": searchparm,
				"psam": psam,
				"page": page,
				"pageSize": pageSize
			};

			var resultJson = ajaxCommon(urlSeachbygoodsname,data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _ReturnData = resultJson._ReturnData;
				if (_ReturnData.length > 0) {
					$(".none-data").remove();
					//反馈搜索结果条数
					/*if(i == 0 && page == 1){
						$('.header').empty();
						$('.header').append('共搜索到<em class="color" id="total">' + + '</em>个相关订单 ');
					}*/
					for (var i = 0, max = _ReturnData.length ; i < max; i++) {
					    var tGoodInfoPoolId = _ReturnData[i].tGoodInfoPoolId;
					    var issaleflag = _ReturnData[i].issaleflag;  //判断产品池产品是否已上架到小店  0-未上架，1-已上架
					    var goodbigpic = _ReturnData[i].goodBigPic.split(";")[0]; 
					    content +="<dl>";
					    content +="<dt><img src='" + urlImage + "/" + tGoodInfoPoolId + "/" + imgw100 + "/" + goodbigpic + "' onerror='productErrImg(this);'/></dt>";
					    content +="<dd>";
					    content +="<h3>"+_ReturnData[i].goodName+"</h3>";
					    if(issaleflag == "0") {
							//未上架到小店
					    	content +="<a href='addgoods1.html?tGoodsInfoId=" + tGoodInfoPoolId +"' class='added'>上架到小店</a>";
						} else {
							//已上架到小店
							content +=		"<a href='javascript:;' class='added disabled'>商品已上架</a>";
						}
					    content +="</dd>";
					    content +="</dl>";
					}
					container.append(content);
					myScroll.refresh();		
				}else if(page == 1 ){
					//反馈搜索结果
					//showAlertMsg()
					showNoDataMsg(msgGoodSearchFail);
				}
				
			} else {
				hasNextPage = false;
				//showAlertMsg()
			}
			page ++;
		}
	},400);		
}

/* 商品搜索*/
//键盘搜索事件
$(".orders-search input").on("keyup",function(e) {
	if(e.keyCode == 13) {
		searchparm = $.trim($('.orders-search input').val());
		if(searchparm == ""){
			showAlertMsg(msgProductNameEmpty);
			return;
		}
		window.location.href = encodeURI(encodeURI("searchgoods.html?searchparm=" + searchparm + "&t=" + t));
	}
});

//点击搜索事件
$(".good-search").on("touchend",function(e) {
	e.preventDefault();
	searchparm = $.trim($('.orders-search input').val());
	if(searchparm == ""){
		showAlertMsg(msgProductNameEmpty);
		return;
	}
	window.location.href = encodeURI(encodeURI("searchgoods.html?searchparm=" + searchparm + "&t=" + t));
});