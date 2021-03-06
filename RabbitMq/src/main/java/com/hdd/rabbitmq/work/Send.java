package com.hdd.rabbitmq.work;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //简历连接通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 50; i++) {
            String msg = "Hello"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            try {
                Thread.sleep(i*20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        channel.close();
        connection.close();
    }
}
