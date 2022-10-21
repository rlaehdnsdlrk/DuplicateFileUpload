package com.shop.biz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.shop.jdbc.JDBCConnection;

@WebServlet("/AddProduct")
public class AddProduct extends HttpServlet {

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/AddProduct");
		request.setCharacterEncoding("UTF-8");

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			MultipartRequest multi = new MultipartRequest(request, "C:\\shopImg", 5 * 1024 * 1024, "UTF-8",
					new DefaultFileRenamePolicy());
			// 하나의 서블릿에 enctype="multipart/form-data"와 getParameter처리를 동시에 할 수 없다. (null값으로
			// 출력)
			// 따라서 cos라이브러리를 이용하여 넘어온 getParameter값을 직접 받아와서 처리.
			String code = multi.getParameter("code");
			String description = multi.getParameter("description");

			System.out.println(code + " " + description);
			
			Enumeration files=multi.getFileNames();
			// file의 이름을 Enumeration형으로 받아와서 처리.(Enumeration -> 열거형  - 콜렉션의 일종)
			String[] fileNameArray = new String[3];
			int index=2;
			while(files.hasMoreElements()) {
				
				String file=(String) files.nextElement();
				
				String fileName=multi.getFilesystemName(file);
				String origFileName=multi.getOriginalFileName(file);
				fileNameArray[index]=fileName;
				System.out.println("파일"+index);
		        System.out.println("fileName : " + fileName);
		        System.out.println("origFileName : " + origFileName);
		        index--;
			}
			System.out.println(fileNameArray[0]+" "+fileNameArray[1]+" "+fileNameArray[2]);
			conn = JDBCConnection.getConnection();
			String sql = "insert into product(code,description,regdate,filename1,filename2,filename3) values (?,?,sysdate,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, code);
			stmt.setString(2, description);
			stmt.setString(3, fileNameArray[0]);
			stmt.setString(4, fileNameArray[1]);
			stmt.setString(5, fileNameArray[2]);
			int cnt = stmt.executeUpdate();

			System.out.println(cnt + "개 상품추가");
			
			response.sendRedirect("GetProductList");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(stmt, conn);
		}

	}

}
