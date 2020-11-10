package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PlainTextConnectionUtil {
	
	// url for jdbc -> jdbc:postgresql://host:port/dbName?currentSchema=schema
		// default schema is public, this can be changed
		private final String url = "jdbc:postgresql://naugledatabase.ct9itnphbbno.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=public";
		private final String usr = "nauglesteve";
		private final String pwd = "wasspord";

		// to make a singleton
		private static PlainTextConnectionUtil instance;

		private PlainTextConnectionUtil() {
			super();
			
		}

		public static PlainTextConnectionUtil getInstance() {
			if (instance == null) {
				instance = new PlainTextConnectionUtil();
			}
			return instance;
		}

		// to create a connection to the db
		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection(url, usr, pwd);
		}

}
