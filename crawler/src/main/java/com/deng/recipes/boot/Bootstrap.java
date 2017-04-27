package com.deng.recipes.boot;


import com.google.common.base.Strings;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class Bootstrap {
    public static CrawlController crawlStart(Class crawlerClass, String host, int port, String seed, String cacheDir, int threadNum, boolean cache) throws Exception {

        CrawlConfig config = new CrawlConfig();

        if (!Strings.isNullOrEmpty(host)) config.setProxyHost(host);
        if (port > 0) config.setProxyPort(port);

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
}
