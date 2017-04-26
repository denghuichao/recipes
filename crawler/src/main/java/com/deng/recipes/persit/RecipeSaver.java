package com.deng.recipes.persit;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeSaver {

    private static BlockingQueue<SaveRequest> recipeProvider = new LinkedBlockingDeque<>();

    private static Thread recipeSaver;

    static {
        recipeSaver = new Thread(new RecipeEntitySaver());
        recipeSaver.start();
    }

    public static void saveRecipe(RecipeEntity recipeEntity, String dirPath) {
        String content = JSON.toJSONString(recipeEntity);
        String path = dirPath + FileUtils.getMD5(content);
        try {
            recipeProvider.put(new SaveRequest(path, content));
        } catch (InterruptedException e) {
            System.out.println("fail to submit RecipeEntity");
        }
    }

    public static void saveHtml(String url, String html, String dirPath) {
        String path = dirPath + String.valueOf(url.hashCode()) + UUID.randomUUID() + ".html";
        try {
            recipeProvider.put(new SaveRequest(path, html));
        } catch (InterruptedException e) {
            System.out.println("fail to submit html");
        }
    }

    private static class SaveRequest {
        private final String filePath;
        private final String content;

        public SaveRequest(String filePath, String content) {
            this.filePath = filePath;
            this.content = content;
        }
    }

    private static class RecipeEntitySaver implements Runnable {
        @Override
        public void run() {
            while (true) {
                SaveRequest request = null;
                try {
                    request = recipeProvider.take();
                    saveFile(request);
                } catch (InterruptedException e) {
                    System.out.println("error when saving: " + e.getMessage());
                }
            }
        }

        private void saveFile(SaveRequest request) {
            FileWriter writer = null;
            try {
                System.out.println("saving " + request.filePath);
                writer = new FileWriter(request.filePath);
                writer.write(request.content);
                writer.close();
            } catch (IOException e) {
                System.out.println("fail to save RecipeEntity " + e.getMessage());
            }
        }
    }
}
