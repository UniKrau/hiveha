package com.agoda.hadoop.event;

/**
 * Created by lhao on 5/30/17.
 */
public abstract class AbstractEvent<TYPE extends Enum<TYPE>>
        implements Event<TYPE> {

    private final TYPE type;
    private final long timestamp;


    public AbstractEvent(TYPE type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }


    public TYPE getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EventType: " + getType();
    }

}
