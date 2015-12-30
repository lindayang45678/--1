$(function(){
	loadJs();
})

//分享
$(function() {
	$('#share').on('tap', function(e) {
		var title = $(this).siblings('h2').text();
		cordova._native.share.share(title, title, '', function() {
			
		}, function() {
			
		});
	});
})
