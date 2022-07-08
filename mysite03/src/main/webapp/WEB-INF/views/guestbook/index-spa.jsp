<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var render = function(vo, mode) {
   var htmls = 
      "<li data-no=''>"+
      "<strong>"+ vo.name +"</strong>"+
      "<p>"+ vo.message +"</p>"+
      "<strong></strong>"+
      "<a href='' data-no='"+vo.no+"'>삭제</a>"+
      "</li>";
      
   $("#list-guestbook")[mode?"append":"prepend"](htmls);
}



var fetch = function(sno) { // 게시글 불러오기
   $.ajax({
      url: "${pageContext.request.contextPath }/api/guestbook",
      type: "get",
	  data: "sno="+sno,
      dataType: "json",
	  error: function(xhr, status, error) {
		console.error("xhr: " + xhr);
		console.error("status: " + status);
		console.error("Error: " + error);
	},
      success: function(response) {
         if(response.result !== 'success'){
            console.error(response.message);
            return;
         }
         
         response.data.forEach(function(list){
            render(list, true);
         });
      }      
   });
};
 
$(function(){
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				console.log("AJAX 삭제 하기");
			},
			"취소": function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			console.log("dialog close!!!");
		}
	});

	$(window).scroll(function(){
		var $window = $(this);
		var $document = $(document);
		
		var windowHeight = $window.height();
		var documentHeight = $document.height();
		var scrollTop = $window.scrollTop();
		
		if(documentHeight < windowHeight + scrollTop + 10) {
			//fetch(10);		
		}
	});
	
	$(document).on('click', '#list-guestbook li a', function(event){
		event.preventDefault();
		
		$("#hidden-no").val($(this).data('no'));
		dialogDelete.dialog('open');
	});	
	
	$("#add-form").submit(function(event){
		event.preventDefault();

		if($("#input-name").val() === ''){
			alert("이름이 비어 있습니다.");
			$("#input-name").focus();
			return;
		}

		if($("#input-password").val()  === ''){
			alert("비밀번호가 비어 있습니다.");
			$("#input-password").focus();
			return;
		}

		if($("#tx-content").val()  === '') {
			alert("내용이 비어있습니다.");
			$("#tx-content").focus();
			return;
		}

		this.submit();
	});
	

	fetch(10);
});


</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="${pageContext.request.contextPath }/guestbook/insert" method="post">
					<input type="text" id="input-name" name="name" placeholder="이름">
					<input type="password" id="input-password" name="password" placeholder="비밀번호">
					<textarea id="tx-content" name="message" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>

				<ul id="list-guestbook"> </ul>

			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>				
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>