package com.hdd.rabbitmq.ps;


import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class send {
    private static final String EXCHANGE_NAME="test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");    //分发

        String msg="Hello exchang";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes()  );
        System.out.println("send MSG:"+msg);
        channel.close();
        connection.close();
    }
}
