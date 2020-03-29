package test08.integrate_hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BookShopService实现类
 * @author zhangqingli
 *
 */
@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
	@Autowired
	private BookShopDao bookShopDao;
	
	
	@Override
	public void sellBook(String username, String isbn) {
		//得到书的单价
		int price = bookShopDao.findBookPriceByIsbn(isbn);
		
		//更新库存
		bookShopDao.updateBookStock(isbn);
		
		//更新用户余额
		bookShopDao.updateUserAccount(username, price);
	}
}
