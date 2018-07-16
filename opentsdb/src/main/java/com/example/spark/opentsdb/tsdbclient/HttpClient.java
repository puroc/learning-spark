package com.example.spark.opentsdb.tsdbclient;


import com.example.spark.opentsdb.tsdbclient.builder.MetricBuilder;
import com.example.spark.opentsdb.tsdbclient.request.QueryBuilder;
import com.example.spark.opentsdb.tsdbclient.response.Response;
import com.example.spark.opentsdb.tsdbclient.response.SimpleHttpResponse;

import java.io.IOException;



public interface HttpClient extends Client {

	public Response pushMetrics(MetricBuilder builder,
								ExpectResponse exceptResponse) throws IOException;

	public SimpleHttpResponse pushQueries(QueryBuilder builder,
										  ExpectResponse exceptResponse) throws IOException;
}