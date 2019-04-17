package com.example.spark.java.rdd;

import com.example.spark.SparkTestEnv;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by puroc on 2016/10/31.
 */
public class RddTransformerTest extends SparkTestEnv {

    private static final String INPUT_FILE = "input/README.md";

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
    //对RDD中的每个元素做map操作
    public void testMap() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> expectRdd = ctx.parallelize(Arrays.asList(2, 3, 4));
        JavaRDD<Integer> map = inputRdd.map(new Function<Integer, Integer>() {
            public Integer call(Integer v1) throws Exception {
                return v1 + 1;
            }
        });
        JavaRDD<Integer> resultRdd = map.subtract(expectRdd);
        Assert.assertTrue(resultRdd.count() == 0);
    }

    @Test
    //对RDD中的每个元素做map操作,并返回一个集合,最终RDD是由多个集合元素组成
    public void testMapReturnCollection() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<List<Integer>> map = inputRdd.map(new Function<Integer, List<Integer>>() {
            public List<Integer> call(Integer v1) throws Exception {
                List<Integer> list = new ArrayList<Integer>();
                list.add(v1);
                list.add(1);
                return list;
            }
        });
        List<List<Integer>> collect = map.collect();
        for (List<Integer> it :
                collect) {
            Assert.assertTrue(it.size() == 2);
        }
    }

    @Test
    //对RDD的每个元素做map操作,并返回一个集合,最终RDD内的数据是打撒的
    public void testFlatmap() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> expectRdd = ctx.parallelize(Arrays.asList(1, 1, 2, 1, 3, 1));
        JavaRDD<Integer> flatmap = inputRdd.flatMap(new FlatMapFunction<Integer, Integer>() {

            public Iterator<Integer> call(Integer s) throws Exception {
                List<Integer> list = new ArrayList<Integer>();
                list.add(s);
                list.add(1);
                return list.iterator();
            }
        });
        JavaRDD<Integer> resultRdd = flatmap.subtract(expectRdd);
        Assert.assertTrue(resultRdd.count() == 0);
    }

    @Test
    //对一个分区中的所有数据做map操作
    public void testMapPartitions() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(new Integer[]{1, 2, 3}));
        final List<Integer> list = new ArrayList<Integer>();
        JavaRDD<Integer> mapPartitions = inputRdd.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {
            public Iterator<Integer> call(Iterator<Integer> integerIterator) throws Exception {
                while (integerIterator.hasNext()) {
                    Integer next = integerIterator.next();
                    list.add(next);
                }
                return list.iterator();
            }
        });
        Assert.assertTrue(mapPartitions.count() != 0);
    }

    //    @Test
    public void testMapPartitionsWithIndex() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3));
        inputRdd.mapPartitionsWithIndex(new Function2<Integer, Iterator<Integer>, Iterator<Integer>>() {
            public Iterator<Integer> call(Integer v1, Iterator<Integer> v2) throws Exception {
                return null;
            }
        }, true);

    }

    @Test
    //从RDD中随机抽取样例数据
    public void testSample() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        long preCount = inputRdd.count();
        //从10个数字中抽取70%的样例数据
        JavaRDD<Integer> sample = inputRdd.sample(true, 0.5);
        List<Integer> collect = sample.collect();

        int sampleSize = collect.size();
        long inputCount = inputRdd.count();
//        System.out.println("samplesize:" + sampleSize + ",inputCount:" + inputCount);
        Assert.assertTrue(sampleSize < inputCount);
    }

    @Test
    //union操作是不会去除重复元素的
    public void testUnion() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> inputRdd2 = ctx.parallelize(Arrays.asList(3, 5, 6));
        JavaRDD<Integer> unionRdd = inputRdd1.union(inputRdd2);
//        System.out.println("uninoCount:"+unionRdd.count());
        Assert.assertTrue(unionRdd.count() == 6);
    }

    @Test
    //intersection是取两个RDD的交集
    public void testIntersection() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> inputRdd2 = ctx.parallelize(Arrays.asList(3, 5, 6));
        JavaRDD<Integer> intersectionRdd = inputRdd1.intersection(inputRdd2);
        Assert.assertTrue(intersectionRdd.count() == 1);
    }

    @Test
    //distinct是合并另个RDD,并且去重
    public void testDistinct() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> inputRdd2 = ctx.parallelize(Arrays.asList(3, 5, 6));
        JavaRDD<Integer> distinctRdd = inputRdd1.union(inputRdd2).distinct();
        Assert.assertTrue(distinctRdd.count() == 5);
    }

    @Test
    //两个RDD做笛卡尔积
    public void testCartesian() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3));
        JavaRDD<Integer> inputRdd2 = ctx.parallelize(Arrays.asList(3, 5, 6));

        JavaPairRDD<Integer, Integer> cartesianRdd = inputRdd1.cartesian(inputRdd2);
        cartesianRdd.cache();
        long count = cartesianRdd.count();
        Assert.assertTrue(count == 9);
        List<Tuple2<Integer, Integer>> collect = cartesianRdd.collect();
        for (Tuple2<Integer, Integer> t :
                collect) {
            System.out.println("[" + t._1() + "," + t._2() + "]");
        }
    }


    @Test
    //改变RDD分区数量
    public void testCoalesce() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3), 2);
        //shuffle默认值为false,为false时不能增加分区数,虽然不报错,但是分区数不变,只有为true时可以增加分区数
        JavaRDD<Integer> coalesceRdd = inputRdd1.coalesce(5, true);
        Assert.assertTrue(inputRdd1.partitions().size() == 2);
        Assert.assertTrue(coalesceRdd.partitions().size() == 5);
//        System.out.println(coalesceRdd.partitions().size());
    }

    @Test
    //repartition跟coalesce(分区数,true)效果一样
    public void testRepartition() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3), 2);
        JavaRDD<Integer> repartitionRdd = inputRdd1.repartition(5);
        Assert.assertTrue(inputRdd1.partitions().size() == 2);
        Assert.assertTrue(repartitionRdd.partitions().size() == 5);
    }

    @Test
    //glom将分区中的所有元素添加到一个数组中,这样每个分区中只有一个数组元素
    public void testGlom() throws Exception {
        JavaRDD<Integer> inputRdd1 = ctx.parallelize(Arrays.asList(1, 2, 3), 2);
        List<List<Integer>> collect = inputRdd1.glom().collect();
        for (List<Integer> list : collect) {
            System.out.println(list.toString());
        }
    }

    @Test
    //将RDD按照权重分成多个RDD
    public void testRandomSplit() throws Exception {
        JavaRDD<Integer> inputRdd = ctx.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        //设置权重1.0, 2.0, 7.0
        JavaRDD<Integer>[] randomSplitRdd = inputRdd.randomSplit(new double[]{1.0, 2.0, 7.0});
        int sum = 0;
        for (JavaRDD<Integer> rdd : randomSplitRdd) {
            sum += rdd.count();
        }
        Assert.assertTrue(inputRdd.count() == sum);
    }
}

