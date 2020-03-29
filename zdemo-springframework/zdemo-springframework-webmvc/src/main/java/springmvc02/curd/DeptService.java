package springmvc02.curd;

import java.util.Collection;
import org.springframework.stereotype.Service;

/**
 * 模拟的DeptService
 * 
 * @author zhangqingli
 * @creation 2017年4月12日
 *
 */
@Service
public class DeptService {
	
	public Collection<Dept> getAll() {
		return InitData.depts.values();
	}
	
	public Dept getDept(Integer id) {
		return InitData.depts.get(id);
	}
}
