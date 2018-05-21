/*
코드명 : 수경재배기_슬레이브
*/
#include <ArduinoJson.h>
#include <SoftwareSerial.h>
#include <OneWire.h>	//DS18 센서 사용
#include <DHT_U.h>
#include <DHT.h>		//DH11/22 센서 사용

#define DHT_pin 39
#define WATER_LEVEL_echo 22	//초음파 수위센서(echo)
#define WATER_LEVEL_trigger 23 //초음파 수위센서(trigger)
#define TANK_HEIGHT 20	//물탱크 최대 크기(cm)
#define MAX_WATER_LEVEL 15	//수위 최대 레벨(cm)
#define POT1_pin 0
#define POT2_pin 1
#define POT3_pin 2
#define POT4_pin 3		//조도센서 1,2,3,4(아날로그핀)
#define DS18_pin 26

DHT dht22(DHT_pin, DHT22);
OneWire DS18(DS18_pin);		//수온센서
byte DS18_addr[8] = { 0 };	//수온센서 주소값

boolean fan_state = 0;		//냉각팬 상태. 0 : OFF, 1 : ON
boolean led_state = 0;		//LED 상태. 0 : OFF, 1 : ON

boolean ds18_state = 0;		//연결상태. 0 : 연결X, 1 : 연결O


int POT_val[5] = { 0, };	//val1, val2, val3, val4, avg
float water_level = 0.0;	//수위

unsigned long sensor_previousTime = 0;
unsigned long data_send_previousTime = 0;
unsigned long sensor_interval = 10000;		//센서 측정 시간.
unsigned long data_send_interval = 15000;	//센서 전송 시간.

char* userName = "";
char* ssid = ""; //AP's ssid
char* psw = ""; // AP's password

char* server_ip = "192.168.4.1"; //웹 서버 아이피
unsigned int server_port = 80; //웹 서버 포트
							   //웹 서버 아이피랑 포트가 변경되면 변경해줘야

String uri = "/send.php"; //웹서버 uri경로
						  //이부분은 웹개발자랑 서로 조율해야

String bluetooth_cmd = "";
boolean wifi_join = false;
boolean automatic_led = true;	//led 동작방법.(자동/수동)
boolean automatic_fan = true;	//냉각팬 동작방법. (자동/수동)
boolean automatic_pump = true;	//펌프 동작방법. (자동/수동)

void send_control_data(String cmd) {
	Serial.println("control data send to ControlBoard : " + cmd);
	Serial3.print(cmd);
}

//냉각팬 제어 함수.
void fan_control(float temp) {
	if (fan_state == 0) {
		if (temp > 22.00) {
			fan_state = 1;
			send_control_data("8");
		}
	}
	else if (fan_state == 1) {
		if (temp <= 20.00) {
			fan_state = 0;
			send_control_data("9");
		}
	}
}

//DS18 수온 측정 함수
float getTemp(void) {
	byte data[9] = { 0 };

	if (ds18_state == 0) {
		//Serial.println("DS18_not_find!!!");
		return 0.0;
	}		//수온센서 연결이 안될경우,

	if (OneWire::crc8(DS18_addr, 7) != DS18_addr[7]) {
		Serial.println("CRC is not valid!");
		return;
	}
	DS18.reset();
	DS18.select(DS18_addr);
	DS18.write(0x44, 1);        //???
	DS18.reset();
	delay(1000);
	DS18.select(DS18_addr);
	DS18.write(0xBE);		//Read Scratchpad.

	for (int i = 0; i < 9; i++) {           // we need 9 bytes
		data[i] = DS18.read();
	}

	DS18.reset_search();

	byte MSB = data[1];
	byte LSB = data[0];
	float tempRead = ((MSB << 8) | LSB);
	float TemperatureSum = tempRead / 16;		//측정된 값을 섭씨온도로 변환.

	return TemperatureSum;
}

//수위 측정함수. 
float getWaterLevel(void) {
	float distance = 0;

	// 초음파를 보낸다. 다 보내면 echo가 HIGH 상태로 대기하게 된다.
	digitalWrite(WATER_LEVEL_trigger, HIGH);
	delayMicroseconds(10);
	digitalWrite(WATER_LEVEL_trigger, LOW);

	// HIGH 였을 때 시간(초음파가 보냈다가 다시 들어온 시간)을 가지고 거리를 계산 한다.
	distance = TANK_HEIGHT - ((float)pulseIn(WATER_LEVEL_echo, HIGH) / 29 / 2);		// cm단위로 바닥에서 물의 높이.
	distance = (distance / MAX_WATER_LEVEL) * 100;	//물의 높이를 %로 표현.

	return distance;
}

//조도센서 값 읽는 함수.
void read_POT() {
	int temp = 0;		//4개 값의 합 저장.

	for (int i = 0; i < 4; i++) {
		POT_val[i] = analogRead(i);		//조도센서 각각의 값을 저장.
		temp += POT_val[i];
	}
	POT_val[4] = temp / 4;	//4개 값 평균(조도센서 1개 빼고 테스트 위함
}

//조도센서 값에 따라 LED 제어용 릴레이 ON/OFF
void Relay_Control() {
	if (POT_val[4] <= 120 && led_state == 0) {		//LED 켜기
		send_control_data("4");
		led_state = 1;	//제어보드로 값 전송
	}
	else if (POT_val[4] > 140 && led_state == 1) {						//LED 끄기
		send_control_data("5");
		led_state = 0;	//제어보드로 값 전송
	}
}

//각종 센서 핀모드 설정 및 초기화 함수
void sensors_setup() {
	pinMode(WATER_LEVEL_trigger, OUTPUT);
	pinMode(WATER_LEVEL_echo, INPUT);		//초음파 센서 출력방향 설정.
	dht22.begin();
	Connect_DS18();
}

//DS18 연결 함수
void Connect_DS18() {
	if (DS18.search(DS18_addr)) {
		Serial.println("DS18 Connected.");
		ds18_state = 1;
	}
	else {
		Serial.println("DS18 Can not find!");
		ds18_state = 0;
	}
}

void esp8266Client_setup() {
	Serial.begin(9600);
	Serial1.begin(9600);		//마스터보드 통신포트
	Serial2.begin(9600);		//esp 통신포트
	Serial3.begin(9600);		//제어보드 통신포트
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
boolean esp8266_send(float temp, float humi, float waterTemp, float waterLev, int pot) {
	String conn = String("AT+CIPSTART=\"TCP\"") + ",\"" + server_ip + "\"," + server_port + "\r\n";
	if (sendData(conn, 5000, 0).indexOf("OK") == -1) {
		Serial2.flush();
		return false;
	}		//서버와 연결하는 부분

	String query = "?t=" + String(temp) + "&h=" + String(humi) + "&wt=" + String(waterTemp) + "&wl=" + String(waterLev) + "&e=" + String(pot);
	String request = "GET " + uri + query + "\r\n";
	request += "Connection:close\r\n\r\n";
	sendData(String("AT+CIPSEND=") + request.length() + "\r\n", 1000, 0);
	sendData(request, 1000, 0);
	Serial2.flush();
	return true;
}

//건드릴 필요 없음-
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

int count = 0;
//마스터보드에서 전송하는 값 받기.(수동/자동)
void get_masterData() {
	if (Serial1.available()) {
		int cmd = Serial1.parseInt();	//마스터보드에서 전송되는 제어값 수신. 정수로 변환
		count++;
		Serial.print(String("request from master : ") + cmd + String("  count : ") + String(count) + "\n");
		switch (cmd) {
			//마스터에서 led 제어값 전송.
		case 2:
			Serial.println("automatic_led = true");
			automatic_led = true;
			break;
		case 3:
			Serial.println("automatic_led = false");
			automatic_led = false;
			break;
		case 6:
			Serial.println("automatic_fan = true");
			automatic_fan = true;
			break;
		case 7:
			Serial.println("automatic_fan = false");
			automatic_fan = false;
			break;
		default:
			Serial.println("request from master : error");
			break;
		}

	}
}

// the setup function runs once when you press reset or power the board
void setup() {
	esp8266Client_setup(); //esp설정
	while (!Serial1.available()) {}//AVR스핀(마스터에서 bluetooth_cmd 값 전송 안오면 루프.)
	delay(2000);
	while (Serial1.available()) //UART에 값이 들어오면(마스터로부터 받은 값),
	{
		bluetooth_cmd += (char)Serial1.read(); //읽어들이고,
	}
	Serial.println(bluetooth_cmd);
	bluetooth_set();//userName, AP ssid, AP PWD값 저장
	esp8266_joinAP();//저장한 정보를 가지고 AP에 연결.
	sensors_setup();

}

// the loop function runs over and over again until power down or reset
void loop() {
	//와이파이 연결과 상관없이 센서값 측정.
	float DHT_temp = dht22.readTemperature();
	float DHT_humi = dht22.readHumidity();
	float water_temp = getTemp();		//수온측정
	water_level = getWaterLevel();		//수위 측정. (%값으로 리턴)
	unsigned long present_millis = millis();	//루프 시작시간
	read_POT();							//조도센서값 배열에 저장.

	get_masterData();		//마스터보드에서 전송한 제어값 수신.
	Serial1.flush();

	//냉각팬 자동/수동 설정 if문
	if(automatic_fan == true)
		fan_control(water_temp);			//수온에 따른 냉각팬 제어

	//LED 자동설정/수동설정 if문
	if(automatic_led == true)		
		Relay_Control();

	if (wifi_join) {
		if (present_millis - sensor_previousTime > sensor_interval) {
			Serial.println(String("LED mode : ") + automatic_led);
			Serial.print(String("Water Temperature : ") + water_temp + "\n");
			Serial.print(String("DHT Temperature : ") + DHT_temp + "\n");
			Serial.print(String("DHT Humidity : ") + DHT_humi + "\n");
			Serial.print(String("Fan State : ") + fan_state + "\n");
			Serial.print(String("POT Value : ") + POT_val[0] + " " + POT_val[1] + " " + POT_val[2] + " " + POT_val[3] + " " + POT_val[4] + "\n");
			Serial.print(String("LED state : ") + led_state + "\n");
			Serial.print(String("Water Level : ") + water_level + "%");
			Serial.println();
			Serial.println();
			sensor_previousTime = millis();
		}
		if (present_millis - data_send_previousTime > data_send_interval) {
			Serial.println("====Data Sending start=====");
			if (esp8266_send(DHT_temp, DHT_humi, water_temp, water_level, POT_val[4])) {		//서버로 전송 성공할 경우,
				Serial.println("=====Data sending end..=====");
			}
			else {
				Serial.println("====Data sending fail..====");
			}
			data_send_previousTime = millis();
		}
	}
}