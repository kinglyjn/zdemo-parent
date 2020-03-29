package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 生产者-消费者模型：
 *
 * 1. 与前面的保护性暂停中的GuardObject不同，不需要产生结果和消费结果的线程一一对应
 * 2. 消费队列可以用来平衡生产和消费的线程资源
 * 3. 生产者仅负责产生结果数据，不关心数据该如何处理，而消费者专心处理结果数据
 * 4. 消息队列是有容量限制的，满时不会再加入数据，空时不会再消耗数据
 * 5. JDK中各种阻塞队列，采用的就是这种模式
 *
 */
public class Pattern05_ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {
        MessageQueue queue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    queue.put(new Message(id, "消息-"+id));
                }
            }, "producer-"+i).start();
        }

        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    queue.take();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "consumer").start();
    }


    /**
     * 消息队列类
     */
    @Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern05_ProducerConsumer.MessageQueue")
    static class MessageQueue {
        private LinkedList<Message> list = new LinkedList<>(); // 双向队列实现
        private int capacity;
        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }

        // 获取消息
        public Message take() {
            synchronized (list) {
                while (list.isEmpty()) {
                    log.debug("队列空了");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message = list.removeFirst();// 从头部取
                log.debug("消费消息：{}", message);
                list.notifyAll();
                return message;
            }
        }

        // 存入消息
        public void put(Message message) {
            synchronized (list) {
                while (list.size() == capacity) {
                    log.debug("队列满了");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.addLast(message); // 从尾部加
                log.debug("生产消息：{}", message);
                list.notifyAll();
            }
        }
    }


    /**
     * 消息类
     */
    @Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern05_ProducerConsumer.Message")
    static class Message {
        private int id;
        private Object content;

        public Message(int id, Object content) {
            this.id = id;
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public Object getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "id=" + id +
                    ", content=" + content +
                    '}';
        }
    }
}
