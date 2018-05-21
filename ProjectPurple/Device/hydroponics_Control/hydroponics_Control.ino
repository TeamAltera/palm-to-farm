#include <SoftwareSerial.h>

#define RELAY_IN1 4
#define RELAY_IN2 5
#define RELAY_IN3 6
#define RELAY_IN4 7
#define fan1 11
#define fan2 12
#define fan3 13		//냉각팬 핀번호

SoftwareSerial Serial_M(2, 3);	//마스터보드와 통신

void Relay_Control(int cmd) {
	if (cmd == 4) {
		for (int i = RELAY_IN1; i <= RELAY_IN4; i++)
			digitalWrite(i, LOW);
	}
	else if (cmd == 5) {
		for (int i = RELAY_IN1; i <= RELAY_IN4; i++)
			digitalWrite(i, HIGH);
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
}

void setup()
{
	Serial.begin(9600);		//슬레이브 보드와 통신
	Serial_M.begin(9600);	//마스터보 보드와 통신
	pinMode(fan1, OUTPUT);
	pinMode(fan2, OUTPUT);
	pinMode(fan3, OUTPUT);		//냉각팬 핀모드 : 출력
	pinMode(RELAY_IN1, OUTPUT);
	pinMode(RELAY_IN2, OUTPUT);
	pinMode(RELAY_IN3, OUTPUT);
	pinMode(RELAY_IN4, OUTPUT);			//LED제어용 릴레이 핀모드.
	for (int i = RELAY_IN1; i <= RELAY_IN4; i++) {
		digitalWrite(i, HIGH);
	}
	digitalWrite(fan1, LOW);
	digitalWrite(fan2, LOW);
	digitalWrite(fan3, LOW);
}

void loop()
{
	if (Serial.available()) {		//슬레이브 보드에서 전송한 데이터 존재할 경우
		int cmd = Serial.parseInt();
		switch (cmd) {
		case 4:			//LED ON
			Relay_Control(4);
			break;
		case 5:			//LED OFF
			Relay_Control(5);
			break;
		case 8:			//FAN ON
			fan_control(8);
			break;
		case 9:			//FAN OFF
			fan_control(9);
			break;
		}
	}
}

