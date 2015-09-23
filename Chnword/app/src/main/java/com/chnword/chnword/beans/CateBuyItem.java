package com.chnword.chnword.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khtc on 15/9/19.
 */
public class CateBuyItem implements Parcelable {

    private String name;
    private float price;
    private String priceString;
    private boolean isChecked;
    private int resourceId;
    private String iconUrl;

    public CateBuyItem() {
    }

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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void calcute(CateBuyer buyer) {
        if (isChecked) {
            buyer.add(this);
        } else {
            buyer.sub(this);
        }
    }

    public static final Parcelable.Creator<CateBuyItem> CREATOR = new Creator<CateBuyItem>(){

        public CateBuyItem createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            CateBuyItem cus = new CateBuyItem();
            cus.name = source.readString();
            cus.price = source.readFloat();
            cus.priceString = source.readString();
            cus.iconUrl = source.readString();
            cus.resourceId = source.readInt();
            return cus;
        }

        public CateBuyItem[] newArray(int size) {
            // TODO Auto-generated method stub
            return new CateBuyItem[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(priceString);
        dest.writeString(iconUrl);
        dest.writeInt(resourceId);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name : " + name);
        sb.append(" price : " + price);
        sb.append(" priceString : " + priceString);
        sb.append(" iconUrl : " + iconUrl);
        sb.append(" resourceId : " + resourceId);

        return sb.toString();
    }
}
