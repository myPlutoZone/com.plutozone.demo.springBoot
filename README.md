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
## only Build
```cmd
C:\>mvnw.cmd clean package
C:\>gradlew.bat clean build         # gradlew.bat clean jar
```

## Build & Deploy by Jenkins + Docker
1. checkout Source
2. build Source
3. ... Unit, SonarQube, ...
4. build Docker Image(Linux) include Source(springBoot.jar)
5. push Docker Image
6. ... run MySQL(schema.sql), ...
7. run Docker Image
8. ... JMeter, ...
9. ... Slack, ...