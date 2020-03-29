package java8.interfaze;

import org.junit.Test;

/**
 * 接口默认方法
 *
 * 接口默认方法的“类优先”原则：
 * 若一个接口中定义了一个默认方法，而另外一个父类或接口中又定义了一个同名的方法时，那么：
 * a) 选择父类中的方法。如果一个父类提供了具体的实现，那么接口中具有相同名称和参数的默认方法会被忽略。
 * b) 如果两个接口的方法产生冲突时，实现这两个接口的实现类必须使用覆盖该方法来解决冲突。
 *
 */
public class Demo01_DefaultImplMethod {
    interface A {
        default void haha() {
            System.out.println("哈哈aaa");
        }
    }
    interface B {
        default void haha() {
            System.out.println("哈哈bbb");
        }
    }
    class C {
        public void haha() {
            System.out.println("哈哈ccc");
        }
    }
    class D extends C implements A {}
    class E implements A,B {
        @Override
        public void haha() {
            A.super.haha();
            B.super.haha();
            System.out.println("哈哈eee");
        }
    }

    @Test
    public void test01() {
        new D().haha(); //哈哈ccc
    }

    @Test
    public void test02() {
        new E().haha(); // 哈哈aaa 哈哈bbb 哈哈eee
    }
}
