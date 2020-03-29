package design.pattern.iterator;

/**
 * 测试遍历器模式
 *
 */
public class Demo01_Iterator {

    /**
     * main
     *
     */
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("张三");
        list.add("李四");
        list.add("小娟");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    static interface Iterator<E> {
        boolean hasNext();
        E next();
    }
    static interface Collection<E> {
        void add(E e);
        int size();
        Iterator<E> iterator();
    }

    static class ArrayList<E> implements Collection<E> {
        private static final int DEFAULT_CAPACITY = 10;
        private Object[] array = new Object[DEFAULT_CAPACITY];
        private int index = 0;

        @Override
        public void add(E e) {
            array[index++] = e;
            if (index==array.length) {
                Object[] newArray = new Object[array.length * 2];
                System.arraycopy(array, 0, newArray,0, array.length);
                array = newArray;
            }
        }

        @Override
        public int size() {
            return index;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private int currentIndex = 0;

                @Override
                public boolean hasNext() {
                    return currentIndex<index;
                }

                @Override
                public E next() {
                    return (E) array[currentIndex++];
                }
            };
        }
    }

    static class LinkedList<E> implements Collection<E> {
        static class Node<E> {
            private E data;
            private Node next;
            public E getData() {
                return data;
            }
            public void setData(E data) {
                this.data = data;
            }
            public Node getNext() {
                return next;
            }
            public void setNext(Node next) {
                this.next = next;
            }
        }
        private Node preHead;
        private Node head;
        private Node tail;
        private int index;

        @Override
        public void add(E e) {
            if (head==null) {
                head = tail = new Node<E>();
                head.setData(e);
                preHead = new Node();
                preHead.setNext(head);
            } else {
                Node<E> newNode = new Node<>();
                newNode.setData(e);
                tail.setNext(newNode);
                tail = newNode;
            }
            index++;
        }

        @Override
        public int size() {
            return index;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private Node<E> currentNode = preHead;
                @Override
                public boolean hasNext() {
                    return currentNode.getNext()==null ? false : true;
                }

                @Override
                public E next() {
                    currentNode = currentNode.getNext();
                    return currentNode.getData();
                }
            };
        }
    }
}
