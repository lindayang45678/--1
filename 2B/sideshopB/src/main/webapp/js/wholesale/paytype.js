$(function() {
	var paychanel = storage.getItem("paychanel") || paychannelLklCard;
	if(paychanel) {
		$(".pay-items div").removeClass("paytype-selected");
		paychanel = parseInt(paychanel);
		switch (paychanel) {
			case paychannelLklCard:
				$(".pay-items div").eq("0").addClass("paytype-selected");
				break;
			case paychannelLklWallet:
				$(".pay-items div").eq("1").addClass("paytype-selected");
				break;
			case paychannelLkl:
				$(".pay-items div").eq("2").addClass("paytype-selected");
				break;
			case paychanelWx:
				$(".pay-items div").eq("3").addClass("paytype-selected");
				break;
			case paychanelZfb:
				$(".pay-items div").eq("4").addClass("paytype-selected");
				break;
		}
	}
});

$(function() {
	var paychanel = paychannelLklCard;
	var payIndex = 0;
	$(".pay-items div").on("click", function(e) {
		$(".pay-items div").removeClass("paytype-selected");
		$(this).addClass("paytype-selected");

		payIndex = parseInt($(this).attr("id"));
		switch (payIndex) {
			case 0:
				paychanel = paychannelLklCard;
				break;
			case 1:
				paychanel = paychannelLklWallet;
				break;
			case 2:
				paychanel = paychannelLkl;
				break;
			case 3:
				paychanel = paychanelWx;
				break;
			case 4:
				paychanel = paychanelZfb;
				break;
		}
		storage.setItem("paychanel",paychanel);
		e.preventDefault();
		window.location.href = "fill.html";
	})
});