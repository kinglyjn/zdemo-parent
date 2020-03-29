package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @Author Kingly
 *
 * 测试 spring 框架对多线程的支持
 *
 */
@Configuration
@ComponentScan("concurrent.jmm_shared.basic.thread_create")
@EnableAsync
public class Demo06_SpringTask {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Demo06_SpringTask.class);
        SService service = context.getBean(SService.class);
        service.a();
        service.b();
    }
}

@Service
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.SService")
class SService {
    @Async
    public void a() {
        while (true) {
            log.debug("a");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void b() {
        while (true) {
            log.debug("b");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
