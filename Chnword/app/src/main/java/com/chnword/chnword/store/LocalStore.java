package com.chnword.chnword.store;

import android.content.Context;
import android.content.SharedPreferences;

import com.chnword.chnword.beans.Category;
import com.chnword.chnword.beans.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by khtc on 15/5/26.
 */
public class LocalStore {

    private static final String USERNAMEKEY = "USERNAMEKEY";

    private static final String WORDDEFAULTKEY = "WORDDEFAULTKEY";

    private static final String CHNWORD_UNLOCK_USER = "CHNWORD_UNLOCK_USER";
    private static final String CHNWORD_UNLOCK_ALL_USER = "CHNWORD_UNLOCK_ALL_USER";

    private SharedPreferences perference;


    public LocalStore(Context context) {
        perference = context.getSharedPreferences("LocalStore", Context.MODE_PRIVATE);

    }

    private static final String DEFAULT_USER_KEY = "DEFAULT_USER_KEY";

    public void setDefaultUser(String userCode) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putString(DEFAULT_USER_KEY, userCode);
        editor.commit();

    }

    public String getDefaultUser() {
        return perference.getString(DEFAULT_USER_KEY, "0");
    }

    //用户类型，用户扫描
    private static final String USER_TYPE = "USER_TYPE";

    public void setUserType(int type) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putInt(USER_TYPE, type);
        editor.commit();
    }
    public int getUserType() {
        return perference.getInt(USER_TYPE, 0);
    }


    private static final String USER_BIND = "USER_BIND";
    public void setUserBind(boolean type) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putBoolean(USER_BIND, type);
        editor.commit();
    }
    public boolean getUserBind() {
        return perference.getBoolean(USER_BIND, false);
    }







    public Set<String> getUnlockModels(String userCode) {
        SharedPreferences.Editor editor = perference.edit();

        String key = CHNWORD_UNLOCK_USER + "_" + userCode;

        Set<String> values;

        values = perference.getStringSet(key, new HashSet<String>());

//        List<String> lists = new ArrayList<String>();
//        for (String str : values) {
//            lists.add(str);
//        }
        return values;
    }



    //
    private static final String SHAREDWORDLIST_KEY = "SHAREDWORDLIST_KEY";

    public void setSharedwordlist(String userCode) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putString(SHAREDWORDLIST_KEY, userCode);
        editor.commit();

    }











    //-------------------default module and word

    public void setDefaultModule(List<Category> list) {
        Set<String> set = new HashSet<String>();

        for (Category category : list ) {
            set.add(category.getName() + ":" + category.getCname());
        }
        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(WORDDEFAULTKEY, set);
        editor.commit();

    }

    public List<Category> getDefaultModule() {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(WORDDEFAULTKEY, set);
        List<Category> lists = new ArrayList<Category>();
        for (String str : modules) {
            String[] strs = str.split(":");
            if (strs.length ==2) {
                Category m = new Category();
                m.setName(strs[0]);
                m.setCname(strs[1]);
            }
        }
        setDefaultModule(lists);
        return lists;
    }





}
