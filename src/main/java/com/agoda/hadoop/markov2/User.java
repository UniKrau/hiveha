package com.agoda.hadoop.markov2;

/**
 * Created by lhao on 6/21/17.
 */
public class User implements Comparable<User>{

    double arrival;
    double depart;
    double serving;

    private int priority;

    @Override
    public int compareTo(User o) {
        return this.priority - o.priority;
    }
}
