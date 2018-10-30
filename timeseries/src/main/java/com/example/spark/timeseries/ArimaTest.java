package com.example.spark.timeseries;

import com.cloudera.sparkts.models.ARIMA;
import com.cloudera.sparkts.models.ARIMAModel;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


//运行时需要添加虚拟机参数 -Djava.library.path=/usr/local/lib
public class ArimaTest {

    //    private static final String MASTER_IP = "yarn-client";
    private static final String MASTER_IP = "local";
    public static final String TIMESTAMP = "timestamp";
    public static final String DEVICE_ID = "deviceId";
    public static final String READING = "reading";
    public static final String FILE_PATH = "timeseries/src/main/resources/test3_old_1.csv";

    public static void main(String[] args) {
        JavaSparkContext ctx = new JavaSparkContext(MASTER_IP, "ArimaTest");
        SQLContext sqlContext = new SQLContext(ctx);
        try {
            DataFrame dataFrame = loadDeviceData(ctx, sqlContext);

            List<Row> rows = dataFrame.collectAsList();

            int size = rows.size();
            double[] rowData = new double[size];
            for (int i = 0; i < size; i++) {
                rowData[i] = (double) rows.get(i).get(2);
            }

//            ZoneId zone = ZoneId.systemDefault();
//            ZonedDateTime startTime = ZonedDateTime.ofInstant(new Timestamp(1533608145247l).toInstant(), zone);
//            ZonedDateTime endTime = ZonedDateTime.ofInstant(new Timestamp(1536618521178l).toInstant(), zone);
//
//            DateTimeIndex dtIndex = DateTimeIndexFactory.uniformFromInterval(startTime, endTime, new HourFrequency(1));
//
//            // Align the ticker data on the DateTimeIndex to create a TimeSeriesRDD
//            JavaTimeSeriesRDD<String> rowRdd = JavaTimeSeriesRDDFactory.timeSeriesRDDFromObservations(
//                    dtIndex, dataFrame, TIMESTAMP, DEVICE_ID, READING);
//
//            List<Tuple2<String, Vector>> collect = rowRdd.collect();
//
//
//            System.out.println("!!!!!!!!!!!!!!!!!!!!");
//
//            for (Tuple2<String, Vector> tuple2 : collect) {
//                System.out.println(tuple2._1 + "," + tuple2._2);
//            }


            //将原始数据转换为向量
            Vector ts = Vectors.dense(rowData);

            //获得模型
            ARIMAModel arimaModel = ARIMA.fitModel(1, 1, 1, ts, true, "css-cgd", null);

            //打印模型系数
            printCoefficients(arimaModel);

            //预测数据
            forecast(ts, arimaModel,20);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (ctx != null) {
                ctx.stop();
            }
        }
    }

    private static DataFrame loadDeviceData(JavaSparkContext ctx, SQLContext sqlContext) {
        JavaRDD<String> rdd = ctx.textFile(FILE_PATH);
        JavaRDD<Row> rdd1 = rdd.map(new Function<String, Row>() {
            @Override
            public Row call(String line) throws Exception {
                String[] split = line.split(",");
                long timestamp = Long.parseLong(split[0]);
                String deviceId = split[1];
                double reading = Double.parseDouble(split[2]);
                return RowFactory.create(new Timestamp(timestamp), deviceId, reading);
            }
        });

        List<StructField> fields = new ArrayList();
        fields.add(DataTypes.createStructField(TIMESTAMP, DataTypes.TimestampType, true));
        fields.add(DataTypes.createStructField(DEVICE_ID, DataTypes.StringType, true));
        fields.add(DataTypes.createStructField(READING, DataTypes.DoubleType, true));
        StructType schema = DataTypes.createStructType(fields);
        return sqlContext.createDataFrame(rdd1, schema);
    }

    private static void printCoefficients(ARIMAModel arimaModel) {
        double[] coefficients = arimaModel.coefficients();
        StringBuilder sb1 = new StringBuilder("coefficient:");
        for (double coefficient : coefficients) {
            sb1.append(coefficient + ",");
        }
        System.out.println(sb1.toString());
    }

    private static void forecast(Vector ts, ARIMAModel arimaModel, int forecastNum) {
        Vector forecast = arimaModel.forecast(ts, forecastNum);
        double[] forecastArray = forecast.toArray();
        StringBuilder sb2 = new StringBuilder("forecast:");
        for (double v : forecastArray) {
            sb2.append(new Double(Math.floor(v)).intValue() + ",");
        }
        System.out.println(sb2.toString());
    }

}
