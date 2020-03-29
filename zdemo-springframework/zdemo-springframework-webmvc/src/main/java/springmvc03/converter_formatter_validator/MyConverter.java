package springmvc03.converter_formatter_validator;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 
 * @author zhangqingli
 * @creation 2017年4月12日 
 *
 */
@Component("myConverter")
public class MyConverter implements Converter<String, Emp> {

	/**
	 * 自定义类型转化方法
	 * 
	 */
	@Override
	public Emp convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		String[] splits = source.split("-");
		if (splits.length!=4) {
			return null;
		}
		return new Emp(Integer.parseInt(splits[0]), splits[1], splits[2], Integer.parseInt(splits[3]), null);
	}
}
