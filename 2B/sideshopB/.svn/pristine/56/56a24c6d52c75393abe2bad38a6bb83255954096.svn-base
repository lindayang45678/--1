$(function() {
	var longitude = "";//经度
	var latitude = "";//纬度
	$("#baiduMapFrame").attr("src",baiduMapAPI + "?t=" + t + "&psam=" + psam);
	//地图
	function loadmap(){
		var ifm = document.getElementById("baiduMapFrame");
		var src = ifm.src;
		if(src.indexOf("&psam=") > -1) {
			ifm.src = src.substring(0,src.lastIndexOf("&psam=")+6) + psam + "#save";
		} else {
			ifm.src = src + "&psam=" + psam + "#save";
		}
	}
	//提交
	$(".tj").on("click", function() {
		loadmap();//地图调用
		$(".shop-position iframe").remove();
		window.location.href = "setting.html?t=" + t;
	});
});