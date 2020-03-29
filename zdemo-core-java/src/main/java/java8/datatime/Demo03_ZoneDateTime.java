package java8.datatime;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 带时区的日期或时间：
 * ZonedDate、ZonedTime、ZonedDateTime
 * 每个时区都对应着id，地区id都为 “{区域/城市}” 的格式，例如 Asia/Shanghai
 * ZonedId类包含了所有的时区信息，getAvailableZoneIds()可以获取所有时区的信息，
 * of(id)用于指定时区信息获取ZoneId对象。
 *
 */
public class Demo03_ZoneDateTime {

    @Test
    public void test01() {
        ZoneId.getAvailableZoneIds().forEach(System.out::println);

        LocalDateTime zhDt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(zhDt); // 2019-11-12T16:49:01.815

        ZonedDateTime utcDt = zhDt.atZone(ZoneId.of("UTC"));
        System.out.println(utcDt); // 2019-11-12T16:49:01.815Z[UTC]
    }

}
