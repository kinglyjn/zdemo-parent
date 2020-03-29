package test08.integrate_hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出纳员服务接口实现
 * @author zhangqingli
 *
 */
@Service("cashierService")
public class CashierServiceImpl implements CashierService {
	@Autowired
	private BookShopService bookShopService;
	
	@Override
	public void checkout(String username, List<String> isbns) {
		for (int i = 0; i < isbns.size(); i++) {
			bookShopService.sellBook(username, isbns.get(i));
		}
	}
}
