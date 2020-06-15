package com.hdd.rabbitmq.utile;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取MQ连接
 */
public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //设置端口  5672
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/wn_admin");
        //设置用户名
        factory.setUsername("user_admin");
        //设置密码
        factory.setPassword("123");
        return factory.newConnection();
    }
}
