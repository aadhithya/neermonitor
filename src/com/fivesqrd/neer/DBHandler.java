package com.fivesqrd.neer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHandler {
	public static Connection getConnection(){
		Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/neer_monitor", "fivesqrd", "neer");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
}
