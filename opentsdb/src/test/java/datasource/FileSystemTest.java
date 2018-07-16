package datasource;

import com.example.spark.opentsdb.tsdbclient.*;
import com.example.spark.opentsdb.tsdbclient.request.Filter;
import com.example.spark.opentsdb.tsdbclient.request.QueryBuilder;
import com.example.spark.opentsdb.tsdbclient.request.SubQueries;
import com.example.spark.opentsdb.tsdbclient.response.SimpleHttpResponse;
import com.example.spark.opentsdb.tsdbclient.rest.MetricTimeRange;
import com.example.spark.opentsdb.tsdbclient.util.Aggregator;
import com.fasterxml.jackson.core.type.TypeReference;
import datasource.domain.Cabinet;
import datasource.domain.CabinetData;
import datasource.domain.CpuInfo;
import datasource.domain.People;
import datasource.utils.JSONUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by puroc on 2016/11/7.
 */
public class FileSystemTest extends SparkTestEnv {

    private SparkSession session;

    private final String FILE_PATH = "src/test/resources/";

    public static final String ADDRESS = "http://10.10.30.183:31998";

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
    public void readFromOpenTsdb() throws Exception {
        try {
            HttpClientImpl openTSDBClient = OpenTsdbClient.getInstance(ADDRESS);
            QueryBuilder builder = QueryBuilder.getInstance();
            List<Filter> filters = new ArrayList<Filter>();
            String timeRange = MetricTimeRange.thirtymin.toString();

            SubQueries ioWaitSubQueries = QueryBuilderUtils.initSubQueries(filters,
                    "system.cpu.used", Aggregator.sum.toString(),
                    QueryDateUtils.getDownsample(timeRange, Aggregator.avg.toString()));

            long start = QueryDateUtils.getStartDate(timeRange).getTime() / 1000;
            long now = QueryDateUtils.getCurDate().getTime() / 1000;

            builder.getQuery().addStart(start).addEnd(now).addSubQuery(ioWaitSubQueries);

            SimpleHttpResponse response = openTSDBClient.pushQueries(builder, ExpectResponse.STATUS_CODE);
            final String content = response.getContent();
            System.out.println(content);

            List<OpenTsdbResponse> openTsdbResponses = ObjectMapperUtils.getInstance()
                    .readValue(content, new TypeReference<List<OpenTsdbResponse>>() {
                    });
            final JavaSparkContext sparkContext = new JavaSparkContext(session.sparkContext());

            List<String> result = new ArrayList<String>();

            for (OpenTsdbResponse resp : openTsdbResponses) {
                for (Long key : resp.getDps().keySet()) {
//                    System.out.println(("key:" + key + ",value:" + resp.getDps().get(key)));
                    result.add(key+","+resp.getDps().get(key));
                }
            }

            final JavaRDD<String> rdd = sparkContext.parallelize(result);

            final Dataset<CpuInfo> dataset = session.read().json(rdd).flatMap(new FlatMapFunction<Row,
                    CpuInfo>() {
                public Iterator<CpuInfo> call(Row row) throws Exception {
                    CpuInfo cpu = new CpuInfo();
                    cpu.setTime(row.getString(0).split(",")[0]);
                    cpu.setValue(row.getString(0).split(",")[1]);
//                    final String s = row.toString();
//                    final CpuInfo cpuInfo = JSONUtils.toObj(s, CpuInfo.class);
//                    System.out.println(JSONUtils.toJson(cpuInfo));
                    return Arrays.asList(cpu).iterator();
                }
            }, Encoders.bean(CpuInfo.class));


//            final Dataset<CpuInfo> flatMapDf = rdd.flatMap(new FlatMapFunction<String, CpuInfo>() {
//                public Iterator<CpuInfo> call(String s) throws Exception {
//                    return Arrays.asList(JSONUtils.toObj(s, CpuInfo.class)).iterator();
//                }
//            }, Encoders.bean(CpuInfo.class));

            for (CpuInfo cpu : dataset.collectAsList()) {
                System.out.println(cpu.getTime() + "," + cpu.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void readFromFile() throws Exception {
        final Dataset<String> df = session.read().textFile(FILE_PATH + "README.md");
        for (String s : df.collectAsList()) {
            System.out.println("--------------------------------------------");
            System.out.println(s);
            System.out.println("--------------------------------------------");
        }
    }

    @Test
    public void readText() throws Exception {
        final Dataset<String> df = session.read().textFile(new String[]{FILE_PATH + "README.md", FILE_PATH + ""});
        for (String s : df.collectAsList()) {
            System.out.println("--------------------------------------------");
            System.out.println(s);
            System.out.println("--------------------------------------------");
        }
    }

    @Test
    public void writeText() throws Exception {
        session.createDataset(Arrays.asList(1, 2, 3), Encoders.INT()).toJavaRDD().saveAsTextFile(FILE_PATH +
                "output-text");
    }

    @Test
    public void readCsv() throws Exception {
        final Dataset<Row> df = session.read().csv(FILE_PATH + "test.csv");
        for (Row row : df.collectAsList()) {
            System.out.println("--------------------------------------------");
            System.out.println(row.toString());
            System.out.println("--------------------------------------------");
        }
    }

    @Test
    public void writeCsv() throws Exception {
        session.createDataset(Arrays.asList("1,2,3", "4,5,6"), Encoders.STRING()).toJavaRDD().saveAsTextFile
                (FILE_PATH + "output-csv");
    }

    @Test
    public void readJson() throws Exception {
        final Dataset<String> json = session.read().json(FILE_PATH + "json").toJSON();
        for (String s : json.collectAsList()) {
            System.out.println(s);
        }
        final Dataset<People> flatMapDf = json.flatMap(new FlatMapFunction<String, People>() {
            public Iterator<People> call(String s) throws Exception {
                return Arrays.asList(JSONUtils.toObj(s, People.class)).iterator();
            }
        }, Encoders.bean(People.class));

        for (People people : flatMapDf.collectAsList()) {
            System.out.println(people.getName() + "," + people.getAge());
        }
    }

    @Test
    public void writeJson() throws Exception {
        final People p1 = new People();
        p1.setName("xiaoming");
        p1.setAge(10);

        final People p2 = new People();
        p2.setName("wangwu");
        p2.setAge(25);

        final List<People> list = Arrays.asList(p1, p2);

        final JavaRDD<People> peopleJavaRDD = session.createDataset(list, Encoders.bean(People.class)).toJavaRDD();
        final JavaRDD<String> rdd = peopleJavaRDD.map(new Function<People, String>() {
            public String call(People v1) throws Exception {
                return JSONUtils.toJson(v1);
            }
        });
        rdd.saveAsTextFile(FILE_PATH + "output-json");
    }

    @Test
    public void readJson2() throws Exception {
        final Dataset<String> json = session.read().json(FILE_PATH + "iot").toJSON();
        for (String s : json.collectAsList()) {
            System.out.println(s);
        }
        final Dataset<Cabinet> flatMapDf = json.flatMap(new FlatMapFunction<String, Cabinet>() {
            public Iterator<Cabinet> call(String s) throws Exception {
                return Arrays.asList(JSONUtils.toObj(s, Cabinet.class)).iterator();
            }
        }, Encoders.bean(Cabinet.class));

        for (Cabinet cabinet : flatMapDf.collectAsList()) {
            System.out.println("deviceId:" + cabinet.getDeviceId() + ",temperature:" + cabinet.getData()
                    .getTemperature());
        }
    }

    @Test
    public void test() throws Exception {
        Cabinet c = new Cabinet();
        c.setDeviceId("001");
        c.setDeviceType("2");
        c.setCreateTime("2016-10-10");
        CabinetData data = new CabinetData();
        data.setAvailableRate("1");
        data.setAvailableSpace("2");
        data.setTemperature("3");
        c.setData(data);
        String x = JSONUtils.toJson(c);
        System.out.println(x);
        System.out.println(JSONUtils.toObj(x, Cabinet.class).getData().getTemperature());
    }

    @Test
    public void readSequenceFile() throws Exception {


    }

    @Test
    public void readProtobuf() throws Exception {


    }

    @Test
    public void readObjectFile() throws Exception {


    }


}
