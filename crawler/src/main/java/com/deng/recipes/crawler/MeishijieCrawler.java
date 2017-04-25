package com.deng.recipes.crawler;

import com.deng.recipes.entity.CookStep;
import com.deng.recipes.entity.Ingredient;
import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.persit.RecipeSaver;
import com.google.common.base.Preconditions;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.elasticsearch.common.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by hcdeng on 2017/4/24.
 */
public class MeishijieCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");

    private static final String BASE_URL = "meishij.net";

    private static final String RECIPE_PATTERN = "/zuofa/";

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.contains(BASE_URL);
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {

        String url = page.getWebURL().getURL();

        if (!url.contains(RECIPE_PATTERN))
            return ;

        System.out.println("visiting: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            RecipeEntity recipeEntity = processPageContent(html);
            if (recipeEntity != null) {
                RecipeSaver.saveRecipe(recipeEntity);
                RecipeSaver.saveHtml(url, html);
            }
            else{
                System.out.println("something bad happened: "+url);
            }
        }
    }

    private static RecipeEntity processPageContent(String content) {
        Preconditions.checkNotNull(content);
        Recipe recipe = new Recipe();
        Document doc = Jsoup.parse(content);

        Elements tmp = doc.select("h1"); // 标题
        recipe.setName(tmp.text());

        tmp = doc.select("div.cp_headerimg_w > img");
        String srcUrl = tmp.attr("src");
        recipe.setImages(srcUrl);

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

        //extract the main ingredient
        tmp = doc.select("div[class=yl zl clearfix] li");
        if (tmp != null) {
            for (int i = 0; i < tmp.size(); i++) {
                Ingredient ingredient = new Ingredient();
                Element e = tmp.get(i);
                ingredient.setIngredientName(e.select("h4 > a").text());
                ingredient.setUrl(e.select("img").attr("src"));
                ingredient.setQuantityDesc(e.select("span").text());
                recipe.addMainIngredient(ingredient);
            }
        }

        //extract the sub ingredient
        tmp = doc.select("div[class=yl fuliao clearfix] li");
        if (tmp != null) {
            for (int i = 0; i < tmp.size(); i++) {
                Ingredient ingredient = new Ingredient();
                Element e = tmp.get(i);
                ingredient.setIngredientName(e.select("a").text());
                ingredient.setQuantityDesc(e.select("span").text());
                recipe.addSubIngredient(ingredient);
            }
        }


        //extract steps
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

        RecipeEntity recipeEntity = Strings.isNullOrEmpty(recipe.getName()) || cookSteps.size()==0 ? null :
                new RecipeEntity(recipe, cookSteps);

        return recipeEntity;
    }
}
