package com.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketOnlineBusiness {
	private ServerSocket serverSocket;

	public SocketOnlineBusiness() {
		new Thread() {
			public void run() {
				try {
					serverSocket = new ServerSocket(10002);
					System.out.println("服务已开启");
					while (true) {
						Socket client_socket = serverSocket.accept();
						new SocketThread(client_socket).start();
						System.out.println("连接成功");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
}
