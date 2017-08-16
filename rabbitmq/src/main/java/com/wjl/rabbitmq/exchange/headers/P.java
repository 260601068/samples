package com.wjl.rabbitmq.exchange.headers;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class P {
    private final static String EXCHANGE_NAME = "header-exchange";  
    
    @SuppressWarnings("deprecation")  
    public static void main(String[] args) throws Exception {  
        // 创建连接和频道  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("127.0.0.1");  
        // 指定用户 密码  
        factory.setUsername("guest");  
        factory.setPassword("guest");    
        // 指定端口  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
          
        //声明转发器和类型headers，headers类型转换器也会忽略routingKey，是一种键值对，
        //只有接收者与发送者定义的键值对相匹配，接收者才能获得相应的消息
        channel.exchangeDeclare(EXCHANGE_NAME, "headers"); 
        String message = new Date().toLocaleString() + " : log something";  
          
        Map<String,Object> headers =  new Hashtable<String, Object>();  
        headers.put("aaa", "01234");  
        Builder properties = new BasicProperties.Builder();  
        properties.headers(headers);  
          
        // 指定消息发送到的转发器,绑定键值对headers键值对  
        channel.basicPublish(EXCHANGE_NAME, "",properties.build(),message.getBytes());  
          
        System.out.println("Sent message :'" + message + "'");  
        channel.close();  
        connection.close();  
    }  
}
