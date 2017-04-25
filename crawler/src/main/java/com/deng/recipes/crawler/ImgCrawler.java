package com.deng.recipes.crawler;

import com.deng.recipes.utils.Constants;
import com.deng.recipes.utils.FilePathUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class ImgCrawler {

    static Logger log = Logger.getLogger(ImageCrawler.class);
    private static final BlockingQueue<String> urlProvider = new LinkedBlockingDeque<String>();
    private static Thread imageDownloader;

    static {
        imageDownloader = new Thread(new ImageDownloader());
        imageDownloader.start();
    }

    public static void commit(String imgUrl){
        try {
            urlProvider.put(imgUrl);
        }catch (InterruptedException e){
            log.info("fail to commit img url to blocking queue: "+e.getMessage());
        }
    }


    private static class ImageDownloader implements Runnable{
        public void run() {
            while (true) {
                try {
                    String url = urlProvider.take();
                    System.out.println("downloading "+url);
                    downloadImageAndSave(url);
                } catch (InterruptedException e) {
                    log.info("fail to take url from blocking queue: "+e.getMessage());
                }
            }
        }

        private void downloadImageAndSave(String url){

            String filePath = FilePathUtils.urlPath2LocalPath(url, Constants.IMAGE_DIR);
            File file = new File(filePath);

            if(!file.exists()){
                HttpClient httpClient = new DefaultHttpClient();
                try {
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse response = httpClient.execute(httpGet);
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        FileOutputStream output = new FileOutputStream(file);
                        output.write( EntityUtils.toByteArray(entity) );
                        output.close();
                    }
                }catch (Exception e) {
                    log.info("fail to download image: "+url);
                }finally{
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
    }
}
