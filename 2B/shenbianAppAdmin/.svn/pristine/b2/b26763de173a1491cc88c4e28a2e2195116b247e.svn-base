$(function() {
	$(".receipt-actual a:eq(0)").on("tap", function() {
		var num = $(this).next().attr("value");
		if (num > 1) {
			$(this).next().attr("value", num - 1);
		}
	});
	$(".receipt-actual a:eq(1)").on("tap", function() {
		var num = parseInt($(this).prev().attr("value"));
		$(this).prev().attr("value", (1 + num));
	});
});