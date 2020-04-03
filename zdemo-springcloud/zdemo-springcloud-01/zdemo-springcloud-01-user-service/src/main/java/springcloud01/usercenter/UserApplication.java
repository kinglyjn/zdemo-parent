package springcloud01.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author KJ
 * @Date 2020-04-01 3:32 PM
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient // 标记为可发现的服务
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}


