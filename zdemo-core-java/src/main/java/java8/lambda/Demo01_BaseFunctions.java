package java8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda表达式使用的条件：
 *
 * 注意只有函数接口才能使用lambda来表达，函数式接口就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法
 * 的接口。通常函数接口就是只有一个方法(不包括接口中的默认方法、静态方法、Object类中的方法)的接口，该接口
 * 可以用 @FunctionalInterface 来修饰。lambda表达式会极大简化 接口对于匿名实现类 的开发，使代码更优雅。
 *
 * 四大核心函数式接口：
 * 消费接口：Consumer<T> { void accept(T t); }
 * 供给接口：Supplier<T> { T get(); }
 * 断言接口：Predicate<T> { boolean test(T t); }
 * 函数接口：Function<T,R> { R apply(T t); }
 *
 * lambda语法糖的语法：
 * 左侧为一括号省，右侧为一括号省。（能省就省）
 *
 */
public class Demo01_BaseFunctions {

    /**
     * 消费接口
     *
     */
    @Test
    public void test_consumer_01() {
        consumer_f01(1,v -> System.out.println(v) );
    }
    private void consumer_f01(Integer id, Consumer<Integer> consumer) {
        consumer.accept(id);
    }


    /**
     * 供给接口
     *
     */
    @Test
    public void test_supplier_01() {
        List<Integer> list = supplier_f01(10, () -> (int) (Math.random()*100) );
        System.out.println(list);
    }
    private List<Integer> supplier_f01(Integer size, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i=0; i<size; i++) {
            list.add(supplier.get());
        }
        return list;
    }


    /**
     * 断言接口
     *
     */
    @Test
    public void test_predicate_01() {
        List<String> resultList = filerStr(Arrays.asList("a", "demo01_hello", "world"), (v) -> v.length()>2 );
        System.out.println(resultList);
    }
    private List<String> filerStr(List<String> ss, Predicate<String> predicate) {
        List<String> resultList = new LinkedList<>();
        for (String s : ss) {
            if (predicate.test(s)) {
                resultList.add(s);
            }
        }
        return resultList;
    }


    /**
     * 函数接口
     *
     */
    @Test
    public void test_function_01() {
        String resultStr = handleStr("我只要前三个字符", v -> v.substring(0, 3));
        System.out.println(resultStr);
    }
    private String handleStr(String in, Function<String,String> function) {
        return function.apply(in);
    }


    /**
     * 自定义lambda接口
     *
     */
    @Test
    public void test_custom_01() {
        supermanFly(() -> System.out.println("我是超人我会飞"));
    }
    @FunctionalInterface
    interface Superman {
        void fly();
    }
    private void supermanFly(Superman superman) {
        superman.fly();
    }
}
