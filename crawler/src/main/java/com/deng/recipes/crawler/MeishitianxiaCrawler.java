package com.deng.recipes.crawler;

import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.extractor.MeishitianxiaRecipeExtractor;
import com.deng.recipes.extractor.RecipeExtractor;
import com.google.common.base.Preconditions;


/**
 * Created by hcdeng on 2017/4/25.
 */
public class MeishitianxiaCrawler extends AbstractRecipeCrawler{

    private RecipeExtractor recipeExtractor = new MeishitianxiaRecipeExtractor();

    public MeishitianxiaCrawler() {
        BASE_URL = "meishichina.com";
        RECIPE_PATTERN = ".*/recipe-\\d+\\.html$";
        IMAGE_DIR = "D:\\data\\meishitianxia\\iamges\\";
        RECIPES_DIR = "D:\\data\\meishitianxia\\recipes\\";
        HTML_DIR = "D:\\data\\meishitianxia\\htmls\\";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        Preconditions.checkNotNull(html);
        return recipeExtractor.extract(html);
    }
}
