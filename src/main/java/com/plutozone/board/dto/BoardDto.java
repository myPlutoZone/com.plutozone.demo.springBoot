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
 * File			: BoardDto.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20250101000000][pluto#plutozone.com][CREATE: Initial Release]
 */
package com.plutozone.board.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0.0
 * @author pluto#plutozone.com
 *
 * @since 2025-01-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class BoardDto {

	private Integer seq_brd					= 0;
	private String mbr_nm					= "";
	private String dt_reg					= "";
	private String content					= "";
	private MultipartFile uploadingFile		= null;
	private String file_save				= "";


	public BoardDto() {
	}

	public BoardDto(String mbr_nm, String dt_reg, String content) {
		this.mbr_nm		= mbr_nm;
		this.dt_reg		= dt_reg;
		this.content	= content;
	}
	
	public BoardDto(String mbr_nm, String dt_reg, String content, MultipartFile uploadingFile) {
		this.mbr_nm			= mbr_nm;
		this.dt_reg			= dt_reg;
		this.content		= content;
		this.uploadingFile	= uploadingFile;
	}

	public int getSeq_brd() {
		return seq_brd;
	}

	public void setSeq_brd(int seq_brd) {
		this.seq_brd = seq_brd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDt_reg() {
		return dt_reg;
	}

	public void setDt_reg(String dt_reg) {
		this.dt_reg = dt_reg;
	}

	public String getFile_save() {
		return file_save;
	}

	public void setFile_save(String file_save) {
		this.file_save = file_save;
	}

	public String getMbr_nm() {
		return mbr_nm;
	}

	public void setMbr_nm(String mbr_nm) {
		this.mbr_nm = mbr_nm;
	}

	public MultipartFile getUploadingFile() {
		return uploadingFile;
	}

	public void setUploadingFile(MultipartFile uploadingFile) {
		this.uploadingFile = uploadingFile;
	}

	@Override
	public String toString() {
		StringBuffer sb =  new StringBuffer();
		sb.append("작성자="		+ this.mbr_nm + "\n");
		sb.append("작성일시="	+ this.dt_reg + "\n");
		sb.append("내용="		+ this.content + "\n");
		sb.append("첨부파일="	+ this.file_save + "\n");
		sb.append("\n");
		return sb.toString();
	}
}