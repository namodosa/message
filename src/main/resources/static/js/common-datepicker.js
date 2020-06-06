$(function() {
	// 날짜형만 입력받기
	$(".date-picker").keyup(function(event) {
		event.preventDefault();
		if ((event.keyCode !== 46) && (event.keyCode < 37 || event.keyCode > 40)) {
			$(this).val($(this).val().replace(/[^0-9:\-]/gi, ""));
		}
	}).datepicker({
		// todayBtn: "linked",
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true,
		format : 'yyyy-mm-dd',
		orientation : "bottom left",
		language : "ko"
	}).prop("maxlength", 10);
})



$(function() {
	// 날짜형만 입력받기
	$(".date-picker2").keyup(function(event) {
		event.preventDefault();
		if ((event.keyCode !== 46) && (event.keyCode < 37 || event.keyCode > 40)) {
			$(this).val($(this).val().replace(/[^0-9:\-]/gi, ""));
		}
	}).datepicker({
	    format: "yyyy-mm",
	    startView: "months", 
	    minViewMode: "months",
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true,
		orientation : "bottom left",
		language : "ko"
	}).prop("maxlength", 10);
})
