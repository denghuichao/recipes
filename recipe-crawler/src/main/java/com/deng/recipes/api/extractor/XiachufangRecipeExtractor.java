package com.deng.recipes.api.extractor;

import com.deng.recipes.api.entity.CookStep;
import com.deng.recipes.api.entity.Ingredient;
import com.deng.recipes.api.entity.Recipe;
import com.deng.recipes.api.entity.RecipeEntity;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/27.
 */
public class XiachufangRecipeExtractor extends RecipeExtractor {

    private static final String BASE_URL = "http://www.xiachufang.com/";

    @Override
    public RecipeEntity extract(String content) {
        Preconditions.checkNotNull(content);
        Recipe recipe = new Recipe();
        Document doc = Jsoup.parse(content);

        String title = doc.select("h1.page-title").text();
        if (Strings.isNullOrEmpty(title)) return null;
        recipe.setTitle(title);
        System.out.println(title);

        // <div class="cover image expandable block-negative-margin"
        String img = doc.select("div.cover.image.expandable.block-negative-margin img").attr("src");
        recipe.setImages(Arrays.asList(img));
        System.out.println(img);

        String ratingValue = doc.select("div.score.float-left span.number").text();
        System.out.println(ratingValue);
        if (!Strings.isNullOrEmpty(ratingValue)) recipe.setScore(Float.parseFloat(ratingValue));

        String cookedNunm = doc.select("div.cooked.float-left span.number").text();
        System.out.println(cookedNunm);
        if (!Strings.isNullOrEmpty(cookedNunm))
            recipe.setHot(Integer.parseInt(cookedNunm));

        String desc = doc.select("div.desc.mt30").text();
        System.out.println(desc);
        recipe.setDesc(desc);

        Elements trs = doc.select("div.ings tr");
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < trs.size(); i++) {
            Element tr = trs.get(i);
            String key = tr.select("td.name a").text();
            String url = BASE_URL + tr.select("td.name a").attr("href");
            String value = tr.select("td.unit").text();
            System.out.println(key + ":" + value + "-->" + url);
            ingredients.add(new Ingredient(key, value, url));
        }

        List<CookStep> cookSteps = new ArrayList<>();
        Elements tmp = doc.select("div.steps");
        tmp = tmp.select("li.container");
        for (int i = 0; i < tmp.size(); i++) {
            Element e = tmp.get(i);
            String sDesc = e.select("p.text").text();
            String sImg = e.select("img").attr("src");
            System.out.println(sDesc + ":" + sImg);
            cookSteps.add(new CookStep(i + 1, sDesc, sImg));
        }

        String tips = doc.select("div.tip").text();
        System.out.println(tips);
        recipe.setTips(tips);

        return new RecipeEntity(recipe, cookSteps);
    }

    public static void main(String[] args) throws IOException {
        RecipeExtractor recipeExtractor = new XiachufangRecipeExtractor();
        recipeExtractor.processAllRecipes(new File("D:\\data\\xiachufang\\htmls"));
    }
}
