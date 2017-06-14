package com.agoda.hadoop.deadlinescheduler;

import com.agoda.hadoop.event.EventHandler;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.examples.terasort.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lhao on 5/28/17.
 */
public class DeadlineHandler implements EventHandler<DeadlineEvent> {

    private long startTime;
    private long endTime;
    private long leaveTime;

    Object lock = new Object() ;

    public long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(long leaveTime) {
        this.leaveTime = leaveTime;
    }

    public long getstartime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getendtime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void handle(DeadlineEvent event) {

        switch(event.getType()){

            case STATED:
                //start a thread do something
                System.out.println("begin to start up");
                setStartTime(System.currentTimeMillis());
                break;
            case RUNNING:
                setStartTime(System.currentTimeMillis());


                new Thread(running()).start();

                for(int j=0;j<1000;j++){

                    new Random().nextDouble();
                }
                //setEndTime(System.currentTimeMillis());

                break;
            case COMPLETED:
                //store the data for analysis
                // start timestamp , end start timestamp

                break;
            default:
                //
                break;
        }
    }

    Runnable running(){

        return new Runnable(){

            public void run() {

                synchronized(lock) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.clear();

                    String [] args = {"input","output"};

//                    for (long i = 0; i < 10000000l; i++) {
//                        list.add("test");
//
//                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    list.clear();
                }

            }
        };
    }
}
