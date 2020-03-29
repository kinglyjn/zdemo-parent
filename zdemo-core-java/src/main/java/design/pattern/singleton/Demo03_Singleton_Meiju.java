package design.pattern.singleton;

/**
 * 枚举式(枚举不用继承Serializable就能实现序列化)
 *
 */
public enum Demo03_Singleton_Meiju {
    INSTANCE {
        @Override
        protected void doSomething() {
            System.out.println("haha");
        }
    };

    protected abstract void doSomething();


    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println(Demo03_Singleton_Meiju.INSTANCE == Demo03_Singleton_Meiju.INSTANCE);
    }
}
