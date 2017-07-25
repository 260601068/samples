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
	System.out.println("�����socket�ȴ�����");
	//accept()������һֱ������ֱ���пͻ���ִ��new Socket(ip,port)����
	Socket socket=serverSocket.accept();
	InputStream in=socket.getInputStream();
	InputStreamReader reader=new InputStreamReader(in);
	char[] chars=new char[64];
	int len;
	StringBuilder sb=new StringBuilder();
	while((len=reader.read(chars))!=-1){
		sb.append(new String(chars,0,len));
	}
	System.out.println("���Ƿ���ˣ��ͻ���˵�� "+sb.toString());
	
	OutputStreamWriter writer=new OutputStreamWriter(socket.getOutputStream());
	writer.write("Hello Client");
	writer.flush();
	socket.shutdownOutput();
	reader.close();
	serverSocket.close();
	
	System.out.println("�����socket�ѹر�");
}
}
