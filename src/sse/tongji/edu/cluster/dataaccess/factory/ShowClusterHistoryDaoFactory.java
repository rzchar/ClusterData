package sse.tongji.edu.cluster.dataaccess.factory;

import sse.tongji.edu.cluster.dataaccess.ShowClusterHistoryDaoLocal;
import sse.tongji.edu.cluster.dataaccess.ShowClusterHistoryDaoRemoteOne;
import sse.tongji.edu.cluster.dataaccess.intf.IShowClusterHistoryDao;

public class ShowClusterHistoryDaoFactory {
	static public IShowClusterHistoryDao getDao(String id){
		if(id.equals("remote1")){
			return new ShowClusterHistoryDaoRemoteOne();
		}
		return new ShowClusterHistoryDaoLocal();
	}
}
