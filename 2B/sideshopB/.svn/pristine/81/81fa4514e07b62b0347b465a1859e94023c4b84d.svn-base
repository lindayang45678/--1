$(function() {
	$("#btnQuit").on("tap", function() {
		var data = {"mobile":mobile};
		//var resultJson = ajaxCommon(urlLogout, data);
		var source = storage.getItem("source");

		clearStorage();

		window.location.href = "../accounts/login.html?source=" + source + "&t=" + t;
		//if(resultJson._ReturnCode === returnCodeSuccess) {
			//清除所有localStorage里的信息
			//clearStorage();
			//window.location.href = "../accounts/login.html?t=" + t;
		//} else {
			//@TODO 如果接口有错改如何处理？
			//清除所有localStorage里的信息
			//clearStorage();
			//window.location.href = "../accounts/login.html?t=" + t;
		//}
	});
});