package com.deng.recipes.entity;

import java.util.List;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeEntity {
    private final int recipeId;
    private final Recipe recipe;
    private final List<CookStep> cookSteps;

    public RecipeEntity(Recipe recipe, List<CookStep> cookSteps) {
        this.recipe = recipe;
        this.cookSteps = cookSteps;
        this.recipeId = recipe.getRecipeid();
    }
}
