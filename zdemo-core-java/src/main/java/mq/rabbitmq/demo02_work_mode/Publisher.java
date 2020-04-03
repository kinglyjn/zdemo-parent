package mq.rabbitmq.demo02_work_mode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import mq.rabbitmq.demo01_hello.ConnectionHelper;

/**
 * @Author KJ
 * @Date 2020-03-30 1:16 PM
 * @Description
 */
public class Publisher {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [p1] Sent '" + message + "'");

            Thread.sleep(i * 10);
        }

        channel.close();
        connection.close();
    }

}
