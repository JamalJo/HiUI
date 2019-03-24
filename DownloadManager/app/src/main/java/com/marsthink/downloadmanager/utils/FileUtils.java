package com.marsthink.downloadmanager.utils;

import java.io.File;

/**
 * Created by zhoumao on 2019/3/23.
 * Description:
 */
public class FileUtils {
    public static boolean mkdirs(String path) {
        File file=new File(path);
        if (!file.exists()){
            return file.mkdirs();
        }
        return true;
    }
}
