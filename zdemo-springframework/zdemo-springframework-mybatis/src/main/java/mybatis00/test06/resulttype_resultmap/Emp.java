package mybatis00.test06.resulttype_resultmap;

import java.io.Serializable;

/**
 * 员工表
 * @author zhangqingli
 *
 */
public class Emp implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	private Dept dept;
	
	public Emp(String lastName, String email, String gender) {
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}
	public Emp() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	@Override
	public String toString() {
		return "Emp [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", dept=" + dept
				+ "]";
	}
}
