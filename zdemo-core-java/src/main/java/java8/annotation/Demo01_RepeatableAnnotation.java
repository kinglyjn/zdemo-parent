package java8.annotation;

import org.junit.Test;

import java.lang.annotation.*;
import java.util.Collection;

/**
 * 可重复注解
 * 自从Java 5中引入注解以来，这个特性开始变得非常流行，并在各个框架和项目中被广泛使用。
 * 不过，传统注解有一个很大的限制是：在同一个地方不能多次使用同一个注解。Java 8 打破了
 * 这个限制，引入了重复注解的概念，允许在同一个地方多次使用同一个注解。在 Java 8 中使
 * 用 @Repeatable 注解定义重复注解，实际上，这并不是语言层面的改进，而是编译器做的一
 * 个trick，底层的技术仍然相同。
 */
public class Demo01_RepeatableAnnotation {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    static @interface Filters {
        Filter[] value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Filters.class)
    static @interface Filter {
        String value();
    }

    @Filter("filter01")
    @Filter("filter02")
    static interface Filterable<T> {
        void filter(Collection<T> collection);
    }


    @Test
    public void test01() {
        // 获取某个类型的重复注解
        Filter[] filters = Filterable.class.getAnnotationsByType(Filter.class);
        for (Filter filter : filters) {
            System.out.println(filter.value()); // filter1 filter2
        }

        System.out.println("由于Filterable接口使用了两个相同的@Filter注解，相当于使用Filters注解，故此处得到的值为null>>");
        Filter filter = Filterable.class.getAnnotation(Filter.class);
        System.out.println(filter); // null

        System.out.println("返回值为 @xxx$Filters(value=...>>");
        Filters fs = Filterable.class.getAnnotation(Filters.class);
        System.out.println(fs);
    }
}
