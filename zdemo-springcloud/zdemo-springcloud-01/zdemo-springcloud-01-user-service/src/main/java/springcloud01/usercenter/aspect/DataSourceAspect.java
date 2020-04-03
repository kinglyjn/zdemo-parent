package springcloud01.usercenter.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springcloud01.usercenter.datasource.DataSource;
import springcloud01.usercenter.datasource.DataSourceContextHolder;

/**
 * @Author KJ
 * @Date 2020-04-01 5:54 PM
 * @Description
 */
@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {

    @Pointcut("@within(springcloud01.usercenter.datasource.DataSource) || @annotation(springcloud01.usercenter.datasource.DataSource)")
    public void pointCut() {
    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource) {
        log.info("选择数据源---" + dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
