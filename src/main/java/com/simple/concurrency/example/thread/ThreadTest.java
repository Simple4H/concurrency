package com.simple.concurrency.example.thread;

import java.util.Random;

/**
 * Create by S I M P L E on 2018/04/24 12:27:44
 */
public class ThreadTest {

    private int x, y, x_read, y_read;

    private void randomSleep() {
        try {
            Thread.sleep(new Random().nextInt(30));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Thread createThread1() {
        return new Thread(() -> {
            randomSleep();
            synchronized (this) {
                x = 1;
                y_read = y;
            }
        });
    }

    private Thread createThread2() {
        return new Thread(() -> {
            randomSleep();
            synchronized (this) {
                y = 1;
                x_read = x;
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {


            ThreadTest tests = new ThreadTest();
            Thread thread1 = tests.createThread1();
            Thread thread2 = tests.createThread2();

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();

            System.out.println(tests.x_read + " " + tests.y_read);
            if (tests.x_read == 0 && tests.y_read == 0) {
                throw new RuntimeException("what?");
            }
        }
    }
}
