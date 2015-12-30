$(function() {
	var paychanel = storage.getItem("paychanel") || paychannelLklWallet;
	if(paychanel) {
		$(".pay-items div").removeClass("paytype-selected");
		paychanel = parseInt(paychanel);
		switch (paychanel) {
			case paychannelLklWallet:
				$(".pay-items div").eq("0").addClass("paytype-selected");
				break;
			case paychannelLkl:
				$(".pay-items div").eq("1").addClass("paytype-selected");
				break;
			case paychanelWx:
				$(".pay-items div").eq("2").addClass("paytype-selected");
				break;
		}
	}
});

$(function() {
	var paychanel = paychannelLklWallet;
	var payIndex = 0;
	$(".pay-items div").on("click", function(e) {
		$(".pay-items div").removeClass("paytype-selected");
		$(this).addClass("paytype-selected");

		payIndex = parseInt($(this).attr("id"));
		switch (payIndex) {
			case 0:
				paychanel = paychannelLklWallet;
				break;
			case 1:
				paychanel = paychannelLkl;
				break;
			case 2:
				paychanel = paychanelWx;
				break;
		}
		storage.setItem("paychanel",paychanel);
		e.preventDefault();
		window.location.href = "fill.html";
	})
});