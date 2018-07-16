/*******************************************************************************
 * @(#)OpenTsdbClient.java 2016年12月15日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient;

/**
 * openTSDB client
 * 
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月15日 上午10:29:33
 */
public class OpenTsdbClient {
    private static HttpClientImpl instance;

    private OpenTsdbClient() {
    }

    public static synchronized HttpClientImpl getInstance(String address) {
        if (instance == null) {
            instance = new HttpClientImpl(address);
        }
        return instance;
    }
}
