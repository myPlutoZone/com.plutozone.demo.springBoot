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
 * File			: SystemCtrl.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20250101000000][pluto#plutozone.com][CREATE: Initial Release]
 */
package com.plutozone.common.controller;

import com.plutozone.common.dto.HostDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @version 1.0.0
 * @author pluto#plutozone.com
 *
 * @since 2025-01-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Controller("com.plutozone.common.controller.SystemCtrl")
public class SystemCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemCtrl.class);

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
	@GetMapping("/common/exception.web")
	public String exception(Model model) {

		String viewPage = "error";

		try {
			model.addAttribute("hostDto"		, new HostDto());
			model.addAttribute("requestCount"	, requestCount);

			// 5회 이상의 요청일 경우 500 Internal Server Error 발생
			requestCount++;
			if (requestCount > 5) throw new RuntimeException();

			viewPage = "common/exception";
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".exception()] " + e.getMessage(), e);
		}

		return viewPage;
	}
}
