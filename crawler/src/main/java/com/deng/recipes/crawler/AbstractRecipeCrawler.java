package com.deng.recipes.crawler;

import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.persit.RecipeSaver;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

/**
 * Created by hcdeng on 2017/4/25.
 */
public abstract class AbstractRecipeCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");

    protected String BASE_URL;
    protected String RECIPE_PATTERN;
    protected String IMAGE_DIR;
    protected String RECIPES_DIR;
    protected String HTML_DIR;

    public AbstractRecipeCrawler() {
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.contains(BASE_URL);
    }


    @Override
    public void visit(Page page) {

        String url = page.getWebURL().getURL();

        if (!url.contains(RECIPE_PATTERN))
            return;

        System.out.println("visiting: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            RecipeSaver.saveHtml(url, html, HTML_DIR);
            RecipeEntity recipeEntity = processPageContent(html);
            if (recipeEntity != null) {
                RecipeSaver.saveRecipe(recipeEntity, RECIPES_DIR);
                RecipeSaver.saveHtml(url, html, HTML_DIR);
            } else {
                System.out.println("something bad happened: " + url);
            }
        }
    }

    protected abstract RecipeEntity processPageContent(String html);
}
