#include <SoftwareSerial.h>

#define RELAY_IN1 4
#define RELAY_IN2 5
#define RELAY_IN3 6
#define RELAY_IN4 7
#define RELAY_PUMP 8
#define fan1 11
#define fan2 12
#define fan3 13		//냉각팬 핀번호

SoftwareSerial Serial_M(2, 3);	//마스터보드와 통신

void Relay_Control(int cmd) {
	if (cmd == 4) {
		for (int i = RELAY_IN1; i <= RELAY_IN4; i++) {
			digitalWrite(i, HIGH);
		}
	}
	else if (cmd == 5) {
		for (int i = RELAY_IN1; i <= RELAY_IN4; i++) {
			digitalWrite(i, LOW);
		}
	}
	else if (cmd == 41)	digitalWrite(RELAY_IN1, HIGH);
	else if (cmd == 42) digitalWrite(RELAY_IN3, HIGH);
	else if (cmd == 43) digitalWrite(RELAY_IN2, HIGH);
	else if (cmd == 44) digitalWrite(RELAY_IN4, HIGH);
	else if (cmd == 45) { digitalWrite(RELAY_IN1, HIGH); digitalWrite(RELAY_IN3, HIGH); }
	else if (cmd == 46) { digitalWrite(RELAY_IN2, HIGH); digitalWrite(RELAY_IN4, HIGH); }

	else if (cmd == 51)	digitalWrite(RELAY_IN1, LOW);
	else if (cmd == 52) digitalWrite(RELAY_IN3, LOW);
	else if (cmd == 53) digitalWrite(RELAY_IN2, LOW);
	else if (cmd == 54) digitalWrite(RELAY_IN4, LOW);
	else if (cmd == 55) { digitalWrite(RELAY_IN1, LOW); digitalWrite(RELAY_IN3, LOW); }
	else if (cmd == 56) { digitalWrite(RELAY_IN2, LOW); digitalWrite(RELAY_IN4, LOW); }

	else if (cmd == 12) {
		digitalWrite(RELAY_PUMP, LOW);
	}
	else if (cmd == 13) {
		digitalWrite(RELAY_PUMP, HIGH);
	}
}

void fan_control(int cmd) {
	if (cmd == 8) {
		digitalWrite(fan1, HIGH);
		digitalWrite(fan2, HIGH);
		digitalWrite(fan3, HIGH);
	}
	else if (cmd == 9) {
		digitalWrite(fan1, LOW);
		digitalWrite(fan2, LOW);
		digitalWrite(fan3, LOW);
	}
	else if (cmd == 81) digitalWrite(fan1, HIGH);
	else if (cmd == 82) digitalWrite(fan1, HIGH);
	else if (cmd == 83) digitalWrite(fan1, HIGH);
	else if (cmd == 91) digitalWrite(fan1, LOW);
	else if (cmd == 92) digitalWrite(fan1, LOW);
	else if (cmd == 93) digitalWrite(fan1, LOW);
}

void setup()
{
	Serial.begin(9600);		//슬레이브 보드와 통신
	Serial_M.begin(9600);	//마스터 보드와 통신
	pinMode(fan1, OUTPUT);
	pinMode(fan2, OUTPUT);
	pinMode(fan3, OUTPUT);		//냉각팬 핀모드 : 출력
	pinMode(RELAY_IN1, OUTPUT);
	pinMode(RELAY_IN2, OUTPUT);
	pinMode(RELAY_IN3, OUTPUT);
	pinMode(RELAY_IN4, OUTPUT);			//LED제어용 릴레이 핀모드.
	for (int i = RELAY_IN1; i <= RELAY_IN4; i++) {
		digitalWrite(i, LOW);
	}
	digitalWrite(fan1, LOW);
	digitalWrite(fan2, LOW);
	digitalWrite(fan3, LOW);
}

void loop()
{	//자동모드의 경우 슬레이브 보드에서 수신.
	if (Serial.available()) {		//슬레이브 보드에서 전송한 데이터 존재할 경우
		int cmd = Serial.parseInt();

		switch (cmd) {
		case 4:			//LED ON
			Relay_Control(4);
			Serial.print(1);
			break;
		case 5:			//LED OFF
			Relay_Control(5);
			Serial.print(0);
			break;
		case 8:			//FAN ON
			fan_control(8);
			Serial.print(1);
			break;
		case 9:			//FAN OFF
			fan_control(9);
			Serial.print(0);
			break;
		}
	}
	//수동모드 경우 마스터보드에서 수신.
	if (Serial_M.available()) {
		int cmd = Serial_M.parseInt();
		Relay_Control(cmd);
		/*
		switch (cmd) {
		case 4:			//LED ON
			Relay_Control(4);
			Serial.print(1);
			break;
		case 5:			//LED OFF
			Relay_Control(5);
			Serial.print(0);
			break;
		case 8:			//FAN ON
			fan_control(8);
			break;
		case 9:			//FAN OFF
			fan_control(9);
			break;
		}
		*/
	}
}