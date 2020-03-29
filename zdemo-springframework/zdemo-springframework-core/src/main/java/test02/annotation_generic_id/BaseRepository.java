package test02.annotation_generic_id;

/**
 * 泛型类BaseRepository
 * @author zhangqingli
 * @param <T>
 */
public class BaseRepository<T> {
	
	public void add(T t) {
		System.out.println("BaseRepository#add, t=" + t);
	}
}
