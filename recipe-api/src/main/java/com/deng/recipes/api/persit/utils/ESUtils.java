package com.deng.recipes.api.persit.utils;

import java.lang.reflect.Method;

/**
 * Created by hcdeng on 2017/5/8.
 */
public class ESUtils {
    public static String getId(Object o){
        String id = null;
        Class c = o.getClass();
        Method[] methods = c.getDeclaredMethods();
        for(Method method : methods){
            String name = method.getName();
            if(name.equalsIgnoreCase("getId")){
                try {
                    id = String.valueOf(method.invoke(o));
                }catch (Exception e){
                    //do nothing if Exception hanppens
                }
            }
        }

        return id;
    }
}
