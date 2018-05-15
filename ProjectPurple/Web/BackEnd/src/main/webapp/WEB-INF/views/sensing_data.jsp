<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="/smart_plant/resources/js/highchart/highcharts.js"></script>
<script type="text/javascript" src="/smart_plant/resources/js/highchart/dark-unica.js"></script>
<script
	src="/smart_plant/resources/js/highchart/highchart.call_func.js?ver=0"></script>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/0.3.4/sockjs.min.js"></script> -->
<script src="/smart_plant/resources/js/realtime/sockjs-0.3.4.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
</script>
<script>
/* var subscribeCode=${sessionScope.userInfo.userCode}; */
var subscribeCode=0;
var stompClient = null;
function connect() {
	console.log(window.location.host);
    var socket = new SockJS('http://'+'203.250.32.180:9001'+'/smart_plant/sensing_data');
    stompClient = Stomp.over(socket);  
    stompClient.connect('manager','manager', function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages'+subscribeCode, function(message) {
        	var msg=JSON.parse(message.body);
        	console.log(msg['t']);
        	dataAdd(parseInt(msg['t']));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function test(){
	$.ajax({
		type : 'POST',
		url : 'test',
		cache : false,
		success : function(data) {
			console.log('se');
		}
	});
}
</script>
</head>
<body onload="connect()">
	<a href="/smart_plant/main">뒤로가기</a>
	<hr>
	<h2>${param.index}번째수경재배기정보</h2>
	<h3>온도 데이터 정보</h3>
	<div id="chartOfTemp"></div>
	<script>createChart('chartOfTemp');</script>
	<!-- <li>
			<h3>습도 데이터</h3>
			<div id="chartOfHumi"></div>
		</li>
		<li>
			<h3>조도 데이터</h3>
			<div id="chartOfElum"></div>
		</li>
		<li>
			<h3>수온 데이터</h3>
			<div id="chartOfWaterTemp"></div>
		</li>
		<li>
			<h3>수위 데이터</h3>
			<div id="chartOfWaterLim"></div>
		</li> -->
	<button onclick="test();">테스트 버튼</button>
</body>
</html>