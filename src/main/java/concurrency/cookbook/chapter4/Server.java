package concurrency.cookbook.chapter4;


import java.util.concurrent.*;

/**
 * Created by lhao on 6/19/17.
 */
public class Server {

    private ThreadPoolExecutor executor;

    public Server() {
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void executeTask(Task task){

        executor.execute(task);

        System.out.printf("Server: Pool Size: %d\n",executor.
                getPoolSize());
        System.out.printf("Server: Active Count: %d\n",executor.
                getActiveCount());
        System.out.printf("Server: Completed Tasks: %d\n",executor.
                getCompletedTaskCount());
    }


    public void endServer() {
        executor.shutdown();
    }

}
