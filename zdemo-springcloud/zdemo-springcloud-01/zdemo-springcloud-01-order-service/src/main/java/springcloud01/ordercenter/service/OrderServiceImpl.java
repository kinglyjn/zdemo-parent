package springcloud01.ordercenter.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springcloud01.ordercenter.mapper.OrderMapper;
import springcloud01.ordercenter.module.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @Author KJ
 * @Date 2020-04-01 11:48 PM
 * @Description
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient; // nacos注册客户端


    @Override
    public Order makeOder(Long productId, Long userId) {
        // 1. 保存订单
        Order order = Order.builder()
                .createTime(new Date())
                .img("aaa.jpg")
                .price(new BigDecimal(12.3))
                .userId(userId)
                .productId(productId)
                .orderNumber(UUID.randomUUID().toString())
                .build();


        // 2. 根据用户id调用用户服务接口数据，查询出用户的名字

        /*
        // 方式一：直接使用RestTemplate直接调用远程服务，缺点是不支持负载均衡
        String userServiceUrl = "http://localhost:8080/user-service/user/{id}";
        String forObject = restTemplate.getForObject(userServiceUrl, String.class, userId); // 这里不仅可以用String接收，还可以使用Map或专门对象接收，非常灵活
        log.info("请求服务地址是: {}，返回值是: {}", userServiceUrl, forObject);
        //{"id":1,"username":"zhangsan","password":"123456","name":"张三","age":27,"gender":1,"birthday":"2020-04-01T16:00:00.000+0000","createTime":"2020-04-01T04:24:40.000+0000","updateTime":"2020-04-01T05:53:22.000+0000","remark":"这是张三的信息"}
        */

        // 方式二：使用discoveryClient发现并调用远程服务，支持负载均衡，缺点是需要定义 "契约项目" 来定义数据规格
        /*
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size()); // 随机取负载均衡
        ServiceInstance userServiceInstance = instances.get(randomIndex);
        String userServiceUrl = userServiceInstance.getUri() + "/user-service/user/{id}";
        String forObject = restTemplate.getForObject(userServiceUrl, String.class, userId);
        String username = JSONObject.parseObject(forObject).getString("username");
        order.setUsername(username);
        log.info("请求服务地址是: {}，返回值是: {}", userServiceUrl, forObject);
        */

        // 方式三：在RestTemplate上使用 @LoadBalanced 注解代理自动实现负载均衡，注意这时候url就可以使用服务名来代替
        String userServiceUrl = "http://user-service/user-service/user/{id}";
        String forObject = restTemplate.getForObject(userServiceUrl, String.class, userId);
        String username = JSONObject.parseObject(forObject).getString("username");
        order.setUsername(username);

        // 3. 保存数据库
        this.save(order);
        return order;
    }
}
