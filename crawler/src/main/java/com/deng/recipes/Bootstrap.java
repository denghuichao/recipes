package com.deng.recipes;

import com.deng.recipes.crawler.MeishijieCrawler;
import com.deng.recipes.crawler.MeishijiePageVisitor;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.persit.RecipeSaver;
import com.deng.recipes.utils.Constants;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import net.vidageek.crawler.PageCrawler;
import net.vidageek.crawler.config.CrawlerConfiguration;
import net.vidageek.crawler.visitor.DomainVisitor;
import net.vidageek.crawler.visitor.RejectAtDepthVisitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class Bootstrap {
//    public static void main(String[] args) {
//        Executor executor = Executors.newSingleThreadExecutor();
//        BlockingQueue<RecipeEntity> blockingQueue = new LinkedBlockingDeque<>();
//        RecipeSaver recipeSaver = new RecipeSaver(blockingQueue);
//        executor.execute(recipeSaver);
//
//        CrawlerConfiguration cfg = new CrawlerConfiguration(Constants.BEGIN_URL);
//        PageCrawler crawler = new PageCrawler(cfg);
//        crawler.crawl(new RejectAtDepthVisitor(3,new DomainVisitor(Constants.BEGIN_URL, new MeishijiePageVisitor(blockingQueue))) );
//    }

    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 1;

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setProxyHost("61.191.41.130");
        config.setProxyPort(80);
       // config.setResumableCrawling(true);
        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.meishij.net/chufang/diy/");


        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MeishijieCrawler.class, numberOfCrawlers);
    }
}
