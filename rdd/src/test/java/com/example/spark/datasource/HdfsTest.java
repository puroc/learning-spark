package com.example.spark.datasource;

import com.example.spark.SparkTestEnv;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by puroc on 2016/11/3.
 */
public class HdfsTest extends SparkTestEnv {
    private transient JavaSparkContext ctx;

    @Before
    public void setUp() throws Exception {
        ctx = new JavaSparkContext(MASTER_IP, this.getClass().getName());
    }

    @After
    public void tearDown() throws Exception {
        ctx.close();
    }

    @Test
    public void testRead() throws Exception {
        JavaRDD<String> rdd = ctx.textFile("hdfs://" + HADOOP_MASTER + "/README.txt");
        JavaRDD<String> flatMapRdd = rdd.flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
        JavaPairRDD<String, Integer> result = flatMapRdd.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        for (Map.Entry<String, Integer> entry : result.collectAsMap().entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }
}
