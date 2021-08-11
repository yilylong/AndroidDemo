package com.zhl.androiddemo.bean;

/**
 * 描述：
 * Created by zhaohl on 2021/3/8.
 */

public class Humam {
    public String name;
    public String skin;
    public String gender;
    public int height;
    public int weight;

    public Humam(String name){
        this.name = name;
    }
    public Humam(String name,String gender){
        this.name = name;
        this.gender = gender;
    }
    public Humam(String name,int height,int weight){
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
