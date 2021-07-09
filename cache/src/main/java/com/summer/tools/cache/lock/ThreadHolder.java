package com.summer.tools.cache.lock;

public class ThreadHolder {
    public static final ThreadLocal<Thread> CURRENT_THREAD = new ThreadLocal<>();

    public static Thread get() {
        return CURRENT_THREAD.get();
    }

    public static void set(Thread thread) {
        CURRENT_THREAD.set(thread);
    }

    public static void remove() {
        CURRENT_THREAD.remove();
    }
}
