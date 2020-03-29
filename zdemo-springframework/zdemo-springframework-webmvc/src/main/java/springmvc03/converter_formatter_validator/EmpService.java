package springmvc03.converter_formatter_validator;

import java.util.Collection;
import org.springframework.stereotype.Service;

/**
 * 模拟的EmpService
 * 
 * @author zhangqingli
 * @creation 2017年4月12日
 * 
 */
@Service("empService03")
public class EmpService {
	
	public void saveOrUpdate(Emp emp) {
		if (emp!=null && emp.getId()==null) {
			emp.setId(InitData.initId++);
		}
		emp.setDept(InitData.depts.get(emp.getDept().getId()));
		InitData.emps.put(emp.getId(), emp);
	}
	
	public void delete(Integer id) {
		InitData.emps.remove(id);
	}
	
	public Emp getEmp(Integer id) {
		Emp emp = InitData.emps.get(id);
		return emp;
	}
	
	public Collection<Emp> getAll() {
		return InitData.emps.values();
	}
}
