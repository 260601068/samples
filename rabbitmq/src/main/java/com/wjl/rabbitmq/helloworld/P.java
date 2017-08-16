package com.wjl.rabbitmq.helloworld;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class P {
    private final static String QUEUE_NAME = "hello";  
    
    public static void main(String[] argv) throws Exception {  
        // 创建连接工厂  
        ConnectionFactory factory = new ConnectionFactory();  
//      设置RabbitMQ地址  
        factory.setHost("127.0.0.1");  
// 		指定用户 密码 ，默认guest,guest
//      factory.setUsername("guest");  
//      factory.setPassword("guest");
// 		指定端口 ，默认5672
//      factory.setPort(AMQP.PROTOCOL.PORT); 
//      创建一个新的连接  
        Connection connection = factory.newConnection();  
//      创建一个频道  
        Channel channel = connection.createChannel();  
//      声明一个队列 ,如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);  
        String message = "Hello World!";  
//      发送消息到队列中  
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("P [x] Sent '" + message + "'");  
//      关闭频道和连接  
        channel.close();  
        connection.close();  
    } 
}
