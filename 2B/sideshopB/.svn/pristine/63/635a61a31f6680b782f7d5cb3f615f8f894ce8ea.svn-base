//获取小店信息
$(function(){
	var businessStartTimes;
	var businessEndTimes;
	if($.trim(businessStartTime).length >0){
		businessStartTimes = businessStartTime;
	}else{
		businessStartTimes = "09:00";
	}
	if($.trim(businessEndTime).length >0){
		businessEndTimes = businessEndTime;
	}else{
		businessEndTimes = "22:00";
	}
	
	if(parseInt(businessStartTimes.split(":")[0]) <10 && $.trim(businessStartTimes).length <2){
		businessStartTimes = businessStartTimes.replace(businessStartTimes.split(":")[0],"0"+businessStartTimes.split(":")[0]);
	}
	if(parseInt(businessEndTimes.split(":")[0]) <10 && $.trim(businessEndTimes).length <2){
		businessEndTimes = businessEndTimes.replace(businessEndTimes.split(":")[0],"0"+businessEndTimes.split(":")[0]);
	}

	innitSelectedTime("startSelectTime", businessStartTimes);
	innitSelectedTime("endSelectTime", businessEndTimes);

	if(osType() == "ios") {
		$("#startSelectTime").width("auto");
		$("#endSelectTime").width("auto");

		$("#startSelectDiv").on("touchend",function(e){
			e.preventDefault();

			$("#startSelectTime").focus();
		});

		$("#endSelectDiv").on("touchend",function(e){
			e.preventDefault();

			$("#endSelectTime").focus();
		});
	}

	$(".btn-red").on("touchend",function(e){
		e.preventDefault();

		var businessStartTimes = $.trim($("#startSelectTime").val());
		var businessEndTimes = $.trim($("#endSelectTime").val());
		$(this).removeClass("focus");
		var reg = /^([01]\d|2[01234]):([0-5]\d|59)$/;
		if(!reg.test(businessStartTimes) || !reg.test(businessEndTimes)){//判断时间格式
			showAlertMsg(msgStartEndFormatError);
			return false;
		}
		storage.setItem("businessStartTime", businessStartTimes);
		storage.setItem("businessEndTime", businessEndTimes);
		window.location.href = "editshop1.html?preSaveData=1&t="+t;
	});
})

//初始化select值
function innitSelectedTime(id, value) {
	var obj = document.getElementById(id);
	for(var i=0; i<obj.options.length; i++){
		if(value == obj.options[i].value){
			obj.options[i].selected = "selected";
			break;
		}
	}
}