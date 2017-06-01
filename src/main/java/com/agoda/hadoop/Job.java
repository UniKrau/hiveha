package com.agoda.hadoop;

import com.agoda.hadoop.deadlinescheduler.DeadlineScheduler;

/**
 * Created by lhao on 5/28/17.
 */
public class Job {

    private long arrivalTime = 0L;
    private long serveTime = 0L;
    private long departureTime = 0L;

    private DeadlineScheduler deadlineScheduler;

    public Job(){


    }

    public long deadlinetTime(){

        return deadlineScheduler.getStartTime()- deadlineScheduler.getEndTime();
    }

    public DeadlineScheduler getDeadlineScheduler() {
        return deadlineScheduler;
    }

    public void setDeadlineScheduler(DeadlineScheduler deadlineScheduler) {
        this.deadlineScheduler = deadlineScheduler;
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
        DeadlineScheduler deadlineScheduler = new DeadlineScheduler();
        job.setDeadlineScheduler(deadlineScheduler);

    }
}
