/*
Name:		IR_slave.ino
Created:	2018-02-12 오후 10:43:24
Author:	AN
*/
#include <ArduinoJson.h>
#include <SoftwareSerial.h>
#include <DHT11.h>
char* userName = "";
char* ssid = ""; //AP's ssid
char* psw = ""; // AP's password

char* server_ip = "203.250.32.37"; //웹 서버 아이피
unsigned int server_port = 9001; //웹 서버 포트
								 //웹 서버 아이피랑 포트가 변경되면 변경해줘야

String uri = "/Aircontroll/setHumiAndTemp.Action"; //웹서버 uri경로
												   //이부분은 웹개발자랑 서로 조율해야

String bluetooth_cmd = "";
boolean wifi_join = false;

void esp8266Client_setup() {
	Serial.begin(9600);
	Serial1.begin(9600);
	Serial2.begin(9600);
	delay(1000);
	sendData("AT+RST\r\n", 3000, 0); //esp리셋
	sendData("AT\r\n", 2000, 0); //esp테스트
	sendData("AT+CWMODE=1\r\n", 2000, 0); //esp모드는 스테이션
}

void esp8266_joinAP() {
	sendData("AT+CWQAP\r\n", 2000, 0); //esp 연결된 AP접속 끊기
	String join = String("AT+CWJAP=\"") + ssid + "\",\"" + psw + "\"\r\n";
	sendData(join, 5000, 0); //esp 새로운 AP에 연결
	if (sendData("AT+CWJAP?\r\n", 3000, 0).indexOf("OK") != -1) {
		Serial.println("slave success");
		Serial1.print('1');//송신 아두이노에게 성공함을 전송
		sendData("AT+CIFSR\r\n", 2000, 0); //할당받은 아이피는?
		wifi_join = true;
	}
	else {
		Serial.println("slave fail");
		Serial1.print('0');//송신 아두이노에게 실패함을 전송
	}
}

//웹서버에 센싱한 값 전송, 매개변수랑 함수내부 코드 수정필요
//복수개의 수경재배기 운용시, 어느 수경재배기인지 식별해줘야 되므로
//쿼리스트링에 수경재배기 번호 포함해줘야
void esp8266_send(String temp, String humi) {
	String conn = String("AT+CIPSTART=\"TCP\"") + ",\"" + server_ip + "\"," + server_port + "\r\n";
	if (sendData(conn, 3000, 0).indexOf("OK") == -1) {
		return;
	}

	String query = "?userName=" + String(userName) + "&temp=" + temp + "&humi=" + humi;
	String request = "GET " + uri + query + "\r\n";
	request += "Connection:close\r\n\r\n";

	sendData(String("AT+CIPSEND=") + request.length() + "\r\n", 1000, 0);
	sendData(request, 1000, 0);
	Serial2.flush();
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
	if (!debug) {
		Serial.println(response);
	}
	return response;
}

// the setup function runs once when you press reset or power the board
void setup() {
	esp8266Client_setup(); //esp설정
	while (!Serial1.available()) {}//AVR스핀
	delay(2000);
	while (Serial1.available()) //UART에 값이 들어오면(마스터로부터 받은 값),
	{
		bluetooth_cmd += (char)Serial1.read(); //읽어들이고,
	}
	Serial.println(bluetooth_cmd);
	bluetooth_set();//userName, AP ssid, AP PWD값 저장
	esp8266_joinAP();//저장한 정보를 가지고 AP에 연결
}

// the loop function runs over and over again until power down or reset
void loop() {
	if (wifi_join) {
		//AP가 연결되어 있다면 센싱되는 정보 일정 주기마다 웹서버로 전송하는 코드
		//포함해야
	}
	//이외에도 주기적으로 값을 센싱하다가 특정조건이 되었을때 자동으로 제어하는
	//코드 포함해야
}
