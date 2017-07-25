package com.wjl.socket.multiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
public static void main(String[] args) throws UnknownHostException, IOException {
	Socket socket=new Socket("127.0.0.1", 8800);
	OutputStreamWriter writer=new OutputStreamWriter(socket.getOutputStream());
	writer.write("Hello Server");
	writer.flush();
	socket.shutdownOutput();
	BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	StringBuilder sb=new StringBuilder();
	String temp;
		while((temp=br.readLine())!=null){
			sb.append(temp);
		}
	System.out.println("我是客户端，服务端说： "+sb.toString());
	br.close();
}
}
