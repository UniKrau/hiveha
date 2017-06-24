package concurrency.cookbook.chapter3;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Created by lhao on 6/19/17.
 */
public class Producer implements Runnable {

    private List<String> buffer;

    Exchanger<List<String>> exchanger;

    public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    public void run() {

        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Producer: Cycle %d\n",cycle);
        }

    }
}
