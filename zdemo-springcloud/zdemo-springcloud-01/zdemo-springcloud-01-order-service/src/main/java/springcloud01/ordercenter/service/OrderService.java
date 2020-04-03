package springcloud01.ordercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import springcloud01.ordercenter.module.Order;

/**
 * @Author KJ
 * @Date 2020-04-01 11:44 PM
 * @Description
 */
public interface OrderService extends IService<Order> {

    /**
     * 下单
     * @param productId
     * @param userId
     */
    Order makeOder(Long productId, Long userId);

}
