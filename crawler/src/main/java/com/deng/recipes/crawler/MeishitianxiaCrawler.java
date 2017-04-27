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

    @Override
    protected String baseUrl() {
        return "meishichina.com";
    }

    @Override
    protected String recipePattern() {
        return ".*/recipe-\\d+\\.html$";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        Preconditions.checkNotNull(html);
        return recipeExtractor.extract(html);
    }
}
