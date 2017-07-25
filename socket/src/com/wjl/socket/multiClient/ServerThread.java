package com.wjl.socket.multiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run(){
		OutputStreamWriter writer;
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		StringBuilder sb=new StringBuilder();
		String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			String clientIP=socket.getInetAddress().getHostAddress();
			System.out.println("来自客户端IP，"+clientIP+"的消息： "+sb.toString());
			writer=new OutputStreamWriter(socket.getOutputStream());
			writer.write("Hello Client");
			writer.flush();
			socket.shutdownOutput();
			br.close();
		} catch (IOException e) {
		}
	}
}
