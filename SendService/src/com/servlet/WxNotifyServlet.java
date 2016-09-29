package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SignatureException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.business.PrintWriterMap;
import com.common.LogRecord;
import com.common.Signature;
import com.common.XMLParser;
import com.dao.Daocrud;
import com.exception.CommonException;
import com.exception.ResultCodeException;
import com.exception.ReturnCodeException;

/**
 * 1:成功 0：失败
 * 
 * Servlet implementation class WxNotifyServlet
 */
@WebServlet("/WxNotifyServlet")
public class WxNotifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WxNotifyServlet() {
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
		String customer = null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String wxBackData = sb.toString();// 微信的返回数据
		LogRecord.writeLog(wxBackData);
		try {
			Map<String, Object> analysisResponseMap = analysisResponseData(wxBackData);
			if (analysisResponseMap != null) {
				String out_trade_no = (String) analysisResponseMap.get("out_trade_no");
				Daocrud daocrud = new Daocrud();
				// 判断是否存在该订单，如果不存在，便插入
				if (!daocrud.queryOutTradeNo(out_trade_no)) {
					String attach = (String) analysisResponseMap.get("attach") + "," + out_trade_no;
					String first_String = attach.split(",")[0];
					if (first_String.equals("beauty_buy")) {
						try {
							customer = attach.split(",")[1];
							// 插入微信支付成功返回数据
							if (daocrud.insertWxNotify(customer, analysisResponseMap)) {
								if (daocrud.addAdvanceCharge(attach)) {
									System.out.println("成功");
									response.getWriter().write("SUCCESS");
								} else {
									System.out.println("失败");
								}
							} else {
								PrintWriterMap.getPwInstance().getPw(customer).println("false");
							}
						} catch (CommonException e) {
							System.out.println("异常失败");
							e.printStackTrace();
						}
					} else {
						customer = attach.split(",")[0];
						// 插入微信支付成功返回数据
						if (daocrud.insertWxNotify(customer, analysisResponseMap)) {
							// 插入购买的卡
							if (daocrud.InsertBuyCardRecord(attach)) {
								PrintWriterMap.getPwInstance().getPw(customer).println("true");
								response.getWriter().write("SUCCESS");
							} else {
								PrintWriterMap.getPwInstance().getPw(customer).println("false");
							}
						} else {
							PrintWriterMap.getPwInstance().getPw(customer).println("false");
						}
					}
				} else {// 如果存在，通知微信支付不再回调
					response.getWriter().write("SUCCESS");
				}
			}
		} catch (SignatureException | ReturnCodeException | ResultCodeException e) {
			PrintWriterMap.getPwInstance().getPw(customer).println("false");
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

	/**
	 * 返回数据的校验判断
	 * 
	 * @param string
	 * @throws ReturnCodeException
	 * @throws ResultCodeException
	 * @throws SignatureException
	 */
	private Map<String, Object> analysisResponseData(String string)
			throws ReturnCodeException, ResultCodeException, SignatureException {
		try {
			Map<String, Object> mapFromXML = XMLParser.getMapFromXML(string);
			String return_code = (String) mapFromXML.get("return_code");
			if (return_code.equals("SUCCESS")) {// 先进行retur——code和result_code的判断
				String result_code = (String) mapFromXML.get("result_code");
				if (result_code.equals("SUCCESS")) {// 当return_code
													// 和result_code都为SUCCESS，再校验签名
					if (Signature.checkIsSignValidFromResponseString(string)) {// 判断sign是否正确
						// return (String) mapFromXML.get("attach") + "," +
						// (String) mapFromXML.get("out_trade_no");
						return mapFromXML;
					} else {// 签名错误，被篡改,重新购买
						throw new SignatureException("签名错误，被篡改,重新购买");
					}
				} else {// 不常见，先不处理了
					System.out.println(string);
					throw new ResultCodeException("result_code为fail，请重新购买");
				}
			} else {// 发生这个情况一般为appid或mchid或key错误,也是主要错误
				throw new ReturnCodeException((String) mapFromXML.get("return_msg"));
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
