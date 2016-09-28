package sse.tongji.edu.cluster.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

import sse.tongji.edu.cluster.config.Params;
import sse.tongji.edu.cluster.dataaccess.ShowClusterHistoryDaoBase;
import sse.tongji.edu.cluster.dataaccess.ShowClusterHistoryDaoRemoteOne;
import sse.tongji.edu.cluster.dataaccess.intf.IShowClusterHistoryDao;
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
		result = result.replaceAll("memory", Params.MemoryShortName);
		result = result.replaceAll("networkReceive", Params.NetworkReceiveShortName);
		result = result.replaceAll("networkSend", Params.NetworkSendShortName);
		result = result.replaceAll("cpu", Params.CPUShortName);
		result = result.replaceAll("createTime", Params.CreateTimeShortName);
		return result;
	}

}
