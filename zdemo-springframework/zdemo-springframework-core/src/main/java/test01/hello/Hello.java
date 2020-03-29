package test01.hello;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 测试各种注入方式类
 * @author zhangqingli
 *
 */
public class Hello {
	private Integer num;
	private String str;
	private Double doubleValue;
	private String[] strArray;
	private List<String> list;
	private Set<Integer> set; 
	private Map<String, Integer> map;
	private Properties properties;
	
	//构造方法
	public Hello() {
		System.out.println("class Hello: " + this.hashCode() + ": 无参构造器");
	}
	public Hello(Integer num, String str) {
		System.out.println("class Hello: " + this.hashCode() + ": 有参构造器");
		this.num = num;
		this.str = str;
	}

	//IOC初始化和销毁时执行的方法
	public void myInit() {
		System.out.println("class Hello: " + this.hashCode() + "#myInit");
	}
	public void myDestroy() {
		System.out.println("class Hello: " + this.hashCode() + "#myDestory");
	}
	
	//set方法
	public void setNum(Integer num) {
		System.out.println("class Hello: " + this.hashCode() + "#setNum");
		this.num = num;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public void setStrArray(String[] strArray) {
		this.strArray = strArray;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public void setSet(Set<Integer> set) {
		this.set = set;
	}
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	//get方法
	public Integer getNum() {
		return num;
	}
	public String getStr() {
		return str;
	}
	public Double getDoubleValue() {
		return doubleValue;
	}
	public String[] getStrArray() {
		return strArray;
	}
	public List<String> getList() {
		return list;
	}
	public Set<Integer> getSet() {
		return set;
	}
	public Map<String, Integer> getMap() {
		return map;
	}
	public Properties getProperties() {
		return properties;
	}
	
	@Override
	public String toString() {
		return "Hello [num=" + num + ", str=" + str + ", doubleValue="
				+ doubleValue + ", strArray=" + Arrays.toString(strArray)
				+ ", list=" + list + ", set=" + set + ", map=" + map
				+ ", properties=" + properties + "]";
	}
}
