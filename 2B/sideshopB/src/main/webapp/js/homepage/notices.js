var resultJson = getJsonData();

$(function() {
	var sUrl = window.location.href;
	if(storage.getItem(hasLogined)==null || storage.getItem(hasLogined)=="" || storage.getItem(hasLogined)=="0") {
		//没有登录时，跳转到登录页面
		window.location.href = "../accounts/login.html?refer=" + sUrl;
	}
	
	if(resultJson._ReturnCode === returnCodeSuccess) {
		if(resultJson._ReturnData.length == 0) {
			$(".notice-wrap").hide();
			$(".none-notices").removeClass("hidden");
		} else {
			//获取消息通知列表
			getNewsInfoList();
		}
	}
})

function getJsonData(){
	var data = {"memberName":mobile, "newstype":newsType};
	return $.parseJSON(ajaxCommon(urlGetNewsList, data));
}

function getNewsInfoList(){
	var result = "";
	if(resultJson._ReturnCode === returnCodeSuccess){
		var newsCounts = resultJson._ReturnData.length;
		if(newsCounts > 0){
			var readCount = newsCounts > newsCount ? newsCount : newsCounts;
			for(var i = 0; i < readCount; i++){
				var _ReturnData = resultJson._ReturnData;
				var newsDateTime = _ReturnData[i].sendTime;
				var content = "";  //消息内容
				var type = "";  //0:商品详情 1：商品列表  2：外链  3：专题列表  4：咨询
				var itemsid = "";  //子节点id
				var title = "";  //子消息标题
				var picture = "";  //消息图片
				var url = "";  //消息链接到的url
				result += "<div class='notice-wrap'>";
				result += "<h4><span>" + newsDateTime + "</span></h4>";
				result += "<div class='products'>";
				var results = _ReturnData[i].items;
				for(j = 0; j < results.length; j ++){
					content = results[j].content
					type = results[j].type;
					itemsid = results[j].itemsid;
					title = results[j].title;
					picture = results[j].picture;
					url = getUrlByType(itemsid, type, content);
					if(type == "4") {
						storage.setItem(itemsid + "", JSON.stringify(results[j]));
						storage.setItem(itemsid + "_date ", newsDateTime);
					}
					if(j == 0) {
						result += "<a class='top' href='" + url + "'>";
						result += "<img src='" + picture + "'/>";
						result += "<em>" + title + "</em>";
						result += "</a>";
						result += "<ul class='items'>";
					} else if(j == results.length) {
						result += "</ul>";
					} else {
						result += "<li>";
						result += "<a href='" + url + "'>";
						result += "<em>" + title + "</em>";
						result += "<img src='" + picture + "' onerror='productErrImg(this);' />";
						result += "</a>";
						result += "</li>";
					}
				}
				result += "</div>";
				result += "</div>";
			}
		} else {
			$(".notice-main").append($(".none-notices"));
			$(".none-notices").removeClass("hidden");
		}
	} else {
		$(".notice-main").append($(".none-notices"));
		$(".none-notices").removeClass("hidden");
	}
	
	$(".notice-main").html(result);
}

function getUrlByType(itemsid, type, content) {
	//0:商品详情  1：商品列表  2：外链  3：专题列表  4：咨询
	var url = "";
	if(type == "0") {
		url = "../wholesale/prodetail.html?goodsid=" + content + "&t=" + t;
	} else if(type == "4") {
		url = "details.html?itemsid=" + itemsid + "&t=" + t;
	} else {
		url = content;
	}

	return url;
}