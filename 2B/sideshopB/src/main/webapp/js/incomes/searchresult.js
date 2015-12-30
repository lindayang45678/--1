/* 初始化iScroll*/
var incomesScroll;
function scrollIncomes(){
	incomesScroll = new iScroll("incomes-wrap",{ 
		hScrollbar: false, 
		vScrollbar: false,
		onScrollEnd: function(){
			incomesSearch();
		},
	});
}

/*根据浏览器的高度计算iScroll的高度*/
$(function(){
	$("#incomes-wrap").css("height",windowHeight-$("header").height());
});

var page = 0;
var hasNextPage = true;//是否有下一页
var data;

$(function(){
	var url = window.location.href;
	data = getParamValueByName(url, "data");
	console.log(data);
	data = $.parseJSON(data);
})

$(function(){
	scrollIncomes();
	incomesSearch();
})

function incomesSearch(){
	var container = $(".incomes ul");
	var content = "";
	setTimeout(function(){
		if(hasNextPage){
			page ++;
			data.page = page;
			console.log("page: "+page);
			var resultJson = ajaxCommon(urlQueryEarningsOrder, data);
			resultJson = convertNullToEmpty(resultJson);
			if (resultJson._ReturnCode === returnCodeSuccess) {
				var _returnData = resultJson._ReturnData;
				if(_returnData.length > 0){
					for (var i = 0; i < _returnData.length; i++) {
						content += "<li class='incomes-result'>";
						var url = "details.html?earningsTime=" +_returnData[i].earningsDate +"&orderProviderId=" +_returnData[i].providerOrderId+ "&returnFlag=" +_returnData[i].returnFlg+ "&earningsType=" +data.earningsType;
						content += "<a href='" +url+ "'>";
						content += "<p>" +formatData(_returnData[i].earningsDate)+ "</p>";
						content += "<p>订单号：" +_returnData[i].providerOrderId+ "<em>" +_returnData[i].afterState+ "</em></p>";
						if(data.earningsType == selfGoods){
							content += "<p>商品分润：" +_returnData[i].earningsAmount+ "</p>";
						}else{
							content += "<p>付款方式：" +_returnData[i].payMode+ "</p>";
							content += "<p>结算金额：￥" +_returnData[i].settlementAmount+ "</p>";
						}
						content += "</a>";
						content += "</li>";
					}
					container.append(content);
					incomesScroll.refresh();
				}else{
					if(page == 1) {
						window.location.href = "searchfail.html";
					} else {
						hasNextPage = false;
					}
				}
			} else {
				//接口返回错误
			}
		}
	}, 50)
}