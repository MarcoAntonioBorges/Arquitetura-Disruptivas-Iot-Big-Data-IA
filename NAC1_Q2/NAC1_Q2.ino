#include "DHT.h"
#define DHTPIN A2 // pino que estamos conectado
#define DHTTYPE DHT11 // DHT 11
DHT dht(DHTPIN, DHTTYPE); //Instanciação do objeto do sensor

const int RED = 3;
const int GREEN = 5;
const int BLUE = 9; 

void setup() {
  Serial.begin(9600);
  dht.begin();
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
}
void loop() {
  // A leitura da temperatura e umidade pode levar 250ms!
  float h = dht.readHumidity();//Valor da umidade
  float t = dht.readTemperature(); //Valor da temperatura
  if (isnan(t) || isnan(h)) {
    Serial.println("Erro ao ler do DHT");
  } else {
    Serial.print("Umidade: ");
    Serial.print(h); Serial.print(" %\t");
    Serial.print("Temperatura: ");
    Serial.print(t); Serial.println(" ºC");
  }
  if(t <= 23 && h < 40) {
    analogWrite(RED, 0);
    analogWrite(GREEN, 0);
    analogWrite(BLUE, 0);
  } else if(t > 23 && h < 40) {
    analogWrite(RED, 0);
    analogWrite(BLUE, 127);
    analogWrite(GREEN, 255);
  } else if(t <= 23 && h > 40) {
    analogWrite(RED, 255);
    analogWrite(BLUE, 0);
    analogWrite(GREEN, 255);
  } else {
    analogWrite(RED, 0);
    analogWrite(BLUE, 255);
    analogWrite(GREEN, 255); 
  }
  delay(200);
}

