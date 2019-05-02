//Invoca a biblioteca Servo
#include <Servo.h>
// Cria um objeto para o controle do servo
// No Arduino UNO no máximo 12 servos podem ser usados
Servo meu_servo;
void setup() {
 // Configura a porta 9 digital como servo
 meu_servo.attach(9);
}
void loop() {
 //meu_servo.write(0); // Comanda o servo para a posição 0 grau
 //delay(1000);
 meu_servo.write(-180); // Comanda o servo para a posição 90 graus
 delay(500);
 meu_servo.write(180); // Comanda o servo para a posição 180 graus
 delay(1000);
}
