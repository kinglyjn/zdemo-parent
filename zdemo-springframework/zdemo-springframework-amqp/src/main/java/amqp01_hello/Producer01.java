package amqp01_hello;

import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author KJ
 * @Date 2020-03-30 11:57 PM
 * @Description
 */
public class Producer01 {

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/amqp01_hello/rabbitmq-context.xml");

        //RabbitMQ模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);

        //发送消息
        template.convertAndSend("Hello Keyllo!");
        Thread.sleep(1000);// 休眠1秒
    }

}
