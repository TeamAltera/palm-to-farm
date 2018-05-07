<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	var confirm_id = false;
	var confirm_pwd = false;
	var confirm_email = false;
	var user_email;
	var checkAjaxSetTimeout;
	var checkPwdSetTimeout;
	var sessionClockTimeout;
	function checkId() {
		clearTimeout(checkAjaxSetTimeout);
		checkAjaxSetTimeout = setTimeout(
			function() { //커넥션이 많이 발생되는 것을 방지하기 위해 1~2초정도의 텀을준다
				var sendData = JSON.stringify({
					memberId : $('.searchText').val()
				});
				console.log(sendData);
				$.ajax({
					type : 'POST',
					url : 'userExist',
					data : sendData,
					dataType : "json",
					contentType : "application/json;charset=UTF-8",
					cache : false,
					success : function(data) {
						console.log(data);
						var result = data['result'];
						if (confirm_id = result == 0) {//사용가능한 아이디라면 confirm_id는 true
							$('#checkIdMsg').html('<p style="color:green"><strong>사용가능한 아이디입니다.</strong></p>');
						} else if (result == 1) {
							$('#checkIdMsg').html('<p style="color:red"><strong>존재하는 아이디입니다.</strong></p>');
						} else {
							$('#checkIdMsg').html('');
						}
							confirm();//조건 충족하는지 검사
						}
				}); //end ajax    
		}, 1500); //setTimeout
	}

	function sendEmail() {
		$('#send_code_btn').html('인증코드재전송');
		user_email=$('#str_email01').val()+'@'+$('#str_email02').val();
		var sendData = JSON.stringify({
			mail : user_email
		});
		 var html="<p id='joinCodeMsg'>인증코드를 <strong><span id='session_clock'>3:00</span></strong> 이내로 입력해주세요."
		 +"<br><input type='text' placeholder='인증코드' id='joinCode'/><button onclick='sendJoinCode(); return false;'>입력</button></p>";
		$('#checkJoinCode').html(html);
		$.ajax({
			type : 'POST',
			url : 'send_email',
			data : sendData,
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			cache : false,
			success : function(data) {
			}
		}); //end ajax
	}
	
	function sendJoinCode(){
		var sendData = JSON.stringify({code :$('#joinCode').val()});
		$.ajax({
			type : 'POST',
			url : 'joinCodeCheck',
			data : sendData,
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			cache : false,
			success : function(data) {
				if(confirm_email=data['result']){
					$('#checkJoinCode').html('<p style="color:green"><strong>인증성공</strong></p>');
					$('#email_area').html(user_email);
					$('#user_email').val(user_email);//히든태그에 인증받은 사용자 이메일 포함
					console.log($('#user_email').val());
				}
				else{
					if(!$('#joinCodeMsg:last-child').is('span'))
						$('#joinCodeMsg').append("<span style='color:red'><strong>인증실패</strong></span>");
				}
				confirm();
			}
		}); //end ajax
	}
	
	function mailChange(){
		var input_mode;
		if(input_mode=$('#select_email').val()!=1){
			$('#str_email02').val($('#select_email').val());
		}
		else{
			$('#str_email02').val('');
		}
		$('#str_email02').prop('disabled', input_mode);
	}

	function confirm() { //가입버튼 활성화/비활성화
		$('.submit_btn').prop('disabled', !(confirm_id && confirm_pwd && confirm_email));
	}
</script>
<style>
    ul,li {
    	list-style-type: none;
    }
    .invalid {
    	color: #ec3f41;
    }
    .valid {
    	color: #3a7d34;
    }
    #pwd_info {
    	display: none;
    }
</style>
</head>
<body>
	<a href="login">뒤로가기</a>
	<hr>
	<h2>회원가입 페이지 입니다.</h2>
	
	<%--회원가입 폼 --%>
	<form action="memberJoinAction" method="post" autocomplete="off">
		<%--아이디--%>
		<%--패스워드 --%>
		패스워드: <input type="password" name="pwd" required class="pwd" id=pwd><br>
		<%--패스워드 확인--%>
		패스워드 확인: <input type="password" required class="pwd_confirm"><br>
		<div id="checkPwdMsg"></div>
		성: <input type="text" id="first" required><br>
		이름: <input type="text" id="second" required><br>
		<%-- 이메일 --%>
		이메일: 
		<span id="email_area">
		<input type="text" name="str_email01" id="str_email01" style="width:100px" required>@ 
		<input type="text" name="str_email02" id="str_email02" style="width:100px;" disabled value="naver.com">
		<select style="width:100px;margin-right:10px" name="select_email" id="select_email"> 
			<option value="1">직접입력</option> 
			<option value="naver.com" selected>naver.com</option> 
			<option value="hanmail.net">hanmail.net</option> 
			<option value="hotmail.com">hotmail.com</option> 
			<option value="nate.com">nate.com</option> 
			<option value="yahoo.co.kr">yahoo.co.kr</option>
			<option value="gmail.com">gmail.com</option>
		</select> 
		<button type="button" onclick="sendEmail(); return false;" id="send_code_btn">인증코드전송</button>
		</span>
		<input type="hidden" name="email" id="user_email">
		<br>
		<div id="checkJoinCode">
			<p style="color: red">
				아이디, 비밀번호 분실 시 이메일 주소로 안내해드리므로,<br>사용하시는 이메일을 정확하게 입력해주세요.
			</p>
		</div>
		<%--가입버튼--%>
		<button type="submit" class="submit_btn">가입</button>
	</form>
</body>
</html>