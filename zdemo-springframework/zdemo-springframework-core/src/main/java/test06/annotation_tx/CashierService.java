package test06.annotation_tx;

import java.util.List;

/**
 * 出纳员接口
 * @author zhangqingli
 *
 */
public interface CashierService {
	
	/**
	 * 一次买多本书
	 */
	void checkout(String username, List<String> isbns);
}
