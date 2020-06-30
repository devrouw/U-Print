package com.lollipop.uprint.model;

import com.google.gson.annotations.SerializedName;

public class DataHistory {

    @SerializedName("id")
    private String id;
    @SerializedName("filename")
    private String filename;
    @SerializedName("file_url")
    private String file_url;
    @SerializedName("date")
    private String date;
    @SerializedName("status")
    private String status;
    @SerializedName("id_user")
    private String id_user;

    public DataHistory(String id, String filename, String file_url, String date, String status, String id_user) {
        this.id = id;
        this.filename = filename;
        this.file_url = file_url;
        this.date = date;
        this.status = status;
        this.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
