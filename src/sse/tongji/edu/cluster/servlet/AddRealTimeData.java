package sse.tongji.edu.cluster.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Buffer;

import sse.tongji.edu.cluster.config.Params;

/**
 * Servlet implementation class AddRealTimeData
 */
@WebServlet("/servlet/AddRealTimeData")
public class AddRealTimeData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddRealTimeData() {
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
		String codedJson = "";
		while (br.ready()) {
			codedJson = br.readLine();
		}
		String decodeJson = URLDecoder.decode(codedJson, "UTF-8");
		System.out.println("received :" + codedJson);
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
		return toInnerJSON(outerJSONString, "createTime", "cpu", "memory", "networkSend", "networkReceive");
	}

	protected JSONObject toInnerJSON(String outerJSONString, String createTime, String cpu, String mem,
		String nts, String ntr) {
		try {
			JSONObject jojo = new JSONObject(outerJSONString);
			JSONObject jo = new JSONObject();
			jo.accumulate(Params.CPUShortName, jojo.getDouble(cpu));
			jo.accumulate(Params.MemoryShortName, jojo.getDouble(mem));
			jo.accumulate(Params.MemoryShortName, jojo.getLong(ntr));
			jo.accumulate(Params.MemoryShortName, jojo.getLong(nts));
			jo.accumulate(Params.MemoryShortName, jojo.getLong(mem));
			return jo;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
