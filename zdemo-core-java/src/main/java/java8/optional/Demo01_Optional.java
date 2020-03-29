package java8.optional;

import org.junit.Test;

import java.util.Optional;
import java.util.Random;

/**
 * java.util.Optional<T>是一个容器类，代表一个值存在或者不存在。
 * 原来使用null表示一个值不存在，现在Optional可以更好地表达这个概
 * 念，并且可以很好地避免空指针异常。
 *
 *
 * 常用的方法：
 *
 * Optional.of(T t)：创建一个Optional实例（如果t为空则抛出空指针异常）
 * Optional.empty()：创建一个空的Optional实例
 * Optional.ofNullable(T t)：若t不为null则创建Optional实例，否则创建空实例
 *
 * orElse(T t)：如果调用对象包含值则返回该值，否则返回备用的t
 * orElseGet(Supplier s)：如果调用对象包含值则返回该值，否则返回s获取的值（与orElse的区别是值不为空则不执行s，这样可以大大节省程序的开销）
 * orElseThrow(Supplier s)：如果调用对象包含值则返回该值，否则抛出s定义的异常
 * get()：如果调用对象包含值则返回该值，否则抛出NoSuchElementException异常
 *
 * isPresent()：判断是否包含值
 * ifPresent(Consumer c)：判断是否包含值，包含则执行c定义的函数，不包含则不作任何处理
 *
 * map(Function f)：如果有值则对其处理并返回处理后的Optional，否则返回Optional.empty()
 * flatMap(Function mapper)：功能和用法与map类似，但要求返回的值必须是Optional
 * filter(Predicate p)：如果有值则判断p条件，满足p就返回处理后的Optional，否则都返回Optional.empty()
 * equals(Object o)：如果o也是一个Optioanl，并且两个Optional代表的值相同（都为null或equals为true），则返回true，否则返回false
 *
 */
public class Demo01_Optional {

    /**
     * 测试创建：of
     */
    @Test
    public void testOf() {
        Optional<Object> optional = Optional.of(null);
    }

    /**
     * 测试创建：empty
     */
    @Test
    public void testEmpty() {
        Optional<Object> optional = Optional.empty();
        System.out.println(optional); // Optional.empty
        System.out.println(optional.orElse(0)); // 0
    }

    /**
     * 测试创建：ofNullable
     */
    @Test
    public void testOfNullable() {
        Optional<Object> optional = Optional.ofNullable(null);
        System.out.println(optional); // Optional.empty
    }


    private Long genderateRandomLongNum() {
        System.out.println("执行genderateRandomLongNum方法");
        return new Random().nextLong();
    }

    /**
     * orElse
     */
    @Test
    public void testOrElse() {
        Object o = Optional.ofNullable(1L).orElse(genderateRandomLongNum());
        System.out.println(o.getClass() + " " + o); // class java.lang.Long 1，无论何时都执行genderateRandomLongNum
    }

    /**
     * orElseGet
     */
    @Test
    public void testOrElseGet() {
        Object o = Optional.ofNullable(1L).orElseGet(() -> genderateRandomLongNum());
        System.out.println(o.getClass() + " " + o); // class java.lang.Long 1，此时不执行genderateRandomLongNum，节省了开销
    }

    /**
     * orElseThrow
     */
    @Test
    public void testOrElseThrow() {
        Object o = Optional.ofNullable(null).orElseThrow(() -> new IllegalArgumentException("不合法的参数"));
        System.out.println(o);
    }

    /**
     * get
     */
    @Test
    public void testGet() {
        Object o = Optional.ofNullable(null).get();
        System.out.println(o);
    }

    /**
     * isPresent
     */
    @Test
    public void testIsPresent() {
        Optional<Object> optional = Optional.ofNullable(null); // 等价于 Optional.empty()
        boolean isPresent = optional.isPresent();
        System.out.println(isPresent); // false
    }

    /**
     * ifPresent
     */
    @Test
    public void testIfPresent() {
        Optional.ofNullable(1L).ifPresent(v -> System.out.println(v));
    }

    /**
     * map & flatMap
     */
    @Test
    public void testMap() {
        Optional.ofNullable(null).map(v -> (Long)v*10).ifPresent(System.out::println);
    }

    /**
     * filter
     */
    @Test
    public void test() {
        Optional.ofNullable(1L).filter(v -> (Long)v > 30L).ifPresent(System.out::println);
    }

    /**
     * equals
     */
    @Test
    public void testO() {
        Optional<Long> optional01 = Optional.ofNullable(0L);
        Optional<Long> optional02 = Optional.ofNullable(0L);
        Optional<Object> optional03 = Optional.ofNullable(null);
        Optional<Object> optional04 = Optional.ofNullable(null);

        System.out.println(optional01.equals(optional02)); // true
        System.out.println(optional03.equals(optional04)); // true
        System.out.println(optional01.equals(optional03)); // false
    }
}
