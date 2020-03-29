package test06.annotation_tx;

import java.util.Arrays;

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
		"classpath:/test06/annotation_tx/spring06-config.xml"
})
public class Test06 {
	@Autowired
	private BookShopDao bookShopDao;
	@Autowired
	private BookShopService bookShopService;
	@Autowired
	private CashierService cashierService;
	
	
	@Test
	public void testBookShopFindPriceByIsbn() {
		System.out.println(bookShopDao.findBookPriceByIsbn("1001"));
	}
	@Test
	public void testBookShopDaoUpdateBookStock() {
		bookShopDao.updateBookStock("1001");
	}
	@Test
	public void testBookShopDaoUpdateUserAccount() {
		bookShopDao.updateUserAccount("AA", 10);
	}
	
	
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
	
	
	/**
	 * 测试事务的传播属性
	 * cashierService.checkout的事务传播属性为REQUIRED，
	 * bookShopService.purchase的事务传播属性为REQUIRES_NEW
	 * 测试结果：客户买第一本书买成功了，但买第二本书时由于月不足抛出异常，整个过程数据库数据始终保持一致性！
	 * 
	 */
	@Test
	public void testCashierService() {
		cashierService.checkout("AA", Arrays.asList(new String[]{"1001", "1002"}));
	}
}
