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
	//����ִ��flush��������Ȼ�����޷����ݵ������
	writer.flush();
	//����ر�socket�����������Ȼ����˵�socket����������read������һֱ������
	//����Ҫ����reader��writer��close�����ر�������Ϊ����writer.close()�Ὣ��ӦsocketҲͬʱ�ر�
	socket.shutdownOutput();
	InputStreamReader reader=new InputStreamReader(socket.getInputStream());
	char[] chars=new char[64];
	int len;
	StringBuilder sb=new StringBuilder();
	while((len=reader.read(chars))!=-1){
		sb.append(new String(chars,0,len));
	}
	System.out.println("���ǿͻ��ˣ������˵�� "+sb.toString());
	//�ر���������ʱ��Ὣ��ӦsocketҲͬʱ�ر�,���Բ���Ҫִ��socket.close()
	reader.close();
}
}
