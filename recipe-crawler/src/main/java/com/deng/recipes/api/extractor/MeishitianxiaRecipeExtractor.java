package com.deng.recipes.api.extractor;

import com.deng.recipes.api.entity.CookStep;
import com.deng.recipes.api.entity.Ingredient;
import com.deng.recipes.api.entity.Recipe;
import com.deng.recipes.api.entity.RecipeEntity;
import com.google.common.base.Preconditions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by hcdeng on 2017/4/26.
 */
public class MeishitianxiaRecipeExtractor extends RecipeExtractor {
    @Override
    public RecipeEntity extract(String content) {
        Preconditions.checkNotNull(content);
        Recipe recipe = new Recipe();
        Document doc = Jsoup.parse(content);

        String title = doc.select("h1.recipe_De_title").text();
        recipe.setTitle(title);
        //System.out.println(title);

        Element de = doc.getElementById("block_txt1");
        if (de != null) {
            String desc = de.text();//.select("div.");
            recipe.setDesc(desc);
            //System.out.println(desc);
        }

        String mainUrl = doc.select("a.J_photo > img").attr("src");
        recipe.setImages(Arrays.asList(mainUrl));
        //System.out.println(mainUrl);

        //<div id="path" class="clear">
        extractTags(recipe, doc);

        extractIngredients(recipe, doc);
        //<div class="recipeCategory_sub_R mt30 clear">
        extractOtherInfos(recipe, doc);
        //<div class="recipeStep">
        List<CookStep> steps = extractCookSteps(doc);

        String tips = doc.select("div.recipeTip").text();
        recipe.setTips(tips);
        //System.out.println(tips);
        return steps.size() == 0 ? null : new RecipeEntity(recipe, steps);
    }

    private void extractTags(Recipe recipe, Document doc) {
        Element tmp = doc.getElementById("path");
        if(tmp != null) {
            Elements as = tmp.select("a");
            for (int i = 2; i < as.size(); i++) {
                String tag = as.get(i).text();
                recipe.addTag(tag);
            }
        }
    }

    private List<CookStep> extractCookSteps(Document doc) {
        Elements tmp = doc.select("div.recipeStep li");
        List<CookStep> steps = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            Element e = tmp.get(i);
            String url = e.select("div.recipeStep_img img").attr("src");
            String stepDesc = e.select("div.recipeStep_word").text().replaceFirst("^\\d+", "");
            String step = e.select("div.recipeStep_num").text();
            //System.out.println(step + ":" + stepDesc + "->" + url);
            int order = i;
            if (step.matches("\\d+")) order = Integer.parseInt(step);
            steps.add(new CookStep(order, stepDesc, url));
        }
        return steps;
    }

    private void extractOtherInfos(Recipe recipe, Document doc) {
        Elements tmp = doc.select("div.recipeCategory_sub_R.mt30.clear").select("li");
        for (int i = 0; i < tmp.size(); i++) {
            Element e = tmp.get(i);
            String value = e.select("span.category_s1").text();
            String key = e.select("span.category_s2").text();

            //System.out.println(key + ":" + value);

            if ("口味".equals(key)) recipe.setTaste(value);
            else if ("工艺".equals(key)) recipe.setCookMethod(value);
            else if ("耗时".equals(key)) recipe.setCookingTime(value);
            else if ("难度".equals(key)) recipe.setRecruit(value);
        }
    }

    private void extractIngredients(Recipe recipe, Document doc) {
        Elements tmp = doc.select("div[class=recipeCategory_sub_R clear] li");
        for (int i = 0; i < tmp.size(); i++) {
            Element e = tmp.get(i);
            String iName = e.select("span.category_s1").text();
            String url = e.select("a").attr("href");
            String iQuality = e.select("span.category_s2").text();
            //System.out.println(iName + ":" + iQuality + "->" + url);
            recipe.addIngredient(new Ingredient(iName, iQuality, url));
        }
    }
}
