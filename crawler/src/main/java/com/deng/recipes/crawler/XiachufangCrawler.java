package com.deng.recipes.crawler;

import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by hcdeng on 2017/4/25.
 */
public final class XiachufangCrawler extends AbstractRecipeCrawler{

    public XiachufangCrawler() {
        BASE_URL = "xiachufang.com";
        RECIPE_PATTERN = "/recipe/";
        IMAGE_DIR = "D:\\data\\xiachufang\\iamges\\";
        RECIPES_DIR = "D:\\data\\xiachufang\\recipes\\";
        HTML_DIR = "D:\\data\\xiachufang\\htmls\\";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        return new RecipeEntity(new Recipe(), new ArrayList<>());
    }
}
