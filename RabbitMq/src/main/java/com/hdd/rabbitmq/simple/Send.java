package com.hdd.rabbitmq.simple;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class Send {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //简历连接通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg="Hello simple ;";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes() );
        System.out.println("simple msg："+msg);
        channel.close();
        connection.close();
    }
}
