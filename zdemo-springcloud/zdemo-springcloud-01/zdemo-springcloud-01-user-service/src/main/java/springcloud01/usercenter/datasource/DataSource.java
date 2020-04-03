package springcloud01.usercenter.datasource;

import java.lang.annotation.*;

/**
 * @Author KJ
 * @Date 2020-04-01 5:53 PM
 * @Description
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceEnum value() default DataSourceEnum.DB1;
}
