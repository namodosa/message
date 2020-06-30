
//숫자 천단위 콤마(,) 넣기 정규식 함수
function addComma(n) {
    var reg = /(^[+-]?\d+)(\d{3})/;
    n += '';

    while (reg.test(n)) {
        n = n.replace(reg, '$1' + ',' + '$2');
    }
    return n;
}

$(function() {
	// 한글입력 제한
	$(".no-kor").on("blur keyup", function(event) {
		event.preventDefault();
		$(this).val($(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, ""));
	});

	// 숫자만 입력받기
	$(".number").keyup(function(event) {
		if ((event.keyCode !== 46) && (event.keyCode < 37 || event.keyCode > 40)) {
			event.preventDefault();
			$(this).val($(this).val().replace(/[^0-9]/gi, ""));
		}
	});
	
	// 숫자(실수형)만 입력받기
	$(".float-number").on("keyup", function(event) {
		if ((event.keyCode !== 46) && (event.keyCode < 37 || event.keyCode > 40)) {
			event.preventDefault();
			$(this).val($(this).val().replace(/[^0-9:\.]/gi, ""));
		}
	});
	
	// 영문+숫자만 입력받기
	$(".eng-number").keyup(function(event) {
		if ((event.keyCode !== 46) && (event.keyCode < 37 || event.keyCode > 40)) {
			event.preventDefault();
			$(this).val($(this).val().replace(/[^A-Za-z0-9]/gi, ""));
		}
	});
	
	// 연락처만 입력받기
	$(".phone-number").keyup(function(event) {
		if ((event.keyCode !== 46) && (event.keyCode < 37 || event.keyCode > 40)) {
			event.preventDefault();
			$(this).val($(this).val().replace(/[^0-9:\-]/gi, ""));
		}
	});
	
	// 숫자만 입력받고 천단위 콤마 찍기 
	$(".money").keyup(function(event) {
		$(this).val($(this).val().replace(/\D/g,"").replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,'));
	});
	
})




 $(document).ready(function(){
    if($('.dotline').length){
        var $dotline =$('.dotline');
        $dotline.dotdotdot({
            watch:true
        });

    }
});
