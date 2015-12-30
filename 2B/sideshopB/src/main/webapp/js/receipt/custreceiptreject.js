$(function() {

	var url = window.location.href;
	var sincecode = getParamValueByName(url, "sincecode");
	$("#sincecode").html(sincecode);
	
});