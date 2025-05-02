package com.tca.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.tca.entities.Teacher;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DisplayTeacher")
public class DisplayTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		
		final String DB_URL = "jdbc:postgresql://localhost/ajdb19";
		final String DB_USER= "postgres";
		final String DB_PWD = "redhat";
		
		String qry    = "";
		
		
		String ttsi =  request.getParameter("tsi"); 
		String sbtn =  request.getParameter("sbtn"); 
		
		if(sbtn == null) {
			qry   = "select * from teacher order by tsi";
		}
		else if(sbtn.equals("Refresh")) {
			qry   = "select * from teacher order by tsi";
		}
		else if(sbtn.equals("Search")) {
			qry = "select * from teacher where ttsi = " + ttsi;
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
		
	    request.setAttribute("listofteachers", L);
		
		RequestDispatcher rd = request.getRequestDispatcher("DisplayTeacher.jsp");
		rd.forward(request, response);
		
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
