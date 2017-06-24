package concurrency.cookbook.chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/18/17.
 */
public class DataSourcesLoader implements Runnable {

    public void run() {

        System.out.printf("%s\n",new Date());

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Data sources loading has finished:%s\n",new Date());

    }

    public static void main(String[] args) {


        DataSourcesLoader dataSourcesLoader = new DataSourcesLoader();
        Thread thread = new Thread(dataSourcesLoader);

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main: Configuration has been loaded: %s\n",new Date());
    }
}
