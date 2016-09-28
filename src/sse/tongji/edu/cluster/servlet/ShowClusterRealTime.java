package sse.tongji.edu.cluster.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.comet4j.core.CometContext;
import sse.tongji.edu.cluster.config.Params;

@WebListener
public class ShowClusterRealTime implements ServletContextListener {

	private static final String CHANNEL = Params.RealTimeChanel;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		CometContext cc = CometContext.getInstance();
		cc.registChannel(CHANNEL);// 注册应用的channel
	}

}
