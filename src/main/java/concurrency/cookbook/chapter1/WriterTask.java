package concurrency.cookbook.chapter1;

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/18/17.
 */
public class WriterTask implements Runnable{

    private Deque<EventTest> deque;

    public WriterTask(Deque<EventTest> deque){

        this.deque = deque;
    }

    public void run() {

        for (int i = 0; i < 100; i++) {
            EventTest eventTest = new EventTest();
            eventTest.setDate(new Date());
            eventTest.setEvent(String.format("The thread %s has generated an event",Thread.currentThread().getId()));
            deque.addFirst(eventTest);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
