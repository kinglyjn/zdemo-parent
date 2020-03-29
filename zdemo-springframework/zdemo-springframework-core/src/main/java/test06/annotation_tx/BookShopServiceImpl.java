package test06.annotation_tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookShopService实现类 
 * @author zhangqingli
 *
 */
@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
	
	@Autowired
	private BookShopDao bookShopDao;
	
	
	/**
	 * 添加事务注解
	 * 
	 * 事务的传播性为：
	 * 使用propagation指定事务的传播行为，即当前事务方法被另外一个事务方法调用时候如何使用事务
	 * 1. 默认值为REQUIRED，即使用调用方法的事务
	 * 2. REQUIRES_NEW: 表示事务自己的事务，调用方法的事务被挂起
	 *    以上两种事务传播属性是使用较多的。此外事务的传播属性还有：
	 * 3. SUPPORTS: 如果有事务在运行，当前的方法就在这个事务内运行，否则可以不运行在事务中
	 * 4. NOT_SUPPORTED: 当前的方法不应运行在事务中，如果有运行的事务，则将它挂起
	 * 5. MANDATORY: 当前方法必须运行在事务内，如果没有正在运行的事务就抛出异常
	 * 6. NEVER: 当前方法不应运行在事务内，如果有运行的事务，就抛出异常
	 * 7. NESTED: 如果有事务在运行，当前的方法就应该在这个事务的嵌套事务内运行，
	 * 			  否则就启动一个新的事务并在自己的事务内运行
	 * 
	 * REQUIRES_NEW 和 NESTED 的区别：
	 * REQUIRES_NEW 启动一个新的, 不依赖于环境的 "内部" 事务. 这个事务将被完全commited 或 rolled back 而不依赖于外部事
	 * 务, 它拥有自己的隔离范围, 自己的锁, 等等。当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行。
	 * 另一方面, NESTED 开始一个 "嵌套的" 事务, 它是已经存在事务的一个真正的子事务. 嵌套事务开始执行时, 它将取得一个savepoint. 
	 * 如果这个嵌套事务失败, 我们将回滚到此savepoint. 嵌套事务是外部事务的一部分, 只有外部事务结束后它才会被提交。由此可见, 
	 * REQUIRES_NEW 和 NESTED 的最大区别在于, REQUIRES_NEW 完全是一个新的事务, 而 NESTED 则是外部事务的子事务, 如果外部
	 * 事务 commit, 嵌套事务也会被 commit, 这个规则同样适用于 rollback。
	 * 
	 * 
	 * 事务的隔离界别：
	 * 最常用的是READ_COMMITTED
	 * 此外还有READ_UNCOMMITTED、REPEATABLE_READ、SERIALIZABLE
	 * 
	 * 
	 * 事务对那些异常回滚：
	 * 默认情况下spring的声明式事务对所有的运行时异常进行回滚，通常取默认值即可
	 * rollbackFor: 指定事务对哪些异常进行回滚
	 * noRollbackFor: 指定事务对那些异常不进行回滚
	 * 
	 * 
	 * 只读属性：
	 * 使用readOnly指定事务是否为只读，表示这个事务是否只读取数据但不更新数据，
	 * 这样可以帮助数据库引擎优化事务，提高程序的执行速度
	 * 
	 * 过期时间：
	 * 使用timeout指定强制回滚事务之前事务最多可以占用的时间
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW, 
			isolation=Isolation.READ_COMMITTED, 
			rollbackFor={}, noRollbackFor={}, 
			readOnly=false, 
			timeout=3)
	@Override
	public void sellBook(String username, String isbn) {
		// 1.获取书的单价
		int price = bookShopDao.findBookPriceByIsbn(isbn);
		
		// 2.更新书的库存
		bookShopDao.updateBookStock(isbn);
		
		// 3.更新用户余额
		bookShopDao.updateUserAccount(username, price);
	}
}

