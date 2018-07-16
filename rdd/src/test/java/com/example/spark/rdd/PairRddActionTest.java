package com.example.spark.rdd;

import com.example.spark.SparkTestEnv;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by puroc on 2016/11/3.
 */
public class PairRddActionTest extends SparkTestEnv {
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
    public void testCountByKey() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        }, 2);
        for (Map.Entry<String, Long> entry : inputPairRDD.countByKey().entrySet()) {
            Assert.assertTrue(entry.getValue() == 2);
        }
    }

    @Test
    //collectAsMap函数不包含重复的key,对于重复的key,后面的元素覆盖前面的元素
    public void testCollectAsMap() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        }, 2);
        for (Map.Entry<String, Integer> entry : inputPairRDD.collectAsMap().entrySet()) {
            Assert.assertTrue(entry.getValue() == 2);
        }
    }

    @Test
    public void testLookup() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        }, 2);
        List<Integer> result = inputPairRDD.lookup("1");
        Assert.assertArrayEquals( new Integer[]{1, 2},result.toArray());
    }

    @Test
    //聚合各个分区的数据,可以指定分区内和分区间的聚合算法
    public void testAggregate() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        }, 3);
        inputPairRDD.aggregate(0, new Function2<Integer, Tuple2<String, Integer>, Integer>() {
            public Integer call(Integer v1, Tuple2<String, Integer> v2) throws Exception {//v1是分区内聚合操作的结果,v2是分区内每个元素
                return v1 + v2._2();
            }
        }, new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {//v1是分区间聚合操作结果,v2是各个分区汇聚的结果
                return v1+v2;
            }
        });
    }

    @Test
    //相当于SeqOp和comOp函数都相同的aggregate函数
    public void testFold() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        },2);
        Tuple2<String, Integer> result = inputPairRDD.fold(new Tuple2<String, Integer>("", 0), new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
            public Tuple2<String, Integer> call(Tuple2<String, Integer> v1, Tuple2<String, Integer> v2) throws Exception {
                return new Tuple2<String, Integer>(v1._1()+"+"+v2._1(),v1._2()+v2._2());
            }
        });
        System.out.println(result._1() + "=" + result._2());
    }


}
