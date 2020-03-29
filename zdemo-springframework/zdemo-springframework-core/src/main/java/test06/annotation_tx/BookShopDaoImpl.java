package test06.annotation_tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * BookShopDao实现类
 * @author zhangqingli
 *
 */
@Repository("bookShopDao")
public class BookShopDaoImpl implements BookShopDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public int findBookPriceByIsbn(String isbn) {
		String sql = "SELECT PRICE FROM T_BOOK WHERE ISBN=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, isbn);
	}

	@Override
	public void updateBookStock(String isbn) {
		//检查书的库存是否足够，若不够则抛出异常
		String sql2 = "SELECT STOCK FROM T_BOOK_STOCK WHERE ISBN=?";
		int stock = jdbcTemplate.queryForObject(sql2, Integer.class, isbn);
		if (stock<=0) {
			throw new RuntimeException("库存不足！");
		}
		
		String sql = "UPDATE T_BOOK_STOCK SET STOCK=STOCK-1 WHERE ISBN=?";
		jdbcTemplate.update(sql, isbn);
	}

	@Override
	public void updateUserAccount(String username, int price) {
		//检查用户的余额是否足够，若不够则抛出异常
		String sql2 = "SELECT BALANCE FROM T_ACCOUNT WHERE USERNAME=?";
		int balance = jdbcTemplate.queryForObject(sql2, Integer.class, username);
		if (balance<=price) {
			throw new RuntimeException("余额不足！");
		}
		
		String sql = "UPDATE T_ACCOUNT SET BALANCE=BALANCE-? WHERE USERNAME=?";
		jdbcTemplate.update(sql, price, username);
	}

}
