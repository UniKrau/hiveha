package com.agoda.hive.ha;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lhao on 5/2/17.
 */
public class CTTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testComparable() throws Exception {

        CT original = new CT();
        original.setValue(10);
        System.out.println("original value "+original.getValue());

        CT copy = new CT(original);

        System.out.println("first copy value " +copy.getValue());

        copy.setValue(12);

        System.out.println("copy value " +copy.getValue());
        System.out.println("original value "+original.getValue());
    }

    @Test
    public void testGetEnvironment() throws Exception {

        CT original = new CT();

        for (long var: original.getEnvironment()) {

            System.out.println(var/(1024*1024) + "  MB");
        }
    }

    @After
    public void tearDown() throws Exception {
    }

}