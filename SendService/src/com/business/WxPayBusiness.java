package com.business;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import com.common.Configure;
import com.common.RandomStringGenerator;
import com.common.Signature;
import com.common.Util;
import com.dao.Daocrud;
import com.entity.Machine;
import com.entity.Merchant;
import com.entity.PayRequestData;
import com.exception.CommonException;

public class WxPayBusiness {
	private static String PREFIX_CDATA = "<![CDATA[";
	private static String SUFFIX_CDATA = "]]>";
	private HashMap<String, Object> map;
	private String sign;
	private String xml;
	private StringBuffer stringBuffer;
	private PayRequestData requestData;

	public String setSendData(String attach, String body, String total_fee, String details, String bind_beauty) {
		map = new HashMap<String, Object>();// 通过map集合进行签名
		stringBuffer = new StringBuffer();
		requestData = new PayRequestData();
		setMerchantInfo(bind_beauty);
		requestData.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
		requestData.setOut_trade_no(Util.getCurrTime() + "" + RandomStringGenerator.getRandomNumByLength(6));
		requestData.setProduct_id(RandomStringGenerator.getRandomNumByLength(20));
		requestData.setAttach(attach);
		requestData.setBody(body);
		requestData.setTotal_fee(total_fee);
		try {
			Class<PayRequestData> clazz = PayRequestData.class;
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				// 获得get方法
				Method method = pd.getReadMethod();
				if (!fieldName.equals("sign") && !fieldName.equals("detail")) {
					map.put(fieldName, method.invoke(requestData, null));
				} else if (fieldName.equals("detail")) {
					requestData.setDetail(details);
					map.put(fieldName, method.invoke(requestData, null));// 先将无CDATA标签的数据进行签名，再加入有CDADA标签的
					sign = Signature.getSign(map);
					requestData.setDetail(PREFIX_CDATA + details + SUFFIX_CDATA);
				} else {
					requestData.setSign(sign);// 设置签名
				}
				stringBuffer.append(
						"<" + fieldName + ">" + method.invoke(requestData, null) + "</" + field.getName() + ">");
			}
			xml = "<xml>" + stringBuffer.toString() + "</xml>";
			System.out.println(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	/**
	 * 设置商户收款信息
	 * 
	 * @param cpu_id
	 * @throws CommonException
	 */
	private void setMerchantInfo(String bind_beauty) {
		Daocrud daocrud = new Daocrud();
		List<Merchant> queryMerchant = daocrud.queryMerchant(bind_beauty);
		for (int i = 0; i < queryMerchant.size(); i++) {
			requestData.setAppid(queryMerchant.get(i).getAppid());
			requestData.setMch_id(queryMerchant.get(i).getMch_id());
			Configure.setKey(queryMerchant.get(i).getKey());
		}
	}
}
