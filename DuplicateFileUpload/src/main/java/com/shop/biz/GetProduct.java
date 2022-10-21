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
import javax.servlet.http.HttpSession;

import com.shop.jdbc.JDBCConnection;
import com.shop.vo.Opinion;
import com.shop.vo.Product;


@WebServlet("/GetProduct")
public class GetProduct extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/GetProduct");
		HttpSession session=request.getSession();
		String id=(String) session.getAttribute("id");
		String code=request.getParameter("code");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs=null;
		String sql = null;
		try {
			conn=JDBCConnection.getConnection();
			 sql="select code,description,to_char(regdate,'yyyy-mm-dd'),filename1,filename2,filename3 from product where code=?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, code);
			rs=stmt.executeQuery();
			
			Product vo=new Product();
			if(rs.next()) {
				vo.setCode(rs.getString("code"));
				vo.setRegdate(rs.getString(3));
				vo.setDescription(rs.getString("description"));
				vo.setFilename1(rs.getString("filename1"));
				vo.setFilename2(rs.getString("filename2"));
				vo.setFilename3(rs.getString("filename3"));
			}
			
			request.setAttribute("vo", vo);
			
			stmt.close();
			rs.close();
			
			sql="select code,point,opinion,id,to_char(regdate,'yyyy-mm-dd') as regdate from opinion where code=? order by regdate desc";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, code);
			rs=stmt.executeQuery();
			
			ArrayList<Opinion> opinionList=new ArrayList<Opinion>();
			Opinion opinionVo=null;
			while(rs.next()) {
				opinionVo=new Opinion();
				opinionVo.setCode(rs.getString("code"));
				opinionVo.setPoint(rs.getDouble("point"));
				opinionVo.setRegdate(rs.getString("regdate"));
				opinionVo.setId(rs.getString("id"));
				opinionVo.setOpinion(rs.getString("opinion"));
				
				opinionList.add(opinionVo);
			}
			
			request.setAttribute("opinionList",opinionList);
			stmt.close();
			rs.close();
			
			sql = "select avg(point) as avg from opinion where code =  ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,  code);
			rs = stmt.executeQuery();
			
			double opinionPoint = 0;
			if(rs.next()) {
				opinionPoint=rs.getDouble(1);
			}
			
			request.setAttribute("opinionPoint", opinionPoint);
			
			stmt.close();
			rs.close();
			
			sql = "select * from opinion where code= ? and id= ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, code);
			stmt.setString(2, id);
			int cnt1 = stmt.executeUpdate();
			
			request.setAttribute("cnt1", cnt1);
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("getProduct.jsp");
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnection.close(rs, stmt, conn);
		}
	}

}
