package com.example.spark.scala.rdd;

import java.io.Serializable;

/**
 * Created by puroc on 2016/10/31.
 */
public class SparkTestEnv implements Serializable{

    protected final String  MASTER_IP = "local";
//
//    protected final String HADOOP_MASTER="192.168.99.129:9000";

//    protected final String  MASTER_IP = "spark://10.10.20.204:7077";

    protected final String HADOOP_MASTER="10.10.20.204:9000";

}
