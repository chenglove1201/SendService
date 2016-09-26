package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exception.CommonException;
import com.exception.ResultCodeException;
import com.exception.ReturnCodeException;
import com.service.WxPayService;

/**
 * 0:购买失败，请重新购买 -1:请确认此设备有没有绑定商户信息，或者绑定的商户信息有错，请核查 Servlet implementation class
 * WxPayServlet
 */
@WebServlet("/WxPayServlet")
public class WxPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WxPayServlet() {
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
		PrintWriter writer = response.getWriter();
		String attach = request.getParameter("attach");
		String body = request.getParameter("body");
		String total_fee = request.getParameter("total_fee");
		String details = request.getParameter("details");
		String bind_beauty = request.getParameter("bind_beauty");
		try {
			writer.append(new WxPayService().requestPay(attach, body, total_fee, details, bind_beauty));
		} catch (SignatureException e) {
			writer.append("0");
			e.printStackTrace();
		} catch (ReturnCodeException e) {
			writer.append("-1");
			e.printStackTrace();
		} catch (ResultCodeException e) {
			writer.append("0");
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
