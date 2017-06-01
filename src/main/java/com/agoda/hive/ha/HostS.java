package com.agoda.hive.ha;


import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.DataView;
import com.cloudera.api.model.ApiCluster;
import com.cloudera.api.model.ApiClusterList;
import com.cloudera.api.model.ApiHost;
import com.cloudera.api.model.ApiHostList;
import com.cloudera.api.v11.RootResourceV11;

/**
 * Created by lhao on 5/11/17.
 */
public class HostS {

    public static void main(String[] args) {

        /**
         * cm = "10.117.193.207"
         *
         * 10.104.193.200
         port = 7180
         user = "hk-bi-usr"
            //admin
         SGG-----H@d0op@sgp#
         HKG-----H@d0op@hkg#
         pwd = "fBrte!6gU"
         */
        RootResourceV11 apiRoot = new ClouderaManagerClientBuilder().withHost("10.117.193.207")
                .withUsernamePassword("admin", "H@d0op@hkg#").build().getRootV11();


        ApiHostList apiHosts = apiRoot.getHostsResource().readHosts(DataView.SUMMARY);

        for (ApiHost host:apiHosts
             ) {

            System.out.println(host.getIpAddress());

        }
        ApiClusterList clusters = apiRoot.getClustersResource().readClusters(DataView.SUMMARY);
        for (ApiCluster cluster : clusters) {
            //System.out.println( cluster.getName()+"----"+ cluster.getVersion());

        }

    }
}
