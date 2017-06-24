package concurrency.cookbook.chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/18/17.
 */
public class FileClock implements Runnable {

    public void run() {
        for (int i = 0; i < 10; i++) {

            System.out.printf("%s\n",new Date());

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        FileClock fileClock = new FileClock();

        Thread thread = new Thread(fileClock);

        thread.start();
    }
}
