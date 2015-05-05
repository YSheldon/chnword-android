package com.chnword.chnword.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khtc on 15/5/5.
 */
//885d549a
public class HciAccountInfo {

    private static HciAccountInfo mInstance;

    private Map<String, String> mAccountMap;

    private HciAccountInfo() {
        mAccountMap = new HashMap<String, String>();
    }

    public static HciAccountInfo getInstance() {
        if (mInstance == null) {
            mInstance = new HciAccountInfo();

//            mInstance.mAccountMap.put("capKey", "ocr.local");
            mInstance.mAccountMap.put("capKey", "ocr.cloud");
            mInstance.mAccountMap.put("developerKey", "88eda05b0e0a460cd45e9364b088d842");
            mInstance.mAccountMap.put("appKey", "885d549a");
            mInstance.mAccountMap.put("cloudUrl", "test.api.hcicloud.com:8888");
        }
        return mInstance;
    }

    public String getCapKey(){
        return mAccountMap.get("capKey");
    }
    public String getDeveloperKey(){
        return mAccountMap.get("developerKey");
    }
    public String getAppKey(){
        return mAccountMap.get("appKey");
    }
    public String getCloudUrl(){
        return mAccountMap.get("cloudUrl");
    }

}
