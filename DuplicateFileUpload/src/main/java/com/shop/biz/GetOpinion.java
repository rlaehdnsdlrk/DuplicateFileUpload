package com.shop.biz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.jdbc.JDBCConnection;
import com.shop.vo.Opinion;


@WebServlet("/GetOpinion")
public class GetOpinion extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}


	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String code=request.getParameter("code");
		System.out.println("코드 출력 여부 확인 :"+code);
		try {
			conn=JDBCConnection.getConnection();
			String sql="select code,point,opinion,id,to_char(regdate,'yyyy-mm-dd') as regdate from opinion where code=? order by regdate desc";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, code);
			rs=stmt.executeQuery();
			
			ArrayList<Opinion> list=new ArrayList<Opinion>();
			Opinion vo=null;
			while(rs.next()) {
				vo=new Opinion();
				vo.setCode(rs.getString("code"));
				vo.setPoint(rs.getDouble("point"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setId(rs.getString("id"));
				vo.setOpinion(rs.getString("opinion"));
				
				list.add(vo);
			}
			
			request.setAttribute("list", list);
			request.setAttribute("filePath", "filePath");
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("getProduct.jsp");
			dispatcher.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			JDBCConnection.close(rs,stmt, conn);
		}
		
	}

}
