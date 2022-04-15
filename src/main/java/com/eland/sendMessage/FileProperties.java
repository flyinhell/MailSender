package com.eland.sendMessage;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;



/**
 * Properties設定檔公用程式
 *
 * Created by danielhsieh on 2014/6/11.
 */
public class FileProperties extends Properties{
    private Properties properties = new Properties();


    /**
     * 建構子
     * 將相對路徑轉成絕對路徑後，再將路徑使用UTF-8編碼，
     *
     * @param propertirsPath 欲讀取設定檔的路徑
     */
    public FileProperties(String propertirsPath){
        String path = getAbsolutePath(propertirsPath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("找不到" + path + "檔案");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("讀取設定檔發生異常");
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                System.out.println("關閉設定檔發生錯誤");
                e.printStackTrace();
            }
        }
    }

    /**
     * 取得設定檔絕對路徑
     *
     * @param relativePath 相對路徑
     * @return 絕對路徑字串
     */
    public static String getAbsolutePath(String relativePath) {
        String absolutePath = "";
        URL resource = Thread.currentThread().getContextClassLoader().getResource(relativePath);
        if (resource != null) {
            try {
                absolutePath = URLDecoder.decode(resource.getPath(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("不支援UTF-8編碼檔案");
                e.printStackTrace();
            }
        }
        return absolutePath;
    }

    /**
     * 取得設定檔中的參數值並去掉頭尾空白
     *
     * @param key 設定的參數名稱
     * @return 設定的值
     */
    public String getProperty(String key) {
        return properties.getProperty(key).trim();
    }
}
