package sse.tongji.edu.cluster.serverapi;

import org.json.JSONArray;

public class QueryClusterData {
	static public JSONArray getNodesNames() {
		JSONArray ja = new JSONArray();
		for (int i = 1; i < 20; i++) {
			ja.put("127.0.0." + i);
		}
		return ja;
	}
}
