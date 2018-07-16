/*******************************************************************************
 * @(#)OpenTsdbResponse.java 2016年12月15日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient;

import java.util.Map;
import java.util.TreeMap;

/**
 * openTSDB response
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月15日 上午11:15:51
 */
public class OpenTsdbResponse {
    /**
     * metric
     */
    private String metric;
    
    /**
     * tags
     */
    private Map tags;
    
    /**
     * aggregateTags
     */
    private String[] aggregateTags;
    
    /**
     * dps
     */
    private TreeMap<Long, Double> dps;

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Map getTags() {
        return tags;
    }

    public void setTags(Map tags) {
        this.tags = tags;
    }

    public String[] getAggregateTags() {
        return aggregateTags;
    }

    public void setAggregateTags(String[] aggregateTags) {
        this.aggregateTags = aggregateTags;
    }

    public TreeMap<Long, Double> getDps() {
        return dps;
    }

    public void setDps(TreeMap<Long, Double> dps) {
        this.dps = dps;
    }
}
