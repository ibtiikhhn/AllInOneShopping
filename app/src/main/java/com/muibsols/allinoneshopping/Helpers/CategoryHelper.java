package com.muibsols.allinoneshopping.Helpers;

import com.muibsols.allinoneshopping.Models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryHelper {
    List<CategoryModel> categoryModels;

    public CategoryHelper() {
        categoryModels = new ArrayList<>();
    }

    public List<CategoryModel> getCategoryModels() {
        categoryModels.add(new CategoryModel("Top Stores"));
        categoryModels.add(new CategoryModel("Fashion"));
        categoryModels.add(new CategoryModel("Grocery"));
        categoryModels.add(new CategoryModel("Recharge"));
        categoryModels.add(new CategoryModel("Baby & Kids"));
        categoryModels.add(new CategoryModel("Food"));
        categoryModels.add(new CategoryModel("Movie"));
        categoryModels.add(new CategoryModel("Medicine"));
        categoryModels.add(new CategoryModel("Furniture"));
        categoryModels.add(new CategoryModel("Gifts"));
        categoryModels.add(new CategoryModel("Home Services"));
        return categoryModels;
    }
}
