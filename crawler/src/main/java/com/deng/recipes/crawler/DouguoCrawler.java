package com.deng.recipes.crawler;

import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by hcdeng on 2017/4/25.
 */
public class DouguoCrawler extends AbstractRecipeCrawler{
    public DouguoCrawler() {
        BASE_URL = "douguo.com/";
        RECIPE_PATTERN = "/cookbook/";
        IMAGE_DIR = "D:\\data\\douguo\\iamges\\";
        RECIPES_DIR = "D:\\data\\douguo\\recipes\\";
        HTML_DIR = "D:\\data\\douguo\\htmls\\";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        return new RecipeEntity(new Recipe(), new ArrayList<>());
    }
}
