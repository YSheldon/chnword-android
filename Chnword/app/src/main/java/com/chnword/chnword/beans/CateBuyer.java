package com.chnword.chnword.beans;

import java.text.DecimalFormat;

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

    public String getPriceText() {

        DecimalFormat df1 = new DecimalFormat("###,###.0");
        return df1.format(price);
    }

    public void add(CateBuyItem item) {

        this.price += item.getPrice();
    }

    public void sub(CateBuyItem item) {

        this.price -= item.getPrice();
    }

    public void reset() {
        price = 0.0f;
    }
}
