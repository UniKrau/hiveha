package com.agoda.hadoop.markov;

import com.agoda.hadoop.event.Event;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lhao on 5/28/17.
 */
public class MMCQueue {


    private Queue<Event> jobQueue = new LinkedBlockingQueue<Event>();


}
