package com.eland.tools;

import com.eland.tools.postBody.PostBody;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.LinkedList;

/**
 * Created by ccyang on 2020/4/6.
 */
public class post {
    private static final Logger logger = Logger.getLogger("aws");
    private static final String secretKey = "cee3cee31701474983537a708b235a6d";
    //private static final String apiUrl = ConfigService.getInstance().getProperty("api_url");

    public static String getSecretKey(String postBody) {
        String result = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(postBody.getBytes());
            byte[] hexBytes = new Hex().encode(rawHmac);
            // String str = new String(hexBytes, "UTF-8");

            //  Covert array of Hex bytes to a String
            return new String(Base64.encodeBase64(rawHmac));
        } catch (Exception ex) {
            logger.error(ex, ex);
            // System.out.println(ex);
        }
        return result;
    }
    public static void callTheTradeDeskAPI(String headerName, LinkedList<PostBody> postBodies) {
        try {
            Gson gson = new Gson();
            for (PostBody item : postBodies) {
                JsonObject postBody = gson.toJsonTree(item).getAsJsonObject();
                String postStr = gson.toJson(postBody).trim();
                String secretKey = getSecretKey(postStr);

//                CloseableHttpClient client = HttpClients.createDefault();
//                HttpPost httpPost = new HttpPost(apiUrl);
//                StringEntity entity = new StringEntity(postStr);
//                // System.out.println(postStr);
//                httpPost.setEntity(entity);
//                httpPost.setHeader("TtdSignature", secretKey);
//                httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
//                // for (Header header : httpPost.getAllHeaders()) {
//                //     System.out.println(header.getName()+":"+header.getValue());
//                // }
//                CloseableHttpResponse response = client.execute(httpPost);
//                // BufferedReader rd = new BufferedReader
//                //     (new InputStreamReader(
//                //         response.getEntity().getContent()));
//                // System.out.println(rd.lines().collect(Collectors.joining()));
//                if (response.getStatusLine().getStatusCode() == 200) {
//                    logger.info(
//                            "\t[Info] Status Code=%d...\n" + response.getStatusLine().getStatusCode());
//                    client.close();
//                    response.close();
//                } else {
//                    String responseCode = EntityUtils.toString(response.getEntity(), "UTF-8");
//                    logger.error(
//                            "[Error] status Code=%d" + response.getStatusLine().getStatusCode() +
//                                    responseCode);
//                    client.close();
//                    response.close();
//
//                }
            }
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
    }
}

