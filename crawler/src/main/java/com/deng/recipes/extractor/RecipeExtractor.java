package com.deng.recipes.extractor;

import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.persit.PersistUtils;
import com.deng.recipes.utils.FileUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by hcdeng on 2017/4/26.
 */
public abstract class RecipeExtractor implements Extractor<RecipeEntity, String> {

    public void processAllRecipes(File fs) throws IOException {
        if (fs.isDirectory()) {
            for (File f : fs.listFiles()) {
                processAllRecipes(f);
            }
        } else {
            System.out.println(fs.getAbsolutePath());
            String content = FileUtils.readFile(fs.getAbsolutePath());
            RecipeEntity entity = extract(content);
            if (entity != null) {
                PersistUtils.save(entity);
            } else {
                System.out.println(fs.getAbsolutePath() + " is not a recipe");
                fs.delete();
            }
        }
    }

    @Override
    public abstract RecipeEntity extract(String content);
}
