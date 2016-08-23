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

import sse.tongji.edu.cluster.config.Params;

@WebListener
public class ShowClusterRealTime implements ServletContextListener {

	private static final String CHANNEL = Params.RealTimeChanel;

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
				engine.sendToAll(CHANNEL, "");
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
	}

}
