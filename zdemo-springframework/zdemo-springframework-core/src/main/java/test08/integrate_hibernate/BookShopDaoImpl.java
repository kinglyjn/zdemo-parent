package test08.integrate_hibernate;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * BookShopDao实现类
 * @author zhangqingli
 *
 */
@Repository("bookShopDao")
public class BookShopDaoImpl implements BookShopDao {
	@Autowired
	private SessionFactory sessionFactory; //可以获取和当前线程绑定的session
	
	
	@Override
	public int findBookPriceByIsbn(String isbn) {
		String hql = "select b.price from Book b where b.isbn=?";	
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setString(0, isbn);
		return (Integer) query.uniqueResult();
	}

	@Override
	public void updateBookStock(String isbn) {
		//验证书的库存是否充足 //mysql不支持check约束
		String hql2 = "select b.stock from BookStock b where b.isbn=?";
		Integer stock = (Integer) sessionFactory.getCurrentSession().createQuery(hql2).setString(0, isbn).uniqueResult();
		if (stock<=0) {
			throw new RuntimeException("库存不足！");
		}
		String hql = "update BookStock b set b.stock=b.stock-1 where b.isbn=?";
		sessionFactory.getCurrentSession().createQuery(hql).setString(0, isbn).executeUpdate();
	}

	@Override
	public void updateUserAccount(String username, int price) {
		//验证用户余额是否充足 //mysql不支持check约束
		String hql2 = "select a.balance from Account a where a.username=?";
		Integer balance = (Integer) sessionFactory.getCurrentSession().createQuery(hql2).setString(0, username).uniqueResult();
		if (balance < price) {
			throw new RuntimeException("余额不足！");
		}
		String hql = "update Account a set a.balance=a.balance-? where a.username=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setInteger(0, price).setString(1, username);
		query.executeUpdate();
	}

}
