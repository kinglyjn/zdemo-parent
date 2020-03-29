package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保护性暂停模式：
 * 产生结果的线程和消费结果的线程是一一对应的，在这个测试中指的是 一个邮件接收者线程就对应一个邮递员线程
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern04_GuardedSuspension_One2One")
public class Pattern04_GuardedSuspension_One2One {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Recipient().start();
        }

        Thread.sleep(1000);
        for (Integer id : GuardedObjectContainer.getIds()) {
            new Postman(id, "内容"+id).start();
        }
    }

    /**
     * 带id的受保护类
     *
     */
    static class GuardedObject {
        private int id;
        private Object response;

        public GuardedObject(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public Object get(int timeout) {
            if (timeout < 0) {
                throw new IllegalArgumentException("非法参数");
            }
            synchronized (this) {
                long startTime = System.currentTimeMillis();
                long passedTime = 0;
                long needWaitTime;
                while (response==null) {
                    needWaitTime = timeout - passedTime;
                    if (needWaitTime <= 0) {
                        break;
                    }
                    try {
                        this.wait(needWaitTime);
                    } catch (InterruptedException e) {
                        log.debug("{}", e.getMessage());
                    }
                    passedTime = System.currentTimeMillis() - startTime;
                }
            }
            return response;
        }

        public synchronized void complete(Object response) {
            this.response = response;
            this.notifyAll();
        }
    }


    /**
     * 中间解耦类
     *
     */
    static class GuardedObjectContainer {
        private static Map<Integer, GuardedObject> container = new ConcurrentHashMap<>();
        private static int i = 0;

        private static synchronized int generateId() {
            return i++;
        }

        public static GuardedObject createGuardedObject() {
            GuardedObject guardedObject = new GuardedObject(generateId());
            container.put(guardedObject.getId(), guardedObject);
            return guardedObject;
        }

        public static GuardedObject getGuardedObject(int id) {
            return container.remove(id);
        }

        public static Set<Integer> getIds() {
            return container.keySet();
        }
    }


    /**
     * 业务类：邮递员 和 收信者
     *
     */
    @Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern04_GuardedSuspension_One2One.Postman")
    static class Postman extends Thread {
        private int mailId;
        private String content;

        public Postman(int mailId, String content) {
            this.mailId = mailId;
            this.content = content;
        }

        @Override
        public void run() {
            GuardedObject guardedObject = GuardedObjectContainer.getGuardedObject(mailId);
            log.debug("送信 {}-{}", mailId, content);
            guardedObject.complete(content);
        }
    }

    @Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern04_GuardedSuspension_One2One.Recipient")
    static class Recipient extends Thread {
        @Override
        public void run() {
            GuardedObject guardedObject = GuardedObjectContainer.createGuardedObject();
            log.debug("开始收信 id={}", guardedObject.getId());
            Object content = guardedObject.get(5000);
            log.debug("收到信 id={}, content={}", guardedObject.getId(), content);
        }
    }

}
