package com.deng.recipes.boot;

import com.deng.recipes.crawler.DouguoCrawler;
import com.deng.recipes.crawler.XiachufangCrawler;
import com.deng.recipes.utils.Constants;
import edu.uci.ics.crawler4j.crawler.CrawlController;

import java.util.Random;

/**
 * Created by hcdeng on 2017/4/27.
 */
public class BootAll {
    public static void main(String[] args) throws Exception {
        Random random = new Random();

        String seed1 = "http://www.xiachufang.com/";
        String crawlStorageFolder1 = "/data/crawl/" + "cache" + seed1.hashCode();

        String seed2 = "http://www.douguo.com/caipu";
        String crawlStorageFolder2 = "/data/crawl/" + "cache" + seed2.hashCode();

        while (true) {
            int i = random.nextInt(Constants.proxys.length);
            String[] ps = Constants.proxys[i].split("\\s*:\\s*");
            String host = ps[0];
            int port = Integer.parseInt(ps[1]);

            System.out.printf("using proxy %s:%d\n", host, port);
            CrawlController controller1 = Bootstrap.crawlStart(XiachufangCrawler.class, host, port, seed1,crawlStorageFolder1, 8, true);
            CrawlController controller2 = Bootstrap.crawlStart(DouguoCrawler.class, host, port, seed2,crawlStorageFolder2, 8, true);

            Thread.sleep(5 * 60 * 1000);
            controller1.shutdown();
            controller2.shutdown();
            controller1.waitUntilFinish();
            controller2.waitUntilFinish();
        }
    }
}
