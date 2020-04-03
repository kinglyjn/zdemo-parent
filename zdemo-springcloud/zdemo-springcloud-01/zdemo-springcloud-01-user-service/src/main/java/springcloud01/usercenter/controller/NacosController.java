package springcloud01.usercenter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author KJ
 * @Date 2020-04-02 3:39 AM
 * @Description
 */
@RestController
@RequestMapping("/nacos")
@Slf4j
public class NacosController {

    /**
     * spring cloud 提供的服务发现和注册接口，nacos对它进行了非常好的实现(CompositeDiscoveryClient)
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取注册服务列表
     */
    @GetMapping("/getServices")
    public List<String> getServices() {
        log.info("discoveryClient impl class: {}", discoveryClient.getClass());
        return discoveryClient.getServices();
    }

    /**
     * 获取服务实例
     */
    @GetMapping("/getInstances")
    public List<ServiceInstance> getInstances(String serviceId) {
        log.info("discoveryClient impl class: {}", discoveryClient.getClass());
        return discoveryClient.getInstances(serviceId);
    }
}
