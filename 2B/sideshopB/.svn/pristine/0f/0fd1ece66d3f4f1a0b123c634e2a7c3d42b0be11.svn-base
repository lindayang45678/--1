//临时的跳入搜索成功和失败页
$('.w-searchbtn').on('tap',function(){
	var searchparm = $.trim($('.search-wrap input').val());
	if(searchparm != ""){	
		window.location.href = encodeURI(encodeURI("searchsuccess.html?searchparm=" + searchparm));
	}
});

$(function(){
	var url = window.location.href;
	var searchparm = getParamValueByName(url, "searchparm");
	$(".w-search-fail").html("没有搜索到和【" +decodeURI(searchparm)+ "】相关的商品");
})