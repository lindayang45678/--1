/*初始化iScroll*/
var leftScroll;
function scrollLeft() {
	leftScroll = new iScroll("plat-left",{ hScrollbar: false, vScrollbar: false });
}
var rightScroll;
function scrollRight(){
	rightScroll=new iScroll("plat-content",{hScrollbar: false, vScrollbar: false });
}
	

/*根据浏览器的高度计算iScroll的高度以及相应的块高度*/
$(function() {
	var sHeight = $(window).height();
	$("#plat-left").css("height", sHeight-$("header").height()-5);
	$('#plat-content').css("height", sHeight-$("header").height()-5);
	
});

/*批发进货首页菜单*/
$(function() {
	scrollLeft();
	$("#plat-left a").live("tap", function() {
		$("#plat-left").find("a").removeClass("bgwhite").find("i").removeClass("red-icon"); //去掉所有选中样式
		$(this).addClass("bgwhite").find("i").addClass("red-icon"); //添加当前选中样式
		
		if($(this).siblings("ul").hasClass("hidden")) {
			$(this).siblings("ul").removeClass("hidden");
			leftScroll.refresh();
		} else {
			$(this).siblings("ul").addClass("hidden");
			$(this).parent().find("ul").addClass("hidden");
			if($(this).siblings("ul").length != 0) {
				$(this).removeClass("bgwhite").find("i").removeClass("red-icon"); //去掉当前选中样式
			}
			leftScroll.refresh();
		}
		$(this).parent().siblings("li").find("a").siblings("ul").addClass("hidden"); //同级隐藏
	});
	leftScroll.refresh();
});
$(function() {
	scrollRight();
});
