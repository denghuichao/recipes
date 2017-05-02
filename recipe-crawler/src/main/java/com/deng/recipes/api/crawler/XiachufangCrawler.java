package com.deng.recipes.api.crawler;

import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.extractor.RecipeExtractor;
import com.deng.recipes.api.extractor.XiachufangRecipeExtractor;
import com.google.common.base.Preconditions;

/**
 * Created by hcdeng on 2017/4/25.
 */
public final class XiachufangCrawler extends AbstractRecipeCrawler{

    private final RecipeExtractor extractor = new XiachufangRecipeExtractor();

    @Override
    protected String baseUrl() {
        return "xiachufang.com";
    }

    @Override
    protected String recipePattern() {
        return ".*/recipe/\\d+/$";
    }

    @Override
    protected RecipeEntity processPageContent(String html) {
        Preconditions.checkNotNull(html);
        return extractor.extract(html);
    }
}
