package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/guest");
		
		String action = request.getParameter("action");
		
		if("deleteForm".equals(action)) {
			System.out.println("/guestbook>deleteForm");
			
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
	
		}else if("addList".equals(action)) {
			System.out.println("/guestbook>addList");
			
			//파라미터값 가져오기 
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//파라미터 -->Vo로 만들기 
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
			System.out.println(guestbookVo);
			
			//GuestDao 저장하기 
			GuestbookDao guestbookDao = new GuestbookDao();
			int count = guestbookDao.insert(guestbookVo);
			
			
			//포워드 
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
