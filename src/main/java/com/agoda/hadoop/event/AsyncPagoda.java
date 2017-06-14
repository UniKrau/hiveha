package com.agoda.hadoop.event;

import com.agoda.hadoop.deadlinescheduler.DeadlineEventType;
import com.agoda.hadoop.deadlinescheduler.DeadlineHandler;

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


    public AsyncPagoda() {
        this(new LinkedBlockingQueue<Event>());
    }

    public AsyncPagoda(BlockingQueue<Event> queue) {
        this.eventBlockingQueue = queue;
        this.eventDispatchers = new HashMap<Class<? extends Enum>, EventHandler>();
    }

    public EventHandler<Event> getEventHandler() {
        return null;
    }

    public void register(Class<? extends Enum> eventType, EventHandler handler) {

        EventHandler<Event> reigsterHandler = (EventHandler<Event>)
                eventDispatchers.get(eventType);
        if (reigsterHandler == null) {
            eventDispatchers.put(eventType, handler);
        }

    }

    public void init() {

    }

    public void serviceStart() {

        eventHandlingThread = new Thread(createThread());
        eventHandlingThread.setName(eventHandName);
        eventHandlingThread.start();

    }

    Runnable createThread() {

        return new Runnable() {

            public void run() {

                while (!Thread.currentThread().isInterrupted()){
                    Event event = null;
                    try {
                        event = eventBlockingQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if( event != null){

                        dispatch(event);
                    }

                }

            }
        };
    }


    private void dispatch(Event event){

        Class<? extends Enum> type = event.getType().getDeclaringClass();

        EventHandler<Event> handler = eventDispatchers.get(type);

        if( handler != null ){
            System.out.println("begin handle");
            handler.handle(event);
        }else {

        }
    }
    private boolean isStop() {

        return stopped;
    }

    public static void main(String[] args) {

        AsyncPagoda pagoda = new AsyncPagoda();

        pagoda.register(DeadlineEventType.class, new DeadlineHandler());

        pagoda.serviceStart();
    }
}
