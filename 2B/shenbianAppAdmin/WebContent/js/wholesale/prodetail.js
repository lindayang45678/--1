//批发进货商品详情
$(function() {
	var sWidth = $(window).width()-20;
	var picCount = 4;// 动态获取商品图片的数量
	$("#wrapper").css("width", sWidth + "px");
	$("#scroller").css("width", sWidth * picCount + "px");
	$("#product-pic li").css("width", sWidth + "px");
	$("#product-pic img").css("width", sWidth + "px");

	var prodetailScroll = new iScroll('wrapper', {
		snap : true,
		momentum : false,
		hScrollbar : false,
		onScrollEnd : function() {
			$("#pagination a").removeClass("active").eq(this.currPageX).addClass("active");
		}
	});
});
