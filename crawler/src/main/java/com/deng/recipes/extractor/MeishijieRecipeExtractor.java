package com.deng.recipes.extractor;

import com.deng.recipes.entity.CookStep;
import com.deng.recipes.entity.Ingredient;
import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;
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
public class MeishijieRecipeExtractor implements RecipeExtractor{
    @Override
    public RecipeEntity extract(String content) {
        Recipe recipe = new Recipe();
        Document doc = Jsoup.parse(content);

        Elements tmp = doc.select("h1"); // 标题
        recipe.setTitle(tmp.text());

        tmp = doc.select("div.cp_headerimg_w > img");
        String srcUrl = tmp.attr("src");
        recipe.setImages(Arrays.asList(srcUrl));

        tmp = doc.select("span[class=icon icon_nd] ~ a");
        recipe.setRecruit(tmp.text());

        tmp = doc.select("li[class=w127 bb0]  a");
        recipe.setTaste(tmp.text());

        tmp = doc.select("span[class=icon icon_zb] ~ a");
        recipe.setSetupTime(tmp.text());

        tmp = doc.select("span[class=icon icon_pr] ~ a");
        recipe.setCookingTime(tmp.text());

        tmp = doc.select("div.materials > p"); //功能
        recipe.setFuncational(tmp.text());

        extractMainIgredients(recipe, doc);
        extractSubIngredients(recipe, doc);
        List<CookStep> cookSteps = extractCookSteps(doc);

        RecipeEntity recipeEntity = new RecipeEntity(recipe, cookSteps);

        return recipeEntity;
    }

    private List<CookStep> extractCookSteps(Document doc) {
        Elements tmp;//extract steps
        List<CookStep> cookSteps = new ArrayList<>();
        tmp = doc.select("div.content.clearfix");
        if (tmp != null) {
            for (int i = 0; i < tmp.size(); i++) {
                CookStep cookStep = new CookStep();
                Element e = tmp.get(i);
                cookStep.setStepOrder(i);
                cookStep.setDescription(e.select("p").text());
                cookStep.setImage(e.select("img").attr("src"));
                cookSteps.add(cookStep);
            }
        }
        return cookSteps;
    }

    private void extractSubIngredients(Recipe recipe, Document doc) {
        Elements tmp;//extract the sub ingredient
        tmp = doc.select("div[class=yl fuliao clearfix] li");
        if (tmp != null) {
            for (int i = 0; i < tmp.size(); i++) {
                Ingredient ingredient = new Ingredient();
                Element e = tmp.get(i);
                ingredient.setIngredientName(e.select("a").text());
                ingredient.setQuantityDesc(e.select("span").text());
                recipe.addIngredient(ingredient);
            }
        }
    }

    private void extractMainIgredients(Recipe recipe, Document doc) {
        Elements tmp;//extract the main ingredient
        tmp = doc.select("div[class=yl zl clearfix] li");
        if (tmp != null) {
            for (int i = 0; i < tmp.size(); i++) {
                Ingredient ingredient = new Ingredient();
                Element e = tmp.get(i);
                ingredient.setIngredientName(e.select("h4 > a").text());
                ingredient.setUrl(e.select("img").attr("src"));
                ingredient.setQuantityDesc(e.select("span").text());
                recipe.addIngredient(ingredient);
            }
        }
    }
}
