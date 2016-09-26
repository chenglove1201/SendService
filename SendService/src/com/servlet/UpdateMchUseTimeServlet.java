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
 * Servlet implementation class UpdateMchUseTimeServlet
 */
@WebServlet("/UpdateMchUseTimeServlet")
public class UpdateMchUseTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateMchUseTimeServlet() {
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String consume_object = request.getParameter("consume_object");
		String cpu_id = request.getParameter("cpu_id");
		String advance_payment = request.getParameter("advance_payment");
		Daocrud daocrud = new Daocrud();
		try {
			if (daocrud.updateConsumeOrder2(consume_object, cpu_id, advance_payment)) {
				response.getWriter().append("1");
			} else {// 返回false表示服务费模式中，剩余的服务费已不足下次使用
				response.getWriter().append("-1");
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
