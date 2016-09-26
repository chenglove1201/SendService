package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Daocrud;
import com.entity.BeautyCard;
import com.entity.BeautyCardArray;
import com.entity.Machine;
import com.exception.CommonException;
import com.google.gson.Gson;

/**
 * 查询成功返回json字符串 失败：0该美容院尚未创建美容卡 -1查询美容卡失败
 * 
 * Servlet implementation class QueryBeautyCardServlet
 */
@WebServlet("/QueryBeautyCardServlet")
public class QueryBeautyCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryBeautyCardServlet() {
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
		String bind_beauty = request.getParameter("bind_beauty");
		Daocrud daocrud = new Daocrud();
		try {
			Gson gson = new Gson();
			List<BeautyCard> queryBeautyCard = daocrud.queryBeautyCard(bind_beauty);
			BeautyCardArray beautyCardArray = new BeautyCardArray();
			beautyCardArray.setBeautyCard(queryBeautyCard);
			if (queryBeautyCard.size() != 0) {
				String beautyCard = gson.toJson(beautyCardArray);
				response.getWriter().append(beautyCard);
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
