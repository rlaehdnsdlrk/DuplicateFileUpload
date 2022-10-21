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


@WebServlet("/AddOpinion")
public class AddOpinion extends HttpServlet {
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String opinion=request.getParameter("opinion");
		double point=Double.parseDouble(request.getParameter("point"));
		String code=request.getParameter("code");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		String id=(String) session.getAttribute("id");
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn=JDBCConnection.getConnection();
			
			sql = "select * from opinion where code= ? and id= ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, code);
			stmt.setString(2, id);
			int cnt1 = stmt.executeUpdate();
			
			
			System.out.println("result: " + cnt1);
			if(cnt1 > 0) {
				out.print("<script>alert('이미 존재 작성 하셨습니다.');location.href='GetProduct?code="+code+"';</script>");
				return;
			}else {
			
			sql="insert into opinion(code,point,opinion,id) values(?,?,?,?)";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, code);
			stmt.setDouble(2, point);
			stmt.setString(3, opinion);
			stmt.setString(4, id);
			
			
			
			stmt.executeUpdate();
			
			response.sendRedirect("GetProductList");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			JDBCConnection.close(stmt, conn);
		}
		
	}

}
