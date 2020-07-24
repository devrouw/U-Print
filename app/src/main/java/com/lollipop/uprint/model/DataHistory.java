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
    @SerializedName("jml_page")
    private String jml_page;
    @SerializedName("jml_rangkap")
    private String jml_rangkap;
    @SerializedName("ukuran_kertas")
    private String ukuran_kertas;
    @SerializedName("delivery")
    private String delivery;
    @SerializedName("total")
    private String total;
    @SerializedName("status")
    private String status;
    @SerializedName("id_user")
    private String id_user;

    public DataHistory(String id, String filename, String file_url, String date, String jml_page, String jml_rangkap, String ukuran_kertas, String delivery, String total, String status, String id_user) {
        this.id = id;
        this.filename = filename;
        this.file_url = file_url;
        this.date = date;
        this.jml_page = jml_page;
        this.jml_rangkap = jml_rangkap;
        this.ukuran_kertas = ukuran_kertas;
        this.delivery = delivery;
        this.total = total;
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

    public String getJml_page() {
        return jml_page;
    }

    public void setJml_page(String jml_page) {
        this.jml_page = jml_page;
    }

    public String getJml_rangkap() {
        return jml_rangkap;
    }

    public void setJml_rangkap(String jml_rangkap) {
        this.jml_rangkap = jml_rangkap;
    }

    public String getUkuran_kertas() {
        return ukuran_kertas;
    }

    public void setUkuran_kertas(String ukuran_kertas) {
        this.ukuran_kertas = ukuran_kertas;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
