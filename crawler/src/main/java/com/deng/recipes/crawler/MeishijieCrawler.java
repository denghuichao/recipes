package com.deng.recipes.crawler;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.extractor.MeishijieRecipeExtractor;
import com.deng.recipes.extractor.RecipeExtractor;
import com.google.common.base.Preconditions;

/**
 * Created by hcdeng on 2017/4/24.
 */
public final class MeishijieCrawler extends AbstractRecipeCrawler {

    private RecipeExtractor recipeExtractor = new MeishijieRecipeExtractor();

    public MeishijieCrawler() {
        BASE_URL = "meishij.net";
        RECIPE_PATTERN = ".*/zuofa/\\w\\.html$";
        IMAGE_DIR = "D:\\data\\meishijie\\iamges\\";
        RECIPES_DIR = "D:\\data\\meishijie\\recipes\\";
        HTML_DIR = "D:\\data\\meishijie\\htmls\\";
    }

    protected  RecipeEntity processPageContent(String content) {
        Preconditions.checkNotNull(content);
        return recipeExtractor.extract(content);
    }
}
