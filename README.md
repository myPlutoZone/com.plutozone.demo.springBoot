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
- 본 프로젝트는 Spring Boot MVC by IntelliJ + Maven 기반의 데모를 제공한다.


# History
- 2025-02-06 [CREATE] Initial Release


# Build & Deploy
## Plan A) only Build(Maven or Gradle)
```cmd
C:\>mvnw.cmd clean package
C:\>gradlew.bat clean build         # gradlew.bat clean jar
```


## Plan B) byShell.sh = Build & Deploy at Server by Shell + GitHub
```bash
$ vi ~/byShell.sh
#!/bin/bash

# ------------------------------------------------------
# First, Install JDK and Git Client at Server only once.
# ------------------------------------------------------

export JAVA_HOME=/usr/local/java/jdk-21.0.6
export JRE_HOME=$JAVA_HOME
# export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib
# export CLASSPATH=.:$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin

# echo $JAVA_HOME
# echo $PATH
# java -version

# Stop Service(demo.springBoot-0.0.1-SNAPSHOT.jar)
PID=`ps -ef | grep demo.springBoot-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`

if [ "x$PID" != "x" ] && kill -0 $PID 2>/dev/null; then
	RUNNING=1
else
	RUNNING=0
fi

if [ $RUNNING -eq 1 ]; then
    kill -9 $PID
    sleep 3
    echo "----------------------------------------------------------"
    echo " [SUCCESS] The demo.springBoot-0.0.1-SNAPSHOT.jar has stoped successful."
    echo "----------------------------------------------------------"
else
    echo "-----------------------------------------------------------"
    echo " [WARNING] The demo.springBoot-0.0.1-SNAPSHOT.jar process is not nunning!"
    echo "-----------------------------------------------------------"
fi


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
java -jar -server -Xms128M -Xmx256M ./target/demo.springBoot-0.0.1-SNAPSHOT.jar > /dev/null &
# java -jar ./target/demo.springBoot-0.0.1-SNAPSHOT.jar &
```


## Plan C) byDocker.sh = Build & Deploy at Container by Shell + GitHub + Docker(Dockerfile)
```bash
$ vi ~/docker/Dockerfile
FROM plutomsw/demo-springboot
# FROM openjdk:8-alpine

ARG VERSION

COPY ~/com.plutozone.demo.springBoot/target/demo.springBoot-0.0.1-SNAPSHOT.jar /app/demo.springBoot.jar

LABEL maintainer="Sungwan Myung<pluto#plutozone.com>" \
      title="Spring Boot Demo" \
      version="$VERSION" \
      description="This image is Spring Boot Demo"

ENV APP_HOME /app
EXPOSE 8080
VOLUME /app/upload

WORKDIR $APP_HOME
ENTRYPOINT ["java"]
CMD ["-jar", "demo.springBoot.jar"]
```

```bash
$ vi ~/byDocker.sh
#!/bin/bash

# ------------------------------------------------------
# First, Install JDK and Git Client at Server only once.
# ------------------------------------------------------

export JAVA_HOME=/usr/local/java/jdk-21.0.6
export JRE_HOME=$JAVA_HOME
# export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib
# export CLASSPATH=.:$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin

# echo $JAVA_HOME
# echo $PATH
# java -version

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


# ------------------------------------------------------
# Second, Install Docker at Server only once.
# ------------------------------------------------------
cd ..

# 1. Backup & Push
# docker tag plutomsw/demo-springboot myRegistry.com/ID/demo-nginx:날짜

# 2. Build
# $ docker plutomsw/demo.springBoot:${Tag} --build-arg VERSION=${Tag} -f Dockerfile .

# 3. Run(Stop old & Start new) & Push
# $ docker container run plutomsw/demo.springBoot:${Tag}
docker run -d --name demoSpringboot -p 1000:8080 plutomsw/demo-springboot
```


## Plan D) run at Jenkins = Build & Deploy at Container by GitHub + Docker(Dockerfile) + Jenkins(Jenkinsfile) 
1. Checkout Source.
2. Build.
3. ... Unit, SonarQube, ...
4. Build Docker Image(Linux) include Source(springBoot.jar).
5. Push Docker Image.
6. ... run MySQL(schema.sql), ...
7. Run Docker Image
8. ... JMeter, ...
9. ... Slack, ...