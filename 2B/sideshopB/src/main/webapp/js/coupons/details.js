$(function() {
	
	var url = window.location.href;
	var favorablecode = getParamValueByName(url, "favorablecode");
	var startflag = getParamValueByName(url, "startflag");
	var startflag1stChar = startflag.charAt(0);
	var couponType = getParamValueByName(url, "couponType");
	var cost = getParamValueByName(url, "cost");
	var orderamount = getParamValueByName(url, "orderamount");
	var usednum = getParamValueByName(url, "usednum");//已经使用次数
	var usenum = getParamValueByName(url, "usenum");//可用次数
	var endtime = getParamValueByName(url, "endtime");
	var frequencys = getParamValueByName(url, "frequencys");
	var remaining = usenum - usednum;
	//时间戳转换
	var data = new Date(parseInt(endtime));
	function formatDate(data) {     
        var year = data.getFullYear();     
        var month = data.getMonth()+1;     
        var date = data.getDate();         
        return year+"-"+month+"-"+date;     
    }
	function splitnum(number) {
	    var s = number + "";
	    var n = parseInt(((s.split("."))[1]));
	    return n;
	}
	
	var frequencys = decodeURI(frequencys).split(",");	//使用限制
	var container = $(".coupon-details dl");
	var content ="";
	if(startflag1stChar == "Q") {	
		content +="<dt><p>券&nbsp;&nbsp;号："+ QGPF_Coupon + favorablecode +"</p>";
	} else if(startflag1stChar == "B") {
		content +="<dt><p>券&nbsp;&nbsp;号："+ BDPF_Coupon + favorablecode +"</p>";
	} else {
		content +="<dt><p>券&nbsp;&nbsp;号："+ favorablecode +"</p>";
	}
	if(decodeURI(couponType) == 359) {
		content +="<dt>券类别：现金券</dt>";
	} else {
		content +="<dt>券类别：满减券</dt>";
	}
	if(splitnum(cost)>=0) {
		content +="<dt>面值：￥"+ cost +"</dt>";
	} else {
		content +="<dt>面值：￥"+ cost +".00</dt>";
	}
	if(splitnum(orderamount)>=0) {
		content +="<dt>所需消费金额：￥"+ orderamount +"</dt>";
	} else {
		content +="<dt>所需消费金额：￥"+ orderamount +".00</dt>";
	}
	content +="<dt>已用："+ usednum +"次</dt>";
	content +="<dt>剩余："+ remaining +"次</dt>";
	content +="<dt>截止时间："+ formatDate(data) +"</dt>";
	content +="<dt>使用限制：</dt>";
	for (var i = 0; i < frequencys.length; i++) {
		content +="<dt>仅限&nbsp;&nbsp;"+ frequencys[i] +"</dt>";
	};
	container.html("").append(content);
});