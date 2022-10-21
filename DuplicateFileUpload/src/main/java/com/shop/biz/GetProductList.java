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
import com.shop.vo.Product;


@WebServlet("/GetProductList")
public class GetProductList extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/GetProductList");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs=null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql="select code,description,to_char(regdate,'yyyy-mm-dd'),filename1,filename2,filename3 from product";
			stmt=conn.prepareStatement(sql);
			
			rs=stmt.executeQuery();
			
			ArrayList<Product> list=new ArrayList<Product>();
			Product vo=null;
			while(rs.next()) {
				vo=new Product();
				
				vo.setCode(rs.getString("code"));
				vo.setRegdate(rs.getString(3));
				vo.setDescription(rs.getString("description"));
				vo.setFilename1(rs.getString("filename1"));
				vo.setFilename2(rs.getString("filename2"));
				vo.setFilename3(rs.getString("filename3"));
				
				list.add(vo);
			}
			
			request.setAttribute("list", list);
			RequestDispatcher dispatcher=request.getRequestDispatcher("getProductList.jsp");
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnection.close(rs, stmt, conn);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

}
