package com.agoda.hadoop.deadlinescheduler;

import com.agoda.hadoop.event.EventHandler;

/**
 * Created by lhao on 5/28/17.
 */
public class DeadlineScheduler implements EventHandler<DeadlineEvent> {

    private long startTime;
    private long endTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void handle(DeadlineEvent event) {

        switch(event.getType()){

            case STATED:
                //start a thread do something

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
}
