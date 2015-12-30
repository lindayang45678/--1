$(function() {
	var url = window.location.href;
	var logno = getParamValueByName(url, "logno");
	$("#logno").html(decodeURI(logno));
});