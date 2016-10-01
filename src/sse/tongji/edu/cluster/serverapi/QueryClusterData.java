package sse.tongji.edu.cluster.serverapi;

import org.json.JSONArray;

public class QueryClusterData {
	static public JSONArray getNodesNames(){
		JSONArray ja = new JSONArray();
		ja.put("127.0.0.1");
		ja.put("127.0.0.2");
		ja.put("127.0.0.3");
		return ja;
	}
}
