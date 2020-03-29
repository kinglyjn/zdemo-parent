package test10.integrate_hibernate_springdata_jpa;

import javax.persistence.*;
import java.util.Date;

/**
 * User entity
 * 
 * @author zhangqingli
 *
 */
@Entity(name = "tt_user")
@Table(name = "tt_user")
public class User {

	private Integer id;
	private String name;
	private Integer age;
	private Date birthday;

	
	public User() {
		super();
	}

	public User(String name, Integer age, Date birthday) {
		this.name = name;
		this.age = age;
		this.birthday = birthday;
	}

	
	@Id
	@GeneratedValue
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
