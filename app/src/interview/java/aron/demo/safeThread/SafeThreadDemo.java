package aron.demo.safeThread;

import java.util.concurrent.atomic.AtomicInteger;

import aron.Utils.ELogger;

/**
 * des:
 * Created by liuwenrong on 2019/3/29
 */
public class SafeThreadDemo {
    private static final int THREADS_COUNT = 6;
    private static final int INT = 10000000;
    private static int count = 0; //线程不安全耗时 4s  变成 8s
    private static String TAG = "SafeThreadDemo";
    private static volatile int countVolatile = 0; // 线程并不安全 6千万耗时4s 突然变成了 8s
    private static AtomicInteger countAtomic = new AtomicInteger(0); //线程安全 6千万 耗时21s
    private static int countSynchronized = 0; // 线程安全 耗时26s

    private static void increase() {
        count++;
    }

    private static void increaseVolatile() {
//        countVolatile ++; //不是原子操作
        ++countVolatile;
    }

    private static void increaseAtomic() {
        countAtomic.incrementAndGet();
    }

    private static synchronized void increaseSync() {
        countSynchronized++;
    }

    public static void testInteger() {
        Thread[] threads = new Thread[THREADS_COUNT];
        ELogger.d("testInteger----------liuwenrong--2019/3/29--: ");
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < INT; i++) {
                        increase();
                    }
                    ELogger.d("testInteger----------liuwenrong--2019/3/29--: count = " + count + ", curThread = " + Thread.currentThread().getName());
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

        // 无法停止进程 ,导致主线程阻塞
/*        while (Thread.activeCount() > 1) {
            Log.d(TAG, "testInteger: count = " + count);
            Thread.yield();
        }*/
//        System.out.println(count);
    }

    public static void testIntegerVolatile() {
        Thread[] threads = new Thread[THREADS_COUNT];
        ELogger.d("testIntegerVolatile----------liuwenrong--2019/3/29--: count = " + countVolatile);
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < INT; i++) {
                        increaseVolatile();
                    }
                    ELogger.d("volatile----------liuwenrong--2019/3/29--: count = " + countVolatile + ", curThread = " + Thread.currentThread().getName());
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

    }

    public static void testIntegerAtomic() {
        Thread[] threads = new Thread[THREADS_COUNT];
        ELogger.d("testIntegerAtomic----------liuwenrong--2019/3/29--: ");
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < INT; i++) {
                        increaseAtomic();
                    }
                    ELogger.d("atomic----------liuwenrong--2019/3/29--: count = " + countAtomic.get() + ", curThread = " + Thread.currentThread().getName());
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

    }

    public static void testIntegerSynchronized() {
        Thread[] threads = new Thread[THREADS_COUNT];
        ELogger.d("testIntegerSynchronized----------liuwenrong--2019/3/29--: ");
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < INT; i++) {
                        increaseSync();
                    }
                    ELogger.d("Synchronized----------liuwenrong--2019/3/29--: count = " + countSynchronized + ", curThread = " + Thread.currentThread().getName());
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

    }


}
