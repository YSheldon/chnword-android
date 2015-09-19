package com.chnword.chnword.beans;

/**
 * Created by khtc on 15/9/19.
 */
public class CateBuyer {

    private float price;

    public CateBuyer(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void add(CateBuyItem item) {

        this.price += item.getPrice();
    }

    public void sub(CateBuyItem item) {

        this.price -= item.getPrice();
    }

}
