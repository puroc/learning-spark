/*******************************************************************************
 * @(#)ObjectMapperUtils.java 2015年7月22日
 *
 * Copyright 2015 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper实例获取
 * @author <a href="mailto:jinp@emrubik.com">金鹏</a>
 * @version $Revision 1.0 $ 2015年7月22日 下午3:28:49
 */
public class ObjectMapperUtils {
    /**
     * instance初始化，延迟加载
     */
    private static ObjectMapper instance = null;
    
    /**
     * 私有构造方法
     */
    private ObjectMapperUtils() {
        
    }
    
    /**
     * 实例化
     */
    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new ObjectMapper();
        }
    }

    /**
     * 实例获取
     * @return ObjectMapper实例
     */
    public static ObjectMapper getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

}
