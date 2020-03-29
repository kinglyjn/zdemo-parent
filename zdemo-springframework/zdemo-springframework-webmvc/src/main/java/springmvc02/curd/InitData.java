package springmvc02.curd;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化数据
 * @author zhangqingli
 * @creation 2017年4月12日 
 *
 */
public class InitData {
	public static Map<Integer, Dept> depts = null;
	
	public static Map<Integer, Emp> emps = null;
	public static Integer initId = 1006;
	
	
	static {
		depts = new HashMap<Integer, Dept>();
		depts.put(101, new Dept(101, "D-AA"));
		depts.put(102, new Dept(102, "D-BB"));
		depts.put(103, new Dept(103, "D-CC"));
		depts.put(104, new Dept(104, "D-DD"));
		depts.put(105, new Dept(105, "D-EE"));
		
		emps = new HashMap<Integer, Emp>();
		emps.put(1001, new Emp(1001, "E-AA", "aa@keyllo.com", 1, depts.get(101)));
		emps.put(1002, new Emp(1002, "E-BB", "bb@keyllo.com", 0, depts.get(102)));
		emps.put(1003, new Emp(1003, "E-CC", "cc@keyllo.com", 1, depts.get(103)));
		emps.put(1004, new Emp(1004, "E-DD", "dd@keyllo.com", 0, depts.get(104)));
		emps.put(1005, new Emp(1005, "E-EE", "ee@keyllo.com", 1, depts.get(105)));
	}
	
}
