package com.agoda.hive.ha;

/**
 * Created by lhao on 5/2/17.
 */
public class CT implements Comparable<CT> {

    private int value;

    public CT(){

    }

    public CT( CT ct) {

        this.value = ct.getValue();
    }

    public long []  getEnvironment(){

        long totalMemory = Runtime.getRuntime().totalMemory();
        long xms = Runtime.getRuntime().maxMemory();
        long free = Runtime.getRuntime().freeMemory();
        long used = totalMemory - free;
        return  new long []{totalMemory, xms, free, used};
    }
    public int compareTo(CT that) {

        if (this.value - that.value > 1)
            return 1;

        else
            return  -1;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
