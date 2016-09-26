package com.common;

/**
 * User: rizenguo Date: 2014/10/29 Time: 14:40 这里放置各种配置数据
 */
public class Configure {
	// 微信支付统一下单接口
	private static String request_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	// appid:wxf349f5cf77605cdb mchid:1239479402
	// key:0eEYoqR4quMV6a1EAtJmm0T9Rq4JEl8m
	private static String key;

	// 微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID;

	// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID;

	// 终端IP,APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private static String spbill_create_ip = Util.getlocalIp();

	// 通知地址,接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private static String notify_url = "http://123.57.225.241:8881/SendService/wx-pay-notify";

	// 交易类型,取值如下：JSAPI，NATIVE，APP
	private static String trade_type = "NATIVE";

	public static String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public static String getNotify_url() {
		return notify_url;
	}

	public static String getTrade_type() {
		return trade_type;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppid() {
		return appID;
	}

	public static String getMchid() {
		return mchID;
	}

	public static String getRequest_url() {
		return request_url;
	}

}
