package com.deng.recipes.persit;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/27.
 */
public class FilePersistor extends Persistor {
    @Override
    public boolean save(Object o, String path, String typeName) {
        String content = JSON.toJSONString(o);
        return FileUtils.saveFile(content, path + "/" + typeName, FileUtils.getMD5(content));
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
