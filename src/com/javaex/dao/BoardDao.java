package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
		// 0. import java.sql.*;
		private Connection conn = null; 
		private PreparedStatement pstmt = null; 
		private ResultSet rs = null;
		
		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url =  "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb"; 
		private String pw = "webdb";
		
		//연결 
		private void getConnection() {
			
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
				// System.out.println("접속성공");

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		//닫기
		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		public List<BoardVo> getList() {
			
				List<BoardVo> boardList = new ArrayList<BoardVo>();
				
				getConnection();
				
				try {
					
					// 3. SQL문 준비 / 바인딩 / 실행 
					//문자열
					String query ="";
					query += " select   no, ";
					query += "			title, ";
					query += "			content, ";
					query += "  		to_char(reg_date, 'yyyy-mm-dd') regDate, ";
					query += "			user_no ";
					query += " from board ";
					
					//쿼리
					pstmt = conn.prepareStatement(query);
					
					//바인딩 물음표 없어서 X 
					
					//실행
					rs = pstmt.executeQuery();     //리턴값이 달라서
						
					// 4.결과처리	
					while(rs.next()) {
						int no = rs.getInt("no");
						String title = rs.getString("title");  //최종 컬럼명 별명이 있다면 별명 
						String content = rs.getString("content");
						int hit = rs.getInt("hit");
						String regDate = rs.getString("regDate");
						int userNo = rs.getInt("userNo");
						
						//vo로 묶어주기 최종적으로 리스트로 만들기 위해 하는 중 
						BoardVo boardVo = new BoardVo(no, title, content,  hit, regDate, userNo);
						boardList.add(boardVo);
					} 
					
			}catch (SQLException e) { 
						System.out.println("error:" + e);
			} 
			
				
			return boardList;
			
		}
		
		
		
		
		

}
