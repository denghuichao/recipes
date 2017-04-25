package com.deng.recipes;

import com.deng.recipes.crawler.MeishijieCrawler;
import com.deng.recipes.utils.Constants;
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
        while(true) {
            int i = random.nextInt(Constants.proxys.length);
            String []ps = Constants.proxys[i].split("\\s*:\\s*");
            String host = ps[0];
            int port = Integer.parseInt(ps[1]);

            System.out.printf("using proxy %s:%d\n", host, port);
            crawlStart(host,port);
        }
    }

    public static void crawlStart(String host, int port)throws Exception{
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 10;
        CrawlConfig config = new CrawlConfig();

        config.setProxyHost(host);
        config.setProxyPort(port);
        config.setMaxDepthOfCrawling(3);
        config.setSocketTimeout(5000);
        config.setResumableCrawling(true);
        config.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36");
        config.setCrawlStorageFolder(crawlStorageFolder);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("http://www.meishij.net/chufang/diy/");

        controller.startNonBlocking(MeishijieCrawler.class, numberOfCrawlers);

        Thread.sleep(60 * 1000);

        controller.shutdown();
        controller.waitUntilFinish();
    }
}
