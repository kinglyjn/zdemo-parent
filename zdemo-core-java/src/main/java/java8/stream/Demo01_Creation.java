package java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 什么是Stream流？
 * 流是一种数据的渠道，用于操作数据源（集合、数组等）所生成的元素序列。
 * 注意stream自己不会存储元素，它也不会改变源对象，而是返回一个持有结果的新的stream。
 * stream操作是延迟执行的。这意味着它们会等到需要结果的时候才执行。
 *
 * stream操作三个步骤：
 * a) 创建stream，一个数据源（集合、数组等）获取一个流
 * b) 中间操作，一个中间操作链，对数据源的数据进行处理
 * c) 终止操作，一个终止操作，执行中间操作链并产生结果
 *
 * 创建流的四种方法：
 * 1. 通过Collection系列集合提供的 stream()获取串行流或parallelStream()获取并行流
 * 2. 通过 Arrays.stream(array..) 方法获取数组流
 * 3. 通过 Stream.of(..) 方法获取流
 * 4. 通过 Stream.iterate(seed, unaryOperator)或Stream.generate(supplier)方法 创建无限流
 *
 */
public class Demo01_Creation {

    /**
     * 创建流：
     * 创建流的四种方法
     */
    @Test
    public void test01() {
        // 1
        Stream<String> stream11 = new ArrayList<String>().stream();
        Stream<String> stream12 = new ArrayList<String>().parallelStream();
        System.out.println(stream11.getClass()); // java.util.stream.ReferencePipeline$Head
        System.out.println(stream12.getClass()); // java.util.stream.ReferencePipeline$Head

        // 2
        Stream<Integer> stream21 = Arrays.stream(new Integer[10]);
        System.out.println(stream21.getClass()); // java.util.stream.ReferencePipeline$Head

        // 3
        Stream<String> stream31 = Stream.of("aa", "bb", "cc");
        System.out.println(stream31.getClass()); // java.util.stream.ReferencePipeline$Head

        // 4
        Stream<Integer> stream41 = Stream.iterate(0, x -> x + 2);
        System.out.println(stream41.getClass()); // java.util.stream.ReferencePipeline$Head
        Stream<Double> stream51 = Stream.generate(() -> Math.random());
        System.out.println(stream51.getClass()); // java.util.stream.ReferencePipeline$Head
    }

}
