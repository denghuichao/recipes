package com.deng.recipes.api.entity;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.api.utils.FileUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeEntity implements Serializable{

    private  String ID;

    private  Recipe recipe;

    private  List<CookStep> cookSteps;

    public RecipeEntity() {
    }

    public RecipeEntity(String ID, Recipe recipe, List<CookStep> cookSteps) {
        this.ID = ID;
        this.recipe = recipe;
        this.cookSteps = cookSteps;
    }

    public RecipeEntity(Recipe recipe, List<CookStep> cookSteps) {
        this.ID = FileUtils.getMD5(JSON.toJSONString(recipe)
                + JSON.toJSONString(cookSteps));

        this.recipe = recipe;
        this.cookSteps = cookSteps;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<CookStep> getCookSteps() {
        return cookSteps;
    }

    public void setCookSteps(List<CookStep> cookSteps) {
        this.cookSteps = cookSteps;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
