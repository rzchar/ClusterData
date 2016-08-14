package sse.tongji.edu.cluster.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sse.tongji.edu.cluster.dao.intf.IShowClusterHistoryDao;
import sse.tongji.edu.cluster.mysqlconnection.ConnectionFactory;

public class ShowClusterHistoryDaoRemoteOne extends ShowClusterHistoryDaoBase {
	static public void main(String[] args) {
		IShowClusterHistoryDao scd = new ShowClusterHistoryDaoRemoteOne();
		// System.out.println(scd.getData(27).toString());
		System.out.println(scd.getDataInfo().toString());
	}

	@Override
	protected Connection getConnection() throws SQLException {
		return ConnectionFactory.getConnection("remote1");
	}

	@Override
	protected String formatJsonContent(String stringFromDB) {
		String result = stringFromDB;
		String errorHead = "{\"data\":";
		String errorEnd = "}";
		if (result.startsWith(errorHead) && result.endsWith(errorEnd)) {
			result = result.substring(errorHead.length(),
				result.length() - errorEnd.length());
		}
		result = result.replaceAll("memory", "Memory");
		result = result.replaceAll("networkReceive", "Network Receive");
		result = result.replaceAll("networkSend", "Network Send");
		result = result.replaceAll("cpu", "CPU");
		return result;
	}

}
