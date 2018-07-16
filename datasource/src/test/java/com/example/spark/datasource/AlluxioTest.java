package com.example.spark.datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by puroc on 16/11/28.
 */
public class AlluxioTest extends SparkTestEnv{

    private SparkSession session;

    private final String FILE_PATH = "src/test/resources/";

    @Before
    public void setUp() throws Exception {
        session = SparkSession
                .builder()
                .master(MASTER_IP)
                .appName("AlluxioTest")
                .getOrCreate();
    }

    @Test
    public void readFromAlluxio() throws Exception {
        final Dataset<Row> df = session.read().text("alluxio://" + ALLUXIO_MASTER + "/LICENSE");
        df.count();

    }
}
