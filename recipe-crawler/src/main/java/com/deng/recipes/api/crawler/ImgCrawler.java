package com.deng.recipes.api.crawler;

import com.deng.recipes.api.utils.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;


public class ImgCrawler {

    private static final BlockingQueue<Request> urlProvider = new LinkedBlockingDeque<Request>();
    private static Executor imageDownloader;
    private static int NUM_THREADS = 3;

    static {
        imageDownloader = Executors.newFixedThreadPool(NUM_THREADS);
        imageDownloader.execute(new ImageDownloader());
    }

    private static class Request{
        private final String url;
        private final String imgDir;

        public Request(String url, String imgDir) {
            this.url = url;
            this.imgDir = imgDir;
        }
    }

    public static void commit(String imgUrl, String imgDir){
        try {
            urlProvider.put(new Request(imgUrl, imgDir));
        }catch (InterruptedException e){
            System.out.println("fail to commit img url to blocking queue: "+e.getMessage());
        }
    }

    private static class ImageDownloader implements Runnable{
        public void run() {
            while (true) {
                try {
                    Request request = urlProvider.take();
                    System.out.println("downloading "+request.url);
                    downloadImageAndSave(request);
                } catch (InterruptedException e) {
                    System.out.println("fail to take url from blocking queue: "+e.getMessage());
                }
            }
        }

        private void downloadImageAndSave(Request request){
            String url = request.url;
            String filePath = request.imgDir + FileUtils.url2FileName(url);

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
                    System.out.println("fail to download image: "+url);
                }finally{
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
    }
}
