package concurrency.cookbook.chapter7;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/21/17.
 */
public class MyExecutor extends ThreadPoolExecutor {

    private ConcurrentHashMap<String, Date> startTimes;


    public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        startTimes = new ConcurrentHashMap<>();

    }

    @Override
    public void shutdown() {
        System.out.printf("MyExecutor: Going to shutdown.\n");
        System.out.printf("MyExecutor: Executed tasks: %d\n",getCompletedTaskCount());
        System.out.printf("MyExecutor: Running tasks: %d\n",getActiveCount());
        System.out.printf("MyExecutor: Pending tasks: %d\n",getQueue().size());
        super.shutdown();
    }
}
