package com.example.spark.java.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {

    @org.junit.Test
    public void test1() {
        SparkConf sparkConf = new SparkConf().setAppName("TimeSeriesAnalysis").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        JavaRDD<Integer> rdd = jsc.parallelize(list);
        JavaRDD<Integer> mapRdd = rdd.map(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 + 100;
            }
        });

        List<Integer> collect = mapRdd.collect();

        for (Integer v : collect) {
            System.out.println(v);
        }

        jsc.stop();
    }
}
