//临时的跳入搜索成功和失败页
$('.w-searchbtn').on('tap',function(){
	var searchparm = $.trim($('.search-wrap input').val());
	if(searchparm != ""){
		storage.setItem("searchparm", searchparm);
		window.location.href = encodeURI(encodeURI("searchsuccess.html?searchparm=" + searchparm + "&t=" + t));
	}
});