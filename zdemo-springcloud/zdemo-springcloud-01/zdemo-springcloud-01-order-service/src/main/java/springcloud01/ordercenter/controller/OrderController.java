package springcloud01.ordercenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloud01.ordercenter.module.Order;
import springcloud01.ordercenter.service.OrderService;

/**
 * @Author KJ
 * @Date 2020-04-02 12:14 AM
 * @Description
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/makeOrder")
    public Order makeOrder(Long pid, Long uid) {
        return orderService.makeOder(pid, uid);
    }

}
