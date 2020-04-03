package mq.rabbitmq.demo04_route_mode;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import mq.rabbitmq.demo01_hello.ConnectionHelper;

import java.io.IOException;

/**
 * @Author KJ
 * @Date 2020-03-30 1:43 PM
 * @Description
 */
public class Consumer01 {
    private final static String QUEUE_NAME = "test_queue_direct_1";
    private final static String EXCHANGE_NAME = "test_exchange_direct";


    /**
     * 消费者1（假设是前台系统）
     *
     */
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println(" [c01] Received '" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
