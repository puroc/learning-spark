package com.example.spark.java.rdd;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public final class WordCount2 {

//    private static final String SPARK_HOME = "/root/spark/spark-2.0.1-bin-hadoop2.7";

//    private static final String APP_PATH = "/root/spark/app/learning-spark-1.0-SNAPSHOT-jar-with-dependencies.jar";

    private static final String APP_NAME = "MY.WordCount";

    private static final String MASTER_IP = "local";

//    private static final String MASTER_IP = "spark://10.10.20.248:7077";


    private static final String OUT_FILE_PATH = "/Users/puroc/IdeaProjects/learning-spark/out/rdd/wordcount";

    private static final String FILE_PATH = "/Users/puroc/IdeaProjects/learning-spark/rdd/src/main/resources/english.txt";

    public static void main(String[] args) throws Exception {

        JavaSparkContext ctx = new JavaSparkContext(MASTER_IP, APP_NAME);

//        JavaSparkContext ctx = new JavaSparkContext(MASTER_IP, APP_NAME, SPARK_HOME, APP_PATH);

        JavaRDD<String> lines = ctx.textFile(FILE_PATH, 1);

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2(s, 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer x, Integer y) throws Exception {
                return x + y;
            }
        });

        counts.persist(StorageLevel.MEMORY_AND_DISK());

        counts.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> tuple2) throws Exception {
                System.out.println(tuple2._1 + "," + tuple2._2);
            }
        });

        counts.saveAsTextFile(OUT_FILE_PATH);

        System.out.println("!!!!!!!!!!!");

    }

}