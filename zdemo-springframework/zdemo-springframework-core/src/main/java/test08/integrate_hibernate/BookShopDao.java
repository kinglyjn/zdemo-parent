package test08.integrate_hibernate;

/**
 * BookShopDao接口
 * @author zhangqingli
 *
 */
public interface BookShopDao {
	//根据书号获取书的单价
	int findBookPriceByIsbn(String isbn);
	
	//更新数据库中书的库存，使书号对应的库存-1
	void updateBookStock(String isbn);
	
	//更新账户余额，使username对应的balance-price
	void updateUserAccount(String username, int price);
}
