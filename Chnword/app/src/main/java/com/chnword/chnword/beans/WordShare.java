package com.chnword.chnword.beans;

/**
 * Created by khtc on 15/9/26.
 */
public class WordShare {
    private String gifUrl;
    private String videoUrl;
    private String iconUrl;
    private String word;
    private String sort;

    private String shareTitle;
    private String shareDesc;
    private String shareUrl;
    private String shareIcon;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" gifurl: " + gifUrl);
        sb.append(" videoUrl: " + videoUrl);
        sb.append(" iconUrl: " + iconUrl);
        sb.append(" word: " + word);
        sb.append(" sort: " + sort);
        sb.append(" shareTitle: " + shareTitle);
        sb.append(" shareDesc: " + shareDesc);
        sb.append(" shareUrl: " + shareUrl);
        sb.append(" shareIcon: " + shareIcon);
        return sb.toString();
    }
}
