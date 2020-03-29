package mybatis00.test06.resulttype_resultmap;

import java.io.Serializable;
import java.util.List;

/**
 * 部门表
 * @author zhangqingli
 *
 */
public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String deptName;
	private List<Emp> emps;
	
	public Dept() {
		super();
	}
	public Dept(String deptName) {
		this.deptName = deptName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<Emp> getEmps() {
		return emps;
	}
	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}
	
	@Override
	public String toString() {
		return "Dept [id=" + id + ", deptName=" + deptName + "]";
	}
}
