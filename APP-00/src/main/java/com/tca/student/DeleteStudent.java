package com.tca.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tca.entities.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DeleteStudent")
public class DeleteStudent extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	final String DB_URL = "jdbc:postgresql://localhost/ajdb19";
	final String DB_USER= "postgres";
	final String DB_PWD = "redhat";
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		
		
		String qry    = "";
		
		
		String trno =  request.getParameter("rno"); //101
		String sbtn =  request.getParameter("sbtn"); // Search
		
		if(sbtn==null)
		{
			qry    = "select * from student order by rno";
		}
		else if(sbtn.equals("Refresh"))
		{
			qry = "select * from student order by rno";
		}
		else if(sbtn.equals("Search"))
		{
			qry = "select * from student where rno = " + trno;
		}
		
		
		
		
		
		List<Student> L = new ArrayList<>(); // L -->[101,AAA,60],[102..],[103..]
		
		try
		{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery();
						
			while(rs.next())
			{
				int rno     = rs.getInt("rno");
				String name = rs.getString("name");
				double per  = rs.getDouble("per");
				
				Student S = new Student(rno,name, per);
				
				L.add(S);
				
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
		
		RequestDispatcher rd = request.getRequestDispatcher("Delete.jsp");
		rd.forward(request, response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    response.setContentType("text/plain");
	    PrintWriter out = response.getWriter();

	    String trno = request.getParameter("rno");

	    if (trno == null || trno.trim().isEmpty()) {
	        out.println("failed");
	        return;
	    }

	    Connection con = null;
	    PreparedStatement ps = null;
	    
	    String qry = "DELETE FROM student WHERE rno = ?";

	    try {
	        Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
	        
	        ps = con.prepareStatement(qry);
	        ps.setInt(1, Integer.parseInt(trno));

	        int rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            out.println("success");
	        } else {
	            out.println("failed");  // If no rows were deleted
	        }
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	        out.println("failed");
	    } 
	    finally {
	        try {
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

}






