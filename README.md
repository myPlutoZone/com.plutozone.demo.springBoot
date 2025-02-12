# com.plutozone.demo.springBoot

## 0. TODO
- Development Standards
- image, css, javascript folder for *.css, *.html 

## 1. Build & Deploy
### 1-1. only Build
```cmd
C:\>mvnw.cmd clean package
```

### 1-2. Build & Deploy by Jenkins + Docker
1. checkout Source
2. build Source
3. ... Unit, SonarQube, ...
4. build Docker Image(Linux) include Source(springBoot.jar)
5. push Docker Image
6. ... run MySQL(schema.sql), ...
7. run Docker Image
8. ... JMeter, ...
9. ... Slack, ...