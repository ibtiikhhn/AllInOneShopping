package com.muibsols.allinoneshopping.Models;

public class CategoryModel {
    String category;

    public CategoryModel(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "category='" + category + '\'' +
                '}';
    }
}
