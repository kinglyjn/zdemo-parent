package springmvc03.converter_formatter_validator;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * entity emp
 * 
 * @author zhangqingli
 * @creation 2017年4月12日
 * 
 */
public class Emp {
	private Integer id;
	
	@NotEmpty //hb-validator校验
	private String name;
	
	@Email //hb-validator校验
	private String email;
	private Integer gender;
	private Dept dept;
	
	@Past //jsr303
	@DateTimeFormat(pattern="yyyy-MM-dd") //时间格式化器
	private Date birthday;
	
	@NumberFormat(pattern="#,###,#")
	private Float salary;

	
	public Emp() {
		super();
	}
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
	public Emp(Integer id, String name, String email, Integer gender,
			Dept dept, Date birthday, Float salary) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.dept = dept;
		this.birthday = birthday;
		this.salary = salary;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}


	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", email=" + email
				+ ", gender=" + gender + ", dept=" + dept + ", birthday="
				+ birthday + ", salary=" + salary + "]";
	}
}
