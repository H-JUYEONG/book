package com.javaex.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.author.AuthorVo;

public class BookSelectALL {

	public static void main(String[] args) {

		List<Book2Vo> bookList = new ArrayList<Book2Vo>();

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문 준비(insert문을 자바의 문자열로 만든다.)
			String query = "";
			query += " select b.book_id ";
			query += " 	  , b.title ";
			query += " 	  , b.pubs ";
			query += " 	  , b.pub_date ";
			query += " 	  , b.author_id ";
			query += " 	  , a.author_name ";
			query += " 	  , a.author_desc ";
			query += " from book b ";
			query += " left join author a ";
			query += " on b.author_id = a.author_id ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			rs.next();
			int bId = rs.getInt("book_id");
			String title = rs.getString("title");
			String pubs = rs.getString("pubs");
			String pdate = rs.getString("pub_date");
			int aId = rs.getInt("b.author_id");
			String name = rs.getString("author_name");
			String desc = rs.getString("author_desc");

			Book2Vo book2Vo = new Book2Vo(bId, title, pubs, pdate, aId, name, desc);
			bookList.add(book2Vo);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

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

		for (int i = 0; i < bookList.size(); i++) {

			Book2Vo bVo = bookList.get(i);

			System.out.print(bVo.getBookId() + ".  ");
			System.out.print(bVo.getTitle() + "\t");
			System.out.print(bVo.getPubs() + "\t");
			System.out.print(bVo.getPubDate() + "\t");
			System.out.print(bVo.getAuthorId() + "\t");
			System.out.print(bVo.getName() + "\t");
			System.out.println(bVo.getDesc());
		}

	}

}
