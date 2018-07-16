package datasource;

import java.io.Serializable;

/**
 * Created by puroc on 2016/10/31.
 */
public class SparkTestEnv implements Serializable{

    protected final String  MASTER_IP = "local";
//
    protected final String HADOOP_MASTER="172.16.114.208:9000";

//    protected final String  MASTER_IP = "spark://10.10.20.204:7077";

//    protected final String HADOOP_MASTER="172.16.114.205:9000";

    protected final String ALLUXIO_MASTER="10.10.20.204:19998";

}
