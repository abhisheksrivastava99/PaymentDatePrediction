package com.higradius;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class edit extends HttpServlet{
	String note=" ";
	double invamt=0.0;
	double invno =0.0;
	long invn=0l;
	String query="";
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
			 note=req.getParameter("edinp2");
			 invamt = Double.parseDouble(req.getParameter("edinp1"));
			 invno = Double.parseDouble(req.getParameter("edinp3"));
			 invn = (long)invno;
			 System.out.println("Note: "+note+" \nInvoice Amount: "+invamt+" \nInvoice Number: "+invn);
			 query = "UPDATE mytable SET Invoice_amount="+invamt+", Notes= '"+note+"' WHERE Invoice_num = "+invn+";";
			 try {
				updateDb(query);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 res.sendRedirect("index.html");
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		 note=req.getParameter("edinp2");
		 invamt = Double.parseDouble(req.getParameter("edinp1"));
		 invno = Double.parseDouble(req.getParameter("edinp3"));
		 invn = (long)invno;
		 System.out.println("Note: "+note+" \nInvoice Amount: "+invamt+" \nInvoice Number: "+invn);
		 query = "UPDATE mytable SET Invoice_amount="+invamt+", Notes= '"+note+"' WHERE Invoice_num = "+invn+";";
		 try {
			updateDb(query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 res.sendRedirect("index.html");
}
	public static void updateDb(String query)throws SQLException,ClassNotFoundException{
		String url="jdbc:mysql://localhost:3306/project";
		String uname="root";
		String pass="root";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
}
