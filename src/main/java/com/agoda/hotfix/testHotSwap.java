package com.agoda.hotfix;

import java.lang.management.ManagementFactory;

/**
 * Created by lhao on 6/22/17.
 */
public class testHotSwap {
    public static void main(String[] args) {
        testStatic t=(new testHotSwap()).new testStatic();
        while(true){
            try{
                Thread.sleep(5000);
                String name = ManagementFactory.getRuntimeMXBean().getName();
                System.out.println(name);
                // get pid
                String pid = name.split("@")[0];
                System.out.println("Pid is:" + pid);
                t.print();
            }
            catch(InterruptedException e){}
        }
    }
    class testStatic{
        public void print(){
            libUtil.printString("did u miss me ?");
        }
    }
}
