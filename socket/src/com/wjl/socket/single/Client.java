package com.wjl.socket.single;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
public static void main(String[] args) throws IOException {
	Socket socket=new Socket("127.0.0.1", 8888);
	OutputStreamWriter writer=new OutputStreamWriter(socket.getOutputStream());
	writer.write("Hello Server");
	//必须执行flush方法，不然数据无法传递到服务端
	writer.flush();
	//必须关闭socket的输出流，不然服务端的socket的输入流的read操作会一直阻塞，
	//并且要慎用reader或writer的close方法关闭流，因为比如writer.close()会将对应socket也同时关闭
	socket.shutdownOutput();
	InputStreamReader reader=new InputStreamReader(socket.getInputStream());
	char[] chars=new char[64];
	int len;
	StringBuilder sb=new StringBuilder();
	while((len=reader.read(chars))!=-1){
		sb.append(new String(chars,0,len));
	}
	System.out.println("我是客户端，服务端说： "+sb.toString());
	//关闭输入流的时候会将对应socket也同时关闭,所以不需要执行socket.close()
	reader.close();
}
}
