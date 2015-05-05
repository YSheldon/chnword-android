package com.chnword.chnword;

import android.content.Intent;

/**
 * Created by khtc on 15/4/28.
 */
public interface TabActivityListener {

    public void startIntent(Intent i);

    public void startIntent(Class<?> cls);
}

