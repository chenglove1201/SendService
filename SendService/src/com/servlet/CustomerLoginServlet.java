package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Daocrud;

/**
 * 0:密码错误 1:登录成功 -1：用户尚未注册或者不是此美容院绑定会员
 * 
 * Servlet implementation class CustomerLogin
 */
@WebServlet("/CustomerLogin")
public class CustomerLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String customer_name = request.getParameter("customer_name");
		String password = request.getParameter("password");
		String bind_beauty = request.getParameter("bind_beauty");
		Daocrud daocrud = new Daocrud();
		try {
			if (daocrud.customerLogin(customer_name, password, bind_beauty)) {
				response.getWriter().append("1");
			} else {
				response.getWriter().append("0");
			}
		} catch (Exception e) {
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
