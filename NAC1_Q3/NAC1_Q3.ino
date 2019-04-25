#include <ArduinoJson.h>
const int RED = 3;
const int GREEN = 5;
const int BLUE = 9;

const int TAMANHO = 200;

#include "DHT.h"
#define DHTPIN A2 // pino que estamos conectado
#define DHTTYPE DHT11 // DHT 11
DHT dht(DHTPIN, DHTTYPE); //Instanciação do objeto do sensor

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10); //1000ms é muito tempo
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  dht.begin();
}

void loop() {
  if (Serial.available() > 0) {
    //Lê o texto disponível na porta serial:
    char texto[TAMANHO];
    Serial.readBytesUntil('\n', texto, TAMANHO);
    //Grava o texto recebido como JSON
    StaticJsonBuffer<TAMANHO> jsonBuffer;
    JsonObject& json = jsonBuffer.parseObject(texto);
    if (json.success() && json.containsKey("red")) {
      analogWrite(RED, 255 - (int)json["red"]);
      Serial.println(String("Vermelho: ") + (255-(int)json["red"]));
    }
    if (json.success() && json.containsKey("green")) {
      analogWrite(GREEN, 255-(int)json["green"]);
      Serial.println(String("Verde: ") + (255-(int)json["green"]));
    }
    if (json.success() && json.containsKey("blue")) {
      analogWrite(BLUE, 255-(int)json["blue"]);
      Serial.println(String("Azul: ") + (255-(int)json["blue"]));
    }
  }
  StaticJsonBuffer<TAMANHO> jsonBuffer;
  JsonObject& json = jsonBuffer.createObject();
  json["temp"] = dht.readTemperature();
  json["umid"] = dht.readHumidity();
  json.printTo(Serial); Serial.println();
  delay(1000);
}
