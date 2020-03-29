package test05.spring_jdbc;

public class Emp {
	private Integer id;
	private String name;
	private double salary;
	private Dept dept;
	
	public Emp(String name, double salary) {
		super();
		this.name = name;
		this.salary = salary;
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
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", salary=" + salary + ", dept=" + dept + "]";
	}
}
