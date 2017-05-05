package com.deng.recipes.api.entity;

import java.io.Serializable;

/**
 * Created by hcdeng on 2017/4/24.
 */
public class Ingredient implements Serializable {
    private  String ingredientName;//配料名称
    private  String quantityDesc;  //用量描述
    private String url;

    public Ingredient(String ingredientName, String quantityDesc, String url) {
        this.ingredientName = ingredientName;
        this.quantityDesc = quantityDesc;
        this.url = url;
    }

    public Ingredient(String ingredientName, String quantityDesc) {
        this.ingredientName = ingredientName;
        this.quantityDesc = quantityDesc;
    }

    public Ingredient() {
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantityDesc() {
        return quantityDesc;
    }

    public void setQuantityDesc(String quantityDesc) {
        this.quantityDesc = quantityDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
