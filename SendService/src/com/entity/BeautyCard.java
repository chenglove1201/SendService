package com.entity;

public class BeautyCard {
	private int id;
	private String beauty_card_name;
	private int custom_price;
	private int custom_times;
	private String bind_beauty;
	private String create_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBeauty_card_name() {
		return beauty_card_name;
	}

	public void setBeauty_card_name(String beauty_card_name) {
		this.beauty_card_name = beauty_card_name;
	}

	public int getCustom_price() {
		return custom_price;
	}

	public void setCustom_price(int custom_price) {
		this.custom_price = custom_price;
	}

	public int getCustom_times() {
		return custom_times;
	}

	public void setCustom_times(int custom_times) {
		this.custom_times = custom_times;
	}

	public String getBind_beauty() {
		return bind_beauty;
	}

	public void setBind_beauty(String bind_beauty) {
		this.bind_beauty = bind_beauty;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

}
