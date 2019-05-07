package com.indusfo.spc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UrlUtils {

    /**
     * 保存URL到本地
     *
     * @author xuz
     * @date 2019/1/5 9:37 AM
     * @param [url,urlFile]
     * @return void
     */
    public static void saveURLToLocal(String url,File urlFile) {
        try {
            urlFile.createNewFile();
            BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(urlFile)));
            bufw.write(url);
            bufw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地获取Cookie
     *
     * @author xuz
     * @date 2019/1/5 9:40 AM
     * @param [urlFile]
     * @return java.lang.String
     */
    public static String getURLFromLocal(File urlFile) {
        try {
            if (urlFile.exists() && urlFile.length() >0) {
                BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(urlFile)));
                String line = bufr.readLine();
                bufr.close();
                return line;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
