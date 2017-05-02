package com.deng.recipes.api.crawler;

import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.extractor.DouguoRecipeExtractor;
import com.deng.recipes.api.extractor.RecipeExtractor;
import com.google.common.base.Preconditions;

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
