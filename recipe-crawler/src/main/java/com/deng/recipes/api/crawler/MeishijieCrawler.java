package com.deng.recipes.api.crawler;
import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.extractor.MeishijieRecipeExtractor;
import com.deng.recipes.api.extractor.RecipeExtractor;
import com.google.common.base.Preconditions;

/**
 * Created by hcdeng on 2017/4/24.
 */
public final class MeishijieCrawler extends AbstractRecipeCrawler {

    private RecipeExtractor recipeExtractor = new MeishijieRecipeExtractor();

    @Override
    protected String baseUrl() {
        return "meishij.net";
    }

    @Override
    protected String recipePattern() {
        return ".*/zuofa/\\w\\.html$";
    }

    protected  RecipeEntity processPageContent(String content) {
        Preconditions.checkNotNull(content);
        return recipeExtractor.extract(content);
    }
}
