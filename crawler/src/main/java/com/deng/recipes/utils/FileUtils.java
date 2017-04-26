package com.deng.recipes.utils;

import com.google.common.base.Preconditions;
import info.monitorenter.cpdetector.io.*;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class FileUtils {

    private FileUtils(){}

    public static String url2FileName(String url){
        Preconditions.checkNotNull(url);

        int dotIdx = url.lastIndexOf('/');
        if(dotIdx < 0)dotIdx = 0;

        return url.hashCode() + url.substring(dotIdx);
    }

    public static String readFile(String fileName) throws IOException {
        String charset = getFileCharSet(fileName);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }

    private static String getFileCharSet(String path) {
        CodepageDetectorProxy detector =
                CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = Charset.forName("UTF-8");
        File f = new File(path);
        try {
            charset = detector.detectCodepage(f.toURI().toURL());
            System.out.println("charset:" + charset);
        } catch (Exception ex) {
            System.out.println("unkown charset, return default UTF-8:" + ex.getMessage());
        } finally {
            return charset.name();
        }
    }
}
