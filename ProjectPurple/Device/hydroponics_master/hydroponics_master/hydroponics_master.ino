#include <ArduinoJson.h>
#include <SoftwareSerial.h>

#define PUMP_RELAY 31

char* userName = "";
char* ssid = ""; //AP's ssid
char* psw = ""; // AP's password

String device_ip = "";
String sf_code = "none";	//ip의 D클래스 값.
int dot_count = 0; // sf 코드 추출을 위한 변수.

char* server_ip = "192.168.4.1"; //웹 서버 아이피
unsigned int server_port = 80; //웹 서버 포트
String uri = "/index.php";

boolean bluetooth_protocol = false; // '{' is true, '}' is false
boolean wifi_join = false;
String bluetooth_cmd = "";
String buffer = "";

boolean automatic_value[3] = { true, true, true };	//순서대로 LED, 냉각팬, 펌프.

SoftwareSerial Serial_C(11, 10);

int buffer_count = 0;

unsigned long wifi_check_previousTime = 0;
unsigned long wifi_check_interval = 10000;

const int success_led = 49;
const int fail_led = 51;
const int piezo = 53;

void esp8266Server_setup() {
	Serial.begin(9600);	
	Serial1.begin(9600);	//slave보드 통신
	Serial2.begin(9600);	//esp통신
	Serial_C.begin(9600);	//control보드 통신
	delay(1000);
	sendData("AT+RST\r\n", 3000, 0);
	sendData("AT\r\n", 2000, 0);
	sendData("AT+CWMODE=1\r\n", 2000, 0);
	sendData("AT+CIPMUX=1\r\n", 2000, 0);
	sendData("AT+CIPSERVER=1,80\r\n", 2000, 0);
}

String sendData(String command, long timeout, boolean debug) {
	String response = "";
	Serial2.print(command);
	long time = millis(); 
	while ((time + timeout) > millis()) {
		while (Serial2.available()) {
			char c = Serial2.read();
			response += c;
		}
	}
	if (command == "AT+CIFSR\r\n") {
		int ip_idx = response.indexOf("192.");
		int ok_idx = response.indexOf("OK");
		Serial.print("ip idx : ");
		Serial.println(ip_idx);
		Serial.print("ok_idx : ");
		Serial.println(ok_idx);
		device_ip = response.substring(ip_idx, ok_idx - 4);
	}
	if (!debug) {
		Serial.println(response);
	}
	return response;
}

void esp8266_joinAP() {
	change_led_state(0);//빨간불
	sendData("AT+CWQAP\r\n", 2000, 0);
	String join = String("AT+CWJAP=\"") + ssid + "\",\"" + psw + "\"\r\n";
	sendData(join, 5000, 0);
	if (sendData("AT+CWJAP?\r\n", 3000, 0).indexOf("OK") != -1) {
		Serial1.print(bluetooth_cmd);
		while (!Serial1.available()) {}
		delay(1000);
		while (Serial1.available())
		{
			char res = (char)Serial1.read();
			Serial.println(res);
			if (res != '1') {
				sendData("AT+CIFSR\r\n", 2000, 0);
				return;
			}
		}
		sendData("AT+CIFSR\r\n", 2000, 0);
		change_led_state(1);//파란불
		tone(piezo, 392);//AP연결 설정이 끝났으므로 소리로 알려준다.
		delay(1000);
		noTone(piezo);
		wifi_join = true;
		//AP나 서버로 현재 할당받은 내부 아이피 알려줘야 함
		//AT+CIFSR로 얻은 아이피를 문자열에 저장해야
		//라즈베리 AP측에서는 해당 정보를 가지고 포트포워딩 해야
	}
	else {
		change_led_state(0);//빨간불
	}
}

void change_led_state(boolean st) {
	if (st == 0) {//빨강
		digitalWrite(success_led, LOW);
		digitalWrite(fail_led, HIGH);
	}
	else {//파랑
		digitalWrite(success_led, HIGH);
		digitalWrite(fail_led, LOW);
	}
}

void send_control_val(int cmd) {								//수동 모드에서 장치 제어할 경우 호출
	Serial.println(String("request to control board : ") + cmd);
	Serial_C.print(cmd);
}

void send_control_val(int cmd, int device, boolean stat) {		//제어 모드 변경할 경우 호출
	if (automatic_value[device] == !stat) {
		Serial.println(String("request to slave : ") + cmd);
		Serial1.print(cmd);
		automatic_value[device] = stat;
	}
}

#if 1
void esp8266_read() { //명령 라우팅
	Serial2.flush();
	if (Serial2.available()) {
		Serial.println("execute esp8266_read");
		String temp = Serial2.readStringUntil('\n');
		Serial.println("DEBUG: " + temp);
		buffer += temp;
		if (temp.charAt(0) == 13) {
			buffer_count++;
			if (buffer_count == 2) {//\r\n\r\n까지 받아오면 수행
				buffer_count = 0;
				unsigned int c_id = buffer.charAt(buffer.indexOf("+IPD,") + 5) - 48;
				Serial.println(c_id);
				int cmd_idx = buffer.indexOf("cmd=");
				String cmd_str = buffer.substring(cmd_idx + 4);
				int cmd = cmd_str.toInt();
				Serial.println(String("cmd is : " + String(cmd)));
				String content = "";
				switch (cmd) //cmd 라우팅
				{
					//LED, 팬 자동설정
				case 2:
					content = "led_fan_auto";
					send_control_val(cmd, 0, true);	//LED 장치 제어
					automatic_value[0] = true;
					automatic_value[1] = true;
					break;
					//LED, 팬 수동설정
				case 3:
					content = "led_fan_manual";
					send_control_val(cmd, 0, false);
					automatic_value[0] = false;
					automatic_value[1] = false;
					break;
				case 4:
					if (automatic_value[0] != true) {
						content = "led_on";
						send_control_val(cmd);
					}
					else
						Serial.println("Led mode is not manual.");
					break;
				case 5:
					if (automatic_value[0] != true) {
						content = "led_off";
						send_control_val(cmd);
					}
					else
						Serial.println("Led mode is not manual.");
					break;
				case 8:
					if (automatic_value[1] != true) {
						content = "fan_on";
						send_control_val(cmd);
					}
					else
						Serial.println("fan mode is not manual.");
					break;
				case 9:
					if (automatic_value[1] != true) {
						content = "fan_off";
						send_control_val(cmd);
					}
					else
						Serial.println("fan mode is not manual.");
					break;
				case 10:		//재배 시작
					content = "pump_on";
					digitalWrite(PUMP_RELAY, HIGH);
					Serial1.print(10);		//슬레이브 보드로 전송
					break;
				case 11:		//재배 중지
					content = "pump_off";
					digitalWrite(PUMP_RELAY, LOW);
					Serial1.print(11);		//슬레이브 보드로 전송
					break;
				case 15:  
					content = "test_button";
					send_control_val(cmd);
					break;
				default:
					content = "err";
					break;
				}
				buffer = ""; //String 버퍼 클리어

				String response = "HTTP/1.1 200 OK\r\n";
				response += "Content-Type:text/html;charset=UTF-8\r\n";//CORS 핸들링에  따른 content-type만 가능
				response += "Content-Length:";
				response += content.length();
				response += "\r\n";
				response += "Access-Control-Allow-Origin:*\r\n";//CORS
				response += "Connection: close\r\n\r\n";//포함하지 않으면 esp 혼동
													   //response += "Keep-Alive:timeout=5,max=999\r\n\r\n";
				response += content;
				sendData(String("AT+CIPSEND=") + c_id + "," + response.length() + "\r\n", 3000, 0);
				sendData(response, 1000, 1);
				Serial2.flush();
			}
		}
	}
}
#endif

//복수개의 수경재배기 운용때 각 블루투스 이름 달리해줘야되므로
//이름정해주는 부분 나중에 수정필요
void bluetooth_setup() {
	Serial3.begin(9600);
	bluetooth_write("AT+NAMEplant", 12); //블루투스 이름
	bluetooth_write("AT+PIN2014", 10); //블루투스 비번
}

//건드릴 필요 없음
void bluetooth_read() {
	while (!Serial3.available()) {}
	delay(2000);
	while (Serial3.available())
	{
		char temp = (char)Serial3.read();
		if (temp == '{') {// 1. 첫번째 문자가 '{'라면 블루투스 통신시작
			bluetooth_protocol = true;
		}
		else if (bluetooth_protocol && temp == '}') {// 3. 끝이 '}'라면 통신끝
			bluetooth_protocol = false;
			bluetooth_cmd += temp;

			bluetooth_set(); //bluetooth_cmd에 저장된 정보를 읽어온다(userName,ssid,psw)
			esp8266_joinAP();//esp랑 AP연결
			bluetooth_cmd = "";//문자열을 비운다
		}
		if (bluetooth_protocol) {// 2. 블루투스 통신중이라면
			bluetooth_cmd += temp;
		}
	}
}

//건드릴 필요 없음
void bluetooth_set() {
	StaticJsonBuffer<100> jsonBuffer;
	JsonObject& jsonValue = jsonBuffer.parseObject(bluetooth_cmd);//json포맷으로 읽어온다
																  //{userName:"~",ssid:"~",psw:"~"}
	userName = jsonValue["userName"];//사용자 아이디
	ssid = jsonValue["ssid"];//AP이름
	psw = jsonValue["psw"];//AP패스워드
}

//건드릴 필요 없음
void bluetooth_write(char* cmd, size_t len) {
	for (size_t i = 0; i < len; i++)
	{
		Serial3.write(cmd[i]);
	}
	delay(2000);
	while (Serial3.available()) Serial3.read();
}

void esp_check_connection() {
	Serial.println("Check AP connection...");
	boolean conn_result = false;	//와이파이 연결결과.
	char result = '0';		//
	String previous_ip = "0";	//연결이 끊어졌을 경우, 이전 d클래스 저장용.
	String join = "";		//AP접속을 위한 AT커맨드
	conn_result = (sendData("AT+CWJAP?\r\n", 3000, 0).indexOf("OK")) != -1;		//접속된 AP조회.
	if (conn_result != true) {
		Serial.println("AP disconnected.. try reconnect");
		wifi_join = false;
		previous_ip = device_ip;
		sendData("AT+CWQAP\r\n", 2000, 0); //esp 연결된 AP접속 끊기
		String join = String("AT+CWJAP=\"") + ssid + "\",\"" + psw + "\"\r\n";
		sendData(join, 5000, 0); //esp 저장된 AP에 다시연결
		if (sendData("AT+CWJAP?\r\n", 3000, 0).indexOf("OK") != -1) {	//연결시도 결과.
			Serial.println("wifi connected.");
			sendData("AT+CIFSR\r\n", 2000, 0);
			Serial.print("previous IP : ");
			Serial.println(previous_ip);
			Serial.print("device IP : ");
			Serial.println(device_ip);
			wifi_join = true;
		}
		else { Serial.println("fail AP reconnect.."); }
	}
	else {
		Serial.println(device_ip);
		Serial.println("AP connection OK.");
	}
}

boolean send_device_ip() {
	String conn = String("AT+CIPSTART=0,\"TCP\"") + ",\"" + server_ip + "\"," + server_port + "\r\n";
	if (sendData(conn, 5000, 0).indexOf("OK") == -1) {
		Serial2.flush();
		return false;
	}		//서버와 연결하는 부분
	String query = "?ip=" + String(device_ip);
	String request = "GET " + uri + query + "\r\n";
	request += "Connection:close\r\n\r\n";
	sendData(String("AT+CIPSEND=0,") + request.length() + "\r\n", 1000, 0);
	sendData(request, 1000, 0);
	Serial2.flush();
	return true;
}

String get_sf_code(String temp_ip) {
	int dot_idx = temp_ip.indexOf('.');
	if (dot_idx != '-1') dot_count++;
	temp_ip = temp_ip.substring(dot_idx + 1);
	return temp_ip;
}

// the setup function runs once when you press reset or power the board
void setup() {
	pinMode(success_led, OUTPUT);
	pinMode(PUMP_RELAY, OUTPUT);
	pinMode(fail_led, OUTPUT);
	digitalWrite(PUMP_RELAY, LOW);
	digitalWrite(success_led, LOW);
	digitalWrite(fail_led, LOW);//점멸등 설정

	esp8266Server_setup(); //esp설정
	bluetooth_setup(); //블루투스 설정, 블루투스 이름정해주는부분 나중에 수정필요
	change_led_state(0);//빨간불
	bluetooth_read(); //블루투스에 값이들어올때 까지 대기
	Serial.print("device IP : ");
	Serial.println(device_ip);
	sf_code = device_ip;
	boolean result = send_device_ip();
	if (result == true) Serial.println("IP 전송완료");
	else Serial.println("IP전송실패");

	while (dot_count != 3) {
		sf_code = get_sf_code(sf_code);
	}
	Serial.print("SF Code : ");
	Serial.println(sf_code);
	Serial1.print(sf_code);
}

// the loop function runs over and over again until power down or reset
void loop() {
	char ch = '0';
	unsigned long present_millis = 0;
	if (wifi_join) {
		//테스트 코드시작
		if (Serial.available()) {
			ch = Serial.read();
			if (ch == '1')
				sendData("AT+CWQAP\r\n", 2000, 0); //esp 연결된 AP접속 끊기
		}
		//테스트코드 끝.
		present_millis = millis();
		if (present_millis - wifi_check_previousTime > wifi_check_interval) {
			esp_check_connection();
			Serial.println("esp_check_connection end");
			wifi_check_previousTime = millis();
		}
		esp8266_read();
	}
}