// JavaScript Document
$(function(){
	$(".add-service").on('tap',function(){
		$(".add-del").removeClass('hidden');
	});
	$('.del-image').on('tap',function(){
		$('.add-del').addClass('hidden');
	});
});