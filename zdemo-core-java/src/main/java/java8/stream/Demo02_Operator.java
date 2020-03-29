package java8.stream;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 中间操作：
 * 多个中间操作连起来形成了一条数据处理流水线，除非在流水线上触发了终止操作，否则中间操作
 * 不会执行任何处理，而在终止操作时一次性全部处理，这称为“惰性取值”。中间操作包含如下几种：
 *
 * a) 筛选与切片：
 *    filter(predicate)             根据predicate从流中排除某些元素
 *    distinct()                    通过流所生成的hashCode()和equals()方法取出重复性的元素
 *    limit(maxSize)                根据maxSize设置的大小截断流（这称为“短路”，可以极大提高效率），使其元素不超过给定的数量
 *    skip(n)                       跳过元素，返回一个扔掉了前n个元素的流，若元素中元素不足n个则返回一个空流。它通常与limit(maxSize)互补使用。
 *
 * b) 映射：
 *    map(Function)                 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
 *    flatMap(Function)             接收一个函数作为参数，将流中的每个值都转换成另一个流，然后把所有的流连城一个流
 *    mapToDouble(ToDoubleFunction) 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的DoubleStream
 *    mapToInt(ToIntFunction)       接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的IntStream
 *    mapToLong(ToLongFunction)     接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的LongStream
 *
 * c) 排序：
 *    sorted()：产生一个新流，按自然顺序排序
 *    sorted(Comparator)：产生一个新流，按比较器顺序排序
 *
 */
public class Demo02_Operator {

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

    private List<Student> students = new ArrayList<>();
    @Before
    public void before() {
        students.add(new Student("张三", 23));
        students.add(new Student("李四", 24));
        students.add(new Student("王五", 30));
        students.add(new Student("赵六", 14));
        students.add(new Student("赵六", 34));
        students.add(new Student("田七", 47));
        students.add(new Student("周八", 50));
        students.add(new Student("臧九", 22));
    }


    /**
     * map & flatMap
     * 如果元素是流的话，map是将每个流加入到当前流中，flatMap是将每个流中的元素加入到当前流中
     * add(E) & addAll(Collection)
     * 如果元素是集合的话，add是将每个集合加入到当前集合中，addAll是将每个集合中的元素加入到当前集合中
     */
    @Test
    public void test01() {
        System.out.println("---------------map test---------------");
        students.stream()
                .distinct()
                .filter(v -> v.getAge() > 20)
                .skip(1)
                .limit(5)
                .map(v -> v.getName())
                .forEach(System.out::println);

        System.out.println("---------------map flat test---------------");
        List<String> ss = Arrays.asList("aaa", "bbb", "ccc");
        ss.stream()
                .flatMap(v -> getCharacters(v))
                .forEach(System.out::println); // {a,a,a,b,b,b,c,c,c}
    }

    private Stream<Character> getCharacters(String ss) {
        if (ss==null || ss.length()==0) {
            return null;
        }
        ArrayList<Character> resultList = new ArrayList<>();
        for (int i = 0; i < ss.length(); i++) {
            resultList.add(ss.charAt(i));
        }
        return resultList.stream();
    }

}
