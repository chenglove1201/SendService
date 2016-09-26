package com.entity;

public class BuyCardRecord {
	private int id;
	private String buy_card_customer;
	private String buy_card_beauty;
	private String buy_card_name;
	private int custom_price;
	private int sell_price;
	private int custom_times;
	private int sell_times;
	private int remain_times;
	private String affiliation_orders;
	private String buy_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuy_card_customer() {
		return buy_card_customer;
	}
	public void setBuy_card_customer(String buy_card_customer) {
		this.buy_card_customer = buy_card_customer;
	}
	public String getBuy_card_beauty() {
		return buy_card_beauty;
	}
	public void setBuy_card_beauty(String buy_card_beauty) {
		this.buy_card_beauty = buy_card_beauty;
	}
	public String getBuy_card_name() {
		return buy_card_name;
	}
	public void setBuy_card_name(String buy_card_name) {
		this.buy_card_name = buy_card_name;
	}
	public int getCustom_price() {
		return custom_price;
	}
	public void setCustom_price(int custom_price) {
		this.custom_price = custom_price;
	}
	public int getSell_price() {
		return sell_price;
	}
	public void setSell_price(int sell_price) {
		this.sell_price = sell_price;
	}
	public int getCustom_times() {
		return custom_times;
	}
	public void setCustom_times(int custom_times) {
		this.custom_times = custom_times;
	}
	public int getSell_times() {
		return sell_times;
	}
	public void setSell_times(int sell_times) {
		this.sell_times = sell_times;
	}
	public int getRemain_times() {
		return remain_times;
	}
	public void setRemain_times(int remain_times) {
		this.remain_times = remain_times;
	}
	public String getAffiliation_orders() {
		return affiliation_orders;
	}
	public void setAffiliation_orders(String affiliation_orders) {
		this.affiliation_orders = affiliation_orders;
	}
	public String getBuy_date() {
		return buy_date;
	}
	public void setBuy_date(String buy_date) {
		this.buy_date = buy_date;
	}

}
