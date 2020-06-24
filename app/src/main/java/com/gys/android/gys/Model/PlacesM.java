package com.gys.android.gys.Model;

public class PlacesM {
    String image , date , description , pid , pname , price , time;

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public PlacesM(String image, String date, String description, String pid, String pname, String price, String time) {
        this.image = image;
        this.date = date;
        this.description = description;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.time = time;
    }

    public PlacesM() {
    }
}
