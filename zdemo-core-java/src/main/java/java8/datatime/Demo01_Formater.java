package java8.datatime;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Java8- 的日期时间格式化器：
 * java.text.SimpleDateFormat
 *
 * Java8+ 日期时间格式化器：
 * java.time.format.DateTimeFormatter
 *
 */
public class Demo01_Formater {

    /**
     * 测试传统时间相关联类的线程安全性（不安全）
     */
    @Test
    public void test01() throws Exception { // 运行结果出现异常 NumberFormatException: multiple points
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<Date>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Date> future = pool.submit(new Callable<Date>() {
                @Override
                public Date call() throws Exception {
                    return sdf.parse("2018-08-08");
                }
            });
            futures.add(future);
        }

        for (Future<Date> future : futures) {
            Date result = future.get();
            System.out.println(result);
        }
        pool.shutdown();
    }

    /**
     * 传统时间相关联类的线程安全性问题的解决
     */
    @Test
    public void test02() throws Exception {
        ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<Date>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Date> future = pool.submit(new Callable<Date>() {
                @Override
                public Date call() throws Exception {
                    return sdf.get().parse("2018-08-08");
                }
            });
            futures.add(future);
        }

        for (Future<Date> future : futures) {
            Date result = future.get();
            System.out.println(result);
        }
        pool.shutdown();
    }


    /**
     * Java8 data formatter API 的优势:
     * Java8中相关的时间类都设置为不可变的线程安全类型，类似于String类
     */
    @Test
    public void test03() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> futures = new ArrayList<>();

        IntStream.range(0, 10).forEach(v -> {
            Future<LocalDate> future = pool.submit(new Callable<LocalDate>() {
                @Override
                public LocalDate call() throws Exception {
                    return LocalDate.parse("2018-08-08", dtf);
                }
            });
            futures.add(future);
        });

        for (Future<LocalDate> future : futures) {
            LocalDate result = future.get();
            System.out.println(result);
        }
        pool.shutdown();
    }

    @Test
    public void test04() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();

        String dtStr = now.format(formatter);
        System.out.println(dtStr);

        LocalDateTime dt = LocalDateTime.parse("2019-09-09 01:12:13.111", formatter); // 注意 0 不可省略
        System.out.println(dt);
    }

}
