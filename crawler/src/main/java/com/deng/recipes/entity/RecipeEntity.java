package com.deng.recipes.entity;

import java.util.List;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeEntity {
    private  Recipe recipe;
    private  List<CookStep> cookSteps;

    public RecipeEntity(Recipe recipe, List<CookStep> cookSteps) {
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
}
