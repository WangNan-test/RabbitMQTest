package com.hdd.rabbitmq.tx;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {
    private static final String QUEUE_NAME="test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg="hello tx message";
        try {
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            int i=10/0;
            System.out.println("send:"+msg);
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("SendMsg Rollback");
        }finally {
            channel.close();
            connection.close();
        }

    }
}
