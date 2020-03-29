package test02.annotation_generic_id;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 泛型类BaseService
 * @author zhangqingli
 * @param <T>
 */
public class BaseService<T> {
	
	@Autowired
	protected BaseRepository<T> baseRepository;
	
	public void add(T t) {
		System.out.println("BaseService#add, use repository add, and repository is " + baseRepository);
		baseRepository.add(t);
	}
}
