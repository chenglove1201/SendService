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
 * 1:更新成功 0：更新失败
 * 
 * Servlet implementation class UpdateCardRemainTimesServlet
 */
@WebServlet("/UpdateCardRemainTimesServlet")
public class UpdateCardRemainTimesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCardRemainTimesServlet() {
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
		String card_id = request.getParameter("card_id");
		String remain_times = request.getParameter("remain_times");
		Daocrud daocrud = new Daocrud();
		try {
			if (daocrud.updateRemainTimes(card_id, remain_times)) {
				response.getWriter().append("1");
			}
		} catch (CommonException e) {
			response.getWriter().append("0");
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
