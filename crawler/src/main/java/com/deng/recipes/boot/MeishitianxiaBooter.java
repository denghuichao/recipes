package com.deng.recipes.boot;

import com.deng.recipes.crawler.MeishitianxiaCrawler;


/**
 * Created by hcdeng on 2017/4/26.
 */
public class MeishitianxiaBooter {
    public static void main(String[] args) throws Exception {
        String seed = "http://home.meishichina.com/recipe.html";
        String cacheDir  = "/cache/crawl/" + "cache" + seed.hashCode();
       Bootstrap.crawlStart(MeishitianxiaCrawler.class, null, 0, seed,cacheDir, 10, false);
    }
}
