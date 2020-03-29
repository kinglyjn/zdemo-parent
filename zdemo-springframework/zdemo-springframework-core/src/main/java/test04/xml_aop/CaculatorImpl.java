package test04.xml_aop;

/**
 * Caculator实现类
 * @author zhangqingli
 *
 */
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
