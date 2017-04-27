package com.deng.recipes.crawler;

import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.persit.PersistUtils;
import com.deng.recipes.persit.RecipeSaver;
import com.deng.recipes.utils.ConfigManager;
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

    protected abstract String baseUrl();

    protected abstract String recipePattern();

    protected abstract RecipeEntity processPageContent(String html);

    private String htmlDir() {
        String dataPath = ConfigManager.instance().getProperty("cache.path");
        return dataPath + "/" + baseUrl().hashCode() + "/images/";
    }

    private String imgDir() {
        String dataPath = ConfigManager.instance().getProperty("cache.path");
        return dataPath + "/" + baseUrl().hashCode() + "/htmls/";
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.contains(baseUrl());
    }

    @Override
    public void visit(Page page) {

        String url = page.getWebURL().getURL();

        if (!url.matches(recipePattern()))
            return;

        System.out.println("visiting: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            RecipeSaver.saveHtml(url, html, htmlDir());
            RecipeEntity recipeEntity = processPageContent(html);
            if (recipeEntity != null) {
                PersistUtils.save(recipeEntity);
                RecipeSaver.saveHtml(url, html, htmlDir());
            } else {
                System.out.println("something bad happened: " + url);
            }
        }
    }
}
