package com.example.spark.sql;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by puroc on 2016/11/6.
 */
public class DatasetTest extends SparkTestEnv {

    private SparkSession session;

//    private static final String regex = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
    private static final String regex = " (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9]";

    private final String FILE_PATH = "src/test/resources/";

    @Before
    public void setUp() throws Exception {
        session = SparkSession
                .builder()
                .master("local")
                .appName("Java Spark SQL basic example")
                .config("session.some.config.option", "some-value")
                .getOrCreate();

    }

    @After
    public void tearDown() throws Exception {
        session.stop();
    }

    @Test
    //返回数据集中数据的数量
    public void testReadJson() throws Exception {
//        session.read().textFile(FILE_PATH + "iot-09.txt").flatMap(new FlatMapFunction<String, String>() {
//            public Iterator<String> call(String s) throws Exception {
//                String[] arr = s.split(";");
//                for(int i=0;i<arr.length;i++){
//                    arr[i]=arr[i].replaceAll(regex, "");
//                }
//                return Arrays.asList(arr).iterator();
//            }
//        }, Encoders.STRING()).foreach(new ForeachFunction<String>() {
//            public void call(String s) throws Exception {
//                System.out.println(s);
//            }
//        });


        session.read().textFile(FILE_PATH + "iot-09.txt").flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(";")).iterator();
            }
        },Encoders.STRING()).map(new MapFunction<String, String>() {
            public String call(String s) throws Exception {
                return s.replaceAll(regex, "");
            }
        },Encoders.STRING()).foreach(new ForeachFunction<String>() {
            public void call(String s) throws Exception {
                System.out.println(s);
            }
        });


//        Dataset<Row> df = session.read().json(FILE_PATH + "people.json");
//        System.out.println(df.col("name").substr(0, 3));
//        df.createOrReplaceTempView("people");
//        final Dataset<Row> resultDf = session.sql("select * from people");
//        for (Row row : resultDf.collectAsList()) {
//            System.out.println(row.toString());
//        }
    }


}
