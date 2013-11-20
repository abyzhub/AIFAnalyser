package com.amdocs.bss.tool.AifAnalyser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.aif.datamodel.Operation;
import com.amdocs.aif.repository.OperationStore;

public class AifAnalyserUtil {

	private String dbDriver = "oracle.jdbc.driver.OracleDriver";
	private String url = "";
	private String user = "sa";
	private String passwd = "sa";

	/**
	 * @param operationName
	 * @throws InvocationTargetException	
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws NoSuchFieldException
	 */
	public Operation analyseOperation(String operationName) throws Exception {
		OperationStore opStore = OperationStore.get();

		Class<?>[] innerClasses = OperationStore.class.getDeclaredClasses();
		Class<?> connFactoryCls = null;
		for (Class<?> innerClass : innerClasses) {
			if (innerClass.getName().equals(
					"com.amdocs.aif.repository.OperationStore$JdbcConFactory"))
				connFactoryCls = innerClass;
		}

			Class.forName(dbDriver);
			Connection jdbcCon = DriverManager.getConnection(url, user, passwd);
			Object connFactory = createObjectByName(connFactoryCls,
					new Class[] { Connection.class, String.class, String.class,
							String.class, String.class }, new Object[] {
							jdbcCon, dbDriver, url, user, passwd });
			// new JdbcConFactory(jdbcCon, dbDriver, url, user, passwd);

			Field m_conFactory = OperationStore.class
					.getDeclaredField("m_conFactory");
			m_conFactory.setAccessible(true);
			m_conFactory.set(opStore, connFactory);

		return opStore.queryOperationById(operationName);
	}

	public String[] readAllOperations() throws Exception {
		Class.forName(dbDriver);
		Connection jdbcCon = DriverManager.getConnection(url, user, passwd);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> operation = new ArrayList<String>();
		try {
			pstmt = jdbcCon.prepareStatement("select name from aif_operation");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				operation.add(rs.getString("name"));
			}
		} finally {
			jdbcCon.close();
			pstmt.close();
			rs.close();
		}

		return operation.toArray(new String[operation.size()]);

	}

/*	private void printMessage(int level, String message, String value) {
		StringBuilder msg = new StringBuilder("");
		for (int i = 0; i < level; i++) {
			msg.append("  ");
		}
		System.out.println(msg + "|");
		msg.append("--").append(message);
		if (value == null) {// Do Nothing
		} else if (!value.isEmpty())
			msg.append(" -> ").append(value);
		else
			msg.append(" -> ").append("N/A");

		System.out.println(msg.toString());

	}
*/
	private Object createObjectByName(Class<?> classDefinition,
			Class<?>[] parameterTypes, Object[] constructorArguments)
			throws Exception {
		// Class classDefinition = Class.forName(className);

		Constructor<?> constructor = classDefinition
				.getDeclaredConstructor(parameterTypes);

		constructor.setAccessible(true);

		return constructor.newInstance(constructorArguments);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
