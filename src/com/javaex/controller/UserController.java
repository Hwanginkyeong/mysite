package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/user");
		
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {
			System.out.println("/user>joinForm");
			
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		
		
		
		}else if("join".equals(action)) {
			System.out.println("/user>join");
			
			//파라미터값 가져오기 
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//파라미터 --> Vo로 만들기(묶어주기)
			UserVo userVo = new UserVo(id, password, name, gender);
			//System.out.println(userVo);
			
			//UserDao 저장하기 (회원가입하기)
			UserDao userDao = new UserDao();
			int count = userDao.insert(userVo);
			
			//포워드 
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
			
		}else if("loginForm".equals(action)) {
			System.out.println("/user>loginForm");
			
			//포워드 
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		
		
		}else if("login".equals(action)) {
			System.out.println("/user>login");
			
			//파라미터 꺼내기 
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id,password); //이거 만들고 다오에 이런애(getUser)를 만들면 됨 
			//System.out.println(userVo);
			
			
			if(authVo == null) {
				System.out.println("로그인 실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
				
			}else { //로그인 성공 
				System.out.println("로그인 성공");
				
				HttpSession session = request.getSession(); //문법 
				session.setAttribute("authUser", authVo); //문법
				
				WebUtil.redirect(request, response, "/mysite/main"); //포워드로 안하는 이유 생각해보기 
				
			}
		
		}else if("logout".equals(action)) {
			System.out.println("/user>logout");
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser"); //문법 
			session.invalidate(); //문법
			
			WebUtil.redirect(request, response, "/mysite/main");
			
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
