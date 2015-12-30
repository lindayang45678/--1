$(function() {
	loadJs();
})

//设置保存key-value，供客户端调用
$(function() {
	$('.setting-choose a').on('tap', function() {
		$(this).parent().toggleClass('on');
		var key = $(this).attr('id');
		if($(this).parent().hasClass('on')){
			cordova._native.saveInfo.set(key,'on');
		}else{
			cordova._native.saveInfo.set(key,'off');
		}
	});
})