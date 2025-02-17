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
 * Environment	: JRE 1.8 or more
 * File			: BoardWeb.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20250101000000][pluto#plutozone.com][CREATE: Initial Release]
 */
package com.plutozone.board.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

import com.plutozone.board.dto.BoardDto;
import com.plutozone.board.service.BoardSrvc;
import com.plutozone.common.dto.HostDto;
import com.plutozone.util.Datetime;
import com.plutozone.util.Number;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0.0
 * @author pluto#plutozone.com
 *
 * @since 2025-01-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Controller("com.plutozone.board.controller.BoardWeb")
public class BoardWeb {

	private static final Logger logger = LoggerFactory.getLogger(BoardWeb.class);

	@Autowired
	private BoardSrvc boardSrvc;

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
	@PostMapping("/board/modifyForm.web")
	public String modifyForm(@ModelAttribute BoardDto boardDto, Model model) {

		String viewPage = "error";

		try {
			model.addAttribute("boardDto", boardDto);
			viewPage = "board/modifyForm";
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".modifyForm()] " + e.getMessage(), e);
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
	@GetMapping("/board/list.web")
	public String list(Model model) {

		String viewPage = "error";

		try {
			// [2025-01-01][pluto#plutozone.com][TODO-필수: 페이징]
			model.addAttribute("boardList"	, boardSrvc.getAll());
			model.addAttribute("hostDto"	, new HostDto());
			
			// 1~5초 사이로 랜덤하게 응답지연
			Thread.sleep((long) Number.randomRange(1, 5) * 1000);
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
			// 파일 첨부
			if (boardDto.getUploadingFile().getOriginalFilename().equals("") == false) {
				// [2025-01-01][pluto@plutozone.com][TODO-필수: 원본 및 저장 파일명(저장 폴더 자동 생성 및 UUID 기반 파일명 포함)으로 개선(파일 다운로드 시 원본 파일명 적용)]
				String uploadedFile = this.upload(boardDto.getUploadingFile(), boardDto.getMbr_nm());
				boardDto.setFile_save(uploadedFile);
			}

			// DB 저장
			// boardDto.setWriteDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
			if (boardSrvc.add(boardDto)) {

				// [2025-01-01][pluto#plutozone.com][TODO-필수: 메시지 출력 후 리다이렉트]
				return "redirect:/board/list.web";

				// 게시판 조회
				// model.addAttribute("boardList"	, boardSrvc.getAll());
				// model.addAttribute("hostDto"	, new HostDto());
				// viewPage = "board/list";
			}

		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".writeProc()] " + e.getMessage(), e);
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
	@GetMapping("/board/download/{fileName:.+}")
	public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws Exception {
		
		// [2025-01-01][pluto@plutozone.com][TODO-필수: 파일 업로드 시 최상위 경로 from Property]
		// [2025-01-01][pluto@plutozone.com][TODO-필수: 원본 및 저장 파일명 from DB]
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
	private String upload(MultipartFile file, String uploaderName) {
		//
		String currentWorkingDirectory	= System.getProperty("user.dir");
		String originalFilename			= file.getOriginalFilename();	// 클라이언트 시스템의 FullPath 포함한 파일명
		String uploadFileName			= Datetime.getNow("yyyyMMddHHmmssSSS") + "_" + uploaderName + "_" + FilenameUtils.getName(originalFilename);
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
}