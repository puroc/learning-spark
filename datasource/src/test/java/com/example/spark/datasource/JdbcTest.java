package com.example.spark.datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by puroc on 2016/11/7.
 */
public class JdbcTest extends SparkTestEnv{

    private SparkSession session;

    private final String FILE_PATH = "src/test/resources/";

    @Before
    public void setUp() throws Exception {
        session = SparkSession
                .builder()
                .master("local")
                .appName("JdbcTest")
                .getOrCreate();
    }

    @After
    public void tearDown() throws Exception {
        session.stop();
    }

    @Test
    public void readByJdbc() throws Exception {

//        Dataset<Row> jdbcDF = session.read()
//                .format("jdbc")
//                .option("url", "jdbc:postgresql:dbserver")
//                .option("dbtable", "schema.tablename")
//                .option("user", "username")
//                .option("password", "password")
//                .load();

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "postgres");
        connectionProperties.put("password", "123456");
        Dataset<Row> jdbcDF2 = session.read()
                .jdbc("jdbc:postgresql:test", "student", connectionProperties);
        for (Row row : jdbcDF2.collectAsList()) {
            System.out.println(row.toString());
        }

        // Saving data to a JDBC source
//        jdbcDF.write()
//                .format("jdbc")
//                .option("url", "jdbc:postgresql:dbserver")
//                .option("dbtable", "schema.tablename")
//                .option("user", "username")
//                .option("password", "password")
//                .save();
//
//        jdbcDF2.write()
//                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);
        // $example off:jdbc_dataset$

    }
}
