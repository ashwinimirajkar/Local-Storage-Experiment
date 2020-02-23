package com.example.assignment.Models.ResponseModel;

import com.google.gson.annotations.SerializedName;

public class RankingProductItem {

    @SerializedName("view_count")
    public int view_count;

    @SerializedName("order_count")
    public int order_count;

    @SerializedName("shares")
    public int shares;

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
