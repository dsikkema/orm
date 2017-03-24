package org.dsikkema.orm.orm.db;

import java.sql.*;

public class DbConnection {
	
    private java.sql.Connection conn;
	
	public DbConnection() {
        try{ 
            Class.forName("com.mysql.jdbc.Driver"); 
            // TODO: store db conn details properly
            
            // no, this is not a real DB user and password.
            this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/orm?user=root&password=password&useUnicode=true&characterEncoding=UTF-8", "root", "password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public ResultSet doQuery(String query) {
		try {
			System.out.println(query);
			return this.conn.createStatement().executeQuery(query);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void doUpdateQuery(String query) {
		try {
			System.out.println(query);
			this.conn.createStatement().execute(query);
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
	}
}
