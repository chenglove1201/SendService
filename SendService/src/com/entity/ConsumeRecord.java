package com.entity;

public class ConsumeRecord {
	private int id;
	private int consume_card_id;
	private String describe;
	private String date_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConsume_card_id() {
		return consume_card_id;
	}

	public void setConsume_card_id(int consume_card_id) {
		this.consume_card_id = consume_card_id;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

}
