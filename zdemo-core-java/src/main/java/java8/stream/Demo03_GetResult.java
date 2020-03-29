package java8.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流的终止操作：
 * 终止操作会从流的流水线生成结果，其结果是任何不是流的值。如list、integer、void等。
 *    reduce(T identity, BinaryOperator b) &  reduce(BinaryOperator b)：将流中元素反复结合运算得到一个归约值
 *    collect(Collector) &  collect(Supplier)：将流转换为其他形式，接收一个Collector接口的实现，通常用于给Stream中元素做聚合汇总
 *    forEach(consumer)：根据consumer操作进行内部迭代
 *    allMatch(predicate)：检查是否匹配所有元素
 *    anyMatch(predicate)：检查是否至少匹配一个元素
 *    noneMatch(predicate)：检查是否没有匹配的元素
 *    findFirst()：返回第一个元素
 *    findAny()：返回当前流中的任意一个元素
 *    count：返回流中元素的总个数
 *    max(Comparator)：返回流中最大的那个元素
 *    min(Comparator)：返回流中最小的那个元素
 *
 */
public class Demo03_GetResult {
    static class Student {
        private String name;
        private Integer age;

        public Student() {}
        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }
        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Student [name=" + name + ", age=" + age + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return Objects.equals(name, student.name) &&
                    Objects.equals(age, student.age);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
    private List<Demo02_Operator.Student> students = new ArrayList<>();
    @Before
    public void before() {
        students.add(new Demo02_Operator.Student("张三", null));
        students.add(new Demo02_Operator.Student("李四", 24));
        students.add(new Demo02_Operator.Student("王五", 30));
        students.add(new Demo02_Operator.Student("赵六", 14));
        students.add(new Demo02_Operator.Student(null, null));
        students.add(new Demo02_Operator.Student("田七", 47));
        students.add(new Demo02_Operator.Student("周八", 50));
        students.add(new Demo02_Operator.Student("臧九", null));
    }


    /**
     * reduce
     */
    @Test
    public void testReduce() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer numSum = nums.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(numSum);

        Integer totalAge = students.stream()
                .map(Demo02_Operator.Student::getAge)
                .reduce((x, y) -> Optional.ofNullable(x).orElse(0) + Optional.ofNullable(y).orElse(0)).get();
        System.out.println(totalAge);
    }

    /**
     * collect
     */
    @Test
    public void testCollect() {
        System.out.println("收集所有学生的名字收集到list中>>");
        List<String> names = students.stream()
                .map(v -> v.getName())
                .collect(Collectors.toList());
        System.out.println(names);

        System.out.println("收集所有学生的名字收集到定制化的LinkedList中>>");
        List<String> names2 = students.stream()
                .map(v -> v.getName())
                .collect(Collectors.toCollection(LinkedList<String>::new));
        System.out.println(names2);

        System.out.println("取出学生姓名并拼接成指定格式的字符串>>");
        String collectionJoinName = students.stream()
                .map(v -> v.getName())
                .collect(Collectors.joining(",", "begin>>", "<<end"));
        System.out.println(collectionJoinName);

        System.out.println("将结果收集为学生总数。除此之外还可以求 总和值、最大值、最小值、平均值等>>");
        Long studentCount = students.stream()
                .collect(Collectors.counting());
        System.out.println(studentCount);

        System.out.println("将结果收集为统计表>>");
        IntSummaryStatistics statistics = students.stream()
                .collect(Collectors.summarizingInt(v -> Optional.ofNullable(v.getAge()).orElse(0)));
        System.out.println(statistics);

        System.out.println("根据学生的年龄段进行分组>>");
        Map<String, List<Demo02_Operator.Student>> studentGroups = students.stream()
                .collect(Collectors.groupingBy(v -> {
                    Integer age = v.getAge();
                    if (age == null) {
                        return "未知";
                    } else if (age < 20) {
                        return "少年";
                    } else if (age < 30) {
                        return "青年";
                    } else if (age < 50) {
                        return "壮年";
                    } else {
                        return "老年";
                    }
                }));
        studentGroups.forEach((k,v) -> System.out.println(k + ":" + v));

        System.out.println("根据学生年龄是否满足条件进行分区>>");
        Map<Boolean, List<Demo02_Operator.Student>> studentPartitions = students.stream()
                .collect(Collectors.partitioningBy(v -> Optional.ofNullable(v.getAge()).orElse(0) > 30));
        studentPartitions.forEach((k,v) -> System.out.println(k + ":" + v));

        System.out.println("根据学生年龄分区，然后对每个分区根据姓名分组>>");
        Map<Boolean, Map<String, List<Demo02_Operator.Student>>> studentPartitions2 = students.stream()
                .collect(
                        Collectors.partitioningBy(
                                v -> Optional.ofNullable(v.getAge()).orElse(0) > 30,
                                Collectors.groupingBy(v -> Optional.ofNullable(v.getName()).orElse(""))
                        )
                );
        studentPartitions2.forEach((pk,pv) -> {
            System.out.println("分区[" + pk + "]结果：");
            pv.forEach((gk,gv) -> {
                System.out.println("组["+gk+"]数据：");
                System.out.println(gv);
            });
        });
    }

    /**
     * forEach
     */
    @Test
    public void testForEach() {
        students.stream().map(Demo02_Operator.Student::getName)
                .forEach(System.out::println);
    }

    /**
     * allMatch, anyMatch, noneMatch
     */
    @Test
    public void testAllMatch() {
        boolean result = students.stream().map(Demo02_Operator.Student::getAge)
                .allMatch(v -> v==null ? false : v>12);
        System.out.println(result);
    }

    /**
     * findFirst, findAny
     */
    @Test
    public void testFindFirst() {
        Optional<Demo02_Operator.Student> operator = students.stream().findFirst();
        Demo02_Operator.Student student = operator.get();
        System.out.println(student);
    }

    /**
     * max, min
     */
    @Test
    public void testMax() {
        Demo02_Operator.Student maxAgeStudent = students.stream()
                .max((s1, s2) ->
                        Integer.compare(
                                Optional.ofNullable(s1.getAge()).orElse(Integer.MIN_VALUE),
                                Optional.ofNullable(s2.getAge()).orElse(Integer.MIN_VALUE)
                        )
                ).get();
        System.out.println(maxAgeStudent);
    }

    /**
     * count
     */
    @Test
    public void testCount() {
        long count = students.stream().count();
        System.out.println(count);
    }

    /**
     * 流的转换
     */
    @Test
    public void testStreamTransform() {
        List<String> result = students.stream()
                .mapToInt(v -> Optional.ofNullable(v.getAge()).orElse(0))
                .asLongStream()
                .mapToDouble(v -> v * 1.0)
                .boxed() // 类似于自动装箱
                .mapToLong(v -> v.longValue())
                .mapToObj(v -> v + "%")
                .collect(Collectors.toList());
        System.out.println(result);

    }



}
