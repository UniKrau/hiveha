package com.agoda.hotfix;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by lhao on 6/22/17.
 */
public class TestHotFix {


    public static void main(String[] args) {

        try {
            VirtualMachine vm = VirtualMachine.attach("8144");

            System.out.println(vm.id());

            Properties properties = vm.getSystemProperties();

            Map<String,String> mapPro = properties.entrySet().stream().collect(Collectors.toMap(
                    e -> (String)e.getKey(),e -> (String)e.getValue()
            ));

            for (Map.Entry<String,String > entry: mapPro.entrySet()
                 ) {

                System.out.println("key   is  "+ entry.getKey() +"\nvalue is  "+ entry.getValue());

            }



        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
