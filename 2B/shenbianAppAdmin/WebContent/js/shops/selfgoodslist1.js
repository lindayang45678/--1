// JavaScript Document
/*删除*/
$(function(){
	$('.del').on('tab',function(){
		$(this).parent('div').parent('.goods-list').hide('slow');
	});
});