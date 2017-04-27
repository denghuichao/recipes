package com.deng.recipes.persit;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeSaver {

    private static BlockingQueue<SaveRequest> recipeProvider = new LinkedBlockingDeque<>();

    private static Executor recipeSaver;

    static {
        recipeSaver = Executors.newCachedThreadPool();
        recipeSaver.execute(new RecipeEntitySaver());
    }

    public static void saveRecipe(RecipeEntity recipeEntity, String dirPath) {
        String content = JSON.toJSONString(recipeEntity);
        try {
            recipeProvider.put(new SaveRequest(dirPath, FileUtils.getMD5(content), content));
        } catch (InterruptedException e) {
            System.out.println("fail to submit RecipeEntity");
        }
    }

    public static void saveHtml(String url, String html, String dirPath) {
        try {
            recipeProvider.put(new SaveRequest(dirPath, FileUtils.getMD5(html) + ".html", html));
        } catch (InterruptedException e) {
            System.out.println("fail to submit html");
        }
    }

    private static class SaveRequest {
        private final String filePath;
        private final String content;
        private final String fileName;

        public SaveRequest(String filePath, String fileName, String content) {
            this.filePath = filePath;
            this.content = content;
            this.fileName = fileName;
        }
    }

    private static class RecipeEntitySaver implements Runnable {
        @Override
        public void run() {
            while (true) {
                SaveRequest request = null;
                try {
                    request = recipeProvider.take();
                    FileUtils.saveFile(request.filePath, request.fileName, request.content);
                } catch (InterruptedException e) {
                    System.out.println("error when saving: " + e.getMessage());
                }
            }
        }
    }
}
