package com.hdd.rabbitmq.workfair;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Resv1 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //简历连接通道
        final Channel channel = connection.createChannel();

        //保证每次只分发一次
        channel.basicQos(1);
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //定义一个消费
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            //接受消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("resv1 msg:"+msg);
                    //手动回消息
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //消息应答
        //boolean autoACK=true;  (自动确认模式)一旦Rabbitmq将消息分发给消费者，就会从内存中删除
        //boolean autoACK=false; (手动确认模式)
        boolean autoACK=false;
        //监听队列
        channel.basicConsume(QUEUE_NAME,autoACK,defaultConsumer);
    }
}
