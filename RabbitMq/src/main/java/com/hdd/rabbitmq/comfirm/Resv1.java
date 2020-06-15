package com.hdd.rabbitmq.comfirm;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Resv1 {
    private static final String QUEUE_NAME = "test_queue_confirm3";
    private static final String EXCHANGE_NAME = "test_exchange_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
         Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("recv[tx] msg:"+msg);
            }
        });
    }
}
