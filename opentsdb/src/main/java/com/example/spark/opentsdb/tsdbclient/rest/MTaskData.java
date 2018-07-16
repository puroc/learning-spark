/*******************************************************************************
 * @(#)MetricMetaData.java 2016年11月28日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

import java.util.List;

import com.emrubik.iot.nms.domain.mtask.CollectTask;
import com.emrubik.iot.nms.domain.mtask.ManagerTask;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 指标查询应答数据体
 * @author <a href="mailto:fengjian@emrubik.com">feng jian</a>
 * @version $Revision 1.0 $ 2016年11月28日 下午5:10:41
 */
@JsonInclude(Include.NON_NULL)
public class MTaskData extends QueryRespBody {

    /**
     * 数据时间戳
     */
    private List<ManagerTask<CollectTask>> mtaskList;

    /**
     * 获取mtaskList字段数据
     * @return Returns the mtaskList.
     */
    public List<ManagerTask<CollectTask>> getMtaskList() {
        return mtaskList;
    }

    /**
     * 设置mtaskList字段数据
     * @param mtaskList
     *            The mtaskList to set.
     */
    public void setMtaskList(List<ManagerTask<CollectTask>> mtaskList) {
        this.mtaskList = mtaskList;
    }

}
