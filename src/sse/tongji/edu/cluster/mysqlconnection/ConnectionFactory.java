package sse.tongji.edu.cluster.mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionFactory {
	// 驱动程序名
	static private String driver = "com.mysql.jdbc.Driver";

	static private class ConnectionParams {

		public ConnectionParams(String url, String user,
			String password) {
			this.url = url;
			this.user = user;
			this.password = password;
		}

		private String url;
		private String user;
		private String password;

		public String getUrl() {
			return url;
		}

		public String getUser() {
			return user;
		}

		public String getPassword() {
			return password;
		}
	}

	static private Map<String, ConnectionParams> paramMap = new HashMap<String, ConnectionParams>();

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		paramMap.put("default",
			new ConnectionParams(
				"jdbc:mysql://localhost:3306/clusterdata",
				"datadrawer", "datadrawer"));
		paramMap.put("remote1",
			new ConnectionParams(
				"jdbc:mysql://10.60.45.101:3306/clusterdata",
				"root", "root"));

	}

	static public Connection getConnection() throws SQLException {
		ConnectionParams defaultParam = paramMap.get("default");
		return DriverManager.getConnection(defaultParam.url,
			defaultParam.user, defaultParam.password);
	}

	static public Connection getConnection(String url, String user,
		String password) throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	static public Connection getConnection(String id)
		throws SQLException {
		ConnectionParams param = paramMap.get(id);
		return DriverManager.getConnection(param.getUrl(),
			param.getUser(), param.getPassword());
	}
}
