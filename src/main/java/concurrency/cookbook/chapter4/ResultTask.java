package concurrency.cookbook.chapter4;

import java.util.concurrent.*;

/**
 * Created by lhao on 6/21/17.
 */
public class ResultTask extends FutureTask<String> {


    public ResultTask(Callable<String> callable) {
        super(callable);
        this.name=((ExecutableTask)callable).getName();
    }
    private String name;

    @Override
    protected void done() {
        if (isCancelled()) {
            System.out.printf("%s: Has been canceled\n",name);
        } else {
            System.out.printf("%s: Has finished\n",name);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = (ExecutorService) Executors.newCachedThreadPool();

        ResultTask resultTasks[]=new ResultTask[5];

        for (int i=0; i<5; i++) {
            ExecutableTask executableTask=new ExecutableTask("Task "+i);
            resultTasks[i]=new ResultTask(executableTask);
            executorService.submit(resultTasks[i]);
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        for (ResultTask rt:resultTasks
             ) {

            rt.cancel(true);
        }

        for (ResultTask rt: resultTasks
             ) {

            if ( ! rt.isCancelled()){

                try {
                    System.out.printf("%s\n", rt.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executorService.shutdown();
    }
}
