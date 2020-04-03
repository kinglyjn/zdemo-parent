package mq.rabbitmq.demo04_route_mode;

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
    private final static String EXCHANGE_NAME = "test_exchange_direct";


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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 消息内容
        String message = "删除商品0001";
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message.getBytes()); // 路由key为delete
        System.out.println(" [p1] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
