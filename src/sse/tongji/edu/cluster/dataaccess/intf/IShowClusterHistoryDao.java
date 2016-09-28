package sse.tongji.edu.cluster.dataaccess.intf;

import org.json.JSONArray;

public interface IShowClusterHistoryDao {

	JSONArray getDataInfo();

	JSONArray getData(int id);

}