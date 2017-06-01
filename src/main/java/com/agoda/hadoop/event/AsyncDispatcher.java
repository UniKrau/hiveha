package com.agoda.hadoop.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lhao on 5/29/17.
 */
public class AsyncPagoda implements Dispatcher {


    private Thread eventHandlingThread;
    private String eventHandName = "Event Handler";

    private volatile boolean stopped = false;
    private BlockingQueue<Event> eventBlockingQueue;
    protected final Map<Class<? extends Enum>, EventHandler> eventDispatchers;


    public AsyncPagoda(){

        this(new LinkedBlockingQueue<Event>());
    }

    public AsyncPagoda(BlockingQueue<Event> queue){

        this.eventBlockingQueue = queue;
        this.eventDispatchers = new HashMap<Class<? extends Enum>, EventHandler>();



    }
    public EventHandler<Event> getEventHandler() {
        return null;
    }

    public void register(Class<? extends Enum> eventType, EventHandler handler) {

        EventHandler<Event> reigsterHandler = (EventHandler<Event>)
                eventDispatchers.get(eventType);
        if(reigsterHandler == null){
            eventDispatchers.put(eventType, handler);
        }

    }

    public void init(){

    }
    public void serviceStart(){

        eventHandlingThread = new Thread(createThread());
        eventHandlingThread.setName(eventHandName);
        eventHandlingThread.start();

    }
    Runnable createThread(){

        return new Runnable(){

            public void run() {

                if (! isStop() && Thread.currentThread().isInterrupted())
                {

                    System.out.println("---");
//                    try {
//                        eventBlockingQueue.take();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        };
    }


    private boolean isStop(){

        return stopped;
    }

    public static void main(String[] args) {

        AsyncPagoda pagoda = new AsyncPagoda();

        pagoda.serviceStart();
    }
}
