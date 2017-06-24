package concurrency.cookbook.chapter4;

import java.util.Date;

/**
 * Created by lhao on 6/19/17.
 */
public class Task implements Runnable {

    private Date initDate;
    private String name;

    public Task(String name) {
        initDate = new Date();
        this.name = name;
    }

    public void run() {

        System.out.println("---");
    }


}
