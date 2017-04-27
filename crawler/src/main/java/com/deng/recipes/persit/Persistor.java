package com.deng.recipes.persit;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/27.
 */
public abstract class Persistor {

    public abstract boolean save(Object o, String dbName, String typeName);

    public abstract List<Boolean> saveAll(List<?> collection, String dbName, String typeName);

    public boolean save(Object o, String dbName) {
        System.out.println(o.getClass().getTypeName());
        return save(o, dbName, o.getClass().getTypeName());
    }

    public List<Boolean> saveAll(List<?> objects, String dbName) {
        List<Boolean> res = null;
        try {
            res = saveAll(objects, dbName,
                    objects.getClass().getMethod("get", Integer.class)
                            .getReturnType().getTypeName());
        } catch (NoSuchMethodException e) {
            res = new ArrayList<>(objects.size());
        }

        return res;
    }
}
