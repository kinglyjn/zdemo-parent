package test02.annotation_generic_id;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 泛型注入测试方法
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test02/annotation_generic_id/spring02-config.xml"
})
public class Test02 {

	@Autowired
	private UserService userService;
	
	
	@Test
	public void test01() {
		userService.add(new User());
	}
}
