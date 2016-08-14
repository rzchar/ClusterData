package sse.tongji.edu.cluster.dao;

import java.sql.Connection;
import java.sql.SQLException;

import sse.tongji.edu.cluster.dao.intf.IShowClusterHistoryDao;
import sse.tongji.edu.cluster.mysqlconnection.ConnectionFactory;

public class ShowClusterHistoryDaoLocal extends ShowClusterHistoryDaoBase {
	static public void main(String[] args) {
		IShowClusterHistoryDao scd = new ShowClusterHistoryDaoLocal();
		//System.out.println(scd.getData(27).toString());
		System.out.println(scd.getDataInfo().toString());
	}

	@Override
	protected Connection getConnection() throws SQLException {
		return ConnectionFactory.getConnection();
	}

	@Override
	protected String formatJsonContent(String stringFromDB) {
		return stringFromDB;
	}
}
