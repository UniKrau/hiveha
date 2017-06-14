package com.agoda.hadoop.markov;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.*;
import org.apache.log4j.Logger;

/**
 * Created by lhao on 5/28/17.
 */
public class MMCQueue {

    public static boolean DEBUG_MODE;

    public static int MAX_NUMBER_OF_CUSTOMERS;
    private static int SERVER_NUMBER;

    private static final Logger LOG = Logger.getLogger(MMCQueue.class);

    private Queue<Customer> customers = new LinkedBlockingDeque<Customer>();//用于产生客户的队列

    private double actualArrivalRate;
    private double actualServiceRate;

    private double mu;
    private double sigma;
    private double lamda;

    private ExecutorService executorService;
    private List<Server> servers = new ArrayList<Server>();

    public Queue<Customer> getCustomers() {
        return customers;
    }

    public static void main(String args[]) {
        //Read user input
        SERVER_NUMBER = 10;

        MAX_NUMBER_OF_CUSTOMERS =24; //产生的客户最大数目

        double lamda = 0.655;

        double mu = 0.54;

        double sigma = 0.234;

        DEBUG_MODE = false;


        MMCQueue mmcQueue = new MMCQueue(mu,
                sigma, lamda);

        mmcQueue.caleTheoreticalData();

        mmcQueue.startServers(SERVER_NUMBER);

        mmcQueue.addCustomerToServerQueue();//向服务窗口等待队列中添加客户

        try {
            mmcQueue.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mmcQueue.printStatistics();

    }

    private void printStatistics() {
        double averageWaitingTime = 0.0;
        double averageStayTime = 0.0;
        double averageQueueLen = 0;

        for (Server server : servers) {
            averageStayTime += server.getTotalStayTime();
            averageWaitingTime += server.getTotalWaitTime();
            averageQueueLen += server.getTotalQueueLen();
        }

        averageWaitingTime = averageWaitingTime / MAX_NUMBER_OF_CUSTOMERS;
        averageStayTime = averageStayTime / MAX_NUMBER_OF_CUSTOMERS;
        averageQueueLen = averageQueueLen / MAX_NUMBER_OF_CUSTOMERS;

        LOG.info("Average waiting time = " + averageWaitingTime);
        LOG.info( "average stay time = " + averageStayTime);
        LOG.info("average Queue Length = " + averageQueueLen +"\n");

    }

    public MMCQueue(double mu, double sigma, double lamda) {
        this.mu = mu;
        this.sigma = sigma;
        this.lamda = lamda;

        executorService = Executors.newCachedThreadPool();
    }

    public void caleTheoreticalData() {
        double arrivalInterval = 0.0;
        double serviceTime = 0.0;

        double arrivalIntervalTotal = 0.0;
        double serviceTimeTotal = 0.0;
        //创建客户
        for (int i = 0; i < MAX_NUMBER_OF_CUSTOMERS; i++) {
            arrivalInterval = RandomGengertor.generateNegativeIndexDistrRand(this.lamda);
            serviceTime = RandomGengertor.generateNormalDistrRand(
                    this.mu, this.sigma);
            arrivalIntervalTotal += arrivalInterval;
            serviceTimeTotal += serviceTime;

            Customer customer = new Customer();
            //后面客户到达时刻=前面所有客户到达时刻之和
            customer.setArrivalTime(arrivalIntervalTotal);
            customer.setServiceTime(serviceTime);
            customers.add(customer);
            if (MMCQueue.DEBUG_MODE) {
                LOG.info(customer);
            }
        }

        actualArrivalRate = 1.0 / (arrivalIntervalTotal / MAX_NUMBER_OF_CUSTOMERS);
        actualServiceRate = 1.0 / (serviceTimeTotal / MAX_NUMBER_OF_CUSTOMERS);

        LOG.info("Theoretical Arrival Rate :  " + this.lamda
                + "   Actual : " + this.actualArrivalRate);
        LOG.info("Theoretical Service Rate :  " + this.mu
                + "   Actual : " + this.actualServiceRate);


        double p = actualArrivalRate / actualServiceRate;
        double ps = actualArrivalRate / (SERVER_NUMBER * actualServiceRate);

        double p0 = 1 / (1 + p + p * p / 2 + p * p * p / (6 * (1 - ps)));
        double averageQueueLen = p0 * Math.pow(p, SERVER_NUMBER) * ps
                / (6 * Math.pow((1 - ps), 2));
        double averageWaitTime = averageQueueLen / actualArrivalRate;
        double averageStayTime = averageWaitTime + 1 / actualServiceRate;
        if (MMCQueue.DEBUG_MODE) {
            LOG.info("Theoretical average wait queue length: " + averageQueueLen);
            LOG.info("Theoretical average stay time: " + averageStayTime);
            LOG.info("Theoretical average wait time: " + averageWaitTime);
        }
    }

    //启动服务窗口线程
    public void startServers(int serverNumber) {
        if (serverNumber < 0) {
            throw new IllegalArgumentException(
                    "Server number should be greater than zero.");
        }
        for (int i = 0; i < serverNumber; i++) {
            if (MMCQueue.DEBUG_MODE) {
                LOG.info("Create server " + i);
            }
            Server server = new Server(i + 1);
            servers.add(server);
            executorService.execute(server);
        }

    }

    private void notifyAllServers() {
        for (Server server : servers) {
            server.wakeUp();
        }
    }

    public void addCustomerToServerQueue() {//将客户添加到等待队列最短的服务窗口
        executorService.execute(new Runnable() {
            public void run() {
                while (customers.size() > 0) {
                    if (MMCQueue.DEBUG_MODE) {
                        LOG.info("customers.size() = "
                                + customers.size());
                    }
                    for (int i = 0; i < SERVER_NUMBER; i++) {
                        Customer nextCustomer = null;
                        try {
                            nextCustomer = customers.remove();
                        } catch (NoSuchElementException e) {
                            break;
                        }
                        Server availableServer = findShortestWaitQueueServer();
                        availableServer.serviceCustomer(nextCustomer);//将客户添加到等待队列最短的服务窗口等待队列中
                    }
                    notifyAllServers();
                }

                shutdownAllServers();
            }

            private Server findShortestWaitQueueServer() {//查找等待队列最短的服务窗口
                int shortestCustomerQueueNumber = Integer.MAX_VALUE;
                Server bestSuitableServer = null;

                for (Server server : servers) {
                    if (server.getCustomerNumber() < shortestCustomerQueueNumber) {
//                        if (MMCQueue.DEBUG_MODE) {
//                            LOG.info("Server " + server.getServerId()
//                                    + " Q Len = " + server.getCustomerNumber());
//                        }
                        shortestCustomerQueueNumber = server
                                .getCustomerNumber();
                        bestSuitableServer = server;
                    }
                }

                return bestSuitableServer;

            }
        });

    }

    protected void shutdownAllServers() {
        if (MMCQueue.DEBUG_MODE) {
            LOG.info("Shutting down all servers...");
        }
        for (Server server : servers) {
            server.setAlive(false);
        }
        notifyAllServers();
    }

    public void shutdown() throws InterruptedException {
        executorService.shutdown();
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            LOG.info("Waiting thread pool close.");
        }

    }


}
