package aron.demo.reflect;

import aron.Utils.ELogger;

/**
 * des:
 * Created by liuwenrong on 2019/4/2
 */
public class TestReflect {
    private int age;
    String name;

    public TestReflect() {
    }

    public TestReflect(int age) {
        this.age = age;
    }

    public TestReflect(int age, String name) {
        this.age = age;
        this.name = name;
    }
    private String intToString(int i) {
        ELogger.d("intToString----------liuwenrong--2019/4/2--: " + i + " , age = " + age + ", name = " + name);
        return i + ", age = " + age + ", name = " + name;
    }
}
