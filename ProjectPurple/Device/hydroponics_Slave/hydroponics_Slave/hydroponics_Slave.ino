/*
코드명 : 수경재배기_슬레이브
*/
#include <ArduinoJson.h>
#include <SoftwareSerial.h>
#include <OneWire.h>	//DS18 센서 사용
#include <DHT_U.h>
#include <DHT.h>		//DH11/22 센서 사용

#define fan1 11
#define fan2 12
#define fan3 13		//냉각팬 핀번호
#define DHT_pin 39
#define WATER_LEVEL_echo 5	//초음파 수위센서(echo)
#define WATER_LEVEL_trigger 6 //초음파 수위센서(trigger)
#define TANK_HEIGHT 20	//물탱크 최대 크기(cm)
#define MAX_WATER_LEVEL 15	//수위 최대 레벨(cm)
#define POT1_pin 0
#define POT2_pin 1
#define POT3_pin 2
#define POT4_pin 3		//조도센서 1,2,3,4(아날로그핀)
#define RELAY_IN1 22
#define RELAY_IN2 23
#define RELAY_IN3 24
#define RELAY_IN4 25
#define DS18_pin 26
/*
#define DHT_temp_interval 60000
#define DHT_humi_interval 60000		//대기 온/습도 1분마다 전송
#define DS18_temp_interval 600000	//수온 10분마다 전송
#define WATER_level_interval 3600000	//수위 1시간 마다 전송
#define POT_val_interval 30000
*/

DHT dht22(DHT_pin, DHT22);
OneWire DS18(DS18_pin);		//수온센서
byte DS18_addr[8] = { 0 };	//수온센서 주소값

boolean fan_state = 0;		//냉각팬 상태. 0 : OFF, 1 : ON
boolean ds18_state = 0;		//연결상태. 0 : 연결X, 1 : 연결O
int POT_val[5] = { 0, };	//val1, val2, val3, val4, avg
float water_level = 0.0;	//수위

unsigned long sensor_previousTime = 0;
unsigned long data_send_previousTime = 0;
unsigned long sensor_interval = 5000;		//센서 측정 시간.
unsigned long data_send_interval = 60000;	//센서 전송 시간.
											/*
											unsigned long DHT_temp_millis = 0;
											unsigned long DHT_humi_millis = 0;
											unsigned long DS18_temp_millis = 0;
											unsigned long POT_val_millis = 0;
											unsigned long WATER_level_millis = 0;		//수위 측정용 시간.
											*/

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

//냉각팬 제어 함수.
void fan_control(float temp) {
	if (temp > 22.00) {
		if (fan_state == 0) {
			fan_state = 1;
			digitalWrite(fan1, HIGH);
			digitalWrite(fan2, HIGH);
			digitalWrite(fan3, HIGH);
		}
	}
	else if (temp < 17.00) {
		if (fan_state == 1) {
			fan_state = 0;
			digitalWrite(fan1, LOW);
			digitalWrite(fan2, LOW);
			digitalWrite(fan3, LOW);
		}
	}
	else {
		if (fan_state == 1) {
			fan_state = 0;
			digitalWrite(fan1, LOW);
			digitalWrite(fan2, LOW);
			digitalWrite(fan3, LOW);
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
	distance = TANK_HEIGHT - ((float)pulseIn(WATER_LEVEL_echo, HIGH) / 29 / 2);		// mm단위로 바닥에서 물의 높이.

	return distance;
}

//조도센서 값 읽는 함수.
void read_POT() {
	int temp = 0;		//4개 값의 합 저장.

	for (int i = 0; i < 4; i++) {
		if (i != 1) {		//조도센서 1개 없을때, 테스트용 코드
			POT_val[i] = analogRead(i);		//조도센서 각각의 값을 저장.
			temp += POT_val[i];
		}
	}

	//POT_val[4] = temp / 4;	//4개 값 평균(조도센서 1개 빼고 테스트 위함
	POT_val[4] = temp / 3;
}

//조도센서 값에 따라 LED 제어용 릴레이 ON/OFF
void Relay_Control() {
	if (POT_val[4] <= 120)
		for (int i = RELAY_IN1; i <= RELAY_IN4; i++)
			digitalWrite(i, LOW);
	else if (POT_val[4] > 140)
		for (int i = RELAY_IN1; i <= RELAY_IN4; i++)
			digitalWrite(i, HIGH);
}

//각종 센서 핀모드 설정 및 초기화 함수
void sensors_setup() {
	pinMode(fan1, OUTPUT);
	pinMode(fan2, OUTPUT);
	pinMode(fan3, OUTPUT);		//냉각팬 핀모드 : 출력
	pinMode(WATER_LEVEL_trigger, OUTPUT);
	pinMode(WATER_LEVEL_echo, INPUT);		//초음파 센서 출력방향 설정.
	pinMode(RELAY_IN1, OUTPUT);
	pinMode(RELAY_IN2, OUTPUT);
	pinMode(RELAY_IN3, OUTPUT);
	pinMode(RELAY_IN4, OUTPUT);			//LED제어용 릴레이 핀모드.
	for (int i = RELAY_IN1; i <= RELAY_IN4; i++) {
		digitalWrite(i, HIGH);
	}
	Serial.begin(9600);
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
boolean esp8266_send(int dataType, String data) {
	String conn = String("AT+CIPSTART=\"TCP\"") + ",\"" + server_ip + "\"," + server_port + "\r\n";
	if (sendData(conn, 5000, 0).indexOf("OK") == -1) {
		Serial2.flush();
		return false;
	}		//서버와 연결하는 부분

	String query = "?userName=" + String(userName) + "&dataType=" + String(dataType) + "&data=" + data;
	String request = "GET " + uri + query + "\r\n";
	request += "Connection:close\r\n\r\n";
	//dataType : 전송할 센서 값의 종류 결정(온도, 습도, 수온 등), data : 센서 값.
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
	/*
	DHT_temp_millis = millis();
	DHT_humi_millis = millis();
	DS18_temp_millis = millis();
	WATER_level_millis = millis();
	POT_val_millis = millis();	//각 센서 작동 시작시간 저장
	*/
}

// the loop function runs over and over again until power down or reset
void loop() {
	//와이파이 연결과 상관없이 센서값 측정.
	float DHT_temp = dht22.readTemperature();
	float DHT_humi = dht22.readHumidity();
	float Water_temp = getTemp();		//수온측정
	water_level = getWaterLevel();		//수위 측정. (%값으로 리턴)
	unsigned long present_millis = millis();	//루프 시작시간

	fan_control(Water_temp);			//수온에 따른 냉각팬 제어
	read_POT();							//조도센서값 배열에 저장.
	Relay_Control();
	if (wifi_join) {
		if (present_millis - sensor_previousTime > sensor_interval) {
			Serial.print(String("Water Temperature : ") + Water_temp + "\n");
			Serial.print(String("DHT Temperature : ") + DHT_temp + "\n");
			Serial.print(String("DHT Humidity : ") + DHT_humi + "\n");
			Serial.print(String("Fan State : ") + fan_state + "\n");
			Serial.print(String("POT Value : ") + POT_val[0] + " " + POT_val[1] + " " + POT_val[2] + " " + POT_val[3] + " " + POT_val[4] + "\n");
			Serial.print(String("Water Level : ") + water_level + "%");
			Serial.println();
			Serial.println();
			sensor_previousTime = millis();
		}
		if (present_millis - data_send_previousTime > data_send_interval) {
			if (esp8266_send(1, String(DHT_temp))) {		//서버로 전송 성공할 경우,
				Serial.println("=====Temperature sending end..=====");
			}
			else {
				Serial.println("====Temperature sending fail..====");
			}
			data_send_previousTime = millis();
		}
#if 0
		if (present_millis - DHT_temp_millis > DHT_temp_interval) {
			Serial.println("=====Temperature sending start..=====");
			if (esp8266_send(1, String(DHT_temp))) {		//서버로 전송 성공할 경우,
				Serial.println("=====Temperature sending end..=====");
			}
			else {
				Serial.println("====Temperature sending fail..====");
			}
			DHT_temp_millis = present_millis;
		}
		if (present_millis - DHT_humi_millis > DHT_humi_interval) {
			Serial.println("=====Humidity sending start..=====");
			if (esp8266_send(2, String(DHT_humi))) {
				Serial.println("====Humidity sending end...====");
			}
			else {
				Serial.println("====Humidity sending fail..====");
			}
			DHT_humi_millis = present_millis;
		}
		if (present_millis - DS18_temp_millis > DS18_temp_interval) {
			Serial.println("=====Water Temperature sending start..=====");
			if (esp8266_send(3, String(Water_temp))) {
				Serial.println("=====Water Temperature sending end...=====");
			}
			else {
				Serial.println("=====Water Temperature sending fail..=====");
			}
			DS18_temp_millis = present_millis;

		}
		if (present_millis - POT_val_millis > POT_val_interval) {
			Serial.println("=====POT value sending start..=====");
			if (esp8266_send(4, String(POT_val[4]))) {
				Serial.println("=====POT value sending end...=====");
			}
			else {
				Serial.println("=====POT value sending fail..=====");
			}
			POT_val_millis = present_millis;
		}
		if (present_millis - WATER_level_millis > WATER_level_interval) {
			Serial.println("=====WaterLevel value sending start..=====");
			if (esp8266_send(5, String(water_level))) {
				Serial.println("=====WaterLevel value sending end...=====");
			}
			else {
				Serial.println("=====WaterLevel value sending fail..=====");
			}
			WATER_level_millis = present_millis;
		}
#endif
	}
}