package design.pattern.strategy;

/**
 * 测试策略模式
 */
public class Demo01_Comparator {


    public static void main(String[] args) {
        Dog[] dogs = {new Dog(2), new Dog(3), new Dog(1)};
        Arrays.sort(dogs);
        for (int i=0; i<dogs.length; i++) {
            System.out.println(dogs[i].getWeight());
        }
    }

    static interface Comparable<T> {
        int compareTo(T t);
    }
    static class Arrays {
        public static void sort(Object[] os) {
            for (int i=os.length-1; i>0; i--) {
                for (int j=0; j<i; j++) {
                    Comparable o1 = (Comparable) os[j];
                    Comparable o2 = (Comparable) os[j+1];
                    if (o1.compareTo(o2) < 0) {
                        Object temp = os[j];
                        os[j] = os[j+1];
                        os[j+1] = temp;
                    }
                }
            }
        }
    }

    static interface Comparator<T> {
        int compare(T o1, T o2);
    }
    static class DogComparator implements Comparator<Dog> {
        @Override
        public int compare(Dog o1, Dog o2) {
            return Integer.compare(o1.getWeight(), o2.getWeight());
        }
    }
    static class Dog implements Comparable<Dog> {
        private int weight;
        public Dog(int weight) {
            this.weight = weight;
        }
        public int getWeight() {
            return weight;
        }
        @Override
        public int compareTo(Dog o) {
            return new DogComparator().compare(this, o);
        }
    }

}
