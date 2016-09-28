package sse.tongji.edu.cluster.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sse.tongji.edu.cluster.dataaccess.intf.IShowClusterHistoryDao;

public abstract class ShowClusterHistoryDaoBase implements IShowClusterHistoryDao {

	protected abstract Connection getConnection() throws SQLException;

	protected abstract String formatJsonContent(String stringFromDB);

	/*
	 * (non-Javadoc)
	 * 
	 * @see sse.tongji.edu.cluster.dao.IShowClusterHistoryDao#getDataInfo()
	 */
	@Override
	public JSONArray getDataInfo() {
		JSONArray ja = new JSONArray();
		try {
			Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select distinct id,jobid,machineid,starttime,endtime,jsoncontent from alldata;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				try {
					jo.accumulate("id", rs.getInt("id"));
					jo.accumulate("jobid", rs.getString("jobid"));
					jo.accumulate("machineid", rs.getString("machineid"));
					jo.accumulate("starttime", rs.getTimestamp("starttime"));
					jo.accumulate("endtime", rs.getTimestamp("endtime"));
					jo.accumulate("value",
						this.formatJsonContent(rs.getString("jsoncontent")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				ja.put(jo);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ja;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sse.tongji.edu.cluster.dao.IShowClusterHistoryDao#getData(int)
	 */
	@Override
	public JSONArray getData(int id) {
		JSONArray ja = new JSONArray();
		try {
			Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select jsoncontent from alldata where id=?;");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				try {
					ja = new JSONArray(
						this.formatJsonContent(rs.getString("jsoncontent")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ja;
	}
}
