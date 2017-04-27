package com.deng.recipes.boot;

import com.deng.recipes.extractor.DouguoRecipeExtractor;
import com.deng.recipes.extractor.MeishijieRecipeExtractor;
import com.deng.recipes.extractor.MeishitianxiaRecipeExtractor;
import com.deng.recipes.extractor.RecipeExtractor;

import java.io.File;
import java.io.IOException;

/**
 * Created by hcdeng on 2017/4/27.
 */
public class Extractor {
    public static void main(String[] args) throws IOException {
        RecipeExtractor meishitianxiaRecipeExtractor = new MeishitianxiaRecipeExtractor();
        meishitianxiaRecipeExtractor.processAllRecipes(new File("D:\\data\\meishitianxia\\htmls-0425"));

        RecipeExtractor douguoRecipeExtractor = new DouguoRecipeExtractor();
        douguoRecipeExtractor.processAllRecipes(new File("D:\\data\\douguo"));

        RecipeExtractor meishijieRecipeExtractor = new MeishijieRecipeExtractor();
        meishijieRecipeExtractor.processAllRecipes(new File("D:\\data\\meishijie"));
    }
}
