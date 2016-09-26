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
 * 1:成功 2：失败 -1:无此设备
 * 
 * Servlet implementation class GetMchOrderServlet
 */
@WebServlet("/GetMchOrderServlet")
public class GetMchOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMchOrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String cpu_id = request.getParameter("cpu_id");
		Daocrud daocrud = new Daocrud();
		try {
			String mchOrder = daocrud.getMchOrder(cpu_id);
			if (mchOrder != null) {
				response.getWriter().append(mchOrder);
			} else {
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
