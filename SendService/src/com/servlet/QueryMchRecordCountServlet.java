package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Daocrud;
import com.exception.CommonException;

/**
 * -1:查询出错,正确返回查询到的数量
 * 
 * Servlet implementation class QueryMchRecordCount
 */
@WebServlet("/QueryMchRecordCount")
public class QueryMchRecordCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryMchRecordCountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String bind_beauty = request.getParameter("bind_beauty");
		Daocrud daocrud = new Daocrud();
		try {
			String recordCount = daocrud.queryRecordCount(bind_beauty);
			String advancePayment = daocrud.queryAdvancePayment(bind_beauty);
			System.out.println(recordCount+","+advancePayment);
			response.getWriter().append(recordCount+","+advancePayment);
		} catch (CommonException e) {
			response.getWriter().append("-1");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
