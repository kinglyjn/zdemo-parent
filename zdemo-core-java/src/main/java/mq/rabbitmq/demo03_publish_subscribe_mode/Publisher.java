package mq.rabbitmq.demo03_publish_subscribe_mode;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import mq.rabbitmq.demo01_hello.ConnectionHelper;

/**
 * @Author KJ
 * @Date 2020-03-30 1:40 PM
 * @Description
 */
public class Publisher {
    private final static String EXCHANGE_NAME = "test_exchange_fanout";


    /**
     * 消息的生产者（看作是后台系统）
     * 向交换机中发送消息
     *
     */
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [p1] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
