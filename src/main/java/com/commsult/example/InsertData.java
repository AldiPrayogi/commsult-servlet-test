package com.commsult.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.*;
import com.google.gson.*;

/**
 * Servlet implementation class InsertData
 */
@WebServlet("/InsertData")
public class InsertData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseConnection dbc = new DatabaseConnection();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertData() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
    	JSONArray json = new JSONArray();
    	java.sql.ResultSetMetaData rsmd = resultSet.getMetaData();
    	while(resultSet.next()) {
    	  int numColumns = rsmd.getColumnCount();
    	  JSONObject obj = new JSONObject();
    	  for (int i=1; i<=numColumns; i++) {
    	    String column_name = rsmd.getColumnName(i);
    	    obj.put(column_name, resultSet.getObject(column_name));
    	  }
    	  json.put(obj);
    	}
    	return json;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		try {
			dbc.initializeDatabase();
			ResultSet rs = dbc.executeQuery("select * from user");
			
			JSONArray returnedArray = convertToJSON(rs);
			out.print(returnedArray);
			out.flush();
			
			dbc.closeCon();
		} catch (Exception e) {
			out.print(e);
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			Connection con = DatabaseConnection.initialize();
			User newUser = new Gson().fromJson(request.getReader(), User.class);
			PreparedStatement st = con.prepareStatement("insert into user values(?, ?)");
			st.setInt(1, newUser.getId());
			st.setString(2, newUser.getName());
//			dbc.executeQuery("insert into user values('"+newUser.getId()+", '"+newUser.getName()+"')");
			st.executeUpdate();
			st.close();
			con.close();
//			
			out.println("<html><body><b>"+newUser+""
                    + "</b></body></html>");	
		} catch(Exception e) {
			e.printStackTrace();
			out.println("<html><body><b>"+e+""
                    + "</b></body></html>");
		}
	}

}
