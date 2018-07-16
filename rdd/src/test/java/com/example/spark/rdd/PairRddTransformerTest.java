package com.example.spark.rdd;

import com.example.spark.SparkTestEnv;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;
import scala.Tuple3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by puroc on 2016/11/3.
 */
public class PairRddTransformerTest extends SparkTestEnv {

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
    //对pairRdd中的元素进行map操作
    public void testMapValues() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        });

        JavaPairRDD<String, Integer> mapValuesRdd = inputPairRDD.mapValues(new Function<Integer, Integer>() {
            public Integer call(Integer v1) throws Exception {
                return v1 + 1;
            }
        });
        Assert.assertTrue(expectPairRDD.count() != 0);
        Assert.assertTrue(mapValuesRdd.subtract(expectPairRDD).count() == 0);
    }

    @Test
    //对pairRdd中的元素进行flatmap操作
    public void testFlatMapValues() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 200));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 200));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 200));
            }
        });

        JavaPairRDD<String, Integer> flatMapValuesRdd = inputPairRDD.flatMapValues(new Function<Integer, Iterable<Integer>>() {
            public Iterable<Integer> call(final Integer v1) throws Exception {
                return new ArrayList<Integer>() {{
                    this.add(v1);
                    this.add(200);
                }};
            }
        });

        Assert.assertTrue(flatMapValuesRdd.subtract(expectPairRDD).count() == 0);
    }

    @Test
    //按照key对pairRdd中的元素进行组合操作
    public void testComineByKey() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });

        JavaPairRDD<String, Integer> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        });

        JavaPairRDD<String, Integer> combineByKeyRdd = inputPairRDD.combineByKey(new Function<Integer, Integer>() {
            public Integer call(Integer v1) throws Exception {//第一次遇到这个key(v1),选择使用v1为key进行统计
                return v1;
            }
        }, new Function2<Integer, Integer, Integer>() {//当再次遇到这个key时,将当前key的值与之前累计的值进行叠加
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        }, new Function2<Integer, Integer, Integer>() {//shuffle之后,合并每个key的累计值,得到该RDD中,每个key的总累计值
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        Assert.assertTrue(expectPairRDD.subtract(combineByKeyRdd).count() == 0);
    }

    @Test
    //与combineByKey类似,但可以指定一个初始值
    public void testFoldByKey() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });

        JavaPairRDD<String, Integer> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 12));
                this.add(new Tuple2<String, Integer>("2", 12));
                this.add(new Tuple2<String, Integer>("3", 12));
            }
        });

        JavaPairRDD<String, Integer> foldByKeyRdd = inputPairRDD.foldByKey(10, new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

//        for (Map.Entry<String, Integer> entry : foldByKeyRdd.collectAsMap().entrySet()) {
//            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
//        }

        Assert.assertTrue(expectPairRDD.subtract(foldByKeyRdd).count() == 0);
    }

    @Test
    public void testReduceByKey() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });

        JavaPairRDD<String, Integer> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 2));
                this.add(new Tuple2<String, Integer>("2", 2));
                this.add(new Tuple2<String, Integer>("3", 2));
            }
        });

        JavaPairRDD<String, Integer> reduceByKeyRdd = inputPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        Assert.assertTrue(reduceByKeyRdd.subtract(expectPairRDD).count() == 0);

    }

    @Test
    //将rdd按照key进行分组,每个key的value是一个集合
    public void testGroupByKey() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });

        JavaPairRDD<String, Iterable<Integer>> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Iterable<Integer>>>() {
            {
                this.add(new Tuple2<String, Iterable<Integer>>("1", Arrays.asList(1, 1)));
                this.add(new Tuple2<String, Iterable<Integer>>("2", Arrays.asList(1, 1)));
                this.add(new Tuple2<String, Iterable<Integer>>("3", Arrays.asList(1, 1)));
            }
        });

        JavaPairRDD<String, Iterable<Integer>> groupByKeyRdd = inputPairRDD.groupByKey();

        Assert.assertTrue(groupByKeyRdd.count() == 3);
    }

    @Test
    //按照key进行升序、降序排列
    public void testSortByKey() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> expectPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("3", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("1", 1));
            }
        });

        JavaPairRDD<String, Integer> sortByKeyRdd = inputPairRDD.sortByKey(false);
        Assert.assertTrue(expectPairRDD.subtract(sortByKeyRdd).count() == 0);
    }

    @Test
    //将多个pairRdd的元素合并
    public void testCogroup() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> inputPairRDD2 = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("4", 1));
                this.add(new Tuple2<String, Integer>("5", 1));
                this.add(new Tuple2<String, Integer>("6", 1));
            }
        });

        JavaPairRDD<String, Integer> inputPairRDD3 = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("7", 1));
                this.add(new Tuple2<String, Integer>("8", 1));
                this.add(new Tuple2<String, Integer>("9", 1));
            }
        });

        JavaPairRDD<String, Tuple2<Iterable<Integer>, Iterable<Integer>>> cogroupRdd = inputPairRDD.cogroup(inputPairRDD2);

        for (Map.Entry<String, Tuple2<Iterable<Integer>, Iterable<Integer>>> entry : cogroupRdd.collectAsMap().entrySet()) {
            Tuple2<Iterable<Integer>, Iterable<Integer>> value = entry.getValue();
            System.out.println("key:" + entry.getKey() + ",value1:" +
                    value._1.toString() + ",value2:" +
                    value._2.toString());
        }
        JavaPairRDD<String, Tuple3<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> cogroupRdd2 = inputPairRDD.cogroup(inputPairRDD2, inputPairRDD3);

        for (Map.Entry<String, Tuple3<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>>> entry : cogroupRdd2.collectAsMap().entrySet()) {
            Tuple3<Iterable<Integer>, Iterable<Integer>, Iterable<Integer>> value = entry.getValue();
            System.out.println("key:" + entry.getKey() + ",value1:" +
                    value._1().toString() + ",value2:" +
                    value._2().toString() + ",value3:" + value._3());
        }
    }

    @Test
    //将两个pairRdd中相同key的值做笛卡尔积
    public void testJoin() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> inputPairRDD2 = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("6", 1));
            }
        });
        JavaPairRDD<String, Tuple2<Integer, Integer>> joinRdd = inputPairRDD.join(inputPairRDD2);
        Map<String, Tuple2<Integer, Integer>> collectAsMap = joinRdd.collectAsMap();
        for (Map.Entry<String, Tuple2<Integer, Integer>> entry : collectAsMap.entrySet()) {
            Tuple2<Integer, Integer> value = entry.getValue();
            System.out.println("key:" + entry.getKey() + ",value1:" +
                    value._1.toString() + ",value2:" +
                    value._2.toString());
        }
    }

    @Test
    //左连接
    public void testLeftOutJoin() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> inputPairRDD2 = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 10));
                this.add(new Tuple2<String, Integer>("2", 20));
                this.add(new Tuple2<String, Integer>("6", 1));
            }
        });
        JavaPairRDD<String, Tuple2<Integer, Optional<Integer>>> rdd = inputPairRDD.leftOuterJoin(inputPairRDD2);
        for (Map.Entry<String, Tuple2<Integer, Optional<Integer>>> entry : rdd.collectAsMap().entrySet()) {
            Tuple2<Integer, Optional<Integer>> value = entry.getValue();
            System.out.println("key:" + entry.getKey() + ",v1:" + value._1() + ",v2:" + value._2());
        }
//        System.out.println("count:"+ rdd.count());
    }

    @Test
    //右连接
    public void testRightOutJoin() throws Exception {
        JavaPairRDD<String, Integer> inputPairRDD = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 1));
                this.add(new Tuple2<String, Integer>("2", 1));
                this.add(new Tuple2<String, Integer>("3", 1));
            }
        });
        JavaPairRDD<String, Integer> inputPairRDD2 = ctx.parallelizePairs(new ArrayList<Tuple2<String, Integer>>() {
            {
                this.add(new Tuple2<String, Integer>("1", 10));
                this.add(new Tuple2<String, Integer>("2", 20));
                this.add(new Tuple2<String, Integer>("6", 1));
            }
        });
        for (Map.Entry<String, Tuple2<Optional<Integer>, Integer>> entry : inputPairRDD.rightOuterJoin(inputPairRDD2).collectAsMap().entrySet()) {
            Tuple2<Optional<Integer>, Integer> value = entry.getValue();
            System.out.println("key:" + entry.getKey() + ",v1:" + value._1() + ",v2:" + value._2());
        }
    }
}
