package com.agoda.hadoop.markov;

import java.util.Random;

/**
 * Created by lhao on 5/27/17.
 */
public class RandomGengertor {

    /**
     * Generate normal distribute random number
     * @param average Expect average
     * @param sigma Standard devation
     * @return Normal distribute random number
     */
    public static double generateNormalDistrRand(double average,
                                                 double sigma) {
        double N = 12;
        double x = 0, temp = N;
        do {
            x = 0;
            for (int i = 0; i < N; i++)
                x = x + (Math.random());
            x = (x - temp / 2) / (Math.sqrt(temp / 12));
            x = average + x * Math.sqrt(sigma);
        } while (x <= 0);
        return x;
    }

    /**
     * Generate negative index distribute random number
     * @param lamda Expect average
     * @return Negative index distribute random number
     */
    public static double generateNegativeIndexDistrRand(double lamda) {
        Random random = new Random();
        return -Math.log(random.nextDouble()) / lamda;
    }
}
