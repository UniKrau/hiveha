package concurrency.cookbook.chapter4;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/21/17.
 */
public class TaskReject implements Runnable {

    private String name;

    public TaskReject(String name) {

        this.name = name;
    }

    @Override
    public void run() {

        System.out.println("Task "+name+": Starting");
        try {
            long duration=(long)(Math.random()*10);
            System.out.printf("Task %s: ReportGenerator: Generating a report during %d seconds\n",name,duration);
                    TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Task %s: Ending\n",name);
    }

    public String toString() {
        return name;
    }

    public static void main(String[] args) {

        RejectedTaskController controller = new RejectedTaskController();

        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.
                newCachedThreadPool();

        executor.setRejectedExecutionHandler(controller);

        System.out.printf("Main: Starting.\n");
        for (int i=0; i<3; i++) {
            TaskReject task=new TaskReject("Task"+i);
            executor.submit(task);
        }
        System.out.printf("Main: Shutting down the Executor.\n");
        executor.shutdown();

        System.out.printf("Main: Sending another Task.\n");
        TaskReject task=new TaskReject("RejectedTask");
        executor.submit(task);

        System.out.println("Main: End");
        System.out.printf("Main: End.\n");

    }
}
