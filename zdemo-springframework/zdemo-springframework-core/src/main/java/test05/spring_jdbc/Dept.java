package test05.spring_jdbc;

public class Dept {
	private Integer id;
	private String name;
	
	
	public Dept(String name) {
		this.name = name;
	}
	public Dept() {
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

	
	@Override
	public String toString() {
		return "Dept [id=" + id + ", name=" + name + "]";
	}
}
