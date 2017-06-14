package com.agoda.sparkjmxsink;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lhao on 6/6/17.
 */
public class Client {

    private final static String hostName= "10.120.214.88";
    private final static int portNum = 8090;

    public static void main(String[] args) throws IOException {

        JMXServiceURL u = new JMXServiceURL(
                "service:jmx:rmi:///jndi/rmi://" + hostName + ":" + portNum +  "/jmxrmi");
        JMXConnector c = JMXConnectorFactory.connect(u);

        MBeanServerConnection connection = c.getMBeanServerConnection();

        connection.getMBeanCount();

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);

        System.out.println(sdf.format(resultdate));
    }

}
