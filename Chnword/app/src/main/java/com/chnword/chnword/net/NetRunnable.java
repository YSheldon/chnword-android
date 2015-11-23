package com.chnword.chnword.net;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by khtc on 15/5/12.
 */
public class NetRunnable implements Runnable{
    private static final String TAG = NetRunnable.class.getSimpleName();

    protected AbstractNet abstractNet;

    protected NetRunnable(AbstractNet abstractNet) {
        this.abstractNet = abstractNet;
    }

    @Override
    public void run() {
        DefaultHttpClient httpClient = null;
        try {
            // 将JSON进行UTF-8编码,以便传输中文
//            String encoderJson = URLEncoder.encode(abstractNet.param.toString(), HTTP.UTF_8);

            Log.e(TAG, abstractNet.url + "");

            httpClient = new DefaultHttpClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);//连接时间
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);//数据传输时间


            HttpPost httpPost = new HttpPost(abstractNet.url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, AbstractNet.APPLICATION_JSON);

//            StringEntity se = new StringEntity(encoderJson);
            StringEntity se = new StringEntity(abstractNet.param.toString(), "utf-8");
            se.setContentType(AbstractNet.CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, AbstractNet.APPLICATION_JSON));

            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);

            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 打印响应状态
            Log.i(TAG, response.getStatusLine().toString());

            if (entity != null) {
                // 打印响应内容长度
                Log.i(TAG, "Response content length: " + entity.getContentLength());
                // 打印响应内容
                //返回的json对象
                String content = EntityUtils.toString(entity);

                Log.e(TAG, "param " + abstractNet.param.toString());
                Log.i(TAG, "Response content: " + content);
                Log.e(TAG, content);
                JSONObject responseJson = new JSONObject(content);
                Log.e(TAG, responseJson.toString());

                if (responseJson.getString("result").equalsIgnoreCase("1")) {
                    abstractNet.didSucess(content);
                } else {
                    abstractNet.didFail(content);
                }
            }

        } catch (Exception e) {
            abstractNet.didFail();
            e.printStackTrace();
        }finally {
            //关闭连接等

        }
    }
}
