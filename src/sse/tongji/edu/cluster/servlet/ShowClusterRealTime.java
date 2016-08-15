package sse.tongji.edu.cluster.servlet;

import java.util.Calendar;
import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.json.JSONException;
import org.json.JSONObject;

@WebListener
public class ShowClusterRealTime implements ServletContextListener {

	private static final String CHANNEL = "test";

	private String fakeData(){
		Random r = new Random();
		double cpu = r.nextDouble();
		long l = Runtime.getRuntime().freeMemory();
		long netsent = (1<<30)+r.nextInt(1<<28);
		long netreceive = (1<<30)+r.nextInt(1<<28);
		JSONObject jo = new JSONObject();
		try {
			jo.accumulate("cpu", cpu);
			jo.accumulate("nts", netsent);
			jo.accumulate("ntr", netreceive);
			jo.accumulate("mem", l);
			jo.accumulate("createtime", Calendar.getInstance().getTimeInMillis());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("backend :: " + jo.toString());
		return jo.toString();
	}
	

	class HelloAppModule implements Runnable {
		public void run() {
			while (true) {
				try {
					// 睡眠时间
					Thread.sleep(1000);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				CometEngine engine = CometContext.getInstance().getEngine();
				
				// 开始发送
				engine.sendToAll(CHANNEL, fakeData());
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		CometContext cc = CometContext.getInstance();
		cc.registChannel(CHANNEL);// 注册应用的channel

		Thread helloAppModule = new Thread(new HelloAppModule(),
			"Sender App Module");
		// 是否启动
		helloAppModule.setDaemon(true);
		// 启动线程
		helloAppModule.start();

	}

}
