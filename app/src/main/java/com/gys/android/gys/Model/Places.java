package com.gys.android.gys.Model;

public class Places {
    String pid;
    String date;
    String time;

    public String getPid() {
        return pid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPname() {
        return pname;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Places(String pid, String date, String time, String pname, String description, String price, String image) {
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Places() {
    }

    String pname;
    String description;
    String price;
    String image;
}
