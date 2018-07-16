/*******************************************************************************
 * @(#)QueryDateUtils.java 2016年12月13日
 *
 * Copyright 2016 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.example.spark.opentsdb.tsdbclient;


import com.example.spark.opentsdb.tsdbclient.rest.MetricTimeRange;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间查询工具类
 * 
 * @author <a href="mailto:jinp@emrubik.com">jinp</a>
 * @version $Revision 1.0 $ 2016年12月13日 下午2:20:48
 */
public class QueryDateUtils {
    /**
     * LOG
     */
    private static Logger log = LoggerFactory.getLogger(QueryDateUtils.class);
    
    /**
     * 获取当前时间
     * 
     * @return 当前时间
     */
    public static Date getCurDate() {
        return new Date();
    }

    /**
     * 根据查询时间范围确定时间间隔值
     * @param timeRange 时间范围
     * @return 时间间隔
     */
    public static int getDateInterval(String timeRange) {
        int interval = 0;
        if (MetricTimeRange.fifteenmin.toString().equals(timeRange)) {
            // 30s
            interval = 30; 
        } else if (MetricTimeRange.thirtymin.toString().equals(timeRange)) {
            // 1m
            interval = 60;
        } else if (MetricTimeRange.onehour.toString().equals(timeRange)) {
            // 2m
            interval = 2 * 60;
        } else if (MetricTimeRange.threehour.toString().equals(timeRange)) {
            // 5m
            interval = 5 * 60;
        } else if (MetricTimeRange.sixhour.toString().equals(timeRange)) {
            // 10m
            interval = 10 * 60;
        }
        return interval;
    }
    
    /**
     * 获取总点数
     * @param timeRange 时间范围
     * @return 总点数
     */
    public static int getPointsNum(String timeRange) {
        return (MetricTimeRange.threehour.toString().equals(timeRange)
                || MetricTimeRange.sixhour.toString().equals(timeRange)) ? 36 : 30;
    }
    
    /**
     * 获取查询起始时间
     * @param timeRange 查询时间范围
     * @return 起始时间
     */
    public static Date getStartDate(String timeRange) {
        Date startDate = null;
        if (MetricTimeRange.today.toString().equals(timeRange)) {
            Calendar todayStart = Calendar.getInstance();
            todayStart.set(Calendar.HOUR, 0);
            todayStart.set(Calendar.MINUTE, 0);
            todayStart.set(Calendar.SECOND, 0);
            todayStart.set(Calendar.MILLISECOND, 0);
            startDate = todayStart.getTime();
        } else if (MetricTimeRange.fifteenmin.toString().equals(timeRange)) {
            startDate = DateUtils.addMinutes(getCurDate(), -15);
        } else if (MetricTimeRange.thirtymin.toString().equals(timeRange)) {
            startDate = DateUtils.addMinutes(getCurDate(), -30);
        } else if (MetricTimeRange.onehour.toString().equals(timeRange)) {
            startDate = DateUtils.addHours(getCurDate(), -1);
        } else if (MetricTimeRange.threehour.toString().equals(timeRange)) {
            startDate = DateUtils.addHours(getCurDate(), -3);
        } else if (MetricTimeRange.sixhour.toString().equals(timeRange)) {
            startDate = DateUtils.addHours(getCurDate(), -6);
        } else if (MetricTimeRange.oneday.toString().equals(timeRange)) {
            startDate = DateUtils.addDays(getCurDate(), -1);
        } else if (MetricTimeRange.oneweek.toString().equals(timeRange)) {
            startDate = DateUtils.addWeeks(getCurDate(), -1);
        } else if (MetricTimeRange.onemonth.toString().equals(timeRange)) {
            startDate = DateUtils.addMonths(getCurDate(), -1);
        }
        return startDate;
    }

    /**
     * 获取日期秒
     * @param date 日期
     * @return 秒
     */
    private static String getDateSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int second = cal.get(Calendar.SECOND);
        return String.format("%02d", second);
    }
    
    /**
     * 获取日期分钟
     * @param date 日期
     * @return 分钟
     */
    private static String getDateMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int minute = cal.get(Calendar.MINUTE);
        return String.format("%02d", minute);
    }
    
    /**
     * 获取日期小时
     * @param date 日期
     * @return 小时
     */
    private static String getDateHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour24 = cal.get(Calendar.HOUR_OF_DAY);
        return String.format("%02d", hour24);
    }
    
    /**
     * 获取日期天
     * @param date 日期
     * @return 天
     */
    private static String getDateDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d", day);
    }
    
    /**
     * 获取周
     * @param date 日期
     * @return 周
     */
    private static String getDateWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_MONTH);
        return String.format("%02d", week);
    }
    
    /**
     * 获取downsample
     * @param timeRange 查询时间范围
     * @param aggregator 聚合类型
     * @return 聚合标签
     */
    public static String getDownsample(String timeRange, String aggregator) {
        String downsample = "";
        if(MetricTimeRange.fifteenmin.toString().equals(timeRange)) {
            downsample = "30s" + "-" + aggregator + "-none";
        } else if(MetricTimeRange.thirtymin.toString().equals(timeRange)) {
            downsample = "1m" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.today.toString().equals(timeRange)) {
            downsample = "1h" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.onehour.toString().equals(timeRange)) {
            downsample = "2m" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.threehour.toString().equals(timeRange)) {
            downsample = "5m" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.sixhour.toString().equals(timeRange)) {
            downsample = "10m" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.oneday.toString().equals(timeRange)) {
            downsample = "2h" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.oneweek.toString().equals(timeRange)) {
            downsample = "1d" + "-" + aggregator + "-none";
        } else if (MetricTimeRange.onemonth.toString().equals(timeRange)) {
            downsample = "1w" + "-" + aggregator + "-none";
        }
        return downsample;
    }
    
    /**
     * 输出查询时间段
     * @param start 开始时间
     * @param end 结束时间
     */
    public static void printDateRange(Long start, Long end) {
        String startDate = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(start * 1000));
        String endDate = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(end * 1000));
        log.info("查询开始时间：{}，查询结束时间：{}。", startDate, endDate);
    }
    
    /**
     * 获取时间划分粒度
     * @param timeRange 查询范围
     * @param dateTime 日期
     * @return 时间划分粒度
     */
    public static String getTimeValue(String timeRange, Long dateTime) {
        String timeValue = "";
        Date date = new Date(dateTime.longValue() * 1000);
        if (MetricTimeRange.fifteenmin.toString().equals(timeRange)) {
            timeValue = getDateHour(date) + ":" + getDateMinute(date) + ":" + getDateSecond(date);
        } else if (MetricTimeRange.thirtymin.toString().equals(timeRange)) {
            timeValue = getDateHour(date) + ":" + getDateMinute(date);
        } else if (MetricTimeRange.today.toString().equals(timeRange)) {
            timeValue = getDateHour(date);
        } else if (MetricTimeRange.onehour.toString().equals(timeRange)) {
            timeValue = getDateHour(date) + ":" + getDateMinute(date);
        } else if (MetricTimeRange.threehour.toString().equals(timeRange)) {
            timeValue = getDateHour(date) + ":" + getDateMinute(date);
        } else if (MetricTimeRange.sixhour.toString().equals(timeRange)) {
            timeValue = getDateHour(date) + ":" + getDateMinute(date);
        } else if (MetricTimeRange.oneday.toString().equals(timeRange)) {
            timeValue = getDateHour(date);
        } else if (MetricTimeRange.oneweek.toString().equals(timeRange)) {
            timeValue = getDateDay(date);
        } else if (MetricTimeRange.onemonth.toString().equals(timeRange)) {
            timeValue = getDateWeek(date);
        }
        return timeValue;
    }
}
