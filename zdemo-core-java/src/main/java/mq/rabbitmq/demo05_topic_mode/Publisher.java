package mq.rabbitmq.demo05_topic_mode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import mq.rabbitmq.demo01_hello.ConnectionHelper;

/**
 * @Author KJ
 * @Date 2020-03-30 10:19 PM
 * @Description
 */
public class Publisher {
    private final static String EXCHANGE_NAME = "test_exchange_topic";


    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 消息内容
        String message = "Hello World!!";
        channel.basicPublish(EXCHANGE_NAME, "routekey.1", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}