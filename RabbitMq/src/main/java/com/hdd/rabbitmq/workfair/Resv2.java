package com.hdd.rabbitmq.workfair;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Resv2 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //简历连接通道
        final Channel channel = connection.createChannel();

        channel.basicQos(1);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    System.out.println("Resv2 msg:" + msg);
                    //收动回消息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //自动应答设置成false
        boolean autoACK=false;
        channel.basicConsume(QUEUE_NAME,autoACK,defaultConsumer);
    }
}
