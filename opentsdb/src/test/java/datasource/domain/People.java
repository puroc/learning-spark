package datasource.domain;


import datasource.utils.JSONUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class People {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public static void main(String[] args) {
        try {
             People p1 = new People();
            p1.setAge(35);
            p1.setName("pud");
            People p2 = new People();
            p2.setAge(20);
            p2.setName("zhangsan");
            People p3 = new People();
            p3.setAge(50);
            p3.setName("lisi");

            List<People> list = new ArrayList<People>();
            list.add(p1);
            list.add(p2);
            list.add(p3);
            final String toJson = JSONUtils.toJson(list);
            System.out.println(toJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}