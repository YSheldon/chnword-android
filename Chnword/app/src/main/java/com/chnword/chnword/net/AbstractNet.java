package com.chnword.chnword.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

/**
 * Created by khtc on 15/5/12.
 */
public class AbstractNet {

    public static final String TAG = HandleNet.class.getSimpleName();

    public static final String APPLICATION_JSON = "application/json";

    public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static final int NETWHAT_SUCESS = 1001;
    public static final int NETWHAT_FAIL = 1002;


    protected Handler handler;
    protected JSONObject param;
    protected String url;
    protected Thread thread;

    public AbstractNet(Handler handler, JSONObject param, String url) {
        this.handler = handler;
        this.param = param;
        this.url = url;
        initThread();
    }


    public void initThread(){
        Runnable runnable = new NetRunnable(this);
        thread = new Thread(runnable);
    }

    public void start(){
        thread.start();
    }

    protected void didSucess(String jsonData) {
        Message message = new Message();
        message.what = AbstractNet.NETWHAT_SUCESS;
        Bundle b = new Bundle();
        b.putString("responseBody", jsonData);
        message.setData(b);

        handler.sendMessage(message);
    }

    protected void didFail() {
        Message message = new Message();
        message.what = AbstractNet.NETWHAT_FAIL;
        Bundle b = new Bundle();
        handler.sendMessage(message);
    }

    protected void didFail(String jsonData) {
        Message message = new Message();
        message.what = AbstractNet.NETWHAT_SUCESS;
        Bundle b = new Bundle();
        b.putString("responseBody", jsonData);
        message.setData(b);
        handler.sendMessage(message);
    }



    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
