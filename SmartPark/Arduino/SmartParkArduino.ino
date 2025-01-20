#include <Servo.h>
#include <Arduino.h>
#include <TM1637Display.h>

#define CLK 4
#define DIO 5
#define SERVO_IN 10
#define SERVO_OUT 11

TM1637Display display = TM1637Display(CLK, DIO);
Servo servo_in;
Servo servo_out;

int n;
const byte numChars = 32;
char receivedChars[numChars];
char receivedChars1[numChars];
boolean newData = false;
boolean newData1 = false;
boolean enterBarrierOpened = false;
boolean exitBarrierOpened = false;
const String spots[40]={String("A1"),String("A2"),String("A3"),String("A4"),String("A5"),String("A6"),String("A7"),String("A8"),String("A9"),String("A10"),
                String("B1"),String("B2"),String("B3"),String("B4"),String("B5"),String("B6"),String("B7"),String("B8"),String("B9"),String("B10"),
                String("C1"),String("C2"),String("C3"),String("C4"),String("C5"),String("C6"),String("C7"),String("C8"),String("C9"),String("C10"),
                String("D1"),String("D2"),String("D3"),String("D4"),String("D5"),String("D6"),String("D7"),String("D8"),String("D9"),String("D10")};
const int digits[]={0xa1,0xa2,0xa3,0xa4,0xa5,0xa6,0xa7,0xa8,0xa9,0xa10,0xb1,0xb2,0xb3,0xb4,0xb5,0xb6,0xb7,0xb8,0xb9,0xb10,0xc1,0xc2,0xc3,0xc4,0xc5,0xc6,0xc7,0xc8,0xc9,0xc10,0xd1,0xd2,0xd3,0xd4,0xd5,0xd6,0xd7,0xd8,0xd9,0xd10};

void setup() {
  n = 40;
  Serial.begin(9600);
  Serial1.begin(9600);
  Serial2.begin(9600);
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(11, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(13, OUTPUT);
  servo_in.attach(SERVO_IN, 500, 2500);
  servo_out.attach(SERVO_OUT, 500, 2500);
  servo_in.write(90);
  servo_out.write(90);
  digitalWrite(12, LOW);//VERDE
  digitalWrite(13, HIGH);//ROSU
  // Set the brightness to 5 (0=dimmest 7=brightest)
  display.setBrightness(7);
}

void showParkingSpotName(String s){
  for(int i=0;i<40;i++){
    if(s.equals(spots[i])){
        display.showNumberHexEx(digits[i]); 
    }  
  }  
}

void readFromScanner() {
  static byte ndx = 0;
  char endMarker = '\n';
  char rc;
  while (Serial1.available() > 0 && newData == false) {
    rc = Serial1.read();
    if (rc != endMarker) {
      receivedChars[ndx] = rc;
      ndx++;
      if (ndx >= numChars) {
        ndx = numChars - 1;
      }
    }
    else {
      receivedChars[ndx] = '\0'; // terminate the string
      ndx = 0;
      newData = true;
    }
  }
}

void readFromESP() {
  static byte ndx = 0;
  char endMarker = '\n';
  char rc;
  while (Serial2.available() > 0 && newData1 == false) {
    rc = Serial2.read();
    if (rc != endMarker) {
      //if (rc != '\0') {
      receivedChars1[ndx] = rc;
      ndx++;
      if (ndx >= numChars) {
        ndx = numChars - 1;
      }
      //}
    }
    else {
      receivedChars1[ndx] = '\0'; // terminate the string
      ndx = 0;
      newData1 = true;
    }
  }
}

void sendData(String s) {
  for (int i = 0; i < s.length(); i++) {
    Serial2.print(s[i]);
  }
  Serial2.print('\n');
}

void openEnterBarrier() {
  for (int i = 90; i > 0; i--) {
    servo_in.write(i);
    delay(100);
  }
}
void openExitBarrier() {
  for (int i = 90; i > 0; i--) {
    servo_out.write(i);
    delay(100);
  }
}
void closeEnterBarrier() {
  for (int i = 0; i < 90; i++) {
    servo_in.write(i);
    delay(100);
  }
}
void closeExitBarrier() {
  for (int i = 0; i < 90; i++) {
    servo_out.write(i);
    delay(100);
  }
}

void loop() {
  display.showNumberDec(n);
  int in = analogRead(A0);
  int out = analogRead(A1);

  if (in < 400) {
    String message;
    String scannerData;
    readFromScanner();
    if (newData == true) {
      scannerData = String(receivedChars);
      message = ("ENTER " + scannerData);
      Serial.print("Request: ");
      Serial.println(message);
      sendData(message);
      delay(4000);
      readFromESP();
      if (newData1 == true) {
        String myResponse(receivedChars1);
        myResponse.trim();
        Serial.print("Raspuns la request:");
        Serial.print(myResponse);
        Serial.print("----Lungime raspuns:");
        Serial.println(myResponse.length());
        scannerData.trim();
        Serial.print("Raspuns asteptat la request:");
        Serial.print(scannerData);
        Serial.print("----Lungime asteptata:");
        Serial.println(scannerData.length());
        if (myResponse.equals(scannerData)) {
          showParkingSpotName(myResponse);
          delay(2000);
          openEnterBarrier();
          enterBarrierOpened = true;
          n--;
        }
        newData1 = false;
      }
    }
    newData = false;
  }
  else if (in > 400) {
    delay(3000);
    if (enterBarrierOpened == true) {
      //servo_in.write(90);
      closeEnterBarrier();
      enterBarrierOpened = false;
    }
  }


  if (out < 400) {
    String message;
    String scannerData;
    readFromScanner();
    if (newData == true) {
      scannerData = String(receivedChars);
      message = ("EXIT " + scannerData);
      Serial.print("Request: ");
      Serial.println(message);
      sendData(message);
      delay(4000);
      readFromESP();
      if (newData1 == true) {
        String myResponse(receivedChars1);
        myResponse.trim();
        Serial.print("Raspuns la request:");
        Serial.print(myResponse);
        Serial.print("----Lungime raspuns:");
        Serial.println(myResponse.length());
        scannerData.trim();
        Serial.print("Raspuns asteptat la request:");
        Serial.print(String("PAID"));
        Serial.print("----Lungime asteptata:");
        Serial.println(String("PAID").length());
        Serial.println(myResponse);
        if (myResponse.equals(String("PAID"))) {
          openExitBarrier();
          exitBarrierOpened = true;
          n++;
        }
        newData1 = false;
      }
    }
  }
  else if (out > 400) {
    delay(3000);
    if (exitBarrierOpened == true) {
      closeExitBarrier();
      exitBarrierOpened = false;
    }

  }


  if (n > 0) {
    digitalWrite(12, LOW);
    digitalWrite(13, HIGH);
  }
  else {
    digitalWrite(12, HIGH);
    digitalWrite(13, LOW);
  }
  newData = false;
  newData1 = false;
}
