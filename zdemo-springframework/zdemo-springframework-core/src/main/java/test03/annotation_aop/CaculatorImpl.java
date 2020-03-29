package test03.annotation_aop;

import org.springframework.stereotype.Component;


/**
 * 计算器功能实现类（保持其清纯性）
 * @author zhangqingli
 *
 */
@Component
public class CaculatorImpl implements Caculator {

	@Override
	public int add(int i, int j) {
		return i+j;
	}

	@Override
	public int minus(int i, int j) {
		return i-j;
	}

	@Override
	public int multi(int i, int j) {
		return i*j;
	}

	@Override
	public int div(int i, int j) {
		return i/j;
	}
}
