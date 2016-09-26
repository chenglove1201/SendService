package com.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.common.Util;
import com.common.XMLParser;
import com.dao.Daocrud;
import com.entity.BeautyCard;
import com.entity.Machine;
import com.google.gson.Gson;

public class Uiie {
	Machine machine;

	public static void main(String[] args) {
		new Uiie();
	}

	public Uiie() {
//		Card[] values = Card.values();
//		Card.BANCHENGKA.setPrice(108);
//		System.out.println(values[2]);
//		Gson gson = new Gson();
//		machine = gson.fromJson("", Machine.class);
//		System.out.println(machine);
		// Daocrud daocrud = new Daocrud();
		// List<BeautyCard> queryBeautyCard = daocrud.queryBeautyCard("克丽缇娜");
		// Gson gson = new Gson();
		// String json = gson.toJson(queryBeautyCard);
		// System.out.println(json);
	}

}
enum Card {
	DANCIKA("单次卡", -1), ZUNXIANGKA("尊享卡", -1), BANCHENGKA("半程卡", -1), YINKA(
			"银卡", -1), LIAOCHENGKA("疗程卡", -1), ZUANSHIKA("钻石卡", -1);
	private Card(String cardName, int price) {
		this.cardName = cardName;
		this.price = price;
	}

	private String cardName;
	private int price;

	public int getPrice() {
		return this.price;
	}

	public void setPrice(String cardName,int price) {
		this.price = price;
	}

	public String getCardName() {
		return this.cardName;
	}
	
	
}
