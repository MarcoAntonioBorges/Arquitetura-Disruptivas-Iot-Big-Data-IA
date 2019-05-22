#include <ArduinoJson.h>
const int LED = 3;
const int LUZ = A1;
const int TAMANHO = 200;
void setup() {
 Serial.begin(9600);
 Serial.setTimeout(10); //1000ms é muito tempo
 pinMode(LED,OUTPUT);
}
void loop() {
 if (Serial.available() > 0) {
   //Lê o texto disponível na porta serial:
   char texto[TAMANHO];
   Serial.readBytesUntil('\n', texto, TAMANHO);
   //Grava o texto recebido como JSON
   StaticJsonBuffer<TAMANHO> jsonBuffer;
   JsonObject& json = jsonBuffer.parseObject(texto);
   if(json.success() && json.containsKey("led")) {
    analogWrite(LED, json["led"]);
   }
 }
 StaticJsonBuffer<TAMANHO> jsonBuffer;
 JsonObject& json = jsonBuffer.createObject();
 json["luz"] = analogRead(LUZ);
 json.printTo(Serial); Serial.println();
 delay(1000);
 
 json["led"] = digitalRead(LED);
 json.printTo(Serial); Serial.println();
 delay(1000);
}
