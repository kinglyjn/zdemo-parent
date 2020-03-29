package test11.integrate_mybatis;

import java.io.Serializable;
import java.util.Date;

/**
 * User
 * @author zhangqingli
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int age;
	private Date birthday;
	
	public User(String name, int age, Date birthday) {
		super();
		this.name = name;
		this.age = age;
		this.birthday = birthday;
	}
	public User() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age
				+ ", birthday=" + birthday + "]";
	}
}