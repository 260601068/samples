package com.wjl.socket.multiClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket=new ServerSocket(8800);
		while(true){
			Socket socket=serverSocket.accept();
			new ServerThread(socket).start();
		}

	}
}
