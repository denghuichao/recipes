package com.deng.recipes.utils;

import com.google.common.base.Preconditions;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class FilePathUtils {

    private FilePathUtils(){}

    public static String urlPath2LocalPath(String url, String localDir){
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(localDir);
        int dotIdx = url.lastIndexOf('/');
        if(dotIdx < 0)dotIdx = 0;

        return localDir+ "/" + url.substring(dotIdx);
    }
}
