package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	static String bd = "vacantes";
	static String parametros="?useSSL=false&serverTimezone=UTC";
	static String user="root";
	static String password="trebujena";
	static String url="jdbc:mysql://localhost:3306/" +bd +parametros;
	static Connection conn = null;
	
	
	public DbConnection(){	
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	public Connection getConnection() {
		return conn;
	}
	
	public void disconnect(){
		if(conn != null) {
			System.err.println("Closing database:[" +conn+"]...");
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println("DB disconnect.");
		}
	}
}
