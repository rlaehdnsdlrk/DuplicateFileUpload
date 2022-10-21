package com.shop.biz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shop.jdbc.JDBCConnection;


@WebServlet("/Login")
public class Login extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out=response.getWriter();
		
		String id=request.getParameter("id");
		String pw=request.getParameter("pw");
		
		HttpSession session;
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql="select id,pw,name from shopmember where id=? and pw=?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, pw);
			
			rs=stmt.executeQuery();
			
			if(rs.next()) {
				session=request.getSession();
				session.setAttribute("id", id);
			}else {
				out.print("<script>alert('존재하지 않는 회원이거나 ID,PW확인 해주세요.');location.href='login.jsp';</script>");
				return;
			}
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("GetProductList");
			dispatcher.forward(request, response);
			out.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			JDBCConnection.close(stmt, conn);
		}
	
		
	}

}
