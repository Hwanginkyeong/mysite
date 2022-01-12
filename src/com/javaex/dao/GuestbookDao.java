package com.javaex.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	
		
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
		
		
		//List 
		public List<GuestbookVo> getList() {

				List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
	
				getConnection();
			
				try {
					
					// 3. SQL문 준비 / 바인딩 / 실행 
					//문자열
					String query ="";
					query += " select   no, ";
					query += "			name, ";
					query += "			password, ";
					query += "			content, ";
					query += " 			to_char(reg_Date, 'YYYY-mm-dd hh:mi:ss') reg_date ";
					query += " from guestbook ";
					query += " order by reg_date desc ";
					
					//쿼리
					pstmt = conn.prepareStatement(query);
					
					//바인딩 물음표 없어서 X 
					
					//실행
					rs = pstmt.executeQuery();     //리턴값이 달라서
						
					// 4.결과처리	
					while(rs.next()) {
						int no = rs.getInt("no");
						String name = rs.getString("name");  //최종 컬럼명 별명이 있다면 별명 
						String password = rs.getString("password");
						String content = rs.getString("content");
						String regDate = rs.getString("reg_date");
						
						//vo로 묶어주기 최종적으로 리스트로 만들기 위해 하는 중 
						GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate);
						guestbookList.add(guestbookVo);
					}
			
			
			
			
			
			} catch (SQLException e) { 
					System.out.println("error:" + e);
			} 
			
			return guestbookList;
				
		}//List 끝 
		
		//insert
		public int insert(GuestbookVo vo) {
			
			int count = 0;
			this.getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += "insert into guestbook ";
				query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate)";

				
				pstmt = conn.prepareStatement(query);

				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getContent());

				count = pstmt.executeUpdate();

				// 4.결과처리
				System.out.println(count + "건 등록");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			
			this.close();
			return count;
		}
	

		//delete
		public int delete(GuestbookVo gusetbookvo) {
			
				int count = 0;
				
				getConnection();
		
				
				try {
					// 3. SQL문 준비 / 바인딩 / 실행
					String query = ""; // 쿼리문 문자열만들기, ? 주의
					query += " delete from guestbook ";
					query += " where no = ?  ";
					query += " and password = ?  ";
					
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기
		
					pstmt.setInt(1, gusetbookvo.getNo());// ?(물음표) 중 1번째, 순서중요
					pstmt.setString(2, gusetbookvo.getPassword());
		
					count = pstmt.executeUpdate(); // 쿼리문 실행
		
					// 4.결과처리
					System.out.println(count + "건 삭제되었습니다.");
		
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
		
				close();
				return count;
		}
		
		//select
		public GuestbookVo select(int index) {
			
			this.getConnection();
			GuestbookVo guestbookVo = new GuestbookVo();
			
			// 3. SQL문 준비 / 바인딩 / 실행
			
			try {	
				//문자열준비
				String query = "";
				query += " SELECT no, ";
				query += " 		  name, ";
				query += " 		  password, ";
				query += " 		  content, ";
				query += " 		  to_char(reg_date, 'yyyy-mm-dd hh24:mi:ss') reg_date ";
				query += " FROM guestbook ";
				query += " WHERE no like ? ";
				
				//쿼리문 만들기
				pstmt = conn.prepareStatement(query);
				
				//바인딩
				pstmt.setInt(1, index );
							
				//실행
				rs = pstmt.executeQuery();
					
			// 4.결과처리
				
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					guestbookVo.setNo(no);
					guestbookVo.setName(name);
					guestbookVo.setPassword(password);
					guestbookVo.setContent(content);
					guestbookVo.setRegDate(regDate);
				}
				
			} catch (SQLException e) {
			System.out.println("error:" + e);
			}
			
			this.close();
					
			return guestbookVo;
		
	}

}
