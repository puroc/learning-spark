package com.example.spark.datasource;

import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by puroc on 16/11/29.
 */
public class HdfsTest extends SparkTestEnv {

    private SparkSession session;

    private final String FILE_PATH = "src/test/resources/";

    @Before
    public void setUp() throws Exception {
        session = SparkSession
                .builder()
                .master(MASTER_IP)
                .appName("FileSystemTest")
                .getOrCreate();
    }

    @After
    public void tearDown() throws Exception {
        session.stop();
    }

    @Test
    public void readFromHdfs() throws Exception {
//        final Dataset<Row> df = session.read().text("hdfs://" + HADOOP_MASTER + "/README.txt);
//        System.out.println(df.count())

//        final Dataset<String> flatMapDf = df.flatMap(new FlatMapFunction<Row, String>() {
//            public Iterator<String> call(Row row) throws Exception {
//                return Arrays.asList(row.toString().split(" ")).iterator();
//            }
//        }, Encoders.STRING());
//
//        JavaPairRDD<String, Integer> result = flatMapDf.toJavaRDD().mapToPair(new PairFunction<String, String, Integer>() {
//            public Tuple2<String, Integer> call(String s) throws Exception {
//                return new Tuple2<String, Integer>(s, 1);
//            }
//        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                return v1 + v2;
//            }
//        });
//        for (Map.Entry<String, Integer> entry : result.collectAsMap().entrySet()) {
//            System.out.println(entry.getKey() + "," + entry.getValue());
//        }
    }
}
