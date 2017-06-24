package concurrency.cookbook.chapter1;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by lhao on 6/18/17.
 */
public class Main {

    public static void main(String[] args) {
        /*
        Deque<EventTest> deque = new ArrayDeque<EventTest>();
        WriterTask writer=new WriterTask(deque);

        for (int i = 0; i < 3; i++) {
            Thread thread=new Thread(writer);
            thread.start();
        }

        CleanerTask cleaner=new CleanerTask(deque);
        cleaner.start();*/

        PricesInfo pricesInfo=new PricesInfo();
        Reader readers[]=new Reader[5];
        Thread threadsReader[]=new Thread[5];
        for (int i=0; i<5; i++){
            readers[i]=new Reader(pricesInfo);
            threadsReader[i]=new Thread(readers[i]);
        }
        Writer writer=new Writer(pricesInfo);
        Thread  threadWriter=new Thread(writer);

        for (int i=0; i<5; i++){
            threadsReader[i].start();
        }
        threadWriter.start();
    }
}
