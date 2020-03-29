package springmvc02.curd;

/**
 * entity dept
 * 
 * @author zhangqingli
 * @creation 2017年4月12日
 *
 */
public class Dept {
	private Integer id;
	private String name;

	
	public Dept(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Dept(String name) {
		super();
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
}
