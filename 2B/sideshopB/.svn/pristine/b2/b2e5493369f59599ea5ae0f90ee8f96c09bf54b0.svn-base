$(function() {
	var url = window.location.href;
	var itemsid = getParamValueByName(url, "itemsid");
	var itemDetails = storage.getItem(itemsid);

	if(itemDetails!=null && itemDetails!="") {
		itemDetails = $.parseJSON(itemDetails);
		var result = "<span>";
		result += "<strong class='f-16'>";
		result += itemDetails.title;
		result += "</strong>";
		result += "<i class='time'>";
		result += storage.getItem(itemsid + "_date ");
		result += "</i>";
		result += "<span class='paragraph'>";
		result += itemDetails.content;
		result += "</span>";

		$("#itemDetails").html(result);
	}
});