package com.agoda.hadoop.deadlinescheduler;

import com.agoda.hadoop.event.AbstractEvent;

/**
 * Created by lhao on 5/30/17.
 */
public class DeadlineEvent extends AbstractEvent<DeadlineEventType>{


    private long timestamp;

    public DeadlineEvent(DeadlineEventType deadlineEventType, long timestamp) {
        super(deadlineEventType, timestamp);
    }

    public long getTimestampe() {
        return timestamp;
    }
}
