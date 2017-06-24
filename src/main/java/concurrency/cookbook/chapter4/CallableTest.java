package concurrency.cookbook.chapter4;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhao on 6/20/17.
 */
public class CallableTest implements Callable<Result> {

    private String name;
    public CallableTest(String name) {

        this.name = name;

    }

    public Result call() throws Exception {
        System.out.printf("%s: Staring\n",this.name);

        try {
            long duration=(long)(Math.random()*10);
            System.out.printf("%s: Waiting %d seconds for results.\n",this.name,duration);
                    TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int value=0;
        for (int i=0; i<5; i++){
            value+=(int)(Math.random()*100);
        }
        Result result=new Result();
        result.setName(this.name);
        result.setValue(value);

        return result;
    }
}
