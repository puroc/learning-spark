/*******************************************************************************
 * @(#)AndroidComamndResult.java 2015年8月21日
 *
 * Copyright 2015 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 查询请求消息
 * @author <a href="mailto:fengjian@emrubik.com">feng jian</a>
 * @version $Revision 1.0 $ 2015年8月21日 上午9:51:32
 */
@JsonInclude(Include.NON_NULL)
public class QueryRequest {

    /**
     * 请求类型
     */
    private String type;

    /**
     * 请求时间戳
     */
    private Long dateTime;

    /**
     * 请求的业务数据
     */
    private String data;

    /**
     * 获取type字段数据
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type字段数据
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取dateTime字段数据
     * @return Returns the dateTime.
     */
    public Long getDateTime() {
        return dateTime;
    }

    /**
     * 设置dateTime字段数据
     * @param dateTime
     *            The dateTime to set.
     */
    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * 获取data字段数据
     * @return Returns the data.
     */
    public String getData() {
        return data;
    }

    /**
     * 设置data字段数据
     * @param data
     *            The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }

}
