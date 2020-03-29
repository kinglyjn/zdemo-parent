package test08.integrate_hibernate;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring和hibernate整合测试
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test08/integrate_hibernate/spring08-config.xml"
})
public class Test08 {
	@Autowired
	private BookShopService bookShopService;
	
	@Autowired
	private CashierService cashierService;
	
	@Test
	public void testInit() {
		System.out.println(bookShopService);
	}
	
	
	/**
	 * 测试BookShopService事务
	 * 
	 */
	@Test
	public void testBookShopService() {
		bookShopService.sellBook("AA", "1001");
	}
	
	
	/**
	 * 测试CashierService事务
	 * 
	 */
	@Test
	public void testCashierService() {
		cashierService.checkout("AA", Arrays.asList(new String[]{"1001", "1002"}));
	}
}
