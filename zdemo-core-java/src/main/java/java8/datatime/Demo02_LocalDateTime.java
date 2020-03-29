package java8.datatime;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Java8规范了大量日期和时间相关类，这些类统一放在java.time包下
 * 这些时间相关类的实例都是不可变、线程安全的（类似于String）。
 *
 * 日期时间相关：
 * LocalDate、LocalTime、LocalDateTime
 * 这些类的实例对象都是不可变的，分别表示使用ISO-8601日历系统的日期、时间、日期时间，
 * ISO-8601日历系统是国际标准化组织制定的现代公民的日期和时间表示法。它们提供了简单
 * 的日期或时间，并不包含当前的时间信息，也不包含与时区相关的信息。
 *
 * 日期时间矫正器及其工具类：
 * TemporalAdjuster、TemporalAdjusters
 *
 */
public class Demo02_LocalDateTime {

    @Test
    public void test01() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now); // 2019-11-12T16:38:49.812

        LocalDateTime dt1 = LocalDateTime.of(2018, 5, 6, 1, 2, 3);
        System.out.println(dt1); // 2018-05-06T01:02:03

        LocalDateTime dt2 = dt1.plusYears(1);
        System.out.println(dt2); // 2019-05-06T01:02:03

        LocalDateTime dt3 = dt2.minusMinutes(2);
        System.out.println(dt3); // 2019-05-06T01:00:03

        System.out.println(dt3.getYear());
        System.out.println(dt3.getMonthValue());
        System.out.println(dt3.getDayOfMonth());
        System.out.println(dt3.getHour());
        System.out.println(dt3.getMinute());
        System.out.println(dt3.getSecond());
        System.out.println(dt3.getNano());

        LocalDateTime dt4 = dt3.withDayOfMonth(10);
        System.out.println(dt4); // 2019-05-10T01:00:03

        LocalDateTime dt5 = dt4.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(dt5); // 2019-05-12T01:00:03
    }

}
