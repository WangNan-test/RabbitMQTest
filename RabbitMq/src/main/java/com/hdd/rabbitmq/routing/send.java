package com.hdd.rabbitmq.routing;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class send {
    private static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg="Hello Routing";
        String routingkey="error";
        channel.basicPublish(EXCHANGE_NAME,routingkey,null,msg.getBytes());
        System.out.println("send:"+msg);
        channel.close();
        connection.close();
    }
}
