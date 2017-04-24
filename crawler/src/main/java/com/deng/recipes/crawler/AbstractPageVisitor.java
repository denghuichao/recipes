package com.deng.recipes.crawler;

import com.google.common.base.Preconditions;
import net.vidageek.crawler.Page;
import net.vidageek.crawler.PageVisitor;
import net.vidageek.crawler.Status;
import net.vidageek.crawler.Url;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Created by hcdeng on 2017/4/21.
 */
public abstract class AbstractPageVisitor<T> implements PageVisitor {

    static Logger log = Logger.getLogger(AbstractPageVisitor.class);

    private final BlockingQueue<T> resultQueue;

    public AbstractPageVisitor(BlockingQueue<T> resultQueue) {
        Preconditions.checkNotNull(resultQueue);
        this.resultQueue = resultQueue;
    }

    public abstract boolean followUrl(Url url);

    public void visit(Page page) {
        T t = processPage(page);
        if (t != null)
            try {
                resultQueue.put(t);
            } catch (InterruptedException e) {
                log.info("fail to put T to blocking queue: " + e.getMessage());
            }
    }

    protected abstract T processPage(Page page);

    public void onError(Url url, Status status) {
        log.info("fail to visit page:" + url + status.toString());
    }
}
