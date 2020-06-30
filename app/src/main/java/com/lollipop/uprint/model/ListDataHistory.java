package com.lollipop.uprint.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListDataHistory {
    @SerializedName("data")
    private ArrayList<DataHistory> dataList;
    @SerializedName("status")
    private String status;

    public ArrayList<DataHistory> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DataHistory> dataList) {
        this.dataList = dataList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
