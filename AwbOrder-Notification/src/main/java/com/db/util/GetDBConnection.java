/**
 * 
 */
package com.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;


/*import oracle.jdbc.driver.OracleDriver;*/



public class GetDBConnection {
	private static Logger LOGGER = Logger.getLogger(GetDBConnection.class);

	public String DBURL;
	public String DBUsername;
	public String DBPassword;
	public String dbType;

	/* This Function loads the properties from batch.properties Property file */
	public void loadProperties(String dbType) {

		if (dbType.equals(Constant.Database.OMS)) {
			this.DBURL = PropertiesReader.getProperty("oms.db.jdbc-url");
			this.DBUsername = PropertiesReader.getProperty("oms.db.user");
			this.DBPassword = PropertiesReader.getProperty("oms.db.password");
			LOGGER.info("Loaded Database Connection  Properties" + Constant.Database.OMS);
		}

	}

	/* This Function creates the JDBC Connection */
	public Connection getConnection() throws Exception {
		Connection jdbcConnection = null;
		if (this.DBURL != null && this.DBUsername != null && this.DBUsername != null && !this.DBURL.trim().equals("")
				&& !this.DBUsername.trim().equals("") && !this.DBUsername.trim().equals("")) {

			try {

				/* Creation of JDBC Connection */
				LOGGER.info("Starting connection");
				//DriverManager.registerDriver(new OracleDriver());
				Class.forName("com.mysql.jdbc.Driver");
				jdbcConnection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
				LOGGER.info(" Connected driver Version:" + jdbcConnection.getMetaData().getDriverVersion());
				/* Initializing the DAOs */

			} catch (SQLException e) {

				LOGGER.error("SQLException at getConnection " + e);
				throw new Exception("Exception occured while opening the db connection: " + e.getMessage());

			}

		} else {
			LOGGER.error("Exception occured while reading the db properties");
			throw new Exception("Exception occured while reading the db properties");

		}

		return jdbcConnection;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException exception) {

				LOGGER.error("Exception while closing the DB Connection: " + exception);

			}
		}
	}
}
