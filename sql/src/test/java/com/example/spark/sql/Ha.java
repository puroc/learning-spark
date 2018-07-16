package com.example.spark.sql;


import static org.apache.avro.TypeEnum.a;

/**
 * Created by puroc on 16/12/15.
 */
public class Ha {

    private static final String regex = " (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9]";
//    private static final String regex = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";

    public static void check(String s) {
        if (s.matches(regex)) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    public static void main(String[] args) {
        String s = "{\"deviceId\":\"001\",\"type\":\"2\",\"createTime\":\"2016-09-10 10:00:00\",\"data\":{\"temperature\":\"3\",\"availableSpace\":\"2\",\"availableRate\":\"1\"}}";
//        String a = "2016-09-10 10:00:00";
//        String b = " 10:00:00";
//        System.out.println(b.matches(regex));
        System.out.println(s.replaceAll(regex, ""));
    }
}
