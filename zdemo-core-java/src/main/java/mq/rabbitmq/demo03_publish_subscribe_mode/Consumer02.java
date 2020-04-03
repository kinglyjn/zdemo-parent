package mq.rabbitmq.demo03_publish_subscribe_mode;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import mq.rabbitmq.demo01_hello.ConnectionHelper;

import java.io.IOException;

/**
 * @Author KJ
 * @Date 2020-03-30 1:43 PM
 * @Description
 */
public class Consumer02 {
    private final static String QUEUE_NAME = "test_queue_work2";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";


    /**
     * 消费者1（看作是搜索系统）
     *
     */
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println(" [c02] Received '" + message + "'");

                Thread.sleep(10);

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
