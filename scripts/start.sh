#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"
P12_FILE="$PROJECT_ROOT/keystore.p12"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date)

# build,p12 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
sudo cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE
echo "$TIME_NOW > $P12_FILE 파일 복사" >> $DEPLOY_LOG
pwd >> $DEPLOY_LOG
sudo cp  $PROJECT_ROOT/keystore.p12 . 2>> $DEPLOY_LOG
sudo chmod +rx keystore.p12

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
ls >> $DEPLOY_LOG
chmod +x $JAR_FILE
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
