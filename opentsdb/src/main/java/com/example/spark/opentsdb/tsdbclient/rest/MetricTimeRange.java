/*******************************************************************************
 * @(#)MetricQueryType.java 2016年12月12日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

/**
 * 性能指标过滤
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月12日 下午4:10:50
 */
public enum MetricTimeRange {
    fifteenmin, thirtymin, today, onehour, threehour, sixhour, oneday, oneweek, onemonth
}
