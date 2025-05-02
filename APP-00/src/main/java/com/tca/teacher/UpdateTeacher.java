package com.tca.teacher;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tca.entities.Teacher;


@WebServlet("/UpdateTeacher")
public class UpdateTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	final String DB_URL = "jdbc:postgresql://localhost/ajdb19";
	final String DB_USER= "postgres";
	final String DB_PWD = "redhat";  
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		
		
		String qry    = "";
		
		String ttsi = request.getParameter("tsi");
		String sbtn = request.getParameter("sbtn");
		
		if(sbtn==null) {
			qry  = "select * from teacher order by tsi";
		}
		else if(sbtn.equals("Refresh")){
			qry = "select * from teacher order by tsi";
		}
		else if(sbtn.equals("Search")) {
			qry ="select * from teacher where tsi =  " + ttsi + "order by tsi";
		}
		List<Teacher> L = new ArrayList<>();
		try
		{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery();
						
			while(rs.next())
			{
				int tsi 			= rs.getInt("tsi");
				String name 		= rs.getString("name");
				String designation 	= rs.getString("designation");
				
				Teacher T = new Teacher(tsi,name,designation);
				
				L.add(T);
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			L = null;
		}
		finally
		{
			try
			{
				rs.close();
				con.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				L = null;
			}
		}
		request.setAttribute("listofstudents", L);
		
		RequestDispatcher rd = request.getRequestDispatcher("UpdateTeacher.jsp");
		rd.forward(request, response);
		
	}
	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/plain");
		 PrintWriter out = response.getWriter();
		
		 String ttsi = request.getParameter("ttsi");
		 String tname = request.getParameter("name");
		 String tdesignation = request.getParameter("designation");
		 
		 System.out.println("Received Teaching Staff Id :  " + ttsi);
		 System.out.println("Received Name: " + tname);
		 System.out.println("Received Designation: " + tdesignation);
		 
		 if (ttsi == null || tname == null || tdesignation == null || ttsi.trim().isEmpty() || tname.trim().isEmpty() || tdesignation.trim().isEmpty()) { 
			   out.println("failed");
			   System.out.println("Error : Missing parameters");
			   return;
			}
		 	Connection con = null;
		 	PreparedStatement ps = null;
		 	
		 	String updateQry = "Update teacher SET name = ? , designation = ? WHERE tsi = ?";
		 	try {
		 		Class.forName("org.postgresal.Driver");
		 		con = DriverManager.getConnection("jdbc:postgresql://localhost/ajdb19\", \"postgres\", \"redhat");
		 		
		 		ps = con.prepareStatement(updateQry);
		 		ps.setInt(1, Integer.parseInt(ttsi));
		 		ps.setString(2, tname);
		 		ps.setString(3,tdesignation);
		 		
		 		int rowsAffected = ps.executeUpdate();
		 		
		 		if (rowsAffected > 0) {
		 			System.out.println("Update successfull for Teaching Staff Id :  "+ ttsi);
		 			out.println("success");
		 		}else {
		 			System.out.println("Error : Update failed for Teaching Staff Id   " + ttsi);
		 			out.println("failed");
		 		}
		 	}
		 	catch(Exception e) {
		 		e.printStackTrace();
		 		out.println("failed");
		 	}
		 	finally {
		 		try {
		 			if(ps != null)ps.close();
		 			if(ps != null)con.close();
		 		}
		 		catch(Exception e) {
		 			e.printStackTrace();
		 		}
		 	}
		 }

}
