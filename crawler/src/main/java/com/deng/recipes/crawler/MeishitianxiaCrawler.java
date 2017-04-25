package com.deng.recipes.crawler;

import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by hcdeng on 2017/4/25.
 */
public class MeishitianxiaCrawler extends AbstractRecipeCrawler{

    public MeishitianxiaCrawler() {
        BASE_URL = "meishichina.com";
        RECIPE_PATTERN = "/recipe-";
        IMAGE_DIR = "D:\\data\\meishitianxia\\iamges\\";
        RECIPES_DIR = "D:\\data\\meishitianxia\\recipes\\";
        HTML_DIR = "D:\\data\\meishitianxia\\htmls\\";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        return new RecipeEntity(new Recipe(), new ArrayList<>());
    }
}
