package com.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord {
	public static void writeLog(String msg) {
		try {
//			File file = new File("E:\\notify2.txt");
			File file = new File("/usr/lib/TOMCAT8.0/notify3.txt");
			FileWriter fileWriter = new FileWriter(file, true);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileWriter.write(dateFormat.format(new Date()) + ":" + msg + "\n" + "\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
