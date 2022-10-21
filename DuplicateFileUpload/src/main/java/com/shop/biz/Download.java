package com.shop.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.jdbc.JDBCConnection;

@WebServlet("/Download")
public class Download extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("/Download");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String file_repo = "C:\\shopImg";

		String code = request.getParameter("code");
		String fileName=request.getParameter("fileName");

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = JDBCConnection.getConnection();
			String sql = "select code,description,to_char(regdate,'yyyy-mm-dd'),filename1,filename2,filename3 from product where code=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, code);
			rs = stmt.executeQuery();
			String fn = null;
			if(rs.next()) {
				fn=rs.getString(fileName);
				
			}
			
			OutputStream out = response.getOutputStream(); 
			String downFile = file_repo + "\\" + fn;
			
			System.out.println(downFile);

			File f = new File(downFile); 

			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName=" + fn);
			

			
			FileInputStream in=new FileInputStream(f);
			byte[] buffer=new byte[1024*10];
			while(true) {
				int count=in.read(buffer);
				
				if(count==-1)
					break;
				
				out.write(buffer,0,count);
			}
						
			in.close();
			out.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, stmt, conn);
		}

		

	}

}
