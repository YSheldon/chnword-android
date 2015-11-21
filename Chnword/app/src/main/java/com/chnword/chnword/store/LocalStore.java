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
    private static final String SHOULD_BIND_IPHONE = "SHOULD_BIND_IPHONE";


    private SharedPreferences perference;


    public LocalStore(Context context) {
        perference = context.getSharedPreferences("LocalStore", Context.MODE_MULTI_PROCESS);

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


    //==============================================================================================
    //===========================   用户的category的分享的添加 ========================================
    //==============================================================================================

    public boolean isLock(String category) {
        boolean flag = false;
        flag = perference.getBoolean(category, false);
        return flag;
    }

    public void unLockCategory(String category) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putBoolean(category, true);
        editor.commit();
    }

    public boolean isWordShared(String word) {
        boolean flag = false;
        flag = perference.getBoolean(word, false);
        return flag;
    }

    public void setWordShared(String word) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putBoolean(word, true);
        editor.commit();
    }

    public void setCategory(List<String> list) {
        Set<String> set = new HashSet<String>();
        for (String category : list ) {
            set.add(category);
        }
        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(WORDDEFAULTKEY, set);
        editor.commit();
    }

    private static final String CategorySetKey = "CategorySetKey";

    public void unLockNextCategory() {
        Set<String> set = perference.getStringSet(CategorySetKey, new HashSet<String>());

        for (String str : set) {
            if (isLock(str)) {
                unLockCategory(str);
                break;
            }
        }
    }

    //==============================================================================================
    //=============================             BIND PHONE          ================================
    //==============================================================================================

    public  void setShouldBindIphoneNumber(boolean shouldBindIphoneNumber) {
        SharedPreferences.Editor editor = perference.edit();
        editor.putBoolean(SHOULD_BIND_IPHONE, shouldBindIphoneNumber);
        editor.commit();
    }

    public boolean getShouldBindIphoneNumber() {
        return perference.getBoolean(SHOULD_BIND_IPHONE, false);
    }
}
