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
 * 校验设备是否绑定美容院，已绑定返回美容院名称，未绑定返回0 Servlet implementation class CheckMchServlet
 * -1:查询绑定美容院失败
 *
 */
@WebServlet("/CheckMchServlet")
public class CheckMchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckMchServlet() {
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
		String cpu_id = request.getParameter("cpu_id");
		String app_version = request.getParameter("app_version");
		String fireware_version = request.getParameter("fireware_version");
		Daocrud daocrud = new Daocrud();
		List<Machine> queryMachine;
		try {
			queryMachine = daocrud.queryMachine(cpu_id, app_version, fireware_version);
			if (queryMachine.size() != 0) {
				String advancePayment = daocrud.queryAdvancePayment(queryMachine.get(0).getBind_beauty());
				response.getWriter().append(queryMachine.get(0).getBind_beauty() + ","
						+ queryMachine.get(0).getCooperation_mode() + "," + advancePayment);
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
