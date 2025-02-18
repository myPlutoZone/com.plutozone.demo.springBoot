> YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS
DOCUMENT IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF
PLUTOZONE.COM.
PLUTOZONE.COM OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS DOCUMENT.
COPYRIGHT (C) 2005 PLUTOZONE.COM ALL RIGHTS RESERVED
***
> 하기 문서에 대한 저작권을 포함한 지적재산권은 plutozone.com에 있으며 plutozone.com이 명시적으
로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
plutozone.com의 지적재산권 침해에 해당된다.
***
> Copyright (C) 2005 plutozone.com All Rights Reserved


# Overview
- Demo for SpringBoot by IntelliJ + Maven


# History
- 2025-02-06 [CREATE] Initial Release


# Build & Deploy
## Plan A) only Build
```cmd
C:\>mvnw.cmd clean package
C:\>gradlew.bat clean build         # gradlew.bat clean jar
```


## Plan B) Build & Deploy at Server by Shell + GitHub
```bash
# Install JDK and Git Client at Server only once.
$ vi ~/run.sh
#!/bin/bash

export JAVA_HOME=/usr/local/java/jdk-21.0.6
# export JRE_HOME=$JAVA_HOME/jre
# export CLASSPATH=.:$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin

# echo $JAVA_HOME
# echo $PATH
# java -version

# Stop Service(demo.springBoot-0.0.1-SNAPSHOT.jar)
PID=`ps -ef | grep demo.springBoot-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
kill -9 $PID
sleep 3

# Remove Source Folder
rm -rf com.plutozone.demo.springBoot
sleep 3

# Checkout Source from GitHub.
git clone https://github.com/myPlutoZone/com.plutozone.demo.springBoot.git
sleep 3

cd com.plutozone.demo.springBoot

# Clean and Build
chmod 754 mvnw
./mvnw clean package
sleep 3

# Start Service(demo.springBoot-0.0.1-SNAPSHOT.jar)
java -jar ./target/demo.springBoot-0.0.1-SNAPSHOT.jar &
```


## Plan C) Build & Deploy at Container by Shell + Docker + GitHub


## Plan D) Build & Deploy at Container by Jenkins + Docker + GitHub
1. Checkout Source.
2. Build.
3. ... Unit, SonarQube, ...
4. Build Docker Image(Linux) include Source(springBoot.jar).
5. Push Docker Image.
6. ... run MySQL(schema.sql), ...
7. Run Docker Image
8. ... JMeter, ...
9. ... Slack, ...