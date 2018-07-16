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
 * 查询应答
 * @author <a href="mailto:fengjian@emrubik.com">feng jian</a>
 * @version $Revision 1.0 $ 2015年8月21日 上午9:51:32
 */
@JsonInclude(Include.NON_NULL)
public class QueryResponse<T extends RespBody> {

    /**
     * 主动请求处理成功
     */
    public static final String STATUS__SUCC = "0";

    /**
     * 主动请求处理失败
     */
    public static final String STATUS__FAILED = "1";

    /**
     * 创建一个新的实例 InitiativeResponse
     */
    public QueryResponse() {
    }

    /**
     * 创建一个新的实例 InitiativeResponse
     * @param requestType
     *            请求类型
     * @param status
     *            应答状态
     * @param data
     *            应答数据
     */
    public QueryResponse(String requestType, String status, T data) {
        super();
        this.type = requestType + "Resp";
        this.status = status;
        this.data = data;
    }

    /**
     * 应答报文类型
     */
    private String type;

    /**
     * 应答状态
     */
    private String status;

    /**
     * 应答结果描述
     */
    private String msg;

    /**
     * 应答的业务数据
     */
    private T data;

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
     * 获取status字段数据
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status字段数据
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取data字段数据
     * @return Returns the data.
     */
    public T getData() {
        return data;
    }

    /**
     * 设置data字段数据
     * @param data
     *            The data to set.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取msg字段数据
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置msg字段数据
     * @param msg
     *            The msg to set.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
