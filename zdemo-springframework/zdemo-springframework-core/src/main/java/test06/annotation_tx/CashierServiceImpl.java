package test06.annotation_tx;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出纳员接口实现
 * @author zhangqingli
 *
 */
@Service("cashierService")
public class CashierServiceImpl implements CashierService {
	
	@Autowired
	private BookShopService bookShopService;
	
	
	@Transactional
	@Override
	public void checkout(String username, List<String> isbns) {
		for (int i = 0; i < isbns.size(); i++) {
			bookShopService.sellBook(username, isbns.get(i));
		}
	}
}



