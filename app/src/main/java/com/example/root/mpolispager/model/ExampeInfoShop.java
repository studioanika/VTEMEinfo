package com.example.root.mpolispager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 2.1.18.
 */
public class ExampeInfoShop {

    @SerializedName("info")
    @Expose
    private List<Info> info = null;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

}
