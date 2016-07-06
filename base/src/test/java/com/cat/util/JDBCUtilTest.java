package com.cat.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Archimedes on 2016/7/6.
 */
public class JDBCUtilTest {
	@Test
	public void getConnection() throws Exception {
		System.out.println(JDBCUtil.getConnection());
		System.out.println(JDBCUtil.getConnection());
	}

	@Test
	public void find() throws Exception {
		Connection connection = JDBCUtil.getConnection();

		QueryHandle queryHandle = new QueryHandle();
		ResultHandle resultHandle = new ResultHandle();

		String sql = "SELECT * FROM user WHERE 1 = 1";

		ResultSet resultSet = queryHandle.execute(connection, sql, null);
		resultHandle.printResult(resultSet);

		sql = "SELECT COUNT(*) AS count FROM user WHERE id > 1";
		resultSet = queryHandle.execute(connection, sql);
		resultHandle.printResult(resultSet);

		connection.close();
	}

	@Test
	public void insert() throws Exception {
		Connection connection = JDBCUtil.getConnection();

		String sql = "INSERT INTO user(name, birthday) VALUES(?, ?)";

		PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pst.setObject(1, "axbycz");
		pst.setObject(2, new Date());
		pst.executeUpdate();
		ResultSet rs = pst.getGeneratedKeys();
		while (rs.next()) {
			System.out.println(rs.getObject(1));
		}

		connection.close();
	}

}
