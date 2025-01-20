/*
  In order to program ESP8266:
   -arduino RST-GND
   -ESP8266 IO0-GND
   -ESP8266->ARDUINO:
      -3.3v-3.3v
      -GND-GND
      -RX-RX
      -TX-TX
      -EN-3,3v
*/

#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "S";
const char* password = "Hyperbeast11234";
const byte numChars = 32;
char receivedChars[numChars];

boolean newData = false;

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN,LOW);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
  }
  digitalWrite(LED_BUILTIN,HIGH);
}


void sendData(String s) {
  for (int i = 0; i < s.length(); i++) {
    Serial.print(s[i]);
  }
  Serial.print('\n');
  newData = false;
}

void recvWithEndMarker() {
  static byte ndx = 0;
  char endMarker = '\n';
  char rc;

  while (Serial.available() > 0 && newData == false) {
    rc = Serial.read();
    if (rc != endMarker) {
      receivedChars[ndx] = rc;
      ndx++;
      if (ndx >= numChars) {
        ndx = numChars - 1;
      }
    }
    else {
      receivedChars[ndx] = '\0';
      ndx = 0;
      newData = true;
    }
  }
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
//    for(int t=0;t<32;t++){
//      receivedChars[t]=-1;
//    }
    String request={};//ENTER A1
    String value={};
    recvWithEndMarker();
    if (newData == true) {
      String s(receivedChars);
      if (s.indexOf("ENTER") != -1) {
        request = String("ENTER");
      }
      else if (s.indexOf("EXIT") != -1) {
        request = String("EXIT");
      }
      int i = s.indexOf(' ');
      int k;
      if (i != -1) {
        char temp[5];
        for (int j = i + 1, k = 0; j < s.length(); j++, k++) {
          temp[k] = s[j];
        }
        value = String(temp);
      }

      String Query = "parkingSpotName=" + value;

      //debug
//            sendData(request);
//            Serial.println(request.length());
//            sendData(value);
//            Serial.println(value.length());

      //aici fac request-urile
      if (request.equals(String("ENTER"))) {
        http.begin("http://192.168.144.153:8080/enter");
        http.addHeader("Content-Type", "application/x-www-form-urlencoded");
        int httpCode = http.POST(Query);
        for (int k = 0; k < 6; k++) {
          digitalWrite(LED_BUILTIN, HIGH);   // Turn the LED on (Note that LOW is the voltage level
          delay(250);                      // Wait for a second
          digitalWrite(LED_BUILTIN, LOW);  // Turn the LED off by making the voltage HIGH
          delay(250);
        }
        if (httpCode > 0) {
          String payload = http.getString();
          sendData(payload);
        }
        http.end();
      }
      else if (request.equals(String("EXIT"))) {
        http.begin("http://192.168.144.153:8080/exit");
        http.addHeader("Content-Type", "application/x-www-form-urlencoded");
        int httpCode = http.POST(Query);
        for (int k = 0; k < 5; k++) {
          digitalWrite(LED_BUILTIN, HIGH);   // Turn the LED on (Note that LOW is the voltage level
          delay(250);                      // Wait for a second
          digitalWrite(LED_BUILTIN, LOW);  // Turn the LED off by making the voltage HIGH
          delay(250);
        }
        if (httpCode > 0) {
          String payload = http.getString();
          sendData(payload);
        }
        http.end();
      }
    }
  }
}
