package com.chnword.chnword.beans;

/**
 * Created by khtc on 15/9/19.
 */
public class CateBuyItem {

    private String name;
    private float price;
    private String priceString;
    private boolean isChecked;
    private int resourceId;

    public CateBuyItem(String name, float price, String priceString, boolean isChecked, int resourceId) {
        this.name = name;
        this.price = price;
        this.priceString = priceString;
        this.isChecked = isChecked;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void calcute(CateBuyer buyer) {
        if (isChecked) {
            buyer.add(this);
        } else {
            buyer.sub(this);
        }
    }
}
