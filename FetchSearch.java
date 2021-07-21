package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class FetchSearch
 */
@WebServlet(urlPatterns= {"/FetchSearch"},name="FetchSearch")
public class FetchSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String value = request.getParameter("value");
		
		
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		List<pojo> DataList = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","root");
			st =con.createStatement();
			sql="SELECT * FROM mytable WHERE Invoice_num LIKE '"+value+"%' ;";
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				pojo pojoObj = new pojo();
				pojoObj.setCName(rs.getString("Customer_name"));
				pojoObj.setCNum(rs.getString("Customer_num"));
				pojoObj.setDD(rs.getString("Due_Date"));
				pojoObj.setPPD(rs.getString("Predicted_Payment_Date"));
				pojoObj.setIAmt(rs.getDouble("Invoice_amount"));
				pojoObj.setINum(rs.getDouble("Invoice_num"));
				pojoObj.setNotes(rs.getString("Notes"));
				DataList.add(pojoObj);
				
			}
			String jsonData = getJSONStringifyFromObject(DataList);
			
			res.setContentType("application/json");
			
			PrintWriter out = res.getWriter();
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			out.print(jsonData);
			out.flush();
			con.close();
			st.close();
			}catch(Exception e) {
			e.printStackTrace();
			}
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private String getJSONStringifyFromObject(List<pojo> dl) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(dl);
		return json;
	}

}
