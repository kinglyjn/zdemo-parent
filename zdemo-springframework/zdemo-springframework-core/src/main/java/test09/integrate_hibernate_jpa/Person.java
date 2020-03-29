package test09.integrate_hibernate_jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * jpa 实体类
 * 
 * @author zhangqingli
 *
 */
@Entity(name = "test09.integrate_hibernate_jpa.Person")
@Table(name = "t_person", uniqueConstraints={@UniqueConstraint(columnNames={"name", "age"})})
public class Person extends IdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;
	private Gender gender;
	private Date birthday;

	public Person() {
		super();
	}
	public Person(String name, Integer age, Gender gender, Date birthday) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.birthday = birthday;
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

	@Enumerated(EnumType.ORDINAL)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", gender=" + gender
				+ ", birthday=" + birthday + ", id=" + id + "]";
	}
}
