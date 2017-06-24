package concurrency.cookbook.chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/18/17.
 */
public class SafeTask implements Runnable {


    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>(){

        @Override
        protected Date initialValue() {
            return new Date();
        }

    };

    public void run() {
        System.out.printf("Starting Thread: %s : %s\n",Thread.
                currentThread().getId(),startDate.get());
        try {
            TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread Finished: %s : %s\n",Thread.
                currentThread().getId(),startDate.get());
    }

    public static void main(String[] args) {

        SafeTask safeTask = new SafeTask();

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(safeTask);

            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
