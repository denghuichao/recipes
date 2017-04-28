package com.deng.recipes.boot;

import com.deng.recipes.crawler.MeishitianxiaCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/28.
 */
public class CrawlerBooter {

    private static final String CLASSPATH_CONFIG_PATH = "crawlers.xml";

    public static void main(String[] args) throws Exception {
        String seed = "http://home.meishichina.com/recipe.html";
        String cacheDir  = "/cache/crawl/" + "cache" + seed.hashCode();
        crawlStart(MeishitianxiaCrawler.class,  seed,cacheDir, 10, false);
    }

    public static CrawlController crawlStart(Class crawlerClass, String seed, String cacheDir, int threadNum, boolean cache) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setMaxDepthOfCrawling(4);
        config.setSocketTimeout(10000);
        config.setResumableCrawling(cache);
        config.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36");
        config.setCrawlStorageFolder(cacheDir);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(seed);

        controller.startNonBlocking(crawlerClass, threadNum);

        return controller;
    }

    /**
     * 基于XML配置文件启动爬虫，配置文件必须放在resources目录下，文件吗crawlers.xml
     */
    private static List<CrawlController> startAllCrawlers()throws Exception{
        /**
         * TODO 实现基于XML配置的爬虫启动
         */
        return null;
    }
}
