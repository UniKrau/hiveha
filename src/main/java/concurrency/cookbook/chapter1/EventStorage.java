package concurrency.cookbook.chapter1;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lhao on 6/18/17.
 */
public class EventStorage {
    private int maxSize;
    private List<Date> storage;

    public EventStorage(int maxSize){

        this.maxSize = maxSize;
        this.storage = new LinkedList<Date>();
    }
}
