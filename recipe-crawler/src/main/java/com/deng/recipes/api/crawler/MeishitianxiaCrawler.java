package com.deng.recipes.api.crawler;

import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.extractor.MeishitianxiaRecipeExtractor;
import com.deng.recipes.api.extractor.RecipeExtractor;
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
