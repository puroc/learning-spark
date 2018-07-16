/*******************************************************************************
 * @(#)HostMonitor.java 2016年12月12日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient.rest;

/**
 * 主机监控类型
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月12日 下午3:14:04
 */
public enum NMSMonitor {
    /**
     * 主机监控
     */
    hostcpu, hostdiskvolume, hostdiskio, hostmem, hostnet,
    /**
     * 容器监控
     */
    containercpu, containermem, containernet,
    /**
     * mongo监控
     */
    mongocurd, mongocommand, mongoconn, mongostore
}
