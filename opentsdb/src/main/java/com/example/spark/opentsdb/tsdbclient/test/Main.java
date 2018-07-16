package com.example.spark.opentsdb.tsdbclient.test;

import com.example.spark.opentsdb.tsdbclient.*;
import com.example.spark.opentsdb.tsdbclient.request.Filter;
import com.example.spark.opentsdb.tsdbclient.request.QueryBuilder;
import com.example.spark.opentsdb.tsdbclient.request.SubQueries;
import com.example.spark.opentsdb.tsdbclient.response.SimpleHttpResponse;
import com.example.spark.opentsdb.tsdbclient.rest.MetricTimeRange;
import com.example.spark.opentsdb.tsdbclient.util.Aggregator;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by puroc on 17/5/21.
 */
public class Main {

    public static final String ADDRESS = "http://10.10.30.183:31998";

    public static void main(String[] args) {
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
            for (OpenTsdbResponse resp : openTsdbResponses) {
                for (Long key : resp.getDps().keySet()) {
                    System.out.println(("key:" + key + ",value:" + resp.getDps().get(key)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
