package com.example.root.mpolispager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 8.1.18.
 */

public class AfishaInfo extends News {

    @SerializedName("desc")
    @Expose
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
