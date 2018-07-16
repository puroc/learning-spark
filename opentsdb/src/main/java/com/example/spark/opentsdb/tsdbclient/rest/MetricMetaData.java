/*******************************************************************************
 * @(#)MetricMetaData.java 2016年11月28日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

import com.emrubik.iot.nms.domain.metricmeta.SynchronismMetricMetaData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;
import java.util.List;

/**
 * 指标查询应答数据体
 * @author <a href="mailto:fengjian@emrubik.com">feng jian</a>
 * @version $Revision 1.0 $ 2016年11月28日 下午5:10:41
 */
@JsonInclude(Include.NON_NULL)
public class MetricMetaData extends RespBody {

    /**
     * 状态没有变化
     */
    public static final int STAT_NOCHANGE = 0;

    /**
     * 状态发生变化
     */
    public static final int STAT_CHANGE = 1;

    /**
     * 数据查询状态<br>
     * 0——数据无变化,不返回数据列表<br>
     * 1——数据变化,返回所有数据列表
     */
    private int stat;

    /**
     * 本次查询服务端的数据时间戳
     */
    private Date timestamp;

    /**
     * 数据时间戳
     */
    private List<SynchronismMetricMetaData> metricMetaList;

    /**
     * 获取stat字段数据
     * @return Returns the stat.
     */
    public int getStat() {
        return stat;
    }

    /**
     * 设置stat字段数据
     * @param stat
     *            The stat to set.
     */
    public void setStat(int stat) {
        this.stat = stat;
    }

    /**
     * 获取timestamp字段数据
     * @return Returns the timestamp.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * 设置timestamp字段数据
     * @param timestamp
     *            The timestamp to set.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取metricMetaList字段数据
     * @return Returns the metricMetaList.
     */
    public List<SynchronismMetricMetaData> getMetricMetaList() {
        return metricMetaList;
    }

    /**
     * 设置metricMetaList字段数据
     * @param metricMetaList
     *            The metricMetaList to set.
     */
    public void setMetricMetaList(List<SynchronismMetricMetaData> metricMetaList) {
        this.metricMetaList = metricMetaList;
    }

}
