package java8.lambda;

import org.junit.Test;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用：
 * 若lambda体中的逻辑已经有方法实现了，我们可以使用“方法引用”，可以理解为 方法引用其实是lambda表达式的另外一种表现形式。
 *
 * 方法引用主要有以下三种语法格式：
 * a) 对象::实例方法名  普通对象的实例方法
 * b) 类::静态方法名    普通类的静态方法
 * c) 类::实例方法名    若lambda参数列表中的第一个参数是实例方法的调用者，第二个参数是实例方法的参数时，可以使用class::method
 *
 *
 * 构造器引用：
 * Classname::new
 *
 *
 * 数组引用：
 * ElementType[]::new
 *
 *
 * 注意上述无论是哪种引用方式，lambda参数列表和返回值类型，都必须要与函数式接口中抽象方法的函数列表和返回值保持一致！
 *
 */
public class Demo02_Method_And_Constructor_Ref {

    /**
     * 对象::实例方法名
     */
    @Test
    public void test01() {
        ////
        Consumer<String> consumer01 = (v) -> System.out.println(v);
        consumer01.accept("哈哈");

        Consumer<String> consumer02 = System.out::println;
        consumer02.accept("呵呵");


        ////
        Student01 student = new Student01(1, "张三");
        Supplier<String> supplier01 = () -> student.getName();
        System.out.println("student name: " + supplier01.get());

        Supplier<Integer> supplier02 = student::getId;
        System.out.println("student id: " + supplier02.get());
    }
    static class Student01 {
        private Integer id;
        private String name;
        public Student01(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        public Integer getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }


    /**
     * 类::静态方法名
     */
    @Test
    public void test02() {
        Comparator<Integer> comparator01 = (x,y) -> Integer.compare(x, y);
        System.out.println(comparator01.compare(1, 2));

        Comparator<Integer> comparator02 = Integer::compare;
        System.out.println(comparator02.compare(2, 1));
    }


    /**
     * 类::实例方法名
     */
    @Test
    public void test03() {
        BiPredicate<String,String> predicate01 = (x,y) -> x.equals(y);
        System.out.println(predicate01.test("a", "b"));

        BiPredicate<String,String> predicate02 = String::equals;
        System.out.println(predicate02.test("a", "b"));
    }


    /**
     * 构造器的引用
     * Classname::new
     */
    @Test
    public void test04() {
        ////
        Supplier<Student02> supplier01 = () -> new Student02();
        System.out.println(supplier01.get());
        Supplier<Student02> supplier02 = Student02::new;
        System.out.println(supplier02.get());

        ////
        Function<Integer,Student02> function01 = (id) -> new Student02(id);
        System.out.println(function01.apply(1).id);
        Function<Integer,Student02> function02 = Student02::new;
        System.out.println(function02.apply(2).id);
    }
    static class Student02 {
        Integer id;
        public Student02() {}
        public Student02(Integer id) {
            this.id = id;
        }
    }

    /**
     * 数组引用：
     * ElementType[]:new
     */
    @Test
    public void test05() {
        Function<Integer,String[]> function01 = (length) -> new String[length];
        String[] ss01 = function01.apply(10);
        System.out.println(ss01.length);

        Function<Integer,String[]> function02 = String[]::new;
        String[] ss02 = function02.apply(20);
        System.out.println(ss02.length);
    }
}
