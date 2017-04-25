package com.deng.recipes;

import com.deng.recipes.crawler.DouguoCrawler;
import com.deng.recipes.crawler.MeishitianxiaCrawler;
import com.deng.recipes.crawler.XiachufangCrawler;
import com.deng.recipes.utils.Constants;
import com.google.common.base.Strings;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.Random;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class Bootstrap {

    public static void main(String[] args) throws Exception {
        Random random = new Random();

        while (true) {
            int i = random.nextInt(Constants.proxys.length);
            String[] ps = Constants.proxys[i].split("\\s*:\\s*");
            String host = ps[0];
            int port = Integer.parseInt(ps[1]);

            System.out.printf("using proxy %s:%d\n", host, port);
            CrawlController controller1 = crawlStart(XiachufangCrawler.class, host, port, "http://www.xiachufang.com/", 5);
            CrawlController controller2 = crawlStart(DouguoCrawler.class, host, port, "http://www.douguo.com/caipu", 5);
            CrawlController controller3 = crawlStart(MeishitianxiaCrawler.class, host, port, "http://home.meishichina.com/recipe.html", 5);

            Thread.sleep(5 * 60 * 1000);
            controller1.shutdown();
            controller2.shutdown();
            controller3.shutdown();
            controller1.waitUntilFinish();
            controller2.waitUntilFinish();
            controller3.waitUntilFinish();
        }
    }

    public static CrawlController crawlStart(Class crawlerClass, String host, int port, String seed, int threadNum) throws Exception {
        String crawlStorageFolder = "/data/crawl/" + "cache" + seed.hashCode();
        CrawlConfig config = new CrawlConfig();

        if (!Strings.isNullOrEmpty(host)) config.setProxyHost(host);
        if (port > 0) config.setProxyPort(port);

        config.setMaxDepthOfCrawling(4);
        config.setSocketTimeout(10000);
        config.setResumableCrawling(true);
        config.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36");
        config.setCrawlStorageFolder(crawlStorageFolder);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(seed);

        controller.startNonBlocking(crawlerClass, threadNum);

        return controller;
    }
}
