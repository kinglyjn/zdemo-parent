package mq.rabbitmq.demo02_work_mode;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import mq.rabbitmq.demo01_hello.ConnectionHelper;

import java.io.IOException;

/**
 * @Author KJ
 * @Date 2020-03-30 1:12 PM
 * @Description
 */
public class Comsumer01 {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println(" [c1] Received '" + message + "'");

                Thread.sleep(10);

                //开启这行 表示使用手动确认模式
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}
