package test03.annotation_aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * aspectj切面测试类
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test03/annotation_aop/spring03-config.xml"
})
public class AspectTest {
	
	@Autowired
	private Caculator caculator;
	
	@Test
	public void test01() {
		caculator.div(1, 0);
	}
}
