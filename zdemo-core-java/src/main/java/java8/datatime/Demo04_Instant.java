package java8.datatime;

import org.junit.Test;

import java.time.*;

/**
 * 时间戳相关：时间戳以Unix元年即1970-01-01 00:00:00到某个时间点间的毫秒值
 * Instant
 */
public class Demo04_Instant {

    @Test
    public void test01() {
        Instant instant1 = Instant.now();
        System.out.println(instant1); //2018-08-09T12:15:48.310Z 默认获取当前UTC时区时间（时间协调时间）

        OffsetDateTime odt = instant1.atOffset(ZoneOffset.ofHours(8)); //2018-08-09T20:15:48.310+08:00 东八区时间
        System.out.println(odt);

        System.out.println(instant1.toEpochMilli()); 	//1533817133187
        System.out.println(Instant.ofEpochSecond(60)); 	//1970-01-01T00:01:00Z

        Instant instant2 = Instant.now();
        System.out.println("时间间隔1：" + Duration.between(instant1, instant2).toMillis() + "ms.");

        LocalDateTime ldt1 = LocalDateTime.now();
        LocalDateTime ldt2 = LocalDateTime.now();
        System.out.println("时间间隔2：" + Duration.between(ldt1, ldt2).toMillis());

        LocalDate ld1 = LocalDate.of(2017, 7, 8);
        LocalDate ld2 = LocalDate.now();
        System.out.println("日期间隔1：" + Period.between(ld1, ld2)); //P1Y1M1D
    }

}
