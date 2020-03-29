package test06.annotation_tx;

/**
 * BookShopDao接口
 * @author zhangqingli
 *
 */
public interface BookShopDao {
	int findBookPriceByIsbn(String isbn);
	void updateBookStock(String isbn);
	void updateUserAccount(String username, int price);
}
