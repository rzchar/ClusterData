package sse.tongji.edu.cluster.datawashing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;

import sse.tongji.edu.cluster.mysqlconnection.ConnectionFactory;

public class InsertTestData {

	private static String fileName = System.getProperty("user.dir") + File.separator + "data" + File.separator
			+ "monitor_data_sample.txt";

	public static void main(String[] args) {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat h = new SimpleDateFormat("yyyyMMddHH");
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			Map<String, JSONArray> map = new HashMap<String, JSONArray>();

			while (br.ready()) {
				try {
					JSONObject jo = new JSONObject(br.readLine());
					String jobid = "test";
					String time = h.format(new Date(jo.getLong("CreateTime")));
					String machineId = jo.getString("MachineId");
					Double cpu = jo.getDouble("CPU");
					String key = jobid + ":" + machineId + ":" + time;
					if (!map.containsKey(key)) {
						map.put(key, new JSONArray());
					}
					jo.remove(machineId);
					map.get(key).put(jo);
				} catch (JSONException e) {
					e.printStackTrace();
					continue;
				}
			}
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement s = con.prepareStatement(
					"insert into alldata(jobid, machineid, starttime, endtime, jsoncontent) values(?,?,?,?,?)");
			for (String key : map.keySet()) {

				try {
					String[] keys = key.split(":");
					String jobid = keys[0];
					String machineid = keys[1];
					Date starttime = h.parse(keys[2]);
					Date endtime = f.parse(keys[2] + "5959");
					String value = map.get(key).toString();

					
					s.setString(1, jobid);
					s.setString(2, machineid);
					s.setTimestamp(3, new Timestamp(starttime.getTime()));
					s.setTimestamp(4, new Timestamp(endtime.getTime()));
					StringReader sr = new StringReader(value);
					s.setCharacterStream(5, sr);

					s.executeUpdate();
					// System.out.println(key + value);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			con.close();
			br.close();
			isr.close();
			fis.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
