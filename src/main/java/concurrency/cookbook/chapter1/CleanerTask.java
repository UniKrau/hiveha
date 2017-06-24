package concurrency.cookbook.chapter1;

import java.util.Date;
import java.util.Deque;

/**
 * Created by lhao on 6/18/17.
 */
public class CleanerTask extends Thread  {

    private Deque<EventTest> deque;

    public CleanerTask(Deque<EventTest> deque){

        this.deque = deque;

        setDaemon(true);
    }
    public void run() {
        while (true){

            Date date = new Date();
            clean(date);

        }
    }

    void clean(Date date){

        long difference;
        boolean delete;

        if(deque.size() == 0)
            return;

        delete = false;

        do {

            EventTest e = deque.getLast();

            difference = date.getTime() - e.getDate().getTime();

            //if (difference > 10000) {
                System.out.printf("Cleaner: %s\n", e.getEvent());
                deque.removeLast();
                delete = true;
           // }
        }while (difference > 10000);

        if (delete){
            System.out.printf("Cleaner: Size of the queue: %d\n",deque.
                    size());
        }
    }
}
