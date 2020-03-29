package test09.integrate_hibernate_jpa;

/**
 * 性别枚举
 * @author zhangqingli
 *
 */
public enum Gender {
	FEMALE("女性", 0), MALE("男性", 1); 
	
	private String name;
	private int index;
	private Gender(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public String getName() {
		return name;
	}
	public int getIndex() {
		return index;
	}
}
