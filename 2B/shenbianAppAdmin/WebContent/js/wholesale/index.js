/*初始化iScroll*/
var indexScroll;
function scrollBdrx() {
	indexScroll = new iScroll("menu-l",{ hScrollbar: false, vScrollbar: false });
}

/*根据浏览器的高度计算iScroll的高度*/
$(function(){
	var sHeight = $(window).height();
	$("#menu-l").css("height",sHeight-62-42);
});

/*批发进货首页菜单*/
$(function() {
	scrollBdrx();
	$("#menu-l a").on("tap", function() {
		$("#menu-l").find("a").removeClass("white").find("i").removeClass("sj-redr");//去掉所有选中样式
		$(this).addClass("white").find("i").addClass("sj-redr");//添加当前选中样式
		
		if($(this).siblings("ul").hasClass("hidden")){
			$(this).siblings("ul").removeClass("hidden");
			indexScroll.refresh();
		}else{
			$(this).siblings("ul").addClass("hidden");
			$(this).parent().find("ul").addClass("hidden");
			if($(this).siblings("ul").length != 0){
				$(this).removeClass("white").find("i").removeClass("sj-redr");//去掉当前选中样式
			}
			indexScroll.refresh();
		}
		$(this).parent().siblings("li").find("a").siblings("ul").addClass("hidden");//同级隐藏
		
		if($(this).hasClass('promotion')){	
			$('.wholesale-ad').removeClass('hidden');
			$('.wholesale-goods').addClass('hidden');
		}else{	
			$('.wholesale-ad').addClass('hidden');
			$('.wholesale-goods').removeClass('hidden');
		}
	});
	indexScroll.refresh();
});

//点击右侧加入购物车按钮弹出sku
$('.add-cart').on('tap',function(event){
	if($(this).hasClass('add-cart-gray')){return;}
	$('#sku').removeClass('hidden');
	$('.masker').removeClass('hidden');
	  event.stopPropagation();	
});
//点击sku取消或加入购物车按钮
$('.sku-cancel').on('tap',function(){
	$('#sku').addClass('hidden');
	$('.masker').addClass('hidden');	
});
$('.sku-sure').on('tap',function(){
	$('#sku').addClass('hidden');
	$('.masker').addClass('hidden');	
});
//点击sku中的属性进行选择
$('.sku-detail em').on('tap',function(){
	$(this).siblings().removeClass('sku-red');
	$(this).addClass('sku-red');	
});
//点击sku进行加减数量
$('.sku-plus').on('tap',function(){
	var n=parseInt($(this).prev().attr('value'));
	$(this).prev().attr('value',n+1);	
});
$('.sku-minus').on('tap',function(){
	var n=parseInt($(this).next().attr('value'));
	if(n>=1){$(this).next().attr('value',n-1);}		
});
//判断底部去结算的购物车数量大于零显示
if(parseInt($('.goodscart').html())>0){
	$('.goodscart').show();
}else{
	$('.goodscart').hidden();
}

