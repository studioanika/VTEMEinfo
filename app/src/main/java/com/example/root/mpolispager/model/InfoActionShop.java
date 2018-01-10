package com.example.root.mpolispager.model;

/**
 * Created by root on 3.1.18.
 */

public class InfoActionShop {

    String name;
    String price;
    String skidka;
    String term;
    String img;

    public InfoActionShop(String name, String price, String skidka, String term, String img) {
        this.name = name;
        this.price = price;
        this.skidka = skidka;
        this.term = term;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getSkidka() {
        return skidka;
    }

    public String getTerm() {
        return term;
    }

    public String getImg() {
        return img;
    }

}