$(function() {
	getCustomerList();
});

function getCustomerList() {
	var id = shopId;
	var page = 1;
	var pageSize = 10;
	var container = $(".container");
	var items = $(".items");
	var ul = $(".items ul");
	var contentUp = "";
	var contentDown = "";
	var data = {
		"id": id,
		"page": page,
		"pageSize": pageSize
	};
	var resultJson = ajaxCommon(urlCustomerList, data);
	resultJson = convertNullToEmpty(resultJson);

	if(resultJson._ReturnCode === returnCodeSuccess) {
		var counts = resultJson._ReturnData.count;
		contentUp += "<div class='head f-16'>本店顾客共<em>" + counts + "</em>人</div>";
		container.html("").append(contentUp);
		var	list = resultJson._ReturnData.customerList;
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				contentDown += "<li class='f-18'>";
				contentDown += "<img src='../../images/customer_default.png' />"; //list[i].icon 暂时使用默认图片
				contentDown += "<h4>"+list[i].customerName+"("+list[i].phone+")";
				contentDown += "<p class='f-16 c-gray nobr'>"+list[i].nickName;
				contentDown += "</p>";
				contentDown += "</h4>";
				contentDown += "</li>";
			}
			ul.html("").append(contentDown);
		}
		items.append(ul);
		container.append(items);
	} else {
		showAlertMsg(resultJson._ReturnMsg);
	}
}