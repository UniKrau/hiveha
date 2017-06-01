package com.agoda.hadoop.event;

/**
 * Created by lhao on 5/28/17.
 */
public interface EventHandler <T extends Event>{

    //method
    void handle(T event);
}
