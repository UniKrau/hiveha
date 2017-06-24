package concurrency.cookbook.chapter6;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by lhao on 6/21/17.
 */
public class PollTask implements Runnable {

    private ConcurrentLinkedDeque<String> list;

    public PollTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {

        for (int i=0; i<5000; i++) {
            System.out.println("list poll first " + list.pollFirst());
            System.out.println("list poll last " + list.pollLast());
        }

    }
}
