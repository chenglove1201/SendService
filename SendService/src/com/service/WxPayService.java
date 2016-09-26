package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SignatureException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.business.WxPayBusiness;
import com.common.Configure;
import com.common.LogRecord;
import com.common.Signature;
import com.common.XMLParser;
import com.exception.CommonException;
import com.exception.ResultCodeException;
import com.exception.ReturnCodeException;

public class WxPayService {

	/**
	 * 请求支付
	 * 
	 * @param attach
	 *            附加参数，原样返回
	 * @param body
	 *            商品详情
	 * @param total_fee
	 *            总价
	 * @param details
	 *            json格式字符串
	 * @param cpu_id
	 *            设备id号
	 * @return
	 * @throws ResultCodeException
	 * @throws ReturnCodeException
	 * @throws SignatureException
	 * @throws CommonException
	 */
	public String requestPay(String attach, String body, String total_fee, String details, String bind_beauty)
			throws SignatureException, ReturnCodeException, ResultCodeException {
		return httpRequest(Configure.getRequest_url(), "POST",
				new WxPayBusiness().setSendData(attach, body, total_fee, details, bind_beauty));
	}

	private String httpRequest(String requestUrl, String requestMethod, String outputStr)
			throws SignatureException, ReturnCodeException, ResultCodeException {
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		InputStream inputStream = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			LogRecord.writeLog(buffer.toString());
			System.out.println(buffer.toString());
			return AnalysisResponseData(buffer.toString());// 对返回数据进行解析
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return outputStr;
	}

	/**
	 * 返回数据的校验判断
	 * 
	 * @param string
	 * @throws ReturnCodeException
	 * @throws ResultCodeException
	 * @throws SignatureException
	 */
	private String AnalysisResponseData(String string)
			throws ReturnCodeException, ResultCodeException, SignatureException {
		try {
			Map<String, Object> mapFromXML = XMLParser.getMapFromXML(string);
			String return_code = (String) mapFromXML.get("return_code");
			if (return_code.equals("SUCCESS")) {// 先进行retur——code和result_code的判断
				String result_code = (String) mapFromXML.get("result_code");
				if (result_code.equals("SUCCESS")) {// 当return_code
													// 和result_code都为SUCCESS，再校验签名
					if (Signature.checkIsSignValidFromResponseString(string)) {// 判断sign是否正确
						return (String) mapFromXML.get("code_url");
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
		return string;
	}
}
