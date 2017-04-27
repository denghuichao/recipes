package com.deng.recipes.persit;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/27.
 */
public class FilePersistor extends Persistor {
    @Override
    public boolean save(Object o, String path, String typeName) {
        String content = JSON.toJSONString(o);
        String fileName = path + "/" + typeName + "/" + FileUtils.getMD5(content);
        return saveFile(content, fileName);
    }

    private boolean saveFile(String content, String fileName) {
        try {
            System.out.println("saving " + fileName);
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("fail to save RecipeEntity " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Boolean> saveAll(List<?> collection, String path, String typeName) {
        List<Boolean> res = new ArrayList<>(collection.size());
        int i = 0;
        for(Object o : collection){
            res.set(i++, save(o, path, typeName));
        }
        return res;
    }
}
