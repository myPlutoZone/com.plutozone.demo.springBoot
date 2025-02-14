/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF PLUTOZONE.COM.
 * PLUTOZONE.COM OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2025 PLUTOZONE.COM ALL RIGHTS RESERVED.

 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 plutozone.com에 있으며,
 * plutozone.com이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * plutozone.com의 지적재산권 침해에 해당된다.
 * Copyright (C) 2025 plutozone.com All Rights Reserved.

 * Program		: com.plutozone.demo.springBoot
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: BoardWeb.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20250101000000][pluto#plutozone.com][CREATE: Initial Release]
 */
package com.plutozone.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.plutozone.board.dto.BoardDto;
import com.plutozone.board.service.BoardSrvc;
import com.plutozone.common.dto.HostDto;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0.0
 * @author pluto#plutozone.com
 *
 * @since 2025-01-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Controller
public class BoardWeb {

	private static final Logger logger = LoggerFactory.getLogger(BoardWeb.class);

	@Autowired
	private BoardSrvc boardSrvc;

	// error 및 rollback 시뮬레이션용 - 요청이 5회 이상일때 오류 발생 재연에 사용
	private int requestCount = 0;

	/**
	 * @param model 모델
	 * @return String
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@GetMapping("/board/list.web")
	public String list(Model model) {

		String viewPage = "error";

		try {
			model.addAttribute("boardList"	, boardSrvc.getAll());
			model.addAttribute("hostDto"	, new HostDto());

			/*
			// 5회 이상의 요청일 경우 error 페이지로 이동
			// 잘못된 버전 배포 후 롤백 데모시 주석 해제할 것!!!
			requestCount++;
			if (requestCount > 5) {
				model.addAttribute("health", "UnHealthy");
				return "error";
			}
			*/

			// 02. 의미없이 CPU 사용량 증가
			this.useCPU(1000);

			// 03. 1~5초 사이로 랜덤하게 응답지연
			Thread.sleep((long)randomRange(1,5) * 1000);
			viewPage = "board/list";
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".list()] " + e.getMessage(), e);
		}

		return viewPage;
	}

	/**
	 * @param boardDto 게시판DTO
	 * @param model 모델
	 * @return String
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@PostMapping("/board/writeProc.web")
	public String writeProc(@ModelAttribute BoardDto boardDto, Model model) {

		String viewPage = "error";

		try {
			// 01-1. 파일 첨부
			if (boardDto.getUploadingFile().getOriginalFilename().equals("") == false) {
				String uploadedFile = this.uploadFile(boardDto.getUploadingFile(), boardDto.getMbr_nm());
				boardDto.setFile_save(uploadedFile);
			}

			// 01-2. DB 저장
			// boardDto.setWriteDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
			if (boardSrvc.add(boardDto)) {
				// 02. 게시판 조회
				model.addAttribute("boardDto"	, boardSrvc.getAll());
				model.addAttribute("hostDto"	, new HostDto());

				viewPage = "board/list";
			}


		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".writeProc()] " + e.getMessage(), e);
		}

		return viewPage;
	}

	/**
	 * @param model 모델
	 * @return String
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@GetMapping("/board/checkHealth.web")
	public String checkHealth(Model model) {

		String viewPage = "error";

		try {
			// 01. 방명록 조회
			model.addAttribute("boardDto"	, boardSrvc.getAll());
			model.addAttribute("hostDto"	, new HostDto());

			// 5회 이상의 요청일 경우 500 Internal Server Error 발생
			requestCount++;
			if (requestCount > 5) throw new RuntimeException();

			viewPage = "board/list";
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".checkHealth()] " + e.getMessage(), e);
		}

		return viewPage;
	}

	/**
	 * @param fileName 파일명
	 * @param  request 서블릿요청
	 * @return ResponseEntity
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {

		File downloadFile = new File(System.getProperty("user.dir") + "/upload/" + fileName);
		Resource resource = new UrlResource(downloadFile.toURI());
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		if (contentType == null) contentType = "text/plain";
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
    }
	
	/**
	 * @param file 파일바이너리
	 * @param uploaderName 파일명
	 * @return String 파일명
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION: 현재 Working Directory/upload 폴더에 파일을 업로드</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	private String uploadFile(MultipartFile file, String uploaderName) {
		//
		String currentWorkingDirectory	= System.getProperty("user.dir");
		String originalFilename			= file.getOriginalFilename();	// 클라이언트 시스템의 FullPath 포함한 파일명
		String uploadFileName			= this.getCurrentTimeMillisFormat() + "_" + uploaderName + "_" + FilenameUtils.getName(originalFilename);
		File uploadFile					= new File( currentWorkingDirectory + "/upload/" + uploadFileName);
		
		try {
			uploadFile.getParentFile().mkdirs(); // upload 디렉토리가 없으면 생성
			file.transferTo(uploadFile);
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".uploadFile()] " + e.getMessage(), e);
		}
		
		return uploadFileName;
	}
	
	/**
	 * @return String
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION: 현재 시간을 "년월일시분초밀리초"로 반환</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	private String getCurrentTimeMillisFormat() {
		long currentTime = System.currentTimeMillis(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		return dateFormat.format(new Date(currentTime)); 
	}
	
	/**
	 * @param loopCount 루프카운트
	 * @return double
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION: CPU 사용률을 높이기 위해 LOOP를 돌면서 산술연산(double 연산) 수행</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	private double useCPU(int loopCount) {

		double result = 0;

		for (int i = 1; i < loopCount; i++) {
			result = i * Math.random() / loopCount;
			for (int j = 1; j < loopCount; j++) {
				result = i * j * Math.random() / loopCount;
				for (int k = 1; k < loopCount; k++) {
					// CPU 소모
				}
			}
		}
		return result;
	}
	
	/**
	 * @param n1 하한값
	 * @param n2 상한값
	 * @return int
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION: 지정된 범위의 정수 1개를 램덤하게 반환</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	private int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}
}