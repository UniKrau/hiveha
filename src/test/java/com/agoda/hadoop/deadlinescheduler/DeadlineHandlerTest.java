package com.agoda.hadoop.deadlinescheduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by lhao on 6/4/17.
 */
public class DeadlineHandlerTest {

    private DeadlineHandler deadlineHandler;
    private DeadlineEvent event;
    private File file = new File("src/main/data/test.txt");
    BufferedWriter write = null;


    @Before
    public void setUp() throws Exception {
        write = new BufferedWriter(new FileWriter(file));

        for ( int i=0 ;i<1000 ;i++){
            deadlineHandler = new DeadlineHandler();
            long startime = System.currentTimeMillis();
            Thread.sleep(10);
            event = new DeadlineEvent(DeadlineEventType.RUNNING, startime);
            deadlineHandler.handle(event);
            write.append("  start " + deadlineHandler.getstartime());

            Thread.sleep(12);
            deadlineHandler.setEndTime(System.currentTimeMillis());
            write.append("  end  " + deadlineHandler.getendtime()+"\n");
        }

        write.close();
    }

    @Test
    public void TestgetEndtime() {


    }

    @After
    public void tearDown() throws Exception {
    }

}