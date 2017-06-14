package com.agoda.hadoop.event;

/**
 * Created by lhao on 5/28/17.
 */
public interface Event<TYPE extends Enum<TYPE>> {


     TYPE getType();
     long getstartime();
     long getendtime();

     String toString();
}
