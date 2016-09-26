package com.entity;

import com.common.Configure;

public class PayRequestData {
	// 微信分配的公众号ID（开通公众号之后可以获取到）
	private String appid;
	// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private String mch_id;
	// 随机字符串，不长于32位。
	private String nonce_str;
	// 商品描述
	private String body;
	// 商户订单号,商户系统内部的订单号,32个字符内、可包含字母
	private String out_trade_no;
	// 总金额
	private String total_fee;
	// 终端IP,APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private String spbill_create_ip;
	// 通知地址,接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private String notify_url;
	// 交易类型,取值如下：JSAPI，NATIVE，APP
	private String trade_type;
	// 商品ID,trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	private String product_id;
	// 附加数据
	private String attach;
	// 商品详情
	private String detail;
	// 签名
	private String sign;

	public PayRequestData() {
		this.spbill_create_ip = Configure.getSpbill_create_ip();
		this.notify_url = Configure.getNotify_url();
		this.trade_type = Configure.getTrade_type();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
