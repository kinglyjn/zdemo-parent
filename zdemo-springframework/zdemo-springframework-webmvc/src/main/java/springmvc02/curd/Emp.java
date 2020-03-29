package springmvc02.curd;

/**
 * entity emp
 * 
 * @author zhangqingli
 * @creation 2017年4月12日
 * 
 */
public class Emp {
	private Integer id;
	private String name;
	private String email;
	private Integer gender;
	private Dept dept;

	public Emp(String name, String email, Integer gender) {
		super();
		this.name = name;
		this.email = email;
		this.gender = gender;
	}

	public Emp(Integer id, String name, String email, Integer gender, Dept dept) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.dept = dept;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
}
