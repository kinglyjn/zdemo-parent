小结：

    线程的创建：
        Thread
        Runnable Task
        FutureTask and Callable
        Timer Task
        Executor Task

    线程重要API：
        sleep
        join
        interrupt
        park

    线程状态：
        NEW
        RUNNABLE (包括CPU的运行、阻塞、可运行三种状态)
        TIMED_WAITING (调用sleep方法)
        WAITING (调用wait、join方法)
        BLOCKED (加锁等)
        TERMINATED

    原理方面：
            线程运行流程：栈、栈帧、上下文切换、程序计数器

    模式方面：
        两阶段终止模式

    应用方面：
        异步调用：主线程执行期间，其它线程异步执行耗时操作
        提高效率：并行计算，缩短运算时间

    同步等待：
        join

    统筹规划：
        合理使用线程，得到最优效果
