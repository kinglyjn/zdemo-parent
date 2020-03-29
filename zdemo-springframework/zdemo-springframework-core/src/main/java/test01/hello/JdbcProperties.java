package test01.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 测试 PropertiesFactoryBean
 *
 */
@Component
public class JdbcProperties {
	
	@Value("${jdbc.driver}")  
    private String driver;
	
	@Value("${jdbc.url}")
	private String url;
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "JdbcProperties [driver=" + driver + ", url=" + url + "]";
	}
	
	 /*
	 * main
	 * 
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("test01/hello/spring01-config.xml");
		
		JdbcProperties jp = (JdbcProperties) ctx.getBean("jdbcProperties");
		System.out.println(jp);
		
		ctx.close();
	}
}
