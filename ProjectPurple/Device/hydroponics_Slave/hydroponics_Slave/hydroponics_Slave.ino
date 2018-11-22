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
#define SOL_A_RELAY 44		//EC조절용
#define SOL_B_RELAY 45		//EC조절용
#define EC_VCC 46			//EC전원조절 릴레이
#define EC_GND 47			//PH전원조절 릴레이
#define PH_RELAY 52
#define POT1_pin 0
#define POT2_pin 1
#define POT3_pin 2
#define POT4_pin 3		//조도센서 1,2,3,4(아날로그핀)
#define DS18_pin 26
#define PH_pin A5
#define Vref 4.95
#define EC_pin A4

float std_ph = 4.5;		//조절의 기준이 되는 PH값.
int sf_code = 0;

DHT dht22(DHT_pin, DHT22);
OneWire DS18(DS18_pin);		//수온센서
byte DS18_addr[8] = { 0 };	//수온센서 주소값

boolean fan_state = 0;		//냉각팬 상태. 0 : OFF, 1 : ON
boolean led_state = 0;		//LED 상태. 0 : OFF, 1 : ON
boolean pump_state = 0;		//펌프 상태. 0 : OFF, 1 : ON
boolean ds18_state = 0;		//연결상태. 0 : 연결X, 1 : 연결O
boolean ph_flag = false;	//PH처음 측정 파악
boolean ph_arr_flag = false;	//PH배열 10개 초과 파악
char pump_run_flag = 'F';	//양액펌프 동작 여부 파악.

int POT_val[5] = { 0, };	//val1, val2, val3, val4, avg
int water_level = 0.0;	//수위
int water_level_arr[10] = { 0 };

unsigned long sensor_previousTime = 0;
unsigned long data_send_previousTime = 0;
unsigned long ph_read_previousTime = 0;
unsigned long ec_control_previousTime = 0;
unsigned long sensor_interval = 5000;		//센서 측정 시간.
unsigned long data_send_interval = 15000;	//센서 전송 시간.
unsigned long timeout_interval = 5000;
unsigned long ph_read_interval = 30000;		//PH센서 측정시간
unsigned long ec_control_interval = 60000;	//EC 조절후 안정화 시간.
unsigned long AnalogSampleTime;
unsigned int AnalogSampleInterval = 25;

float current_ph_arr[10] = { 0, };
int current_ph_index = 0;
float ph_array[10] = { 0, };
int ph_index;

//각 센서 값 변수.
float DHT_temp = 0.0;
float DHT_humi = 0.0;
float water_temp = 0.0;
float ECcurrent = 0.0;
float PHcurrent = 0.0;
unsigned long int ph_Value;     //Store the average value of the sensor feedback

char* userName = "";
char* ssid = ""; //AP's ssid
char* psw = ""; // AP's password

char* server_ip = "192.168.4.1"; //웹 서버 아이피
unsigned int server_port = 80; //웹 서버 포트

String uri = "/send.php"; //웹서버 uri경로
						  //이부분은 웹개발자랑 서로 조율해야
String device_ip = "";

String bluetooth_cmd = "";
boolean wifi_join = false;
boolean automatic_led = true;	//led 동작방법.(자동/수동)
boolean automatic_fan = true;	//냉각팬 동작방법. (자동/수동)
boolean automatic_pump = true;	//펌프 동작방법. (자동/수동)

void send_control_data(String cmd, int type) {		//자동모드에서 수행되는 코드. type : 1(led), 2(fan), 3(pump)
	int time = millis();

	Serial.println("control data send to ControlBoard : " + cmd);

	Serial3.print(cmd);

	while (!Serial3.available()) {		//제어값 전송후, 결과값을 전달받지 못할경우,(timeout)
		if ((time + timeout_interval) > millis()) {
			Serial.println("timeout get value from control board.");
			return;
		}
	}
	int result = Serial3.parseInt();
	Serial.print("get result value from control board : ");
	Serial.println(result);
	switch (type) {
	case 1:
		if (result == 1)
			led_state = 1;
		else if (result == 0)
			led_state = 0;
		else
			Serial.println("error get value from control board..");
		break;
	case 2:
		if (result == 1)
			fan_state = 1;
		else if (result == 0)
			fan_state = 0;
		else
			Serial.println("error get value from control board..");
		break;
	}
	return;
}

//냉각팬 제어 함수.
void fan_control(float temp) {
	if (fan_state == 0) {
		if (temp > 22.00) {
			fan_state = 1;
			send_control_data("8", 2);
		}
	}
	else if (fan_state == 1) {
		if (temp <= 20.00) {
			fan_state = 0;
			send_control_data("9", 2);
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
int getWaterLevel(void) {
	double distance = 0;
	double temp = 0;

	// 초음파를 보낸다. 다 보내면 echo가 HIGH 상태로 대기하게 된다.
	digitalWrite(WATER_LEVEL_trigger, HIGH);
	delayMicroseconds(10);
	digitalWrite(WATER_LEVEL_trigger, LOW);
	temp = pulseIn(WATER_LEVEL_echo, HIGH);
	temp = temp / 29 / 2;
	distance = MAX_WATER_LEVEL - temp;		// cm단위로 바닥에서 물의 높이.
	distance = distance / MAX_WATER_LEVEL * 100;
	// 측정범위 초과시 처리.
	if (distance <= 0)
		distance = 0;
	else if (distance >= 100)
		distance = 100;

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
void Relay_Control(char state) {
	if (state == true)
		send_control_data("4", 1);		//재배 시작시 LED 점등
	else if (state == false)
		send_control_data("5", 1);
	/*
	if (POT_val[4] <= 120 && led_state == 0) {		//LED 켜기
		send_control_data("4", 1);
	}
	else if (POT_val[4] > 140 && led_state == 1) {						//LED 끄기
		send_control_data("5", 1);
	}
	*/
}

//각종 센서 핀모드 설정 및 초기화 함수
void sensors_setup() {
	pinMode(WATER_LEVEL_trigger, OUTPUT);
	pinMode(WATER_LEVEL_echo, INPUT);		//초음파 센서 출력방향 설정.
	pinMode(PH_pin, INPUT);
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
	sendData("AT+RST\r\n", 4000, 0); //esp리셋
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
boolean esp8266_send(float temp, float humi, float waterTemp, float waterLev, int pot, float ec, float ph, String ip, boolean p) {
	String conn = String("AT+CIPSTART=\"TCP\"") + ",\"" + server_ip + "\"," + server_port + "\r\n";
	if (sendData(conn, 5000, 0).indexOf("OK") == -1) {
		Serial2.flush();
		return false;
	}		//서버와 연결하는 부분

	String query = "?t=" + String(temp) + "&h=" + String(humi) + "&wt=" + String(waterTemp) + "&wl=" + String(waterLev) + "&e=" + String(pot) + "&ec=" + String(ec) + "&ph=" + String(ph) + "&sf=" + String(sf_code) + "&p=" + String(pump_run_flag);
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
	//할당받은 IP의 d클래스 추출.
	if (command == "AT+CIFSR\r\n") {
		int ip_idx = response.indexOf("192.");
		int ok_idx = response.indexOf("OK");
		Serial.print("ip idx : ");
		Serial.println(ip_idx);
		Serial.print("ok_idx : ");
		Serial.println(ok_idx);
		device_ip = response.substring(ip_idx, ok_idx - 3);
	}
	if (!debug) {
		Serial.println(response);
	}
	return response;
}

//마스터보드에서 전송하는 값 받기.(수동/자동)
void get_masterData() {
	if (Serial1.available()) {
		int cmd = Serial1.parseInt();	//마스터보드에서 전송되는 제어값 수신. 정수로 변환
		Serial.print(String("request from master : ") + cmd + "\n");
		switch (cmd) {
			//마스터에서 led 제어값 전송.
		case 2:
			Serial.println("automatic_led = true");
			Serial.println("automatic_fan = true");
			automatic_led = true;
			automatic_fan = true;
			break;
		case 3:
			Serial.println("automatic_led = false");
			Serial.println("automatic_fan = false");
			automatic_led = false;
			automatic_fan = false;
			break;
		case 10:
			Serial.println("pump_running");
			send_control_data("4", 1);
			pump_state = true;
			break;
		case 11:
			Serial.println("pump_stop");
			send_control_data("5", 1);
			pump_state = false;
			break;
		default:
			Serial.println("request from master : error");
			break;
		}

	}
}

void get_controlData(int type) {		//수동모드의 경우 수행되는 코드.
	if (Serial3.available()) {
		int result = Serial3.parseInt();
		Serial.print("get result value from control board : ");
		Serial.println(result);
		switch (type) {
		case 1:
			if (result == 1)
				led_state = 1;
			else if (result == 0)
				led_state = 0;
			else
				Serial.println("error get value from control board..");
			break;
		case 2:
			if (result == 1)
				fan_state = 1;
			else if (result == 0)
				fan_state = 0;
			else
				Serial.println("error get value from control board..");
			break;
		default:
			Serial.println("undefined type value.");
		}
	}
}

float readPH(void) {
	float sensorValue;
	long sensorSum;
	int buf[10];                //buffer for read analog
	float result;
	for (int i = 0; i<10; i++)       //Get 10 sample value from the sensor for smooth the value
	{
		buf[i] = analogRead(A5);//Connect the PH Sensor to A0 port
		delay(10);
	}
	for (int i = 0; i<9; i++)        //sort the analog from small to large
	{
		for (int j = i + 1; j<10; j++)
		{
			if (buf[i]>buf[j])
			{
				int temp = buf[i];
				buf[i] = buf[j];
				buf[j] = temp;
			}
		}
	}
	ph_Value = 0;

	for (int i = 2; i<8; i++)                      //take the average value of 6 center sample
		ph_Value += buf[i];
	sensorValue = ph_Value / 6;
	result = 7 - 1000 * (sensorValue - 365) * Vref / 59.16 / 1023;
	return result;
}

void getPH(void) {
	Serial.println("executed function : getPH");
	digitalWrite(PH_RELAY, LOW);
	delay(500);
	digitalWrite(EC_VCC, LOW);
	digitalWrite(EC_GND, LOW);
	delay(100);
	PHcurrent = readPH();
	/*
	current_ph_arr[current_ph_index] = readPH();
	current_ph_index++;
	if (current_ph_index > 10) {
		if (ph_arr_flag == false)
			ph_arr_flag = true;
		current_ph_index = 0;
	}
	*/
	digitalWrite(PH_RELAY, HIGH);
}

void getEC(void) {
	Serial.println("executed function : getEC");
	digitalWrite(PH_RELAY, HIGH);
	delay(500);
	digitalWrite(EC_VCC, HIGH);
	digitalWrite(EC_GND, HIGH);
	delay(100);
	ECcurrent = analogRead(EC_pin) * 5.00 / 1024;
	digitalWrite(EC_VCC, LOW);
	digitalWrite(EC_GND, LOW);
}

void supply_sol(float current_ec) {
	Serial.println("executed function\"supply_sol\"");
	if (current_ec < 1.0) {
		digitalWrite(SOL_B_RELAY, HIGH);
		delay(4000);
		digitalWrite(SOL_B_RELAY, LOW);
		delay(500);
		digitalWrite(SOL_A_RELAY, HIGH);
		delay(2700);
		digitalWrite(SOL_A_RELAY, LOW);
		delay(500);
		Serial.println("Supply SOL A/B 40mL");
		pump_run_flag = 'T';
	}
}

void get_sf_code(void) {
	String sf = "";

	while(1) {
		if (Serial1.available()) {
			sf_code = Serial1.parseInt();
			Serial.print("sfcode = ");
			Serial.println(sf_code);
			Serial1.flush();
			return;
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
	get_sf_code();
	sensors_setup();
	pinMode(SOL_A_RELAY, OUTPUT);
	pinMode(SOL_B_RELAY, OUTPUT);
	pinMode(EC_VCC, OUTPUT);
	pinMode(EC_GND, OUTPUT);
	pinMode(PH_RELAY, OUTPUT);
	digitalWrite(PH_RELAY, HIGH);
	digitalWrite(EC_VCC, LOW);		//ph, ec센서 전부 전원 off
	digitalWrite(EC_GND, LOW);
}

// the loop function runs over and over again until power down or reset
void loop() {
	get_masterData();		//마스터보드에서 전송한 제어값 수신.
	Serial1.flush();
	unsigned long present_millis = 0;
	if (pump_state == true) {
		present_millis = millis();	//루프 시작시간
		//와이파이 연결과 상관없이 센서값 측정.
		DHT_temp = dht22.readTemperature();
		DHT_humi = dht22.readHumidity();
		water_temp = getTemp();		//수온측정
		water_level = 0;
		for (int i = 0; i < 10; i++) {
			water_level_arr[i] = getWaterLevel();		//수위 측정. (%값으로 리턴)
			water_level += water_level_arr[i];
		}
		water_level /= 10;

		read_POT();							//조도센서값 배열에 저장.

		//냉각팬 자동/수동 설정 if문
		if (automatic_fan == true)
			fan_control(water_temp);			//수온에 따른 냉각팬 제어
		else
			get_controlData(2);

		//LED 자동설정/수동설정 if문
		if (automatic_led == true) {}
			//Relay_Control(true);
		else
			get_controlData(1);

		if (ph_flag == false) {
			getPH();
			getEC();
			ph_flag = true;
		}

		if (present_millis - ph_read_previousTime > ph_read_interval) {
			getPH();
			getEC();
			/*
			if (ph_arr_flag == false) {	
				for (int i = 0; i < current_ph_index; i++) {
					PHcurrent += current_ph_arr[current_ph_index];
				}
				Serial.print("ph 배열 합 : ");
				Serial.println(PHcurrent);
				PHcurrent /= (float)current_ph_index;
				Serial.print("ph 평균값 : ");
				Serial.println(PHcurrent);
			}
			else if (ph_arr_flag == true) {		//ph평균 배열이 한번 가득 찬 경우, 10번 값을 평균.
				for (int i = 0; i < 10; i++) {
					PHcurrent += current_ph_arr[current_ph_index];
				}
				PHcurrent /= 10.00;
			}
			*/
			ph_read_previousTime = millis();
		}

		if (present_millis - ec_control_previousTime > ec_control_interval) {		//EC 조절 후, 1분간 값안정화 대기
			supply_sol(ECcurrent);
			ec_control_previousTime = millis();
		}
	}
	if (wifi_join && pump_state) {
		if (present_millis - sensor_previousTime > sensor_interval) {
			Serial.println(String("Device ip : ") + device_ip);
			Serial.println(String("LED mode : ") + automatic_led);
			Serial.print(String("LED state : ") + led_state + "\n");
			Serial.print(String("Fan State : ") + fan_state + "\n");
			Serial.print(String("Water Temperature : ") + water_temp + "\n");
			Serial.print(String("DHT Temperature : ") + DHT_temp + "\n");
			Serial.print(String("DHT Humidity : ") + DHT_humi + "\n");
			Serial.print(String("POT Value : ") + POT_val[0] + " " + POT_val[1] + " " + POT_val[2] + " " + POT_val[3] + " " + POT_val[4] + "\n");
			Serial.print(String("Water Level : ") + water_level + "%\n");
			Serial.print(String("EC value : ") + ECcurrent + "ms/cm\n");
			Serial.print(String("PH value : ") + PHcurrent + "\n");
			Serial.println();

			sensor_previousTime = millis();
		}
		if (present_millis - data_send_previousTime > data_send_interval) {
			Serial.println("====Data Sending start=====");
			if (esp8266_send(DHT_temp, DHT_humi, water_temp, water_level, POT_val[4], ECcurrent, PHcurrent, device_ip, pump_run_flag)) {		//서버로 전송 성공할 경우,
				Serial.println("=====Data sending end..=====");
				pump_run_flag = 'F';
			}
			else {
				Serial.println("====Data sending fail..====");
			}
			data_send_previousTime = millis();
		}
	}
	else {
		if (pump_state == false)	Serial.println("pump not running");
		if (wifi_join == false)	 Serial.println("wifi not connected");
		delay(1000);
	}
}