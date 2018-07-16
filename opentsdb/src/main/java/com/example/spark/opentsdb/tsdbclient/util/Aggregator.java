/*******************************************************************************
 * @(#)Aggregator.java 2016年12月7日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.util;

/**
 * Aggregator
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月7日 下午3:42:36
 */
public enum Aggregator {
    avg,
    count,
    dev,
    ep50r3,
    ep50r7,
    ep75r3,
    ep75r7,
    ep90r3,
    ep90r7,
    ep95r3,
    ep95r7,
    ep99r3,
    ep99r7,
    ep999r3,
    ep999r7,
    mimmin,
    mimmax,
    min,
    max,
    none,
    p50,
    p75,
    p90,
    p95,
    p99,
    p999,
    sum,
    zimsum
}
