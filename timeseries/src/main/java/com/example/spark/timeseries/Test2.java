package com.example.spark.timeseries;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Test2 {
    public static void main(String[] args) {
//        LocalDateTime.parse("2015-08-03T00:00:00");
//        Date date = new Date(1533608145247l);
        ZoneId zone = ZoneId.systemDefault();
        Timestamp timestamp = new Timestamp(1533608145247l);
        ZonedDateTime zonedDateTime =ZonedDateTime.ofInstant(timestamp.toInstant(),zone);

        Timestamp timestamp2 = new Timestamp(1536618521178l);
        ZonedDateTime zonedDateTime2 =ZonedDateTime.ofInstant(timestamp2.toInstant(),zone);

        Timestamp timestamp3 = new Timestamp(1536622123526l);
        ZonedDateTime zonedDateTime3 =ZonedDateTime.ofInstant(timestamp3.toInstant(),zone);

        Timestamp timestamp4 = new Timestamp(1536625714836l);
        ZonedDateTime zonedDateTime4 =ZonedDateTime.ofInstant(timestamp4.toInstant(),zone);

        Timestamp timestamp5 = new Timestamp(1536629317321l);
        ZonedDateTime zonedDateTime5 =ZonedDateTime.ofInstant(timestamp5.toInstant(),zone);

        Timestamp timestamp6 = new Timestamp(1533626811800l);
        ZonedDateTime zonedDateTime6 =ZonedDateTime.ofInstant(timestamp5.toInstant(),zone);

        Timestamp timestamp7 = new Timestamp(1533630414771l);
        ZonedDateTime zonedDateTime7 =ZonedDateTime.ofInstant(timestamp5.toInstant(),zone);



        System.out.println(zonedDateTime.toString());
        System.out.println(zonedDateTime2.toString());
        System.out.println(zonedDateTime3.toString());
        System.out.println(zonedDateTime4.toString());
        System.out.println(zonedDateTime5.toString());
        System.out.println(zonedDateTime6.toString());
        System.out.println(zonedDateTime7.toString());
    }
}
