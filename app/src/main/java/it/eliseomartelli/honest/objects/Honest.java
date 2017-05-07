package it.eliseomartelli.honest.objects;

/**
 * Created by eliseomartelli on 06/05/2017.
 */

public class Honest {

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    private int age;
    private int sex;
    private String text;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

