package sse.tongji.edu.cluster.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sse.tongji.edu.cluster.dataaccess.factory.ShowClusterHistoryDaoFactory;
import sse.tongji.edu.cluster.dataaccess.intf.IShowClusterHistoryDao;

/**
 * Servlet implementation class ShowClusterHistory
 */
@WebServlet("/servlet/ShowClusterHistory")
public class ShowClusterHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowClusterHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mod = request.getParameter("mod");
		if (mod == null) {
			response.sendError(400);
			return;
		}
		
		String dbSource = request.getParameter("dbsource");
		dbSource = dbSource == null ? "" : dbSource;
		IShowClusterHistoryDao scd = ShowClusterHistoryDaoFactory.getDao(dbSource);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (mod.equals("start")) {
			// out.append("start");
			out.append(scd.getDataInfo().toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
