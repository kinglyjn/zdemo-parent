package springcloud01.ordercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author KJ
 * @Date 2020-04-01 3:32 PM
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient // 标记为可发现的服务
@MapperScan("springcloud01.ordercenter.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }

    @Bean
    @LoadBalanced // nacos封装了ribbon，加了这个注解就可以支持自动负载均衡，底层使用jdk的代理对RestTemplate进行增强处理
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


