package mybatis00.test06.resulttype_resultmap;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

public interface EmpMapper {
	List<Emp> findEmpsByDeptId(Integer deptId);
	List<Emp> findEmpByLastnameLike(String lastName);
	Map<String,Object> findEmpByIdWithReturnedMap(Integer id);
	@MapKey("id")
	Map<Integer,Emp> findEmpByIdWithReturnedMap2();
	
	Emp findEmpWithDeptByEmpId(Integer id);
	List<Emp> findAllEmpWithDept();
	
	Emp findEmpByIdReturnedEmpWithDeptStepMode(Integer id);
}
