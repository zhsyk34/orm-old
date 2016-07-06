package com.cat.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Archimedes on 2016/7/6.
 */
public class ResultHandle {

	public List<Map<String, Object>> toMap(ResultSet resultSet) throws SQLException {

		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

		int columnCount = resultSetMetaData.getColumnCount();

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		while (resultSet.next()) {
			map = new HashMap<>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = resultSetMetaData.getColumnLabel(i);
				if (null == columnName || 0 == columnName.length()) {
					columnName = resultSetMetaData.getColumnName(i);
				}
				map.put(columnName, resultSet.getObject(i));
			}
			list.add(map);
		}

		return list;
	}

	public void printResult(ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> list = this.toMap(resultSet);
		list.forEach(map -> {
			map.forEach((columnName, value) -> System.out.print(columnName + " : " + value + ",\t"));
			System.out.println();
		});
	}
}
