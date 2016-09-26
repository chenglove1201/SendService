package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Daocrud;
import com.entity.BuyCardRecord;
import com.entity.BuyCardRecordArray;
import com.exception.CommonException;
import com.google.gson.Gson;

/**
 * 0:未够卡 -1：查询会员购卡失败 json:成功返回的数据
 * 
 * Servlet implementation class GetCustomerBuyCardServlet
 */
@WebServlet("/GetCustomerBuyCardServlet")
public class GetCustomerBuyCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCustomerBuyCardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// // TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String customer_name = request.getParameter("customer_name");

		Daocrud daocrud = new Daocrud();
		try {
			List<BuyCardRecord> queryBuyCardRecord = daocrud.queryBuyCardRecord(customer_name);
			if (queryBuyCardRecord.size() != 0) {
				BuyCardRecordArray buyCardRecordArray = new BuyCardRecordArray();
				buyCardRecordArray.setBuyCardRecords(queryBuyCardRecord);
				Gson gson = new Gson();
				String json = gson.toJson(buyCardRecordArray);
				response.getWriter().append(json);
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
