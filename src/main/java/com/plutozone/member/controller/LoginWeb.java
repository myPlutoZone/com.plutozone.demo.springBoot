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
 * File			: LoginWeb.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20250101000000][pluto#plutozone.com][CREATE: Initial Release]
 *				: [20250101000000][pluto#plutozone.com][REPORT: 프론트이므로 경로에서 member는 생략]
 */
package com.plutozone.member.controller;

import com.plutozone.util.Datetime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @version 1.0.0
 * @author pluto#plutozone.com
 *
 * @since 2025-01-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Controller("com.plutozone.member.controller.LoginWeb")
public class LoginWeb {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginWeb.class);
	
	/**
	 * @return String
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value="/logout.web", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		String viewPage = "error";
		
		try {
			HttpSession session = request.getSession(false);
			
			// String name		= (String) session.getAttribute("NAME");
			// String dt_login	= (String) session.getAttribute("DT_LOGIN");
			session.invalidate();
			
			return "redirect:/";
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".logout()] " + e.getMessage(), e);
		}
		
		return viewPage;
	}
	
	/**
	 * @param request 요청서블릿
	 * @param response 응답서블릿
	 * @return String
	 *
	 * @since 2025-01-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value="/login.web")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		
		String viewPage = "error";
		
		try {
			HttpSession session = request.getSession(true);
			
			session.setAttribute("SEQ_MBR", "1");
			session.setAttribute("NAME", "홍길동");
			session.setAttribute("ID", "pluto");
			session.setAttribute("DT_LOGIN", Datetime.getNow("yyyy-MM-dd HH:mm:ss"));
			
			return "redirect:/";
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".login()] " + e.getMessage(), e);
		}

		return viewPage;
	}
	
}
