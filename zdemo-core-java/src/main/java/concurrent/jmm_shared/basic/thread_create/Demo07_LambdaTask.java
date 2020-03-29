package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Kingly
 * @Date 2020/2/2
 * @Description
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.Demo07_LambdaTask")
public class Demo07_LambdaTask {

    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(10,20,30,40);

        // 串行流
        values.stream().forEach(v -> log.debug("{}", v));
        System.out.println();

        // 并行流
        values.parallelStream().forEach(v -> log.debug("{}", v));
        System.out.println( values.parallelStream().mapToInt(v -> v).sum() );
    }

}
