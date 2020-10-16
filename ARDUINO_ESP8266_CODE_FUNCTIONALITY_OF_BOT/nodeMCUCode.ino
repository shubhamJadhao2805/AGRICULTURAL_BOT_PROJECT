#include <SoftwareSerial.h>
#include <FirebaseESP8266.h>
#include <ESP8266WiFi.h>
SoftwareSerial s(D6,D5);

#define FIREBASE_HOST "https://ammonia-82854.firebaseio.com/"
#define FIREBASE_AUTH "LQaFy0NYlsqS7Gfzd0yCwmFXB5ywDUhUPqv2qN85"
#define WIFI_SSID "srb123"
#define WIFI_PASSWORD "123456789"
FirebaseData firebaseData;
int data;

void setup() {
  Serial.begin(9600);
s.begin(9600);
 WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

    
}
 
void loop() {

 s.write("s");
  if (s.available()>0)
  {
    data=s.read();
    Serial.println(data);
    if(data < 50){
    Firebase.setInt(firebaseData,"Status",1);
    }else{
          Firebase.setInt(firebaseData,"Status",0);

    }
    Firebase.setInt(firebaseData,"SensorValue",data);

    
  }
 
 
}





//#include <SoftwareSerial.h>
//SoftwareSerial s(D6,D5);
//int data;
//void setup() {
//s.begin(9600);
//Serial.begin(9600);
//}
// 
//void loop() {
//  s.write("s");
//  if (s.available()>0)
//  {
//    data=s.read();
//    Serial.println(data);
//  }
// 
// 
//}
//}
