package com.keyllo.demo.mybatis.demo01.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author KJ
 * @Date 2020-03-23 12:07 AM
 * @Description
 */
@Configuration
@PropertySource("/com/keyllo/demo/mybatis/demo01/jdbc.properties")
@ComponentScan("com.keyllo.demo.mybatis.demo01")     // 配置 spring 扫描组件的路径
@MapperScan("com.keyllo.demo.mybatis.demo01.mapper") // 配置 mybatis-spring 扫描 mapper 接口的路径
@EnableTransactionManagement                         // 开启事务
@EnableAspectJAutoProxy                              // 开启切面支持
public class MyConfig {

    @Bean
    public DriverManagerDataSource dataSource01(
            @Value("${jdbc.driver}") String driver,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory01(@Qualifier("dataSource01")  DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new DefaultResourceLoader().getResource("/com/keyllo/demo/mybatis/demo01/mybatis-config.xml"));

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:/com/keyllo/demo/mybatis/demo01/*/**/*.xml");
        factoryBean.setMapperLocations(resources);

        return factoryBean;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource01") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }


//    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
//
//        //SqlSessionTemplate template = (SqlSessionTemplate) context.getBean("sqlSessionTemplate");
//        //System.out.println(template);
//
//        DataSource dataSource01 = (DataSource) context.getBean("dataSource01");
//        System.out.println(dataSource01);
//
//        SqlSessionFactory sessionFactory01 = (SqlSessionFactory) context.getBean("sqlSessionFactory01");
//        System.out.println(sessionFactory01);
//    }

}
