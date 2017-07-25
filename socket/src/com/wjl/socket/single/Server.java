package com.wjl.socket.single;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
public static void main(String[] args) throws IOException{
	ServerSocket serverSocket=new ServerSocket(8888);
	System.out.println("服务端socket等待接收");
	//accept()方法会一直阻塞，直到有客户端执行new Socket(ip,port)方法
	Socket socket=serverSocket.accept();
	InputStream in=socket.getInputStream();
	InputStreamReader reader=new InputStreamReader(in);
	char[] chars=new char[64];
	int len;
	StringBuilder sb=new StringBuilder();
	while((len=reader.read(chars))!=-1){
		sb.append(new String(chars,0,len));
	}
	System.out.println("我是服务端，客户端说： "+sb.toString());
	
	OutputStreamWriter writer=new OutputStreamWriter(socket.getOutputStream());
	writer.write("Hello Client");
	writer.flush();
	socket.shutdownOutput();
	reader.close();
	serverSocket.close();
	
	System.out.println("服务端socket已关闭");
}
}
