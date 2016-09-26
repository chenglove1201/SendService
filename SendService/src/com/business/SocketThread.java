package com.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import com.dao.Daocrud;
import com.exception.CommonException;

public class SocketThread extends Thread {
	private boolean flag = true;
	private Socket clientSocket;
	private BufferedReader br;
	private PrintWriter pw;
	private String customer;
	private Daocrud daocrud;
	private long lineTime;
	private Timer timer;

	public SocketThread(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		pw = new PrintWriter(this.clientSocket.getOutputStream(), true);
		// 连接成功后返回line_ok
		pw.println("line_ok");
		daocrud = new Daocrud();
		// 开启心跳包
		Timingtask();
	}

	@Override
	public void run() {
		try {
			while (flag) {
				clientSocket.sendUrgentData(0xFF);
				String requestData = br.readLine();
				if (requestData != null) {
					if (requestData.equals("heart")) {
						pw.println("heart");
						lineTime = System.currentTimeMillis();
					} else {
						if (requestData.startsWith("ID:")) {
							customer = requestData.substring(3);
							try {
								if (daocrud.checkOnline(customer)) {
									pw.println("alreadyLogin");
								} else {
									try {
										daocrud.insertOnline(customer);
										pw.println("loginOk");
										// 登录成功后，保存此输出流
										PrintWriterMap.getPwInstance().addPw(customer, pw);
									} catch (CommonException e) {
										pw.println("error");
										e.printStackTrace();
									}
								}
							} catch (CommonException e1) {
								pw.println("error");
								e1.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// 判断是否电线，掉线后移除在线
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * 定时任务,一分钟后开启，开启后每隔一分钟检测一次，如果连接时长大于一分钟，则说明断开
	 */
	private void Timingtask() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (System.currentTimeMillis() - lineTime > 60 * 1000) {
					close();
				}
			}
		};
		timer.schedule(task, 60 * 1000, 60 * 1000);
	}

	private void close() {
		try {
			daocrud.removeOnline(customer);
			// 退出登录后，要移除此输出流
			PrintWriterMap.getPwInstance().removePw(customer);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		if (timer != null) {
			timer.cancel();
		}
		flag = false;
		close(pw, br, clientSocket);
	}

	private void close(PrintWriter pw, BufferedReader br, Socket client) {
		try {
			if (pw != null) {
				pw.close();
			}
			if (br != null) {
				br.close();
			}
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
