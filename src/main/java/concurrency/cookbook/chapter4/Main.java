package concurrency.cookbook.chapter4;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by lhao on 6/20/17.
 */
public class Main {

    public static void main(String[] args) {
        Server server=new Server();
        for (int i=0; i<10; i++){
            Task task=new Task("Task "+i);
            server.executeTask(task);
        }

        List<Future<Result>> resultList=null;

        //resultList.
        server.endServer();
    }

}
