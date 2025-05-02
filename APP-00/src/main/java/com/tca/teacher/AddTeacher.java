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
import java.sql.SQLException;


@WebServlet("/AddTeacher")
public class AddTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//RequestDispatcher rd = request.getRequestDispatcher("Add.jsp");
		//rd.forward(request, response);
		response.sendRedirect("AddTeacher.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement ps = null;
		
		final String DB_URL = "jdbc:postgresql://localhost/ajdb19";
		final String DB_USER   = "postgres";
		final String DB_PWD    = "redhat";
		
		
		int tsi = Integer.parseInt(request.getParameter("tsi"));
		String name = request.getParameter("name");
		String designation = request.getParameter("name");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String message="";
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(DB_URL,DB_USER, DB_PWD);
			
			con.setAutoCommit(false);
			
			ps = con.prepareStatement("INSERT INTO teacher VALUES (?,?,?)");
			ps.setInt(1, tsi);
			ps.setString(2, name);
			ps.setString(3, designation);
			ps.executeUpdate();
			
			con.commit();
			message ="Record is Saved Successfully for TSI :  " + tsi;
		}
		catch(Exception e){
			e.printStackTrace();
			
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				e2.printStackTrace();
			}
			message = "Unable to Save Record for TSI  " + tsi;
		}
		finally {
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
				message = "Unable to Save Record for TSI  " + tsi;
			}
		}
		request.setAttribute("msg", message);
		RequestDispatcher rd = request.getRequestDispatcher("AddTeacher.jsp");
		rd.forward(request, response);
	}

}
