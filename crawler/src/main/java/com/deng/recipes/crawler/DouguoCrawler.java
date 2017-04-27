package com.deng.recipes.crawler;

import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.extractor.DouguoRecipeExtractor;
import com.deng.recipes.extractor.RecipeExtractor;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by hcdeng on 2017/4/25.
 */
public class DouguoCrawler extends AbstractRecipeCrawler{

    private final RecipeExtractor extractor = new DouguoRecipeExtractor();

    @Override
    protected String baseUrl() {
        return "douguo.com/";
    }

    @Override
    protected String recipePattern() {
        return ".*/cookbook/\\d+\\.html$";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        Preconditions.checkNotNull(html);
        return extractor.extract(html);
    }
}
