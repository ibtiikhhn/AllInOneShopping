package com.muibsols.allinoneshopping.Models;

public class Store {
    String storeName;
    String storeURL;
    String storeCategory;
    String storeIMG;

    public Store() {

    }

    public Store(String storeName, String storeURL, String storeCategory, String storeIMG) {
        this.storeName = storeName;
        this.storeURL = storeURL;
        this.storeCategory = storeCategory;
        this.storeIMG = storeIMG;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreURL() {
        return storeURL;
    }

    public void setStoreURL(String storeURL) {
        this.storeURL = storeURL;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public String getStoreIMG() {
        return storeIMG;
    }

    public void setStoreIMG(String storeIMG) {
        this.storeIMG = storeIMG;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeName='" + storeName + '\'' +
                ", storeURL='" + storeURL + '\'' +
                ", storeCategory='" + storeCategory + '\'' +
                ", storeIMG='" + storeIMG + '\'' +
                '}';
    }
}
