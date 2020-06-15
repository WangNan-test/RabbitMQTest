package com.hdd.rabbitmq.comfirm;

import com.hdd.rabbitmq.utile.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send3 {
    private static final String QUEUE="test_queue_confirm3";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE,false,false,false,null);
        channel.confirmSelect();
        //未确认的标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        //通道加监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if(b){
                    System.out.println("-----handleNack----B");
                    confirmSet.headSet(l+1).clear();
                }else{
                    System.out.println("-----handleNack----B--false");
                    confirmSet.remove(l);
                }
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if(b){
                    System.out.println("-----handleNack----BXXXXXXXXXXXXXX");
                    confirmSet.headSet(l+1).clear();
                }else{
                    System.out.println("-----handleNack----B--falseXZXXXXXXXXXXXXXXXX");
                    confirmSet.remove(l);
                }
            }
        });

        String msg="Hello O(∩_∩)O哈哈~";

        while(true){
            long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE,null,msg.getBytes());
            confirmSet.add(nextPublishSeqNo);
        }

    }
}
