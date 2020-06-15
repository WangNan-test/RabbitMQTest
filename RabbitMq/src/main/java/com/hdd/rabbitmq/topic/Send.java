package com.hdd.rabbitmq.topic;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String EXCHANGE_NAME="test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg="Hello topic";
        channel.basicPublish(EXCHANGE_NAME,"goods_add",null ,msg.getBytes());
        System.out.println("发送消息："+msg);
        channel.close();
        connection.close();
    }
}
