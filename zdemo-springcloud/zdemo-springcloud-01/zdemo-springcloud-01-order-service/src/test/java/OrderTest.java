import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springcloud01.ordercenter.OrderApplication;
import springcloud01.ordercenter.module.Order;
import springcloud01.ordercenter.service.OrderService;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @Author KJ
 * @Date 2020-04-02 12:48 AM
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderApplication.class})
public class OrderTest {

    @Autowired
    private OrderService orderService;


    @Test
    public void test01() {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .price(new BigDecimal(33.3))
                .img("aaa.jpg")
                .userId(1L)
                .username("")
                .productId(111L)
                .build();
        orderService.save(order);
    }

}
