<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
	function login(){
		var sendData=JSON.stringify({email : $("#e").val(), pwd:$("#p").val()});
		$.ajax({
			type : 'POST',
			url : 'user/signin',
			data : sendData,
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			cache : false,
			success : function(data) {
				console.log(data);
			}
		});
	}
</script>
</head>
<body>
<h2>로그인 페이지 입니다.</h2>
<form action="loginAction" method="post">
	아이디:<input type="text" placeholder required id="e" ><br>
	패스워드:<input type="password" required id="p"><br>
	<button type="button" onclick="login();">로그인</button>
</form>
<hr>
<a href="member_join">회원가입</a><br>
<a href="find_password">비밀번호 찾기</a>
</body>
</html>

