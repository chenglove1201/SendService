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
 * Servlet implementation class TransferLocalTimesServlet
 */
@WebServlet("/TransferLocalTimesServlet")
public class TransferLocalTimesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransferLocalTimesServlet() {
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
		String beauty_local_times = request.getParameter("beauty_local_times");
		String customer_local_times = request.getParameter("customer_local_times");
		String customer_name = request.getParameter("customer_name");
		String beauty = request.getParameter("beauty");
		String cpu_id = request.getParameter("cpu_id");
		System.out.println(beauty_local_times+".."+customer_local_times+".."+customer_name+".."+beauty+".."+cpu_id);
		Daocrud daocrud = new Daocrud();
		try {
			String return_result = daocrud.updateConsumeRecords(beauty_local_times, customer_local_times, customer_name,
					beauty, cpu_id);
			System.out.println("success,"+return_result);
			response.getWriter().append("success,"+return_result);
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
