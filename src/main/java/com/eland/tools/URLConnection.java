package com.eland.tools;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/9/19
 * Time: 下午 1:59
 * To change this template use File | Settings | File Templates.
 */
public class URLConnection {
    private static final Logger log = Logger.getLogger("aws");
    public static String URLConnection(String url, String argument, int timeout) {
        String conetnt = "";

        try {

            URL myUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();

            con.setDoOutput(true);  //設定為可寫入資料至伺服器
            con.setDoInput(true);  //設定為可從伺服器讀取資料
            con.setUseCaches(false);//設定不使用快取
            con.setRequestMethod("POST");
            //設定timeout
            con.setConnectTimeout(timeout);//連接主機的時間
            con.setReadTimeout(timeout);//重主機讀取的時間
            con.connect();//開啟連線
            BufferedWriter outs = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            outs.write(argument);
            outs.flush();
            outs.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line = "";
            conetnt = "";
            while ((line = reader.readLine()) != null) {
                conetnt += line;
            }
            reader.close();
            //log.info("Connection to: " + url+" successfully. ");
        } catch (SocketTimeoutException e) {
            log.error("Connection to "+url +" error, try again after 3 sec.");
            conetnt="error";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Connection to "+ url+" Fail" + e.getMessage(), e);
        }
        return conetnt;
    }


    public static String URLConnectionPostJson(String url, String json, int timeout) {
        String conetnt = "";

        try {

            URL myUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();

            con.setDoOutput(true);  //設定為可寫入資料至伺服器
            con.setDoInput(true);  //設定為可從伺服器讀取資料
            con.setUseCaches(false);//設定不使用快取
            con.setRequestMethod("POST");

            //post json轉utf-8(2020/04/06新增)
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            OutputStream os = con.getOutputStream();
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);

            //設定timeout
            con.setConnectTimeout(timeout);//連接主機的時間
            con.setReadTimeout(timeout);//重主機讀取的時間
            con.connect();//開啟連線

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line = "";
            conetnt = "";
            while ((line = reader.readLine()) != null) {
                conetnt += line;
            }
            reader.close();
            //log.info("Connection to: " + url+" successfully. ");
        } catch (SocketTimeoutException e) {
            log.error("Connection to "+url +" error, try again after 3 sec.");
            conetnt="error";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Connection to "+ url+" Fail" + e.getMessage(), e);
        }
        return conetnt;
    }
}
