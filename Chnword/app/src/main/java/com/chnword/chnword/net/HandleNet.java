package com.chnword.chnword.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by khtc on 15/5/12.
 */
public class HandleNet implements Runnable {
    public static final String TAG = HandleNet.class.getSimpleName();

    public static final String APPLICATION_JSON = "application/json";

    public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static final int NETWHAT_SUCESS = 1001;
    public static final int NETWHAT_FAIL = 1002;

    private Handler handler;
    private JSONObject param;
    private String url;

    public HandleNet(JSONObject param) {
        this.param = param;
    }

    public HandleNet(String url, Handler handler, JSONObject param) {
        this.url = url;
        this.handler = handler;
        this.param = param;
    }

    @Override
    public void run() {
        try {
            // 将JSON进行UTF-8编码,以便传输中文
            String encoderJson = URLEncoder.encode(param.toString(), HTTP.UTF_8);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            StringEntity se = new StringEntity(encoderJson);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);

            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 打印响应状态
            System.out.println(response.getStatusLine());
            if (entity != null) {
                // 打印响应内容长度
                Log.i(TAG, "Response content length: " + entity.getContentLength());
                // 打印响应内容
                Log.i(TAG, "Response content: " + EntityUtils.toString(entity));
            }
            String body = EntityUtils.toString(entity);
            Log.e(TAG, body);
            JSONObject responseJson = new JSONObject(body);
            Log.e(TAG, responseJson.toString());




        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    /*
    //用于解析数据
    public static String receivePost(HttpServletRequest request) throws IOException, UnsupportedEncodingException {

        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }

        // 将资料解码
        String reqBody = sb.toString();
        return URLDecoder.decode(reqBody, HTTP.UTF_8);
    }
    */
}
