package mybatis00.test06.resulttype_resultmap;

public interface DeptMapper {
	Dept findDeptById(Integer id);
	Dept findDeptByIdReturnedDeptWithEmpStepMode(Integer id);
}
