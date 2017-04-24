package com.deng.recipes.persit;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.utils.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeSaver implements Runnable{

    private final BlockingQueue<RecipeEntity> recipeProvider;

    public RecipeSaver(BlockingQueue<RecipeEntity> recipeProvider){
        this.recipeProvider = recipeProvider;
    }

    @Override
    public void run() {
        while(true){
           RecipeEntity recipeEntity = null;
           try {
               recipeEntity = recipeProvider.take();
               //ESUtils.indexObject(recipeEntity, Constants.ES_INDEX_NAME);
               FileWriter writer = null;
               try {
                   writer = new FileWriter(Constants.RECIPES_DIR+"/"+recipeEntity.getRecipe().getName());
                   writer.write(JSON.toJSONString(recipeEntity));
               }finally {
                   if(writer != null)writer.close();
               }
           }catch (InterruptedException  | IOException e){
          }
        }
    }
}
