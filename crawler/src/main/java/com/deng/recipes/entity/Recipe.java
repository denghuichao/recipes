package com.deng.recipes.entity;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class Recipe {
    private Integer recipeid;

    private String name = "";

    private String images = "";

    private String recruit = "";

    private String taste = "";

    private String setuptime = "";

    private String cookingtime = "";

    private String mainingredient = "";

    private String ingredient = "";

    private String funcational = "";

    private String comment = "";

    public Integer getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(Integer recipeid) {
        this.recipeid = recipeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getRecruit() {
        return recruit;
    }

    public void setRecruit(String recruit) {
        this.recruit = recruit == null ? null : recruit.trim();
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste == null ? null : taste.trim();
    }

    public String getSetuptime() {
        return setuptime;
    }

    public void setSetuptime(String setuptime) {
        this.setuptime = setuptime == null ? null : setuptime.trim();
    }

    public String getCookingtime() {
        return cookingtime;
    }

    public void setCookingtime(String cookingtime) {
        this.cookingtime = cookingtime == null ? null : cookingtime.trim();
    }

    public String getMainingredient() {
        return mainingredient;
    }

    public void setMainingredient(String mainingredient) {
        this.mainingredient = mainingredient == null ? null : mainingredient.trim();
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient == null ? null : ingredient.trim();
    }

    public String getFuncational() {
        return funcational;
    }

    public void setFuncational(String funcational) {
        this.funcational = funcational == null ? null : funcational.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}
