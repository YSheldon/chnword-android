package com.chnword.chnword.store;

import android.content.Context;
import android.content.SharedPreferences;

import com.chnword.chnword.beans.Module;
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
        return perference.getString(DEFAULT_USER_KEY, "NULL");
    }

    public void removeDefaultUser() {
        SharedPreferences.Editor editor = perference.edit();
//        editor.putString(DEFAULT_USER_KEY, userCode);
        editor.remove(DEFAULT_USER_KEY);
        editor.commit();
    }


    public void setUnlockModels(String userCode, List<String> lists) {
        SharedPreferences.Editor editor = perference.edit();
        String key = CHNWORD_UNLOCK_USER + "_" + userCode;



        Set<String> values;

        values = perference.getStringSet(key, new HashSet<String>());

        for (String str : lists) {
            if (values.contains(str)) {
                continue;
            }else {
                values.add(str);
            }
        }
        editor.putStringSet(key, values);
        editor.commit();
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

    public void setUnlockAll(String userCode) {
        String key = CHNWORD_UNLOCK_ALL_USER + "_" + userCode;

        SharedPreferences.Editor editor = perference.edit();
        editor.putBoolean(DEFAULT_USER_KEY, true);
        editor.commit();
    }

    public boolean isUnlockAll(String userCode) {
        boolean result = false;

        String key = CHNWORD_UNLOCK_ALL_USER + "_" + userCode;

        result = perference.getBoolean(key, false);

        return result;
    }











    public void addUser(String userCode) {
        Set<String> set = new HashSet<String>();

        Set<String> users = perference.getStringSet(USERNAMEKEY, set);
        users.add(userCode);

        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(WORDDEFAULTKEY, users);
        editor.commit();

    }

    public boolean isContainUser(String userCode) {
        boolean result = false;
        Set<String> names = perference.getStringSet(USERNAMEKEY, null);

        if (names != null || names.contains(userCode)) {
            return true;
        }

        return result;
    }

    //-------------------default module and word

    public void setDefaultModule(List<Module> list) {
        Set<String> set = new HashSet<String>();

        for (Module module : list ) {
            set.add(module.getName() + ":" + module.getCname());
        }
        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(WORDDEFAULTKEY, set);
        editor.commit();

    }

    public List<Module> getDefaultModule() {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(WORDDEFAULTKEY, set);
        List<Module> lists = new ArrayList<Module>();
        for (String str : modules) {
            String[] strs = str.split(":");
            if (strs.length ==2) {
                Module m = new Module();
                m.setName(strs[0]);
                m.setCname(strs[1]);
            }
        }
        setDefaultModule(lists);
        return lists;
    }

    public void setDefaultWord(String moduleName, List<Word> words) {
        Set<String> set = new HashSet<String>();

        for (Word word : words) {
            set.add(word.getWord() + ":" + word.getWord());
        }

        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(moduleName + "_WordDefault", set);
        editor.commit();
    }

    public List<Word> getDefaultWord(String moduleName) {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(moduleName + "_WordDefault", set);
        List<Word> lists = new ArrayList<Word>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Word m = new Word();
            m.setWord(strs[0]);
            m.setWordIndex(strs[1]);
        }
        return lists;
    }


    //--------------------users


    public void setDefaultModule(List<Module> list, String userCode) {
        Set<String> set = new HashSet<String>();

        for (Module module : list ) {
            set.add(module.getName() + ":" + module.getCname());
        }

        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(userCode + "_defaultModule", set);
        editor.commit();

    }

    public List<Module> getDefaultModule(String userCode) {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(userCode + "_defaultModule", set);
        List<Module> lists = new ArrayList<Module>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Module m = new Module();
            m.setName(strs[0]);
            m.setCname(strs[1]);
        }
        return lists;
    }

    public void setDefaultWord(String userCode, String moduleName, List<Word> words) {
        Set<String> set = new HashSet<String>();

        for (Word word : words) {
            set.add(word.getWord() + ":" + word.getWord());
        }
        SharedPreferences.Editor editor = perference.edit();
        editor.putStringSet(userCode + "_" + moduleName + "_WordDefault", set);
        editor.commit();
    }

    public List<Word> getDefaultWord(String userCode, String moduleName) {
        Set<String> set = new HashSet<String>();
        Set<String> modules = perference.getStringSet(userCode + "_" + moduleName + "_WordDefault", set);
        List<Word> lists = new ArrayList<Word>();
        for (String str : modules) {
            String[] strs = str.split(":");
            Word m = new Word();
            m.setWord(strs[0]);
            m.setWordIndex(strs[1]);
        }
        return lists;
    }

}
