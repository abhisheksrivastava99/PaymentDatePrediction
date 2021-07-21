package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class add extends HttpServlet{
	String cname=" ";
	String date=" ";
	String cno=" ";
	String note=" ";
	double invno=0;
	double invamt=0.0;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
			 cname=req.getParameter("cname");
			 date=req.getParameter("date");
			 cno=req.getParameter("cno");
			 note=req.getParameter("note");
			 invno = Double.parseDouble(req.getParameter("invno"));
			 invamt = Double.parseDouble(req.getParameter("invamt"));
			 try {
				updateDb(cname, date, cno, note, invno, invamt);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 res.sendRedirect("index.html");
	
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		 cname=req.getParameter("cname");
		 date=req.getParameter("date");
		 cno=req.getParameter("cno");
		 note=req.getParameter("note");
		 invno = Double.parseDouble(req.getParameter("invno"));
		 invamt = Double.parseDouble(req.getParameter("invamt"));
		 try {
				updateDb(cname, date, cno, note, invno, invamt);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 res.sendRedirect("index.html");
	
}
	
	public static void updateDb(String cname, String date, String cno, String note, Double invno, Double invamt)throws SQLException,ClassNotFoundException {
		String url="jdbc:mysql://localhost:3306/project";
		String uname="root";
		String pass="root";
		String query="INSERT INTO mytable(Customer_name,Customer_num,Invoice_num,Invoice_amount,Due_Date,Predicted_Payment_Date,Notes) VALUES ('"+cname+"','"+cno+"',"+invno+","+invamt+",'"+date+"',NULL,'"+note+"');";      
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
}




