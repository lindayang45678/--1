$(function() {
	var billtype = storage.getItem("billtype") || billtypeNone;//类型
	var billtitle = storage.getItem("billtitle") || "";//发票抬头
	$("#billtitle").hide();//默认发票抬头隐藏
	//默认状态
	if(billtype > billtypeNone){
		$(".invoice-page section").show();
		$(".invoice-choose div").addClass("invoice-choose-on");
		$(".personal i").addClass("ok");
		$("#billtitle").hide();
		$("#billtitle").val();
		if(billtype == billtypeBusiness){
			$(".company i").addClass("ok");
			$(".personal i").removeClass("ok");
			$("#billtitle").show();
			$("#billtitle").val(billtitle);
		} 			
	}else{
		$("#billtitle").val();
		$(".invoice-page section").hide();
	}
	//是否需要发票
	$('#invoice-slide').on('tap',function() {
		$(this).toggleClass('invoice-choose-on');
		$(".invoice-page section").toggle();
	});
	
	$('#invoice-slide').swipe(function(){
		$(this).toggleClass('invoice-choose-on');
		$(".invoice-page section").toggle();
	})
	
	//切换个人和公司选项
	$(".personal").on("tap", function() {
		$(".company i").removeClass("ok");
		$(this).find("i").addClass("ok");
		$("#billtitle").hide();
	});
	$(".company").on("tap", function() {
		$(".personal i").removeClass("ok");
		$(this).find("i").addClass("ok");
		$("#billtitle").show();
	});
	//确认保存
	$(".btn-red").on("touchend", function(e) {
		if($(".invoice-choose div").hasClass("invoice-choose-on")) {
			if($(".company i").hasClass("ok")) {
				var billtitle = $("#billtitle").val();
				if (billtitle == "") {
					showAlertMsg(msgCompanyempty);
					return ;
				} else {
					billtype = billtypeBusiness;
				}
			} else {
				billtype = billtypePersonal;
			}
		} else {
			billtype = billtypeNone;
		}
		storage.setItem("billtype", billtype);
		storage.setItem("billtitle", billtitle);
		e.preventDefault();
		window.location.href = "fill.html";
	})
})