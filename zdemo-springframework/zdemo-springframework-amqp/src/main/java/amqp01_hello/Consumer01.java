package amqp01_hello;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author KJ
 * @Date 2020-03-30 11:51 PM
 * @Description  消费者
 *
 */
@Slf4j(topic = "c.amqp01_hello.Consumer01")
public class Consumer01 {

    public void listen(String foo) {
        log.debug("消费者：{}", foo);
    }

}
