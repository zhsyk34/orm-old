package com.cat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by Archimedes on 2016/7/6.
 */
public class JDBCUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUtil.class);

	private static final String SETTINGS = "jdbc.properties";

	private static boolean isLoad = false;

	private static Properties properties = null;

	static {
		LOGGER.info("loading class and init the settings");
		if (!isLoad) {
			InputStream inputStream = JDBCUtil.class.getResourceAsStream("/" + SETTINGS);

			if (inputStream == null) {
				LOGGER.info("can not load resources : " + SETTINGS);
				throw new RuntimeException("can not read the setting resources");
			}
			try {
				properties = new Properties();
				properties.load(inputStream);

				Class.forName(properties.getProperty("jdbc.driver"));
				isLoad = true;
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("read resources error");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("load class error");
			}
		}
	}

	private JDBCUtil() {
	}

	public static Connection getConnection() {
		Connection connection = null;

		String url = properties.getProperty("jdbc.url");
		String user = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			LOGGER.info("get connection error");
			e.printStackTrace();
		}
		return connection;
	}

	public static void close(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
	}

	public static void close(Statement statement) throws SQLException {
		if (statement != null) {
			statement.close();
		}
	}

	public static void close(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	public static void rollback(Connection connection) throws SQLException {
		if (connection != null) {
			connection.rollback();
		}
	}

	public static void rollbackAndClose(Connection connection) throws SQLException {
		if (connection != null) {
			try {
				connection.rollback();
			} finally {
				connection.close();
			}
		}
	}

	public static void commitAndClose(Connection connection) throws SQLException {
		if (connection != null) {
			try {
				connection.commit();
			} finally {
				connection.close();
			}
		}
	}

}
