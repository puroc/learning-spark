package com.example.spark.scala.rdd;

import com.example.spark.SparkTestEnv;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by puroc on 2016/11/3.
 */
public class RddActionTest extends SparkTestEnv {

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
    public void testReduce() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        Integer expect = 6;
        Integer result = inputRdd.reduce(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        Assert.assertEquals(expect, result);
    }

    @Test
    public void testCollect() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, inputRdd.collect().toArray());
    }

    @Test
    public void testCount() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        Assert.assertEquals(3, inputRdd.count());
    }

    @Test
    public void testFirst() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        Assert.assertTrue(inputRdd.first() == 1);
    }

    @Test
    public void testTake() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        Assert.assertArrayEquals(new Integer[]{1, 2}, inputRdd.take(2).toArray());
    }

    @Test
    public void testTop() throws Exception {
        Assert.assertArrayEquals(new Integer[]{3, 2}, ctx.parallelize(Arrays.asList(1, 2, 3)).top(2).toArray());
    }

    @Test
    //按自然顺序或者指定的排序规则返回前n个元素
    public void testTakeOrdered() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        List<Integer> list = inputRdd.takeOrdered(2);
        Assert.assertTrue(list.size() == 2);
    }
}
