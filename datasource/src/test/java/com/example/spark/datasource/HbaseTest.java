package com.example.spark.datasource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;

import java.io.IOException;

/**
 * Created by puroc on 16/11/28.
 */
public class HbaseTest extends SparkTestEnv {

    private Configuration conf;

//    @Before
//    public void setUp() throws Exception {
//        conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("hbase.zookeeper.quorum", "docker-1,docker-2");
//    }


    private SparkSession session;

    private final String FILE_PATH = "src/test/resources/";

    @Before
    public void setUp() throws Exception {

        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "docker-1");

        session = SparkSession
                .builder()
                .master(MASTER_IP)
                .appName("HBaseTest")
                .getOrCreate();
        session.sparkContext().addJar("/Users/puroc/Documents/repo/org/apache/hbase/hbase-client/1.2.4/hbase-client-1.2.4.jar");
        session.sparkContext().addJar("/Users/puroc/Documents/repo/org/apache/hbase/hbase-common/1.2.4/hbase-common-1.2.4.jar");
    }

    @After
    public void tearDown() throws Exception {
        session.stop();
    }

    @Test
    public void readFromHbase() throws Exception {
        Scan scan = new Scan();
//        scan.setStartRow(Bytes.toBytes("195861-1035177490"));
//        scan.setStopRow(Bytes.toBytes("195861-1072173147"));
        scan.addFamily(Bytes.toBytes("cf"));
        scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("a"));

        try {
            //需要读取的hbase表名
            String tableName = "test";
            conf.set(TableInputFormat.INPUT_TABLE, tableName);
            conf.set(TableInputFormat.SCAN, convertScanToString(scan));

            //获得hbase查询结果Result
            RDD<Tuple2<ImmutableBytesWritable, Result>> hBaseRDD = session.sparkContext().newAPIHadoopRDD(conf,
                    TableInputFormat.class, ImmutableBytesWritable.class,
                    Result.class);

            Assert.assertTrue(hBaseRDD.count() != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertScanToString(Scan scan) throws IOException {
        ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
        return Base64.encodeBytes(proto.toByteArray());
    }

}
