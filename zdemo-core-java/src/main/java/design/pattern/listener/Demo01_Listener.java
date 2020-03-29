package design.pattern.listener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 测试监听者模式
 *
 */
public class Demo01_Listener {

    /**
     * main
     *
     */
    public static void main(String[] args) {
        Child child = new Child();
        child.addListener(new Dad()).addListener(new Mum());
        new Thread(child).start();
    }

    static class Event {
        static enum Type {WAKEUP, SLEEP}
        private Type type;
        private LocalDateTime dt;

        public Event(Type type, LocalDateTime dt) {
            this.type = type;
            this.dt = dt;
        }

        public Type getType() {
            return type;
        }
        public LocalDateTime getDt() {
            return dt;
        }
    }

    static interface Listener {
        void action(Event event);
    }
    static class Dad implements Listener {
        @Override
        public void action(Event event) {
            System.out.println("[" + event.getType() + " " + event.getDt() + "] 孩儿她妈！");
        }
    }
    static class Mum implements Listener {
        @Override
        public void action(Event event) {
            System.out.println("[" + event.getType() + " " + event.getDt() + "] 哦！");
        }
    }

    static class Child implements Runnable {
        private LinkedList<Listener> listeners = new LinkedList<>();

        public Child addListener(Listener listener) {
            this.listeners.add(listener);
            return this;
        }

        @Override
        public void run() {
            System.out.println("睡着...");
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {}
            Event event = new Event(Event.Type.WAKEUP, LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
            for (Listener listener : listeners) {
                listener.action(event);
            }
        }
    }

}
