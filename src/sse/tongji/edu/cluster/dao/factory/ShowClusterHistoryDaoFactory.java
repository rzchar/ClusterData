package sse.tongji.edu.cluster.dao.factory;

import sse.tongji.edu.cluster.dao.ShowClusterHistoryDaoLocal;
import sse.tongji.edu.cluster.dao.ShowClusterHistoryDaoRemoteOne;
import sse.tongji.edu.cluster.dao.intf.IShowClusterHistoryDao;

public class ShowClusterHistoryDaoFactory {
	static public IShowClusterHistoryDao getDao(String id){
		if(id.equals("remote1")){
			return new ShowClusterHistoryDaoRemoteOne();
		}
		return new ShowClusterHistoryDaoLocal();
	}
}
