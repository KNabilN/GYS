package com.gys.android.gys.Model;

public class Carts {
    String name , price , pid ;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPid() {
        return pid;
    }

    public Carts(String name, String price, String pid) {
        this.name = name;
        this.price = price;
        this.pid = pid;
    }

    public Carts() {
    }
}
