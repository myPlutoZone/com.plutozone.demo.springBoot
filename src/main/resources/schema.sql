/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF PLUTOZONE.COM.
 * PLUTOZONE.COM OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2025 PLUTOZONE.COM ALL RIGHTS RESERVED.
 *
 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 plutozone.com에 있으며,
 * plutozone.com이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * plutozone.com의 지적재산권 침해에 해당된다.
 * Copyright (C) 2025 plutozone.com All Rights Reserved.
 *
 *
 * Program		: com.plutozone.demo.springBoot
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: schema.sql
 * Notes		: 데이터베이스 초기화
 * History		: [NO][Programmer][Description]
 *				: [20250101000000][pluto#plutozone.com][CREATE: Initial Release]
 */

-- 필요 시 MySQL Container at Docker를 실행
-- 실행 명령 : docker container run -d --name=plutozone_demo_mysql --restart=always -e MYSQL_ROOT_PASSWORD=암호 -e MYSQL_DATABASE=plutozone -p 3306:3306 plutomsw/plutozone_demo_mysql:1.0

CREATE DATABASE IF NOT EXISTS plutozone;
-- 필요 시 데이터베이스 문자셋 변경
--  ALTER DATABASE plutozone
--  DEFAULT CHARACTER SET utf8
--  DEFAULT COLLATE utf8_general_ci;
-- 필요 시 권한 부여
-- GRANT ALL PRIVILEGES ON plutozone.* TO plutozone@localhost IDENTIFIED BY '비밀번호';

USE plutozone;

CREATE TABLE IF NOT EXISTS TB_BRD (
	SEQ_BRD INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
	, MBR_NM    VARCHAR(32)
	, CONTENT   VARCHAR(512)
	, FILE_NM   VARCHAR(256)
	, DT_REG    DATETIME
)