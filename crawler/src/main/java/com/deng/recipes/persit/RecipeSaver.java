package com.deng.recipes.persit;

import com.deng.recipes.entity.RecipeEntity;

import java.util.concurrent.BlockingQueue;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeSaver {

    private final BlockingQueue<RecipeEntity> recipeProvider;

    public RecipeSaver(BlockingQueue<RecipeEntity> recipeProvider){
        this.recipeProvider = recipeProvider;
    }


}
