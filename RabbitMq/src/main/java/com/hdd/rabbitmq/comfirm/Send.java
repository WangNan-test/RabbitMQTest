package com.hdd.rabbitmq.comfirm;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm普通模式
 */
public class Send {
    private static final String QUEUE="test_queue_confirm_1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE,false,false,false,null);
        //生产者调用 将channel设置成Confirm模式 注意
        channel.confirmSelect();
        String msg=" hello confirm message ";
        //批量发送
        for (int i = 0; i <10 ; i++) {
            channel.basicPublish("",QUEUE,null,msg.getBytes());
        }
        //确认发送结果
        if(!channel.waitForConfirms()){
            System.out.println("消息发送失败");
        }else{
            System.out.println("消息发送成功");
        }
        channel.close();
        connection.close();
    }
}
