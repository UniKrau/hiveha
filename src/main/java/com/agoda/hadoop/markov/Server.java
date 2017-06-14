package com.agoda.hadoop.markov;

import org.apache.log4j.Logger;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lhao on 6/12/17.
 */
public class Server implements Runnable{

    private static final Logger LOG = Logger.getLogger(Server.class);

    private int serverId;
    private volatile boolean isAlive = true;
    private Deque<Customer> customerQueue;

    private int custoemrServed = 0;
    private double totalStayTime = 0.0;
    private double averageStayTime = 0.0;

    private double totalWaitTime = 0.0;
    private double averageWaitTime = 0.0;

    private int totalQueueLen = 0;
    private double averageQueueLen = 0.0;

    public Server(int i) {
        serverId = i;
        customerQueue = new LinkedBlockingDeque<Customer>();
    }

    public double getTotalStayTime() {
        return totalStayTime;
    }

    public void run() {
        if (MMCQueue.DEBUG_MODE) {
            LOG.info("Server with ID " + this.serverId
                    + " has been started.");
        }
        while (isAlive) {
            if (customerQueue.size() == 0) {
                try {
                    if (MMCQueue.DEBUG_MODE) {
                        LOG.info("Server with ID " + this.serverId
                                + " is now waiting...");
                    }
                    synchronized (customerQueue) {
                        customerQueue.wait();
                    }

                } catch (InterruptedException e) {
                    break;
                }
            } else {//当前服务窗口的等待队列中有客户

                //获取当前服务窗口等待队列中第一个客户和最后一个客户，如果只有一个客户，则第一个客户和最后一个客户是同一个
                Customer headCustomer = customerQueue.getFirst();
                Customer lastCustomer = customerQueue.getLast();
                //如果等待队列中最后一个客户到来时第一个客户已经离开，则当前窗口为第一个客户提供服务结束，否则继续为第一个客户提供服务
                if(headCustomer.getDepartureTime() <= lastCustomer
                        .getArrivalTime()) {
                    headCustomer = customerQueue.pop();
                    //当前服务窗口总停留时间，总等待时间和总队列长度，服务的客户总数更新
                    totalStayTime += headCustomer.getDepartureTime()
                            - headCustomer.getArrivalTime();

                    totalWaitTime += headCustomer.getDepartureTime()
                            - headCustomer.getArrivalTime()
                            - headCustomer.getServiceTime();

                    totalQueueLen += headCustomer.getQueueLen();
                    custoemrServed += 1;

//                    if (MMCQueue.DEBUG_MODE) {
//                        LOG.info("Remove customer " + headCustomer
//                                + " from Server " + this.serverId);
//                    }
                    headCustomer = customerQueue.peekFirst();
                    lastCustomer = customerQueue.peekLast();
                }

            }
        }
        if (MMCQueue.DEBUG_MODE) {
            LOG.info("Sever with ID " + this.serverId
                    + " is now shutting down.");
           // printStatistics();
        }
    }

    private void printStatistics() {
        if (custoemrServed != 0) {
            averageStayTime = totalStayTime / custoemrServed;
            averageWaitTime = totalWaitTime / custoemrServed;
            averageQueueLen = totalQueueLen / custoemrServed;
        }
        LOG.info("Server with ID " + this.serverId + " has served "
                + custoemrServed + " customers, " + ", and the average wait queue length is " + averageQueueLen
                + ",and average stay time is "
                + averageStayTime + ", and average wait time is "
                + averageWaitTime);

    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getCustomerNumber() {
        return customerQueue.size();
    }

    public int getServerId() {
        return serverId;
    }

    public synchronized void serviceCustomer(Customer nextCustomer) {
        Customer lastCustomerInQueue = customerQueue.peekLast();
        //当前服务窗口没有客户，则新来的客户不必等待，直接接受服务
        if (lastCustomerInQueue == null) {
            nextCustomer.setDepartureTime(nextCustomer.getArrivalTime()
                    + nextCustomer.getServiceTime());
            nextCustomer.setQueueLen(0);
        } else {//当前服务窗口有客户
            if (MMCQueue.DEBUG_MODE) {
                LOG.info("last customer depart time "
                        + lastCustomerInQueue.getDepartureTime()
                        + ", and this one arrival time "
                        + nextCustomer.getArrivalTime());
            }
            //新来客户到达时，当前服务窗口等待队列中最后一个客户正在接受服务
            if (nextCustomer.getArrivalTime() > lastCustomerInQueue
                    .getDepartureTime()) {
                nextCustomer.setDepartureTime(nextCustomer.getArrivalTime()
                        + nextCustomer.getServiceTime());//新客户离开时刻=新客户到达时间+接受服务时间

                nextCustomer.setQueueLen(0);
            }
            //新来客户到达时，当前服务窗口等待队列中的最后一个客户也在等待
            else {
                nextCustomer.setDepartureTime(lastCustomerInQueue
                        .getDepartureTime() + nextCustomer.getServiceTime());//新客户的离开时刻=前一个客户离开时刻+新客户服务时间

                nextCustomer.setQueueLen(customerQueue.size());
            }
        }
        customerQueue.add(nextCustomer);//将新来的客户添加到服务窗口等待队列中
        if (MMCQueue.DEBUG_MODE) {
            LOG.info("New customer " + nextCustomer
                    + " has been added to server " + this.serverId);
        }
    }

    public void wakeUp() {
        synchronized (customerQueue) {
            customerQueue.notify();
        }
    }

    public double getTotalWaitTime() {
        return totalWaitTime;
    }

    public int getTotalQueueLen() {
        return totalQueueLen;
    }
}
