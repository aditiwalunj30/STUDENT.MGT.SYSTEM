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


@WebServlet("/DeleteTeacher")
public class DeleteTeacher extends HttpServlet {
	
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
			qry = "select * from teacher order by tsi";
		}
		else if(sbtn.equals("Refresh")) {
			qry = "select * from teacher where tsi";
		}
		else if(sbtn.equals("Search")) {
			qry = "select * from teacher where ttsi = " + ttsi;
		}
		
		List<Teacher> L = new ArrayList<>();
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int tsi 			= rs.getInt("tsi");
				String name 		= rs.getString("name");
				String designation 	= rs.getString("designation");
				
				Teacher T = new Teacher(tsi,name,designation);
				
				L.add(T);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			L = null;
		}
		finally {
			try {
				rs.close();
				con.close();
			}catch(Exception e) {
				e.printStackTrace();
				L = null;
			}
		}
		request.setAttribute("listofTeachers" , L);
		
		RequestDispatcher rd = request.getRequestDispatcher("DeleteTeacher.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
	    PrintWriter out = response.getWriter();
	    
	    String ttsi = request.getParameter("tsi");
	    
	    if(ttsi == null || ttsi.trim().isEmpty()) {
	    	 out.println("failed");
		     return;
	    }
	    
	    Connection con = null;
	    PreparedStatement ps = null;
	    
	    String qry = "DELETE FROM Teacher WHERE tsi = ?";
	    
	    try {
	    	Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
	        
	        ps = con.prepareStatement(qry);
	        ps.setInt(1, Integer.parseInt(ttsi));

	        int rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            out.println("success");
	        } else {
	            out.println("failed"); 
	    }
	}
	    catch (Exception e) {
	        e.printStackTrace();
	        out.println("failed");
	    }
	    finally {
	    	try {
	    		if(ps !=null)ps.close();
	    		if (con != null) con.close();
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	    	}
	    }
	}

}
