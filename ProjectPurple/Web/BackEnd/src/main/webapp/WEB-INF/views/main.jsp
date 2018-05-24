<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<!-- <script type="text/javascript" src="http://jsgetip.appspot.com"></script> -->
<script>
	var checkIPSetTimeout;
	var ap_ip;
	var inner_ip;
	var ssid;
	var xhr;
  		function confirm_ip(){
  			<%--텍스트 박스에 입력한 값들을 ip문자열로 만든다--%>
  			ap_ip=$('#Aclass').val()+'.'+$('#Bclass').val()+'.'+$('#Cclass').val()+'.'+$('#Dclass').val()
  			console.log(ap_ip);
  			<%--공유기에 전송할 사용자코드값--%>
  			/* var sendData = JSON.stringify({user : ${sessionScope.userInfo.userCode}}); */
  			var sendData = JSON.stringify({ip: ap_ip});
  			<%--ajax send이전에 대기 문구를 알려준다--%>
  			$('#result_text').html('<p style="color:orange">등록 여부 확인중...</p>');
  			<%--
  				ajax로 send
  			--%>
  			if(xhr)
  				xhr.abort();
  			xhr=$.ajax({
  				type : 'POST',
				url : 'device/confirm',
				data : sendData,
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				success : function(data) {
					console.log(data);
				}
  			});
  			/* xhr=$.ajax({
				type : 'POST',
				url : 'http://'+ap_ip+'/search.php',
				data : sendData,
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				cache : false,
				success : function(data) {
					console.log(data);
					if(data['state']=='OK'){
						$('#regist_btn').prop('disabled', false);
						inner_ip=data['inner_ip'];
						ssid=data['ssid'];
						var str='<br>공유기에 수경재배기가 '+data['inner_ip'].length+'개 연결되어있습니다.';
						for(var i=0; i< data['inner_ip'].length; i++){
							str+=('<br><strong>'+data['inner_ip'][i]+'</strong>');
						}
						$('#result_text').html('<p style="color:green">등록 가능한 IP주소 입니다.</p>'+str);
					}
				}
			}); */
			<%--end ajax--%>
  		}
  		function ip_validation(){
  			clearTimeout(checkIPSetTimeout);
  			checkIPSetTimeout = setTimeout(
			function(){
  				for(var index=0;index<4;index++){
  					if($('#'+String.fromCharCode(65+index)+'class').val()==null){
  						$('#confirm_btn').prop('disabled', true);
  						return;
  					}
  				}
  				$('#confirm_btn').prop('disabled', false);
			},500);
  		}
  		function submit_ip(){
  			var sendData = JSON.stringify({ip: ap_ip});
  			var token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjowLCJwd2QiOm51bGwsImVtYWlsIjoic2VAbmF2ZXIuY29tIiwiZmlyc3ROYW1lIjoi7ZmNICAgICAgICAgICAgICIsInNlY29uZE5hbWUiOiLquLjrj5kgICAgICAgICAgICAgICAgICAgICAgICAiLCJzZkNudCI6MH0sImlhdCI6MTUyNTUzODYwNjA4OH0.twd3p_NppbZN9Z16Lt2DgDAfzm1jWYu1y-eUdY32Dhk";
  			$.ajax({
				type : 'POST',
				url : 'device/add/ap/manual',
				beforeSend: function(request) {
				    request.setRequestHeader("Authorization", token);
				  },
				dataType : "json",
				data:sendData,
				contentType : "application/json;charset=UTF-8",
				cache : false,
				success : function(data) {
					console.log(data);
				}
			});
  		}
  		
  		function info(){
  			var token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjowLCJwd2QiOm51bGwsImVtYWlsIjoic2VAbmF2ZXIuY29tIiwiZmlyc3ROYW1lIjoi7ZmNICAgICAgICAgICAgICIsInNlY29uZE5hbWUiOiLquLjrj5kgICAgICAgICAgICAgICAgICAgICAgICAiLCJzZkNudCI6MH0sImlhdCI6MTUyNTUzODYwNjA4OH0.twd3p_NppbZN9Z16Lt2DgDAfzm1jWYu1y-eUdY32Dhk";
  			$.ajax({
				type : 'GET',
				url : 'device/info',
				beforeSend: function(request) {
				    request.setRequestHeader("Authorization", token);
				  },
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				cache : false,
				success : function(data) {
					console.log(data);
				}
			});
  		}
  		
  		function user_info(){
  			var token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjowLCJwd2QiOm51bGwsImVtYWlsIjoic2VAbmF2ZXIuY29tIiwiZmlyc3ROYW1lIjoi7ZmNICAgICAgICAgICAgICIsInNlY29uZE5hbWUiOiLquLjrj5kgICAgICAgICAgICAgICAgICAgICAgICAiLCJzZkNudCI6MH0sImlhdCI6MTUyNTUzODYwNjA4OH0.twd3p_NppbZN9Z16Lt2DgDAfzm1jWYu1y-eUdY32Dhk";
  			$.ajax({
				type : 'GET',
				url : 'user/info',
				beforeSend: function(request) {
				    request.setRequestHeader("Authorization", token);
				  },
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				cache : false,
				success : function(data) {
					console.log(data);
				}
			});
  		}
  		
  		function delete_device(arg){
  			var sendData=JSON.stringify({sfCode : arg});
  			$.ajax({
				type : 'POST',
				url : 'deleteDevice',
				data : sendData,
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				cache : false,
				success : function(data) {
					console.log('success');
				}
			});
  		}
  		
  		function delete_all(){
  			var token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjowLCJwd2QiOm51bGwsImVtYWlsIjoic2VAbmF2ZXIuY29tIiwiZmlyc3ROYW1lIjoi7ZmNICAgICAgICAgICAgICIsInNlY29uZE5hbWUiOiLquLjrj5kgICAgICAgICAgICAgICAgICAgICAgICAiLCJzZkNudCI6MH0sImlhdCI6MTUyNTUzODYwNjA4OH0.twd3p_NppbZN9Z16Lt2DgDAfzm1jWYu1y-eUdY32Dhk";
  			var sendData=JSON.stringify({ip : '203.250.35.169'});
  			$.ajax({
				type : 'POST',
				url : 'device/delete/ap/manual',
				data : sendData,
				beforeSend: function(request) {
				    request.setRequestHeader("Authorization", token);
				  },
				dataType : "json",
				contentType : "application/json;charset=UTF-8",
				cache : false,
				success : function(data) {
					console.log(data);
				}
			});
  		}
  		
  		function order(cmd){
  			var middle='203.250.35.169';
  			var dest='192.168.4.11';
  			var token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVyIjp7InVzZXJDb2RlIjowLCJwd2QiOm51bGwsImVtYWlsIjoic2VAbmF2ZXIuY29tIiwiZmlyc3ROYW1lIjoi7ZmNICAgICAgICAgICAgICIsInNlY29uZE5hbWUiOiLquLjrj5kgICAgICAgICAgICAgICAgICAgICAgICAiLCJzZkNudCI6MH0sImlhdCI6MTUyNTUzODYwNjA4OH0.twd3p_NppbZN9Z16Lt2DgDAfzm1jWYu1y-eUdY32Dhk";
  			var sendData=JSON.stringify({middle : middle, dest:dest, cmd:cmd, sfCode: 79, usedIp:'203.250.1.1'});
  			$.ajax({
				type : 'POST',
				url : 'device/control',
				beforeSend: function(request) {
				    request.setRequestHeader("Authorization", token);
				  },
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
	<a href="logout">로그아웃</a>
	<br> 최근로그인: 2018-00-00 00:00 PM
	<!-- <br> 현재 로그인IP: <script type="text/javascript">document.write(ip());</script> -->
	<hr>
	<h2>사용자 정보</h2>
	사용자코드:${sessionScope.userInfo.userCode}
	<br> 아이디:${sessionScope.userInfo.memberId}
	<%-- <br> 패스워드:${sessionScope.userInfo.pwd} --%>
	<br> 이메일:${sessionScope.userInfo.email}
	<br>
	<%-- 전화번호:${sessionScope.userInfo.phoneNum}<br> --%>
	사용자명:${sessionScope.userInfo.userName}
	<br> 보유재배기 갯수:${sessionScope.userInfo.sfCnt}
	<br>
	<hr>
	<h2>수경재배기 정보</h2>
	<c:forEach items="${SF}" var="item" varStatus="status">
		<a href="sensing_data?index=${status.index}&code=${item.sfCode}"><strong>${status.index}번째 수경재배기</strong></a>
		<br>
		층수:${item.floorCnt}<br>
		쿨러갯수:${item.coolerCnt}<br>
		LED설정모드:${item.ledCtrlMode}<br>
		온도 측정 딜레이:${item.tempDelay}<br>
		습도 측정 딜레이:${item.humiDelay}<br>
		조도 측정 딜레이:${item.elumDelay}<br>
		수온 측정 딜레이:${item.waterTempDelay}<br>
		수위 측정 딜레이:${item.waterLimDelay }<br>
		연결 AP IP:${item.apPublicIp}<br>
		내부 IP:${item.innerIp}<br>
		<button type="button" onclick="delete_device(${item.sfCode})">삭제</button>
		<button type="button">설정</button>
		<button type="button" disabled="disabled">수경재배 시작</button>
		<button type="button" disabled="disabled">LED켜기</button>
		<button type="button" disabled="disabled">LED끄기</button>
		<hr>
	</c:forEach>
	<button type="button" onclick="show()">수경재배기AP추가</button><br>
	<button type="button" onclick="info();">정보</button><br>
	<button type="button" onclick="user_info();">정보</button><br>
	<button type="button" onclick="order(3);">수동</button><br>
	<button type="button" onclick="order(4);">led켜기</button><br>
	<button type="button" onclick="order(5);">led끄기</button><br>
	<button type="button" onclick="delete_all()">공유기 삭제</button>

	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<!--x닫기 버튼-->
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<h3>공유기 추가</h3>
					<img src="/smart_plant/resources/img/raspberry.png" alt="" /> <br>
					<ol>
						<li>사용자 디바이스로 라즈베리파이 공유기에 연결합니다</li>
						<li>브라우저에서 192.168.4.1로 접속합니다.</li>
						<li>접속해서 나오는 공유기의 IP정보를 하단에 입력하고 등록합니다.</li>
					</ol>
					라즈베리 공유기IP: 
					<input type="text" id="Aclass" size="3" placeholder="A"/>.
					<input type="text" id="Bclass" size="3" placeholder="B"/>. 
					<input type="text" id="Cclass" size="3" placeholder="C"/>. 
					<input type="text" id="Dclass" size="3" placeholder="D"/>
					<input type="button" name="submit" value="조회" onclick="confirm_ip()" id="confirm_btn">
					<p id="result_text"></p>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" id="regist_btn" onclick="submit_ip()">등록</button>
					<!-- <button type="button"  id="regist_btn" disabled="disabled" onclick="submit_ip()">등록</button> -->
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function show(str) {
			<%--
			show 호출시 넘겨준 값을 이용하여 ajax 등을 통해 modal 을 띄울때 동적으로 바뀌어야 하는 값을 얻어온다.
			얻어온 값을 이용하여, modal 에서 동적으로 바뀌어야 하는 값을 바꾸어 준다..
			--%>
			$("#content").html("");
			<%--모달을 띄워준다--%>
			$("#myModal").modal('show');
		}
	</script>
</body>
</html>