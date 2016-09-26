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
 * 1:成功 0：密码错误 -1：失败
 * 
 * Servlet implementation class BeautyLoginServlet
 */
@WebServlet("/BeautyLoginServlet")
public class BeautyLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BeautyLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String beauty_name = request.getParameter("beauty_name");
		String beauty_password = request.getParameter("beauty_password");
		Daocrud daocrud = new Daocrud();
		try {
			if (daocrud.beautyLogin(beauty_name, beauty_password)) {
				response.getWriter().append("1");
			} else {
				response.getWriter().append("0");
			}
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
