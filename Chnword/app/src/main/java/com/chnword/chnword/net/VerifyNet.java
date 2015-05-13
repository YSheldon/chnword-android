package com.chnword.chnword.net;

import android.os.Handler;

import org.json.JSONObject;

/**
 * Created by khtc on 15/5/12.
 */
public class VerifyNet extends AbstractNet {

    public VerifyNet(Handler handler, JSONObject param, String url) {
        super(handler, param, url);
    }

    @Override
    public void initThread() {

        Runnable runnable = new VerifyRunnable();

        thread = new Thread(runnable);
    }
}
