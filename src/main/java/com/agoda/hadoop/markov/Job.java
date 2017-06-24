package com.agoda.hadoop.markov;

/**
 * Created by lhao on 6/12/17.
 */
public class Job {
    private double arrivalTime = 0.0;//客户到达时刻
    private double serviceTime = 0.0;//客户接受服务的时间
    private double departureTime = 0.0;//客户离开时刻=客户到达时刻+客户等待时间+客户接受服务时间
    private int queueLen;

    public double getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "Job stay time = " + (departureTime - arrivalTime)
                + ", and wait time = "
                + (departureTime - arrivalTime - serviceTime);
    }

    public void setQueueLen(int size) {
        this.queueLen = size;

    }

    public int getQueueLen() {
        return this.queueLen;
    }
}
