package com.chnword.chnword.beans;

/**
 * Created by khtc on 15/8/18.
 */
public class ShopItem  {

    private String title;
    private String price;

    private int imageIndex;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(title);
        sb.append(", " + price);
        sb.append(", " + imageIndex);
        return sb.toString();
    }
}
