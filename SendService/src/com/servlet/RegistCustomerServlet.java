package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Daocrud;
import com.entity.Machine;
import com.exception.CommonException;

/**
 * 客户注册，注册成功：返回1 失败：0用户名重复，请重新注册，-2用户注册失败，请重新注册
 * 
 * Servlet implementation class CustomerRegistServlet
 */
@WebServlet("/CustomerRegistServlet")
public class RegistCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistCustomerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String customer_name = request.getParameter("customer_name");
		String md5_password = request.getParameter("password");
		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		String bind_beauty = request.getParameter("bind_beauty");

		Daocrud daocrud = new Daocrud();
		try {
			daocrud.InsertCustomer(customer_name, md5_password, age, sex, bind_beauty);
			response.getWriter().append("1");
		} catch (CommonException e) {
			if (e.getMessage().equals("此用户已存在，请重新注册")) {
				response.getWriter().append("0");
			} else {
				response.getWriter().append("-2");
			}
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
