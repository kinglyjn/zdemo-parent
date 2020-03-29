package test07.xml_tx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring事务管理测试
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test07/xml_tx/spring07-config.xml"
})
public class Test07 {
	@Autowired
	private BookShopService bookShopService;
	
	/**
	 * 测试spring声明式事务
	 * 1. 没有事务的情况下，当用户的余额不足时，买书操作后用户的余额不变，但是库存减少了1
	 * 2. 在有事务的情况下，当用户的余额不足时，买书操作后用户的余额不变，但是库存也不会变
	 * 
	 */
	@Test
	public void testBookShopService() {
		bookShopService.sellBook("AA", "1001");
	}
}
