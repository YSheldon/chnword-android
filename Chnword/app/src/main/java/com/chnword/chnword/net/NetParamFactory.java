package com.chnword.chnword.net;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by khtc on 15/5/12.
 */
public class NetParamFactory {


    public static JSONObject verifyParam(String userid, String deviceId, String code)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);
            obj.put("code", code);

//            JSONObject param = new JSONObject();
//            param.put("code", code);
//            param.put("user", user);
//            obj.put("param", param);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject listParam(String userid, String deviceId, int page, int size)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("page", page);
            param.put("size", size);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static JSONObject shopListParam(String userid, String deviceId)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject subListParam(String userid, String deviceId, String zone, int page, int size)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("page", page);
            param.put("size", size);
            param.put("list", zone);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject wordParam(String userid, String deviceId, String word)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("word", word);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject sharedWordParam(String userid, String deviceId)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject showParam(String userid, String deviceId, String wordCode)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("word_code", wordCode);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject registParam(String userid, String deviceId,
                                         String usercode, String deviceid,
                                         String session, String verify)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("usercode", usercode);
            param.put("deviceid", deviceid);
            param.put("session", session);
            param.put("verify", verify);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject versionParam(int versionCode)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("key", "1");
            obj.put("version", versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject feedbackParam(String userid, String deviceId, String content, String contact)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("deviceid", deviceId);
            param.put("content", content);
            param.put("contact", contact);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject feedbackParam(String userid, String deviceId, String content)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString());
            obj.put("userid", userid);
            obj.put("device", deviceId);

            JSONObject param = new JSONObject();
            param.put("deviceid", deviceId);
            param.put("content", content);
            param.put("contact", "");

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
