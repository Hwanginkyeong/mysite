package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	
   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board");
		
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		
		if("writeForm".equals(action)) {
			System.out.println("/board>writeForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/borad/writeForm.jsp");
			
		}else if("write".equals(action)) {
			System.out.println("/board>write");
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
