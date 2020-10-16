


int M1pwm = 5;
int M2pwm = 6;
int M3pwm = 7;
int M4pwm = 8;
int M1dir = 9;
int M2dir = 10;
int M3dir = 11;
int M4dir = 12;
int speedToSet = 35;
int i;
void setup() {
  // put your setup code here, to run once:

//pinMode(pwm,OUTPUT);
//pinMode(dir,OUTPUT);

pinMode(M1pwm,OUTPUT);
pinMode(M2pwm,OUTPUT);
pinMode(M3pwm,OUTPUT);
pinMode(M4pwm,OUTPUT);
pinMode(M1dir,OUTPUT);
pinMode(M2dir,OUTPUT);
pinMode(M3dir,OUTPUT);
pinMode(M4dir,OUTPUT);


}

void loop() {

moveForwar();
delay(5000);
stopBot(speedToSet);
delay(2000);
moveBack();
stopBot(speedToSet);
delay(2000);

}




void moveBack(){

  digitalWrite(M1dir,HIGH);
  digitalWrite(M2dir,LOW);
  digitalWrite(M3dir,LOW);
  digitalWrite(M4dir,HIGH);

 for(i = 0;i<speedToSet;i++){
   setSpeed(i);
   delay();
   
  }

  
}


//Function to Move Forward

void moveForwar(){
  
  digitalWrite(M1dir,LOW);
  digitalWrite(M2dir,HIGH);
  digitalWrite(M3dir,HIGH);
  digitalWrite(M4dir,LOW);
  for(i = 0;i<speedToSet;i++){
   setSpeed(i);
   delay(10);
   
  }


}
//Function to Turn LEFT

void turnLeft(){

  digitalWrite(M1dir,HIGH);
  digitalWrite(M2dir,HIGH);
  digitalWrite(M3dir,HIGH);
  digitalWrite(M4dir,HIGH);

}


//Function to Turn Right
void turnRight(){

   digitalWrite(M1dir,LOW);
  digitalWrite(M2dir,LOW);
  digitalWrite(M3dir,LOW);
  digitalWrite(M4dir,LOW);
  
}


//Setting Speed
void setSpeed(int speed){

 analogWrite(M1pwm,speed);
  analogWrite(M2pwm,speed);
  analogWrite(M3pwm,speed);
  analogWrite(M4pwm,speed);
  
}


//Function To Stop
void stopBot(int speedToSet){

for(i=speedToSet;i>0;i--){
  setSpeed(i);
  delay(10);
}

  
}
