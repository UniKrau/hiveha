package com.agoda.hadoop;

import com.agoda.hadoop.deadlinescheduler.DeadlineHandler;

/**
 * Created by lhao on 5/28/17.
 */
public class Job {

    private long arrivalTime = 0L;
    private long serveTime = 0L;
    private long departureTime = 0L;

    private DeadlineHandler deadlineHandler;

    public Job(){


    }

    public DeadlineHandler getDeadlineHandler() {
        return deadlineHandler;
    }

    public void setDeadlineHandler(DeadlineHandler deadlineHandler) {
        this.deadlineHandler = deadlineHandler;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getServeTime() {
        return serveTime;
    }

    public void setServeTime(long serveTime) {
        this.serveTime = serveTime;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
    }


    public static void main(String[] args) {

        Job job = new Job();
        DeadlineHandler deadlineHandler = new DeadlineHandler();
        job.setDeadlineHandler(deadlineHandler);

    }
}
