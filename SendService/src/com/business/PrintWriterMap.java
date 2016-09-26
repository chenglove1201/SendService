package com.business;

import java.io.PrintWriter;
import java.util.HashMap;

public class PrintWriterMap {
	private HashMap<String, PrintWriter> map = new HashMap<String, PrintWriter>();
	private static PrintWriterMap printWriterMap;

	private PrintWriterMap() {

	}

	public static PrintWriterMap getPwInstance() {
		if (printWriterMap == null) {
			synchronized (PrintWriterMap.class) {
				if (printWriterMap == null) {
					printWriterMap = new PrintWriterMap();
				}
			}
		}
		return printWriterMap;
	}

	public void addPw(String customer, PrintWriter printWriter) {
		map.put(customer, printWriter);
		System.out.println("增加一个，剩余："+map.size());
	}

	public PrintWriter getPw(String customer) {
		return map.get(customer);
	}

	public void removePw(String customer) {
		map.remove(customer);
		System.out.println("移除一个，剩余："+map.size());
	}
}
