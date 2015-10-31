package com.chnword.chnword.net;

import com.chnword.chnword.beans.CateBuyItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by khtc on 15/5/12.
 */
public class NetParamFactory {

    public static JSONObject loginParam(String userid, String deviceId)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject verifyParam(String userid, String deviceId, String code)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId);
//            obj.put("device", "2");
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
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");

            JSONObject param = new JSONObject();
            param.put("page", page);
            param.put("size", size);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject yzmParam(String userid, String deviceId, String phoneNumber)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");
            obj.put("tel", phoneNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject verifyParam(String userid, String deviceId, String tel, String code, String sn, String yzm)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");
            obj.put("code", code);
            obj.put("tel", tel);
            obj.put("sn", sn);
            obj.put("yzm", yzm);
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
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");

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
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");

            obj.put("page", page);
            obj.put("size", size);
            obj.put("list", zone);

//            JSONObject param = new JSONObject();
//            param.put("page", page);
//            param.put("size", size);
//            param.put("list", zone);
//            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject wordParam(String userid, String deviceId, String word, int usertype)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");
            obj.put("type", usertype + "");

            JSONObject param = new JSONObject();
            param.put("word", word);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject sharedWordParam(String userid, String deviceId, String usertype)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");
            obj.put("type", usertype + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject showParam(String userid, String deviceId, String wordCode, int usertype)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");
            obj.put("type", usertype + "");

            obj.put("word_code", wordCode);

//            JSONObject param = new JSONObject();
//            param.put("word_code", wordCode);
//            obj.put("param", param);
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
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId );
//            obj.put("device", "2");

            JSONObject param = new JSONObject();
            param.put("usercode", usercode);
            param.put("deviceid", deviceid );
            param.put("session", session);
            param.put("verify", verify);

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject versionParam(int versionCode, String userid, String deviceId)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
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
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId);
//            obj.put("device", "2");

            JSONObject param = new JSONObject();
            param.put("deviceid", deviceId );
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
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId);
//            obj.put("device", "2");

            JSONObject param = new JSONObject();
            param.put("deviceid", deviceId );
            param.put("content", content);
            param.put("contact", "");

            obj.put("param", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject shopOrderParam(String userid, String deviceId, String trueprice, String orderNumber, String paytype, List<CateBuyItem> buyed) {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId);
            obj.put("trueprice", trueprice);
            obj.put("ordernum", orderNumber);
            obj.put("paytype", paytype);

            obj.put("remark", "");

            JSONArray array = new JSONArray();
            for (int i = 0; i < buyed.size(); i ++ ) {
                JSONObject item = new JSONObject();
                CateBuyItem buyItem = buyed.get(i);
                item.put("name", buyItem.getName());
                item.put("cname", buyItem.getcName());
                item.put("pid", buyItem.getPid());
                item.put("price", buyItem.getPriceString());
                item.put("icon", buyItem.getIconUrl());
                array.put(item);
            }
            obj.put("product", array);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject shopOrderPaymentParam(String userid, String deviceId, String trueprice, String orderNumber, String paytype) {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("opid", UUID.randomUUID().toString() );
            obj.put("userid", userid);
            obj.put("device", deviceId);
            obj.put("trueprice", trueprice);
            obj.put("ordernum", orderNumber);
            obj.put("paytype", paytype);

            obj.put("remark", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
