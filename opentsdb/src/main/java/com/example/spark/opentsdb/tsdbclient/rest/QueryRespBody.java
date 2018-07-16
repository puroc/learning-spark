/*******************************************************************************
 * @(#)QueryRespBody.java 2016年11月29日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

import java.util.Date;

/**
 * 查询应答body类
 * @author <a href="mailto:fengjian@emrubik.com">feng jian</a>
 * @version $Revision 1.0 $ 2016年11月29日 下午3:29:13
 */
public abstract class QueryRespBody extends RespBody {

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

}
