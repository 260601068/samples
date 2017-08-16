package com.wjl.rabbitmq.exchange;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class C2 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //消费者的channel必须定义queue，不然报错
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("name2: "+queueName);
        //消费者的exchange与queue必须绑定，不然会无法接收到消息
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" C2 Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" C2 Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
