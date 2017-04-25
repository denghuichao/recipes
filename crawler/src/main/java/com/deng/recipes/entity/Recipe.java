package com.deng.recipes.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class Recipe {

    private String name = "";

    private String desc = "";

    private String images = "";

    private String recruit = "";//难度等级

    private String cookMethod = "";

    private String taste = "";

    private String setupTime = "";

    private String cookingTime = "";

    private List<Ingredient> mainIngredients = new ArrayList<>();

    private List<Ingredient> subIngredients = new ArrayList<>();

    private String funcational = "";

    private String tips = "";

    private String score = "";// 评分

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRecruit() {
        return recruit;
    }

    public void setRecruit(String recruit) {
        this.recruit = recruit;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public List<Ingredient> getMainIngredients() {
        return mainIngredients;
    }

    public void addMainIngredient(Ingredient mainIngredient) {
        this.mainIngredients.add(mainIngredient);
    }

    public List<Ingredient> getSubIngredients() {
        return subIngredients;
    }

    public void addSubIngredient(Ingredient subIngredients) {
        this.subIngredients.add(subIngredients);
    }

    public String getFuncational() {
        return funcational;
    }

    public void setFuncational(String funcational) {
        this.funcational = funcational;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCookMethod() {
        return cookMethod;
    }

    public void setCookMethod(String cookMethod) {
        this.cookMethod = cookMethod;
    }

    public void setMainIngredients(List<Ingredient> mainIngredients) {
        this.mainIngredients = mainIngredients;
    }

    public void setSubIngredients(List<Ingredient> subIngredients) {
        this.subIngredients = subIngredients;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
