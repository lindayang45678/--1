//商品加减号
$('.fill-plus').on('tap',function(){
	var n=parseInt($(this).prev().attr('value'));
	$(this).prev().attr('value',n+1);	
});
$('.fill-minus').on('tap',function(){
	var n=parseInt($(this).next().attr('value'));
	if(n>=1){$(this).next().attr('value',n-1);}		
});
//点击叉删除本条购物
$('.fill-close').on('tap',function(){
	if($(this).parent().parent().find('dd').length<=1)
	{$(this).parent().parent().remove();}
	else{$(this).parent().remove();}
});