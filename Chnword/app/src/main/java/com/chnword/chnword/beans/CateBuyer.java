package com.chnword.chnword.beans;

import java.text.DecimalFormat;

/**
 * Created by khtc on 15/9/19.
 */
public class CateBuyer {

    private float price;
    private float couponPrice;

    public CateBuyer(float price) {
        this.price = price;
        couponPrice = 0.0f;
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

    public void sub(float price) {
        this.price -= price;
    }

    public void reset() {
        price = 0.0f;
        couponPrice = 0.0f;
    }

    public void setCouponPrice(float couponPrice) {
        this.couponPrice = couponPrice;
    }

    public float getRealPrice() {
        if (price > couponPrice) {
            return  price - couponPrice;
        } else {
            return  couponPrice - price ;
        }
    }

    public String getRealPriceText() {
        DecimalFormat df1 = new DecimalFormat("###,###.0");
        return df1.format(getRealPrice());
    }
}
