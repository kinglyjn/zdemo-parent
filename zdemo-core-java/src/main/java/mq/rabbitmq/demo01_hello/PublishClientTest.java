package mq.rabbitmq.demo01_hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

/**
 * @Author KJ
 * @Date 2020-03-30 12:38 PM
 * @Description
 */
public class PublishClientTest {
    private final static String QUEUE_NAME = "q_test_01";

    /**
     * 获取连接
     */
    @Test
    public void test01() throws Exception {
        Connection connection = ConnectionHelper.getConnection();
        System.out.println(connection);
        connection.close();
    }


    /**
     * 生产者发送消息到队列
     */
    @Test
    public void test02() throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionHelper.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
