/*******************************************************************************
 * @(#)QueryBuilderUtils.java 2016年12月15日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient;

import com.example.spark.opentsdb.tsdbclient.request.Filter;
import com.example.spark.opentsdb.tsdbclient.request.SubQueries;

import java.util.ArrayList;
import java.util.List;


/**
 * QueryBuilder工具类
 * 
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月15日 下午12:19:05
 */
public class QueryBuilderUtils {
    /**
     * 填加filter
     * @param filters filters
     * @param tagk tagk
     * @param type type
     * @param tagv tagv
     * @param groupBy groupBy
     * @return filters
     */
    public static List<Filter> addFilter(List<Filter> filters, String tagk, String type, String tagv, boolean groupBy) {
        if (null == filters) {
            filters = new ArrayList<Filter>();
        }
        Filter nodeFilter = new Filter();
        nodeFilter.setTagk(tagk);
        nodeFilter.setType(type);
        nodeFilter.setFilter(tagv);
        if (groupBy) {
            nodeFilter.setGroupBy(Boolean.TRUE);
        } else {
            nodeFilter.setGroupBy(Boolean.FALSE);
        }
        filters.add(nodeFilter);
        return filters;
    }
    
    /**
     * 初始化SubQueries
     * @param filters filters
     * @param metric metric
     * @param aggregator aggregator
     * @param downsample downsample
     * @return SubQueries
     */
    public static SubQueries initSubQueries(List<Filter> filters, String metric, String aggregator, String downsample) {
        SubQueries subQueries = new SubQueries();
        subQueries.addMetric(metric).addAggregator(aggregator)
                .addDownsample(downsample);
        subQueries.setFilters(filters);
        return subQueries;
    }
}
