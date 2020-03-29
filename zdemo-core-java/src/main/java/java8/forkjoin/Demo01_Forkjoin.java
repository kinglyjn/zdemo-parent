package java8.forkjoin;

import org.junit.Test;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 测试 Fork/Join框架（在CPU内核中工作的线程底层使用工作窃取模式执行线程任务）
 *
 */
public class Demo01_Forkjoin {

    static class Task extends RecursiveTask<Long> {
        private static final long THRESHOLD = 100L;
        private long start;
        private long end;

        public Task(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0L;
            long length = end-start;

            if (length <= THRESHOLD) {
                return LongStream.range(start, end+1).sum(); // 注意包含头，不包含尾！
            } else {
                long middle =  (start+end)/2;

                Task leftTask = new Task(start, middle);
                leftTask.fork(); // 进行任务的拆分，同时压入线程队列

                Task rightTask = new Task(middle+1, end);
                rightTask.fork(); // 进行任务的拆分，同时压入线程队列

                sum = leftTask.join() + rightTask.join();
            }
            return sum;
        }
    }


    /**
     * 使用传统Fork/Join计算大型任务
     *
     */
    @Test
    public void test01() throws Exception {
        Instant starttime = Instant.now();

        ForkJoinPool poolExecutor = new ForkJoinPool();
        long result = poolExecutor.invoke(new Task(0L, 10000L));
        System.out.println(result);

        Instant endtime = Instant.now();
        System.out.println("Fork/Join#耗费的时间为(ms)：" + Duration.between(starttime, endtime).toMillis());
    }


    /**
     * 使用JDK1.8 API
     */
    @Test
    public void test02() {
        Instant starttime = Instant.now();

        //long result = LongStream.range(0L, 10000000001L).parallel().reduce(0L, Long::sum);
        long result = LongStream.rangeClosed(0, 10000L).parallel().reduce(0, Long::sum);
        System.out.println(result);

        Instant endtime = Instant.now();
        System.out.println("JDK1.8 API#耗费的时间为(ms)：" + Duration.between(starttime, endtime).toMillis());
    }
}
