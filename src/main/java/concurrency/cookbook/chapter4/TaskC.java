package concurrency.cookbook.chapter4;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/21/17.
 */
public class TaskC implements Callable<String> {

    private String name;
    public TaskC(String name) {

        this.name = name;
    }

    public String call() throws Exception {
        return name;
    }

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 5; i++) {

            TaskC taskC = new TaskC("TaskC"+i);

            executor.schedule(taskC,i+1, TimeUnit.SECONDS);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
