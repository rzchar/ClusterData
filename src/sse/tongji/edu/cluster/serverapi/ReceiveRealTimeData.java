package sse.tongji.edu.cluster.serverapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Buffer;

import sse.tongji.edu.cluster.config.Params;

/**
 * Servlet implementation class AddRealTimeData
 */
@WebServlet("/servlet/AddRealTimeData")
public class ReceiveRealTimeData extends HttpServlet {
	private static Set<String> channels = new HashSet<>();
	
	private static final long serialVersionUID = 1L;
	
	static private CometEngine engine = CometContext.getInstance().getEngine();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceiveRealTimeData() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		PrintWriter writer = response.getWriter();
		String codedJSON = "";
		JSONObject resultJSON = new JSONObject();
		while (br.ready()) {
			codedJSON = br.readLine();
		}
		String decodeJSON = URLDecoder.decode(codedJSON, "UTF-8");
		try {
			if (decodeJSON == "") {
				resultJSON.accumulate("status", "fails");
				resultJSON.accumulate("reason", "string empty");
				writer.append(resultJSON.toString());
			} else {
				JSONObject inJson = this.toInnerJSON(decodeJSON);
				String channel = this.getMachineId(decodeJSON);
				if(!channels.contains(channel)){
					channels.add(channel);
					CometContext.getInstance().registChannel(channel);
				}
				engine.sendToAll(channel, inJson.toString());
			}
			//System.out.println("received :" + codedJSON);
		} catch (JSONException e) {
			e.printStackTrace(writer);
		} finally {
			br.close();
			isr.close();
			is.close();
			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected JSONObject toInnerJSON(String outerJSONString) {
		return toInnerJSON(outerJSONString, "createTime", "cpu", "memory",
			"networkSend", "networkReceive");
	}

	protected JSONObject toInnerJSON(String outerJSONString, String createTime,
		String cpu, String mem, String nts, String ntr) {
		try {
			JSONObject jojo = new JSONObject(outerJSONString);
			if(jojo.has("data")){
				jojo = jojo.getJSONObject("data");
			}
			return toInnerJSON(jojo, createTime, cpu, mem, nts, ntr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected JSONObject toInnerJSON(JSONObject jojo, String createTime,
		String cpu, String mem, String nts, String ntr) {
		try {
			JSONObject jo = new JSONObject();
			jo.accumulate(Params.CPUShortName, jojo.getDouble(cpu));
			jo.accumulate(Params.MemoryShortName, jojo.getDouble(mem));
			jo.accumulate(Params.NetworkReceiveShortName, jojo.getLong(ntr));
			jo.accumulate(Params.NetworkSendShortName, jojo.getLong(nts));
			jo.accumulate(Params.CreateTimeShortName, jojo.getLong(createTime));
			return jo;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String getMachineId(String jsonString){
		JSONObject jojo;
		try {
			jojo = new JSONObject(jsonString);
			return jojo.getString("machineid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sakura";
	}

}
